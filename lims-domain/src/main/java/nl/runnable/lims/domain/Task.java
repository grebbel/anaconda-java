package nl.runnable.lims.domain;

import java.util.Date;

public class Task {

	private User assignee;

	private String description;

	private Date dueDate;

	private Object resource;

	public User getAssignee() {
		return assignee;
	}

	public void setAssignee(final User assignee) {
		this.assignee = assignee;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String message) {
		this.description = message;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(final Date dueDate) {
		this.dueDate = dueDate;
	}

	public Object getResource() {
		return resource;
	}

	public void setResource(final Object resource) {
		this.resource = resource;
	}

}
