package nl.runnable.lims.webapp.api;

import java.util.List;

import javax.inject.Inject;

import nl.runnable.lims.domain.Group;
import nl.runnable.lims.domain.GroupRepository;
import nl.runnable.lims.domain.Task;
import nl.runnable.lims.domain.WorkflowService;
import nl.runnable.lims.security.UserHelper;
import nl.runnable.lims.webapp.AbstractController;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/tasks")
public class TaskController extends AbstractController {

	/* Dependencies */

	@Inject
	private WorkflowService workflowService;

	@Inject
	private UserHelper userHelper;

	@Inject
	private GroupRepository groupRepository;

	/* Operations */

	@RequestMapping("/current-user/assigned")
	@ResponseBody
	public List<Task> showCurrentUserAssignedTasks() {
		return workflowService.getAssignedTasks(userHelper.getCurrentUser());
	}

	@RequestMapping("/current-user/candidate")
	@ResponseBody
	public List<Task> showCurrentUserCandidateTasks() {
		return workflowService.getCandidateTasks(userHelper.getCurrentUser());
	}

	@RequestMapping("/unassigned")
	@ResponseBody
	public List<Task> showUnassignedTasks() {
		return workflowService.getUnassignedTasks();
	}

	@RequestMapping("/groups/{id}/candidate")
	@ResponseBody
	public List<Task> showGroupCandidateTasks(@PathVariable final Long id) {
		final Group group = groupRepository.findOne(id);
		if (group == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return workflowService.getCandidateTasks(group);
	}

}
