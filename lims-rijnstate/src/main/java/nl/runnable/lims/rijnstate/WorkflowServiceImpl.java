package nl.runnable.lims.rijnstate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.ManagedBean;
import javax.inject.Inject;

import nl.runnable.lims.domain.Group;
import nl.runnable.lims.domain.Request;
import nl.runnable.lims.domain.RequestRepository;
import nl.runnable.lims.domain.Task;
import nl.runnable.lims.domain.User;
import nl.runnable.lims.domain.WorkflowService;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

@ManagedBean
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class WorkflowServiceImpl implements WorkflowService {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	/* Dependencies */

	@Inject
	private RuntimeService runtimeService;

	@Inject
	private TaskService taskService;

	@Inject
	private UserIdentityHelper userIdentityHelper;

	@Inject
	private RequestRepository requestRepository;

	/* Operations */

	@Override
	public void startRequestWorkflow(final Request request) {
		Assert.notNull(request, "Request cannot be null.");
		Assert.state(StringUtils.hasText(request.getWorkflowId()) == false,
				"Request is already assigned to a workflow.");

		final HashMap<String, Object> variables = new HashMap<String, Object>();
		variables.put(ProcessVariables.REQUEST, request);
		final ProcessInstance processInstance = runtimeService
				.startProcessInstanceByKey(ProcessKeys.REQUEST, variables);
		request.setWorkflowId(processInstance.getProcessInstanceId());
	}

	@Override
	public void cancelRequestWorkflow(final Request request) {
		Assert.notNull(request, "Request cannot be null.");

		if (StringUtils.hasText(request.getWorkflowId()) == false) {
			return;
		}
		final ProcessInstance processInstance = getProcessInstance(request);
		if (processInstance != null) {
			runtimeService.deleteProcessInstance(processInstance.getId(), "Cancelled.");
		}
	}

	@Override
	public List<Task> getAssignedTasks(final User user) {
		Assert.notNull(user, "User cannot be null.");

		return createTasks(taskService.createTaskQuery().taskAssignee(userIdentityHelper.getActivitiUserId(user))
				.list());
	}

	@Override
	public List<Task> getCandidateTasks(final User user) {
		Assert.notNull(user, "User cannot be null.");

		return createTasks(taskService.createTaskQuery().taskCandidateUser(userIdentityHelper.getActivitiUserId(user))
				.list());
	}

	@Override
	public List<Task> getUnassignedTasks() {
		return createTasks(taskService.createTaskQuery().taskUnnassigned().list());
	}

	@Override
	public List<Task> getCandidateTasks(final Group group) {
		Assert.notNull(group, "Group cannot be null.");
		return createTasks(taskService.createTaskQuery().taskCandidateGroup(group.getName()).list());
	}

	/* Utility operations */

	private List<Task> createTasks(final List<org.activiti.engine.task.Task> activitiTasks) {
		final List<Task> tasks = new ArrayList<Task>(activitiTasks.size());
		for (final org.activiti.engine.task.Task activitiTask : activitiTasks) {
			final Task task = createTask(activitiTask);
			if (task != null) {
				tasks.add(task);
			}
		}
		return tasks;
	}

	private Task createTask(final org.activiti.engine.task.Task activitiTask) {
		final Request request = requestRepository.findByWorkflowId(activitiTask.getProcessInstanceId());
		if (request != null) {
			final Task task = new Task();
			if (StringUtils.hasText(activitiTask.getAssignee())) {
				task.setAssignee(userIdentityHelper.getUser(activitiTask.getAssignee()));
			}
			task.setDescription(activitiTask.getTaskDefinitionKey());
			task.setResource(request);
			return task;
		} else {
			if (logger.isWarnEnabled()) {
				logger.warn("Cannot find Request for process instance '{}'.", activitiTask.getProcessInstanceId());
			}
			return null;
		}
	}

	private ProcessInstance getProcessInstance(final Request request) {
		return getProcessInstance(request.getWorkflowId());
	}

	private ProcessInstance getProcessInstance(final String processInstanceId) {
		final ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
				.processInstanceId(processInstanceId).singleResult();
		if (processInstance == null) {
			if (logger.isWarnEnabled()) {
				logger.warn("Cannot find process instance '{}'.", processInstanceId);
			}
		}
		return processInstance;
	}

}
