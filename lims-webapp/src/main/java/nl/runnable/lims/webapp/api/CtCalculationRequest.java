package nl.runnable.lims.webapp.api;

import java.util.List;

import javax.validation.constraints.NotNull;

public class CtCalculationRequest {

	private boolean asynchronous = false;

	private boolean store = false;

	private boolean analysesWithoutCt = false;

	@NotNull
	private List<Long> analysisIds;

	@NotNull
	private Double treshold;

	public boolean isAsynchronous() {
		return asynchronous;
	}

	public void setAsynchronous(final boolean asynchronous) {
		this.asynchronous = asynchronous;
	}

	public List<Long> getAnalysisIds() {
		return analysisIds;
	}

	public void setAnalysisIds(final List<Long> analysisIds) {
		this.analysisIds = analysisIds;
	}

	public void setAnalysesWithoutCt(final boolean analysesWithoutCt) {
		this.analysesWithoutCt = analysesWithoutCt;
	}

	public boolean isAnalysesWithoutCt() {
		return analysesWithoutCt;
	}

	public void setStore(final boolean store) {
		this.store = store;
	}

	public boolean isStore() {
		return store;
	}

	public Double getTreshold() {
		return treshold;
	}

	public void setTreshold(final Double treshold) {
		this.treshold = treshold;
	}

}
