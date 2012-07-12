package nl.runnable.lims.webapp.api;

import java.util.Date;

import nl.runnable.lims.domain.Task;

class TaskItem {

	private final Task task;

	private UserItem assignee;

	TaskItem(final Task task) {
		this.task = task;
	}

	public UserItem getAssignee() {
		if (assignee == null && task.getAssignee() != null) {
			assignee = new UserItem(task.getAssignee());
		}
		return assignee;
	}

	public String getTemplateName() {
		return "task." + task.getDescription();
	}

	public Date getDueDate() {
		return task.getDueDate();
	}

	public Object getResource() {
		return task.getResource();
	}

}
