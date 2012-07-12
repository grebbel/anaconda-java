package nl.runnable.lims.webapp.api;

import org.codehaus.jackson.annotate.JsonUnwrapped;

class Item<T> {

	private final T entity;

	private String displayDate;

	Item(final T entity) {
		this.entity = entity;
	}

	@JsonUnwrapped
	public T getEntity() {
		return entity;
	}

	public void setDisplayDate(final String displayDate) {
		this.displayDate = displayDate;
	}

	public String getDisplayDate() {
		return displayDate;
	}

}
