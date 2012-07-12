package nl.runnable.lims.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.springframework.util.StringUtils;

@Embeddable
public class PersonName implements Comparable<PersonName>, Serializable {

	private static final long serialVersionUID = 3151053744168770328L;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "prefix")
	private String prefix;

	@Column(name = "last_name")
	private String lastName;

	public PersonName(final String firstName, final String prefix, final String lastName) {
		setFirstName(firstName);
		setPrefix(prefix);
		setLastName(lastName);
	}

	public PersonName(final String firstName, final String lastName) {
		this(firstName, null, lastName);
	}

	public PersonName() {
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(final String firstName) {
		this.firstName = firstName;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(final String prefix) {
		this.prefix = prefix;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(final String lastName) {
		this.lastName = lastName;
	}

	public boolean isEmpty() {
		return !(StringUtils.hasText(firstName) || StringUtils.hasLength(lastName));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((prefix == null) ? 0 : prefix.hashCode());
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
		final PersonName other = (PersonName) obj;
		if (firstName == null) {
			if (other.firstName != null) {
				return false;
			}
		} else if (!firstName.equals(other.firstName)) {
			return false;
		}
		if (lastName == null) {
			if (other.lastName != null) {
				return false;
			}
		} else if (!lastName.equals(other.lastName)) {
			return false;
		}
		if (prefix == null) {
			if (other.prefix != null) {
				return false;
			}
		} else if (!prefix.equals(other.prefix)) {
			return false;
		}
		return true;
	}

	@Override
	public int compareTo(final PersonName other) {
		int compare = 0;
		if (other != null) {
			compare = compareNames(this.lastName, other.lastName);
			if (compare == 0) {
				compare = compareNames(this.firstName, other.firstName);
			}
			if (compare == 0) {
				compare = compareNames(this.prefix, other.prefix);
			}
		} else {
			compare = 1;
		}
		return compare;
	}

	private static int compareNames(final String a, final String b) {
		int compare = 0;
		if (a != null) {
			compare = a.compareToIgnoreCase(b);
		} else if (b != null) {
			compare = 1;
		}
		return compare;
	}

	@Override
	public String toString() {
		if (prefix != null) {
			return String.format("%s %s %s", firstName, prefix, lastName);
		} else {
			return String.format("%s %s", firstName, lastName);
		}
	}

}
