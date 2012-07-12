package nl.runnable.lims.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Represents a link to a resource in the application. Resource typically (but not always) map to entities such as an
 * {@link Analysis} or a {@link Request}.
 * 
 * @author Laurens Fridael
 * 
 */
@Embeddable
public class ResourceLink {

	public static ResourceLink forEntity(final AbstractEntity entity) {
		return new ResourceLink(entity.getId(), entity.getClass().getName());
	}

	@Column(name = "resource_id")
	private Long resourceId;

	@Column(name = "resource_type")
	private String resourceType;

	public ResourceLink(final Long resourceId, final String resourceType) {
		this.resourceId = resourceId;
		this.resourceType = resourceType;
	}

	public ResourceLink() {
	}

	public Long getResourceId() {
		return resourceId;
	}

	public void setResourceId(final Long id) {
		this.resourceId = id;
	}

	public String getResourceType() {
		return resourceType;
	}

	public void setResourceType(final String resource) {
		this.resourceType = resource;
	}

}
