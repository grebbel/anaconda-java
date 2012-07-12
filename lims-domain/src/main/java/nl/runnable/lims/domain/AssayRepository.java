package nl.runnable.lims.domain;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AssayRepository extends JpaRepository<Assay, Long> {

	@Query("SELECT DISTINCT assay FROM Assay assay JOIN assay.assayTargets assayTarget JOIN assayTarget.target.tags tag WHERE tag.name IN (:tagNames)")
	List<Assay> findByTargetTags(@Param("tagNames") Collection<String> tag);

	@Query("SELECT DISTINCT target.tags FROM Assay assay JOIN assay.assayTargets assayTarget JOIN assayTarget.target target")
	List<Tag> findTags();

}
