package nl.runnable.lims.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "target")
@EntityListeners(TargetListener.class)
public class Target extends AbstractTaggableEntity implements Describable {

	@Column(name = "name", nullable = false)
	private String name;

	@OneToOne
	@JoinColumn(name = "target_type_id")
	private TargetType targetType;

	@Column(name = "description")
	private String description;

	@ElementCollection(fetch = FetchType.LAZY)
	@JoinTable(name = "target_code", joinColumns = @JoinColumn(name = "target_id"))
	@Column(name = "code", nullable = false, unique = true)
	private Set<String> codes = new HashSet<String>();

	@OneToOne
	@JoinColumn(name = "color_id")
	private Color color;

	public Target(final String name) {
		setName(name);
	}

	public Target() {
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public TargetType getTargetType() {
		return targetType;
	}

	public void setTargetType(final TargetType targetType) {
		this.targetType = targetType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public void setCodes(final Set<String> codes) {
		this.codes = codes;
	}

	public Set<String> getCodes() {
		return codes;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(final Color color) {
		this.color = color;
	}

	@Override
	public String describe() {
		return getName();
	}

}
