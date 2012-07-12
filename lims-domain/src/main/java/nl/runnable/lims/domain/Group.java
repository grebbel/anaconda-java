package nl.runnable.lims.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "groups")
public class Group extends AbstractEntity implements Serializable {

	private static final long serialVersionUID = -6599700233313699757L;

	@Column(name = "name", unique = true, nullable = false)
	private String name;

	public Group(final String name) {
		this.name = name;
	}

	protected Group() {
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

}
