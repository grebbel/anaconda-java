package nl.runnable.lims.domain;

import java.util.List;

/**
 * Defines workflow operations.
 * 
 * @author Laurens Fridael
 * 
 */
public interface WorkflowService {

	/**
	 * Starts a new workflow for the given {@link Request}.
	 * 
	 * @param request
	 * @throws IllegalStateException
	 *             If the given {@link Request} is already in a workflow.
	 */
	public void startRequestWorkflow(Request request);

	/**
	 * Cancels a request workflow. Implementations should clean up the flow state.
	 * 
	 * @param request
	 */
	public void cancelRequestWorkflow(Request request);

	/**
	 * Obtains the {@link Task}s currently assigned to the given {@link User}.
	 * 
	 * @param user
	 * @return
	 */
	public List<Task> getAssignedTasks(User user);

	/**
	 * Obtains candidate tasks for the given {@link User}.
	 * <p>
	 * Candidate tasks are tasks that could potentially be assigned to this user.
	 * 
	 * @param user
	 * @return
	 */
	List<Task> getCandidateTasks(User user);

	/**
	 * Obtains candidate tasks for the given {@link Group}.
	 * 
	 * @return
	 */
	List<Task> getUnassignedTasks();

	/**
	 * Obtains candidate tasks for the given {@link Group}.
	 * 
	 * @return
	 */
	List<Task> getCandidateTasks(Group group);

}
