package nl.runnable.lims.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "assay")
public class Assay extends AbstractEntity {

	@Column(name = "name")
	private String name;

	@Column(name = "label")
	private String label;

	@OneToMany(mappedBy = "assay", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<AssayTarget> assayTargets = new ArrayList<AssayTarget>();

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(final String label) {
		this.label = label;
	}

	protected List<AssayTarget> getAssayTargets() {
		return assayTargets;
	}

	protected void setAssayTargets(final List<AssayTarget> assayTargets) {
		this.assayTargets = assayTargets;
	}

	public void addAssayTarget(final AssayTarget assayTarget) {
		getAssayTargets().add(assayTarget);
		assayTarget.setAssay(this);
	}

	public void removeAssayTarget(final AssayTarget assayTarget) {
		final List<AssayTarget> assayTargets = getAssayTargets();
		if (assayTargets.contains(assayTarget)) {
			if (assayTarget.getAssay() == this) {
				assayTarget.setAssay(null);
			}
			assayTargets.remove(assayTarget);
		}
	}

}
