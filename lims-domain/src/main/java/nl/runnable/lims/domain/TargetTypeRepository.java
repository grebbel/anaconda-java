package nl.runnable.lims.domain;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TargetTypeRepository extends JpaRepository<TargetType, Long> {

	@Query("SELECT targetType.name FROM TargetType targetType WHERE (?1 IS NULL OR id != ?1) AND name IN (?2)")
	List<String> findDuplicateNames(Long id, Collection<String> names);

	List<TargetType> findByCodesIn(Collection<String> codes);
}
