package nl.runnable.lims.rijnstate;

import javax.annotation.ManagedBean;
import javax.inject.Inject;

import nl.runnable.lims.domain.Group;
import nl.runnable.lims.domain.GroupRepository;

@ManagedBean
public class GroupHelper {

	@Inject
	private GroupRepository groupRepository;

	public void createDefaultGroups() {
		createGroup(Groups.TECHNICIAN);
		createGroup(Groups.CLINICAL_MOLECULAR_BIOLOGIST);
	}

	private void createGroup(final String groupId) {
		if (groupRepository.findByName(groupId) == null) {
			groupRepository.save(new Group(groupId));
		}
	}
}
