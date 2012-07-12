package nl.runnable.lims.security;

import java.util.ArrayList;
import java.util.Collection;

import nl.runnable.lims.domain.User;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

public class UserAuthentication implements Authentication {

	private static final long serialVersionUID = 2590965176261544919L;

	private final User user;

	private final ArrayList<GrantedAuthority> grantedAuthorities;

	public UserAuthentication(final User user, final Collection<GrantedAuthority> grantedAuthorities) {
		this.user = user;
		this.grantedAuthorities = new ArrayList<GrantedAuthority>(grantedAuthorities);
	}

	public User getUser() {
		return user;
	}

	/* Authentication implementation. */

	@Override
	public String getName() {
		return user.getEmail();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return grantedAuthorities;
	}

	@Override
	public Object getPrincipal() {
		return user;
	}

	@Override
	public Object getCredentials() {
		return user.getPassword();
	}

	@Override
	public Object getDetails() {
		// Not used.
		return null;
	}

	@Override
	public boolean isAuthenticated() {
		return true;
	}

	@Override
	public void setAuthenticated(final boolean isAuthenticated) throws IllegalArgumentException {
		throw new IllegalArgumentException("Cannot change authenticated state.");
	}

}
