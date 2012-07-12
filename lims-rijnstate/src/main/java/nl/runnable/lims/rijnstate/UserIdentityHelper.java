package nl.runnable.lims.rijnstate;

import javax.annotation.ManagedBean;
import javax.inject.Inject;

import nl.runnable.lims.domain.Group;
import nl.runnable.lims.domain.GroupRepository;
import nl.runnable.lims.domain.User;
import nl.runnable.lims.domain.UserRepository;

import org.activiti.engine.IdentityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Helper for translating between {@link User}s and Activiti workflow identities.
 * 
 * @author Laurens Fridael
 * 
 */
@ManagedBean
public class UserIdentityHelper {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	private IdentityService identityService;

	@Inject
	private UserRepository userRepository;

	@Inject
	private GroupRepository groupRepository;

	/**
	 * Creates groups in the Activiti repository.
	 * <p>
	 * This implementation creates a group for each available {@link Group}.
	 */
	public void createGroups() {
		for (final Group group : groupRepository.findAll()) {
			createGroup(group.getName());
		}
	}

	private void createGroup(final String groupId) {
		if (identityService.createGroupQuery().groupId(groupId).count() == 0) {
			if (logger.isDebugEnabled()) {
				logger.debug("Creating Activiti group '{}'.", groupId);
			}
			identityService.saveGroup(identityService.newGroup(groupId));
		}
	}

	/**
	 * Creates users in the Activiti repository.
	 * <p>
	 * This implementation creates a group for each available {@link User}.
	 */
	public void createUsers() {
		for (final User user : userRepository.findAll()) {
			final String userId = getActivitiUserId(user);
			if (identityService.createUserQuery().userId(userId).count() == 0) {
				final org.activiti.engine.identity.User newUser = identityService.newUser(userId);
				identityService.saveUser(newUser);
			}
		}
	}

	/**
	 * Creates memberships in the Activiti repository.
	 * <p>
	 * This implementation assigns each available group to each available user.
	 */
	public void createMemberships() {
		for (final org.activiti.engine.identity.User activitiUser : identityService.createUserQuery().list()) {
			for (final org.activiti.engine.identity.Group activitiGroup : identityService.createGroupQuery().list()) {
				final String groupId = activitiGroup.getId();
				if (identityService.createUserQuery().userId(activitiUser.getId()).memberOfGroup(groupId).count() == 0) {
					identityService.createMembership(activitiUser.getId(), groupId);
				}
			}
		}
	}

	/**
	 * Obtains the ID of the given {@link User} as identified in the Activiti workflow engine.
	 * <p>
	 * This implementation returns {@link User#getId()} as a String.
	 * 
	 * @param user
	 * @return
	 */
	public String getActivitiUserId(final User user) {
		return String.valueOf(user.getId());
	}

	public User getUser(final String activitiUserId) {
		final long id = Long.parseLong(activitiUserId);
		return userRepository.findOne(id);
	}

}
