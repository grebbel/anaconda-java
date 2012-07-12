package nl.runnable.lims.domain;

import nl.runnable.lims.domain.impl.ActivityServiceHolder;
import nl.runnable.lims.domain.impl.WorkflowServiceHolder;

/**
 * Convenience abstract base class for JPA entity listeners that use an {@link ActivityService}.
 * 
 * @author Laurens Fridael
 * 
 */
public abstract class AbstractEntityListener {

	protected ActivityService getActivityService() {
		return ActivityServiceHolder.getActivityService();
	}

	protected WorkflowService getWorkflowService() {
		return WorkflowServiceHolder.getWorkflowService();
	}

}
