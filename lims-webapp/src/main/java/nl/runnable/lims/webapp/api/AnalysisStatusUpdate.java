package nl.runnable.lims.webapp.api;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;

import nl.runnable.lims.domain.AnalysisStatus;

public class AnalysisStatusUpdate {

	@NotNull
	private Long id;

	private Double treshold;

	private AnalysisStatus status;

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public Double getTreshold() {
		return treshold;
	}

	public void setTreshold(final Double treshold) {
		this.treshold = treshold;
	}

	public AnalysisStatus getStatus() {
		return status;
	}

	public void setStatus(final AnalysisStatus status) {
		this.status = status;
	}

	@AssertTrue(message = "Treshold or status must be specified.")
	public boolean isValid() {
		return (treshold != null || status != null);
	}

}
