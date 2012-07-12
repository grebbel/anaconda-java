package nl.runnable.lims.domain;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

	public Role findByName(String name);

	public List<Role> findByNameIn(Collection<String> names);

}
