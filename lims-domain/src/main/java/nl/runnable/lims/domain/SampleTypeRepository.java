package nl.runnable.lims.domain;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SampleTypeRepository extends JpaRepository<SampleType, Long> {

	@Query("SELECT sampleType.name FROM SampleType sampleType WHERE (?1 IS NULL OR id != ?1) AND name IN (?2)")
	List<String> findDuplicateNames(Long id, Collection<String> names);

	List<SampleType> findByCodesIn(Collection<String> codes);

	SampleType findByCodesIn(String code);

}
