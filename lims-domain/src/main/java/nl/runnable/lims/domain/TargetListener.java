package nl.runnable.lims.domain;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;

public class TargetListener extends AbstractEntityListener {

	@PostPersist
	public void postPersistTarget(final Target target) {
		getActivityService().registerTargetCreated(target);
	}

	@PostUpdate
	public void postUpdateTarget(final Target target) {
		getActivityService().registerTargetUpdated(target);
	}

	@PostRemove
	public void postRemoveTarget(final Target target) {
		getActivityService().registerTargetRemoved(target);
	}
}
