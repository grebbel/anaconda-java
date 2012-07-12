package nl.runnable.lims.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * Represents an application user.
 * <p>
 * Note: this class does not derive from {@link AbstractEntity} due to the requirement for being {@link Serializable}.
 * 
 * @author Laurens Fridael
 * 
 */
@Entity
@Table(name = "member")
public class User implements Serializable {

	private static final long serialVersionUID = -8272079787910306556L;

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;

	@Column(name = "email", nullable = false, unique = true)
	private String email;

	private Password password;

	@Column(name = "person")
	private PersonName name = new PersonName();

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "member_role", joinColumns = @JoinColumn(name = "member_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<Role>();

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "member_group", joinColumns = @JoinColumn(name = "member_id"), inverseJoinColumns = @JoinColumn(name = "group_id"))
	private Set<Group> groups = new HashSet<Group>();

	public User(final String email, final Password password, final Set<Role> roles, final Set<Group> groups) {
		setEmail(email);
		setPassword(password);
		setName(new PersonName());
		getRoles().addAll(roles);
		getGroups().addAll(groups);
	}

	protected User() {
	}

	public Long getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	@JsonIgnore
	public Password getPassword() {
		return password;
	}

	public void setPassword(final Password password) {
		this.password = password;
	}

	public PersonName getName() {
		return name;
	}

	public void setName(final PersonName name) {
		this.name = name;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	protected void setRoles(final Set<Role> roles) {
		this.roles = roles;
	}

	public Set<Group> getGroups() {
		return groups;
	}

	protected void setGroups(final Set<Group> groups) {
		this.groups = groups;
	}

	public String getDisplayName() {
		if (name.isEmpty()) {
			return email;
		} else {
			return name.toString();
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? super.hashCode() : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final User other = (User) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			} else {
				return super.equals(obj);
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}

}
