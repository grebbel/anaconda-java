package nl.runnable.lims.security;

import javax.inject.Inject;

import nl.runnable.lims.domain.Password;
import nl.runnable.lims.domain.User;
import nl.runnable.lims.domain.UserService;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * {@link AuthenticationProvider} that provides {@link UserAuthentication}s. This implementation relies on a
 * {@link UserService}.
 * 
 * @author Laurens Fridael
 * @see UserService#login(String, nl.runnable.lims.domain.Password)
 */
@Transactional
public class UserAuthenticationProvider extends AbstractAuthenticationService implements AuthenticationProvider {

	/* Dependencies */

	@Inject
	private UserService userService;

	/* Operations */

	@Override
	public boolean supports(final Class<?> clazz) {
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(clazz);
	}

	@Override
	public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
		final String email = authentication.getName();
		if (StringUtils.hasText(email) == false) {
			throw new BadCredentialsException("Email is required.");
		}
		final Password password;
		if (authentication.getCredentials() instanceof Password) {
			password = (Password) authentication.getCredentials();
		} else {
			final String credentials = authentication.getCredentials().toString();
			if (StringUtils.hasText(credentials) == false) {
				throw new BadCredentialsException("Password is required.");
			}
			password = Password.forLiteral(credentials);
		}
		final User user = login(email, password);
		return new UserAuthentication(user, createGrantedAuthorities(user.getRoles()));
	}

	/* Utility operations */

	private User login(final String email, final Password password) {
		final User user = userService.login(email, password);
		if (user == null) {
			throw new BadCredentialsException(String.format("Invalid username or password.", email));
		}
		return user;
	}

}