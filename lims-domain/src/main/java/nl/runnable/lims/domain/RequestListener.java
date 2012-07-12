package nl.runnable.lims.domain;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PreUpdate;

public class RequestListener extends AbstractEntityListener {

	@PostPersist
	public void postPersistRequest(final Request request) {
		getActivityService().registerRequestSubmitted(request);
		getWorkflowService().startRequestWorkflow(request);
	}

	@PreUpdate
	public void postUpdateRequest(final Request request) {
		getActivityService().registerRequestUpdated(request);
	}

	@PostRemove
	public void postRemoveRequest(final Request request) {
		getActivityService().registerRequestRemoved(request);
	}

}
