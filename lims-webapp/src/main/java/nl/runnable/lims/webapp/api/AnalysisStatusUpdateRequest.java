package nl.runnable.lims.webapp.api;

import java.util.List;

import javax.validation.constraints.NotNull;

public class AnalysisStatusUpdateRequest {

	@NotNull
	private List<AnalysisStatusUpdate> updates;

	public List<AnalysisStatusUpdate> getUpdates() {
		return updates;
	}

	public void setUpdates(final List<AnalysisStatusUpdate> updates) {
		this.updates = updates;
	}

}
