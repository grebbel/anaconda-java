package nl.runnable.lims.domain;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class AbstractType extends AbstractEntity {

	@Column(name = "name", nullable = false, unique = true)
	private String name;

	@Column(name = "description")
	private String description;

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

}
