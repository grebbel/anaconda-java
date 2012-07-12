package nl.runnable.lims.webapp.api;

import nl.runnable.lims.domain.User;

class UserItem {

	private final User user;

	UserItem(final User user) {
		this.user = user;
	}

	public String getName() {
		return user.getDisplayName();
	}

	public Long getId() {
		return user.getId();
	}

}
