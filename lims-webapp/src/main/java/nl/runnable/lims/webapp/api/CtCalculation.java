package nl.runnable.lims.webapp.api;

import nl.runnable.lims.domain.Ct;

class CtCalculation {

	private final long analysisId;

	private final Ct ct;

	CtCalculation(final long analysisId, final Ct ct) {
		this.analysisId = analysisId;
		this.ct = ct;
	}

	public long getAnalysisId() {
		return analysisId;
	}

	public Ct getCt() {
		return ct;
	}

}
