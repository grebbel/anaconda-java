package nl.runnable.lims.domain.impl;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;

import nl.runnable.lims.domain.WorkflowService;

import org.springframework.beans.factory.annotation.Autowired;

@ManagedBean
public class WorkflowServiceHolder {

	private static WorkflowService singletonWorkflowService;

	public static WorkflowService getWorkflowService() {
		return singletonWorkflowService;
	}

	@Autowired(required = false)
	private WorkflowService workflowService;

	@PostConstruct
	protected void configureSingleton() {
		if (singletonWorkflowService == null) {
			singletonWorkflowService = workflowService;
		}
	}
}
