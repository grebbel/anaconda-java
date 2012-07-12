package nl.runnable.lims.webapp.api;

import nl.runnable.lims.domain.Request;

import org.codehaus.jackson.annotate.JsonUnwrapped;

class RequestItem {

	private final Request request;

	private String displayDate;

	RequestItem(final Request request) {
		this.request = request;
	}

	@JsonUnwrapped
	public Request getRequest() {
		return request;
	}

	public void setDisplayDate(final String displayDate) {
		this.displayDate = displayDate;
	}

	public String getDisplayDate() {
		return displayDate;
	}

}
