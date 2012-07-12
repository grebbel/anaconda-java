package nl.runnable.lims.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "activity")
public class Activity extends AbstractEntity {

	public static final int HIGH_IMPORTANCE = 10;

	public static final int MAJOR_IMPORTANCE = 1;

	public static final int NORMAL_IMPORTANCE = 0;

	public static final int MINOR_IMPORTANCE = -1;

	public static final int UNIMPORTANT = -10;

	@Column(name = "date", nullable = false)
	private Date date = new Date();

	@Column(name = "message", nullable = false)
	private String message;

	@OneToOne
	@JoinColumn(name = "user_id")
	private User user;

	@Column(name = "importance", nullable = false)
	private int importance = NORMAL_IMPORTANCE;

	@Embedded
	private ResourceLink resourceLink;

	public Activity(final User user, final String message) {
		this(user, message, null);
	}

	public Activity(final User user, final String message, final ResourceLink resourceLink, final int importance) {
		setUser(user);
		setMessage(message);
		setResourceLink(resourceLink);
		setImportance(importance);
	}

	public Activity(final User user, final String message, final ResourceLink resourceLink) {
		this(user, message, resourceLink, NORMAL_IMPORTANCE);
	}

	public Activity(final String message) {
		this(null, message, null);
	}

	protected Activity() {
	}

	public Date getDate() {
		return date;
	}

	public void setDate(final Date date) {
		this.date = date;
	}

	public User getUser() {
		return user;
	}

	public void setUser(final User user) {
		this.user = user;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(final String message) {
		this.message = message;
	}

	public ResourceLink getResourceLink() {
		return resourceLink;
	}

	public void setResourceLink(final ResourceLink resourceLink) {
		this.resourceLink = resourceLink;
	}

	public int getImportance() {
		return importance;
	}

	public void setImportance(final int importance) {
		this.importance = importance;
	}

}
