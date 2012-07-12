package nl.runnable.lims.domain;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Represents the settings of an update operation for a {@link Target}.
 * 
 * @author Laurens Fridael
 * 
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TargetUpdate {

	@NotNull
	@Size(min = 1)
	private String name;

	private Long targetTypeId;

	private String description;

	private List<String> codes;

	private List<String> tags;

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public Long getTargetTypeId() {
		return targetTypeId;
	}

	public void setTargetTypeId(final Long targetTypeId) {
		this.targetTypeId = targetTypeId;
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

	public void setTags(final List<String> tags) {
		this.tags = tags;
	}

	public List<String> getTags() {
		return tags;
	}

}
