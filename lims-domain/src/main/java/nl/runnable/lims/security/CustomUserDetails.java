package nl.runnable.lims.security;

import java.util.ArrayList;
import java.util.Collection;

import nl.runnable.lims.domain.User;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

class CustomUserDetails implements UserDetails {

	private static final long serialVersionUID = 7193333511149712196L;

	private final User user;

	private final ArrayList<GrantedAuthority> grantedAuthorities;

	CustomUserDetails(final User user, final Collection<GrantedAuthority> grantedAuthorities) {
		this.user = user;
		this.grantedAuthorities = new ArrayList<GrantedAuthority>(grantedAuthorities);
	}

	public User getUser() {
		return user;
	}

	/* CustomUserDetails implementation. */

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return grantedAuthorities;
	}

	@Override
	public String getUsername() {
		return user.getEmail();
	}

	@Override
	public String getPassword() {
		return user.getPassword().getHash();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
