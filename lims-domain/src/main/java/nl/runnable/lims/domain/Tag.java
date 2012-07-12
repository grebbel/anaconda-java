package nl.runnable.lims.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

@Entity
@Table(name = "tag")
@EntityListeners(TagListener.class)
public class Tag extends AbstractType implements Describable {

	@Column(name = "category")
	private String category;

	public Tag(final String name) {
		setName(name);
	}

	public Tag() {
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(final String category) {
		this.category = category;
	}

	@Override
	public String describe() {
		return getName();
	}

}
