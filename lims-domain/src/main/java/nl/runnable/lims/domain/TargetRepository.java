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

public interface TargetRepository extends JpaRepository<Target, Serializable>, TaggableEntityRepository<Target> {

	List<Target> findByIdIn(Set<Long> ids);

	Target findByCodesIn(String code);

	Target findByName(String name);

	List<Target> findByNameLike(String name);

	List<Target> findByTagsIn(Tag tag);

	List<Target> findByTagsNameIn(Collection<String> tagNames);

	@Override
	@Query("SELECT DISTINCT tag FROM Target target JOIN target.tags tag ORDER BY tag.name")
	List<Tag> findTags();

	@Override
	@Query("SELECT DISTINCT target FROM Target target JOIN target.tags tag WHERE tag.name IN (?1)")
	Page<Target> findAllWithAnyTags(Pageable pageable, Set<String> tags);

	@Query("SELECT target.name FROM Target target WHERE (?1 IS NULL OR id != ?1) AND name IN (?2)")
	List<String> findDuplicateNames(Long id, Collection<String> names);

}
