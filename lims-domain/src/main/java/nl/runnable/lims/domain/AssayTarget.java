package nl.runnable.lims.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "assay_target")
public class AssayTarget extends AbstractEntity {

	@ManyToOne
	@JoinColumn(name = "assay_id", nullable = false)
	private Assay assay;

	@OneToOne
	@JoinColumn(name = "target_id", nullable = false)
	private Target target;

	@Column(name = "treshold")
	private double treshold;

	public Assay getAssay() {
		return assay;
	}

	public void setAssay(final Assay assay) {
		this.assay = assay;
	}

	public Target getTarget() {
		return target;
	}

	public void setTarget(final Target target) {
		this.target = target;
	}

	public double getTreshold() {
		return treshold;
	}

	public void setTreshold(final double treshold) {
		this.treshold = treshold;
	}

}
