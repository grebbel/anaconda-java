package nl.runnable.lims.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import nl.runnable.lims.util.GeometryUtil;
import nl.runnable.lims.util.Point;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.util.Assert;

@Entity
@Table(name = "analysis")
public class Analysis extends AbstractEntity {

	@Column(name = "date")
	private Date date = new Date();

	private Ct ct;

	@Column(name = "treshold", nullable = true)
	private Double treshold;

	@ManyToOne
	@JoinColumn(name = "request_id")
	private Request request;

	@OneToOne
	@JoinColumn(name = "assay_target_id")
	private AssayTarget assayTarget;

	@ElementCollection(fetch = FetchType.LAZY)
	@JoinTable(name = "amplification", joinColumns = @JoinColumn(name = "analysis_id"))
	@OrderBy("cycle asc")
	private List<Amplification> amplifications = new ArrayList<Amplification>();

	@Column(name = "status", nullable = true)
	@Enumerated(EnumType.STRING)
	private AnalysisStatus status;

	@OneToOne
	@JoinColumn(name = "sample_type_id")
	private SampleType sampleType;

	public void setDate(final Date date) {
		this.date = date;
	}

	public Date getDate() {
		return date;
	}

	public Ct getCt() {
		return ct;
	}

	public void setCt(final Ct ct) {
		this.ct = ct;
	}

	public void setTreshold(final Double treshold) {
		this.treshold = treshold;
	}

	public Double getTreshold() {
		if (this.treshold != null) {
			return treshold;
		} else {
			return getAssayTarget().getTreshold();
		}
	}

	@JsonIgnore
	public Request getRequest() {
		return request;
	}

	public String getRequestDescription() {
		final Request request = getRequest();
		if (request != null) {
			return request.getDescription();
		} else {
			return null;
		}
	}

	public String getRequestExternalId() {
		final Request request = getRequest();
		if (request != null) {
			return request.getExternalId();
		} else {
			return null;
		}
	}

	public Long getRequestId() {
		final Request request = getRequest();
		if (request != null) {
			return request.getId();
		} else {
			return null;
		}
	}

	public void setRequest(final Request request) {
		this.request = request;
	}

	public void setAssayTarget(final AssayTarget assayTarget) {
		this.assayTarget = assayTarget;
	}

	public AssayTarget getAssayTarget() {
		return assayTarget;
	}

	public List<Amplification> getAmplifications() {
		return amplifications;
	}

	public void setAmplifications(final List<Amplification> amplifications) {
		this.amplifications = amplifications;
	}

	public void setStatus(final AnalysisStatus status) {
		this.status = status;
	}

	public AnalysisStatus getStatus() {
		if (status != null) {
			return status;
		} else {
			return AnalysisStatus.INDETERMINATE;
		}
	}

	public SampleType getSampleType() {
		return sampleType;
	}

	public void setSampleType(final SampleType sampleType) {
		this.sampleType = sampleType;
	}

	/**
	 * Calculates the CT value using the {@link Target} treshold.
	 * 
	 * @return The CT value or null if none could be calculated.
	 */
	public Ct calculateCt() {
		Assert.state(getAssayTarget() != null,
				"AssayTarget has not been set. Cannot determine treshold for CT calculation without an AssayTarget.");
		return calculateCt(getTreshold());
	}

	/**
	 * Calculates the CT value using a given treshold.
	 * 
	 * @param treshold
	 * @return The CT value or null if none could be calculated.
	 */
	public Ct calculateCt(final double treshold) {
		final List<Amplification> amplifications = new ArrayList<Amplification>(getAmplifications());

		Amplification belowTreshold = null;
		Amplification aboveTreshold = null;
		for (int i = amplifications.size() - 1; i > -1; i--) {
			final Amplification amplification = amplifications.get(i);
			if (amplification.getDrn() < treshold) {
				belowTreshold = amplification;
				if (i < amplifications.size() - 1) {
					aboveTreshold = amplifications.get(i + 1);
				}
				break;
			}
		}

		Ct ct = null;
		if (belowTreshold != null && aboveTreshold != null) {
			ct = calculateCt(treshold, belowTreshold, aboveTreshold);
		}
		return ct;
	}

	private static Ct calculateCt(final double treshold, final Amplification belowTreshold,
			final Amplification aboveTreshold) {
		final Point tresholdA = new Point(belowTreshold.getCycle(), treshold);
		final Point tresholdB = new Point(aboveTreshold.getCycle(), treshold);
		final Point drnA = new Point(belowTreshold.getCycle(), belowTreshold.getDrn());
		final Point drnB = new Point(aboveTreshold.getCycle(), aboveTreshold.getDrn());
		final Point intersection = GeometryUtil.findIntersection(tresholdA, tresholdB, drnA, drnB);
		Ct ct = null;
		if (intersection != null) {
			ct = Ct.forValueAndTreshold(intersection.getX(), treshold);
		}
		return ct;
	}

}
