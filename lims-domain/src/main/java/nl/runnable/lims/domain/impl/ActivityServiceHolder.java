package nl.runnable.lims.domain.impl;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

import nl.runnable.lims.domain.ActivityService;

/**
 * Provides access to an {@link ActivityService} for classes that do not use dependency injection.
 * 
 * @author Laurens Fridael
 * 
 */
@ManagedBean
public class ActivityServiceHolder {

	private static ActivityService singletonActivityService;

	public static ActivityService getActivityService() {
		return singletonActivityService;
	}

	@Inject
	private ActivityService activityService;

	@PostConstruct
	public void setSingletonActivityService() {
		singletonActivityService = activityService;
	}

}
