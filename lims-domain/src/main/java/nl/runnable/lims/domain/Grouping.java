package nl.runnable.lims.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Value object representing a hierarchical grouping of items.
 * 
 * @author Laurens Fridael
 * 
 */
public class Grouping<T> {

	private String title;

	private List<Grouping<T>> subgroups = new ArrayList<Grouping<T>>();

	private List<T> items = new ArrayList<T>();

	public Grouping(final String title) {
		this.title = title;
	}

	public Grouping() {
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	public List<Grouping<T>> getSubgroups() {
		return subgroups;
	}

	public void setSubgroups(final List<Grouping<T>> subgroups) {
		this.subgroups = subgroups;
	}

	public List<T> getItems() {
		return items;
	}

	public void setItems(final List<T> items) {
		this.items = items;
	}

}
