package nl.runnable.lims.rijnstate;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import nl.runnable.lims.domain.Request;
import nl.runnable.lims.domain.RequestRepository;
import nl.runnable.lims.domain.RequestStatus;

import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.Deployment;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class RequestProcessTest {

	private static final String USERNAME = "piet";

	/* Dependencies */

	@Inject
	private RuntimeService runtimeService;

	@Inject
	private IdentityService identityService;

	@Inject
	private TaskService taskService;

	@Inject
	@Rule
	public ActivitiRule activitiSpringRule;

	@Inject
	private RequestRepository requestRepository;

	/* Setup */

	@Before
	public void setupIdentities() {
		identityService.saveUser(identityService.newUser(USERNAME));
		identityService.createMembership(USERNAME, Groups.TECHNICIAN);
	}

	@Before
	public void createRequest() {
		final Request request = new Request();
		request.setExternalId("12345");
		request.setDescription("Some request");
		requestRepository.save(request);
	}

	/* Tests */

	@Test
	@Deployment(resources = "processes/request.bpmn20.xml")
	@Transactional
	public void testProcess() {
		final Request request = requestRepository.findAll().get(0);

		final ProcessInstance process = startProcess(request);
		assertEquals(1, activeProcessCount());

		assertEquals(UserTasks.INTAKE, runtimeService.getActiveActivityIds(process.getId()).get(0));
		testIntakeTask(request, RequestStatus.ACCEPTED);

		assertEquals(1, activeProcessCount());
		assertEquals(UserTasks.ORGANIZE_ANALYSES, runtimeService.getActiveActivityIds(process.getId()).get(0));
	}

	private ProcessInstance startProcess(final Request request) {
		final Map<String, Object> variables = new HashMap<String, Object>();
		variables.put(ProcessVariables.REQUEST, request);
		return runtimeService.startProcessInstanceByKey(ProcessKeys.REQUEST, variables);
	}

	private void testIntakeTask(final Request request, final RequestStatus status) {
		assertEquals(1, taskCountForCandidateGroup(Groups.TECHNICIAN));
		final List<Task> tasks = taskService.createTaskQuery().taskCandidateUser(USERNAME).list();
		final Task intakeTask = tasks.get(0);
		taskService.claim(intakeTask.getId(), USERNAME);
		assertEquals(0, taskCountForCandidateGroup(Groups.TECHNICIAN));
		request.setStatus(status);
		taskService.complete(intakeTask.getId());
	}

	/* Utility operations */

	private long taskCountForCandidateGroup(final String group) {
		return taskService.createTaskQuery().taskCandidateGroup(group).count();
	}

	private long activeProcessCount() {
		return runtimeService.createProcessInstanceQuery().active().count();
	}
}
