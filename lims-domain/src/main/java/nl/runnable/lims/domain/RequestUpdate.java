package nl.runnable.lims.domain;

import java.util.Set;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class RequestUpdate {

	@NotNull
	@Size(min = 1)
	private String externalId;

	@NotNull
	@Size(min = 1)
	private String description;

	@NotNull
	private Long date;

	@NotNull
	private Long sampleTypeId;

	@NotNull
	@Size(min = 1)
	private Set<Long> targetIds;

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

	public Long getDate() {
		return date;
	}

	public void setDate(final Long date) {
		this.date = date;
	}

	public Long getSampleTypeId() {
		return sampleTypeId;
	}

	public void setSampleTypeId(final Long sampleTypeId) {
		this.sampleTypeId = sampleTypeId;
	}

	public Set<Long> getTargetIds() {
		return targetIds;
	}

	public void setTargetIds(final Set<Long> targetIds) {
		this.targetIds = targetIds;
	}

}
