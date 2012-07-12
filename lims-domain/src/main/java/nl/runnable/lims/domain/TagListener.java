package nl.runnable.lims.domain;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;

public class TagListener extends AbstractEntityListener {

	@PostPersist
	public void postPersistTag(final Tag tag) {
		getActivityService().registerTagCreated(tag);
	}

	@PostRemove
	public void postRemoveTag(final Tag tag) {
		getActivityService().registerTagRemoved(tag);
	}

}
