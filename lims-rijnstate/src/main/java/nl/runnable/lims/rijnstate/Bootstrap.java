package nl.runnable.lims.rijnstate;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.activiti.engine.RepositoryService;

/**
 * Performs bootstrap operations.
 * 
 * @author Laurens Fridael
 * 
 */
@ManagedBean
public class Bootstrap {

	@Inject
	private UserIdentityHelper userIdentityHelper;

	@Inject
	private RepositoryService repositoryService;

	@Inject
	private GroupHelper groupHelper;

	@PostConstruct
	public void performBootstrap() {
		repositoryService.createDeployment().addClasspathResource("processes/request.bpmn20.xml").deploy();
		groupHelper.createDefaultGroups();
		userIdentityHelper.createGroups();
		userIdentityHelper.createUsers();
		userIdentityHelper.createMemberships();
	}
}
