package nl.runnable.lims.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import nl.runnable.lims.domain.Role;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.StringUtils;

public class AbstractAuthenticationService {

	/* Configuration */

	private String rolePrefix = "ROLE_";

	private boolean uppercaseRoleNames = true;

	/* Operations */

	protected Collection<GrantedAuthority> createGrantedAuthorities(final Set<Role> roles) {
		final List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>(roles.size());
		for (final Role role : roles) {
			String roleName = StringUtils.hasLength(rolePrefix) ? String.format("%s%s", rolePrefix, role.getName())
					: role.getName();
			if (uppercaseRoleNames) {
				roleName = roleName.toUpperCase();
			}
			grantedAuthorities.add(new SimpleGrantedAuthority(roleName));
		}
		return grantedAuthorities;
	}

	/* Configuration */

	public void setRolePrefix(final String rolePrefix) {
		this.rolePrefix = rolePrefix;
	}

	protected String getRolePrefix() {
		return rolePrefix;
	}

	public void setUppercaseRoleNames(final boolean uppercaseRoleNames) {
		this.uppercaseRoleNames = uppercaseRoleNames;
	}

	protected boolean isUppercaseRoleNames() {
		return uppercaseRoleNames;
	}

}
