package nl.runnable.lims.domain.impl;

import java.util.List;

import javax.annotation.ManagedBean;
import javax.inject.Inject;

import nl.runnable.lims.domain.AbstractEntity;
import nl.runnable.lims.domain.Activity;
import nl.runnable.lims.domain.ActivityRepository;
import nl.runnable.lims.domain.ActivityService;
import nl.runnable.lims.domain.Request;
import nl.runnable.lims.domain.ResourceLink;
import nl.runnable.lims.domain.Tag;
import nl.runnable.lims.domain.Target;
import nl.runnable.lims.domain.User;
import nl.runnable.lims.domain.UserService;
import nl.runnable.lims.security.UserHelper;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@ManagedBean
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class DefaultActivityService implements ActivityService {

	/* Dependencies */

	@Inject
	private ActivityRepository activityRepository;

	@Inject
	private UserHelper userHelper;

	@Inject
	private UserService userService;

	/* Operations */

	@Override
	public void registerRequestSubmitted(final Request request) {
		registerMajorActivity(ActivityMessageKeys.REQUEST_SUBMITTED, request);
	}

	@Override
	public void registerRequestUpdated(final Request request) {
		registerMinorActivity(ActivityMessageKeys.REQUEST_UPDATED, request);
	}

	@Override
	public void registerRequestRemoved(final Request request) {
		deleteActivityRelatedTo(request);
	}

	@Override
	public void registerTargetCreated(final Target target) {
		registerNormalActivity(ActivityMessageKeys.TARGET_CREATED, target);
	}

	@Override
	public void registerTargetRemoved(final Target target) {
		deleteActivityRelatedTo(target);
	}

	@Override
	public void registerTargetUpdated(final Target target) {
		registerMinorActivity(ActivityMessageKeys.TARGET_UPDATED, target);
	}

	@Override
	public void registerTagCreated(final Tag tag) {
		registerMinorActivity(ActivityMessageKeys.TAG_CREATED, tag);
	}

	@Override
	public void registerTagRemoved(final Tag tag) {
		deleteActivityRelatedTo(tag);
	}

	/* Utility operations */

	protected void registerMinorActivity(final String messageKey, final AbstractEntity entity) {
		activityRepository.save(new Activity(getCurrentUser(), messageKey, ResourceLink.forEntity(entity),
				Activity.MINOR_IMPORTANCE));
	}

	protected void registerMajorActivity(final String messageKey, final AbstractEntity entity) {
		activityRepository.save(new Activity(getCurrentUser(), messageKey, ResourceLink.forEntity(entity),
				Activity.MAJOR_IMPORTANCE));
	}

	protected void registerNormalActivity(final String messageKey, final AbstractEntity entity) {
		activityRepository.save(new Activity(getCurrentUser(), messageKey, ResourceLink.forEntity(entity),
				Activity.NORMAL_IMPORTANCE));
	}

	protected void registerUnimportantActivity(final String messageKey, final AbstractEntity entity) {
		activityRepository.save(new Activity(getCurrentUser(), messageKey, ResourceLink.forEntity(entity),
				Activity.UNIMPORTANT));
	}

	protected void registerHighlyImportantActivity(final String messageKey, final AbstractEntity entity) {
		activityRepository.save(new Activity(getCurrentUser(), messageKey, ResourceLink.forEntity(entity),
				Activity.HIGH_IMPORTANCE));
	}

	protected void deleteActivityRelatedTo(final AbstractEntity entity) {
		final ResourceLink resourceLink = ResourceLink.forEntity(entity);
		final List<Activity> activities = activityRepository.findByResourceLink(resourceLink.getResourceType(),
				resourceLink.getResourceId());
		activityRepository.delete(activities);
	}

	private User getCurrentUser() {
		User currentUser = userHelper.getCurrentUser();
		if (currentUser != null) {
			currentUser = userService.getUser(currentUser.getEmail());
		}
		return currentUser;
	}

}
