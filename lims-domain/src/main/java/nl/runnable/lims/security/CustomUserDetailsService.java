package nl.runnable.lims.security;

import javax.inject.Inject;

import nl.runnable.lims.domain.User;
import nl.runnable.lims.domain.UserService;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class CustomUserDetailsService extends AbstractAuthenticationService implements UserDetailsService {

	/* Dependencies */

	@Inject
	private UserService userService;

	/* Operations */

	@Override
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
		final User user = userService.getUser(username);
		if (user == null) {
			throw new UsernameNotFoundException(String.format("User '%s' not found.", username));
		}
		return new CustomUserDetails(user, createGrantedAuthorities(user.getRoles()));
	}

}
