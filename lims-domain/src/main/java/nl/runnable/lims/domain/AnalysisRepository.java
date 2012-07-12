package nl.runnable.lims.domain;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import nl.runnable.lims.jpa.TaggableEntityRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AnalysisRepository extends JpaRepository<Analysis, Serializable>, TaggableEntityRepository<Analysis> {

	@Override
	@Query("SELECT analysis FROM Analysis analysis ORDER BY date DESC, request.id ASC, assayTarget.target.name ASC")
	public Page<Analysis> findAll(Pageable pageable);

	@Override
	@Query("SELECT DISTINCT tag FROM Analysis analysis JOIN analysis.assayTarget.target.tags tag ORDER BY tag.name")
	public List<Tag> findTags();

	@Override
	@Query("SELECT analysis FROM Analysis analysis JOIN analysis.assayTarget.target.tags tag WHERE tag.name IN (?1) "
			+ "ORDER BY analysis.date DESC, analysis.request.id ASC, analysis.assayTarget.target.name ASC")
	public Page<Analysis> findAllWithAnyTags(Pageable pageable, Set<String> tags);

	@Query("SELECT a FROM Analysis a WHERE size(a.amplifications) = 0")
	public List<Analysis> findAllWithoutAmplifications();

	public List<Analysis> findByIdIn(Collection<Long> ids);

	public List<Analysis> findByCtIsNull();

	@Query("SELECT analysis FROM Analysis analysis WHERE analysis.request.id IN (?1) ORDER BY analysis.request.id, analysis.assayTarget.target.name")
	public List<Analysis> findByRequestIds(Collection<Long> requestId);

}
