package nl.runnable.lims.webapp.api;

import java.util.Date;

import javax.persistence.EntityManager;

import nl.runnable.lims.domain.Activity;
import nl.runnable.lims.domain.Describable;
import nl.runnable.lims.domain.ResourceLink;

/**
 * Represents output from {@link ActivityController}.
 * 
 * @author Laurens Fridael
 * 
 */
class ActivityItem {

	private final Activity activity;

	private UserItem userItem;

	private ResourceItem resourceItem;

	private final EntityManager entityManager;

	ActivityItem(final Activity activity, final EntityManager entityManager) {
		this.activity = activity;
		this.entityManager = entityManager;
	}

	public Date getDate() {
		return activity.getDate();
	}

	public String getMessage() {
		return activity.getMessage();
	}

	public UserItem getUser() {
		if (userItem == null && activity.getUser() != null) {
			userItem = new UserItem(activity.getUser());
		}
		return userItem;
	}

	public ResourceItem getResource() {
		if (resourceItem == null && activity.getResourceLink() != null) {
			resourceItem = new ResourceItem(activity.getResourceLink());
		}
		return resourceItem;
	}

	/* Utility classes */

	private class ResourceItem {

		private final ResourceLink resourceLink;

		private ResourceItem(final ResourceLink resourceLink) {
			this.resourceLink = resourceLink;
		}

		public Long getId() {
			return resourceLink.getResourceId();
		}

		@SuppressWarnings("unused")
		public String getDescription() {
			if (getId() != null) {
				Object entity;
				try {
					entity = entityManager.find(Class.forName(activity.getResourceLink().getResourceType()), getId());
					if (entity != null) {
						if (entity instanceof Describable) {
							return ((Describable) entity).describe();
						} else {
							return entity.toString();
						}
					} else {
						return "<Deleted>";
					}
				} catch (final ClassNotFoundException e) {
					return null;
				}
			} else {
				return null;
			}
		}
	}

}
