package nl.runnable.lims.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.util.StringUtils;

@Entity
@EntityListeners(RequestListener.class)
@Table(name = "request")
public class Request extends AbstractEntity implements Describable {

	@Column(name = "external_id", length = 50, nullable = false)
	private String externalId;

	@Column(name = "description", length = 255)
	private String description;

	@Column(name = "date")
	private Date date = new Date();

	@OneToOne
	@JoinColumn(name = "sample_type_id")
	private SampleType sampleType;

	@Column(name = "status", nullable = true)
	@Enumerated(EnumType.STRING)
	private RequestStatus status;

	@Column(name = "workflow_id", unique = true)
	private String workflowId;

	@ManyToMany
	@JoinTable(name = "request_target", joinColumns = @JoinColumn(name = "request_id"), inverseJoinColumns = @JoinColumn(name = "target_id"))
	private Set<Target> targets = new HashSet<Target>();

	@OneToMany(mappedBy = "request", cascade = CascadeType.ALL)
	private Set<Analysis> analyses = new HashSet<Analysis>();

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(final String externalId) {
		this.externalId = externalId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(final Date date) {
		this.date = date;
	}

	public SampleType getSampleType() {
		return sampleType;
	}

	public void setSampleType(final SampleType sampleType) {
		this.sampleType = sampleType;
	}

	public RequestStatus getStatus() {
		if (status != null) {
			return status;
		} else {
			return RequestStatus.INDETERMINATE;
		}
	}

	public void setStatus(final RequestStatus status) {
		this.status = status;
	}

	public String getWorkflowId() {
		return workflowId;
	}

	public void setWorkflowId(final String workflowId) {
		this.workflowId = workflowId;
	}

	public Set<Target> getTargets() {
		return targets;
	}

	public void setTargets(final Set<Target> targets) {
		this.targets = targets;
	}

	public Set<Analysis> getAnalyses() {
		return analyses;
	}

	public void setAnalyses(final Set<Analysis> analyses) {
		this.analyses = analyses;
	}

	public void addAnalysis(final Analysis analysis) {
		getAnalyses().add(analysis);
		analysis.setRequest(this);
	}

	public void removeAnalysis(final Analysis analysis) {
		final Set<Analysis> analyses = getAnalyses();
		if (analyses.contains(analysis)) {
			if (analysis.getRequest() == this) {
				analysis.setRequest(null);
			}
			analyses.remove(analysis);
		}
	}

	@Override
	public String describe() {
		if (StringUtils.hasText(getDescription())) {
			return getDescription();
		} else {
			return getExternalId();
		}
	}

}
