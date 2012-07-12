package nl.runnable.lims.rijnstate;

import javax.annotation.ManagedBean;
import javax.inject.Inject;

import nl.runnable.lims.domain.User;
import nl.runnable.lims.security.UserHelper;

@ManagedBean
public class WorkflowHelper {

	@Inject
	private UserHelper userHelper;

	@Inject
	private UserIdentityHelper userIdentityHelper;

	public String getCurrentUserId() {
		final User currentUser = userHelper.getCurrentUser();
		if (currentUser != null) {
			return userIdentityHelper.getActivitiUserId(currentUser);
		} else {
			return null;
		}
	}
}
