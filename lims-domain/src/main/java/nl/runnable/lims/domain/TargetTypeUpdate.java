package nl.runnable.lims.domain;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class TargetTypeUpdate {

	@NotNull
	@Size(min = 1)
	private String name;

	private String description;

	private List<String> codes = new ArrayList<String>();

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public List<String> getCodes() {
		return codes;
	}

	public void setCodes(final List<String> codes) {
		this.codes = codes;
	}

}
