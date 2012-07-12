package nl.runnable.lims.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "role")
public class Role extends AbstractEntity implements Serializable {

	private static final long serialVersionUID = -6599700233313699757L;

	@Column(name = "name", unique = true, nullable = false)
	private String name;

	public Role(final String name) {
		this.name = name;
	}

	protected Role() {
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

}
