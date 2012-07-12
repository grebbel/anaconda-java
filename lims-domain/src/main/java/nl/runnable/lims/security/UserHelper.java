package nl.runnable.lims.security;

import javax.annotation.ManagedBean;

import nl.runnable.lims.domain.User;

import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Helper for {@link User}s.
 * 
 * @author Laurens Fridael
 * 
 */
@ManagedBean
public class UserHelper {

	/**
	 * Obtains the User that is currently logged in;
	 * 
	 * @return The current {@link User} or null if no User has been logged in.
	 */
	public User getCurrentUser() {
		User user = null;
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication instanceof RememberMeAuthenticationToken) {
			user = ((CustomUserDetails) authentication.getPrincipal()).getUser();
		} else if (authentication != null) {
			user = (User) authentication.getPrincipal();
		}
		return user;
	}

}
