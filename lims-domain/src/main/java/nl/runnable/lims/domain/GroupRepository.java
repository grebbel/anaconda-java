package nl.runnable.lims.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, Long> {

	Group findByName(String groupId);

}
