package nl.runnable.lims.domain;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TagRepository extends JpaRepository<Tag, Long> {

	Tag findByName(String name);

	List<Tag> findDistinctByNameIn(Collection<String> name);

	@Query("SELECT tag FROM Tag tag ORDER BY tag.name")
	List<Tag> findAllSortByName();

	@Query("SELECT tag FROM Tag tag WHERE tag.name LIKE ?1 ORDER BY tag.name")
	List<Tag> findByNameLike(String name);

	@Query("SELECT tag.name FROM Tag tag WHERE (?1 IS NULL OR id != ?1) AND name IN (?2)")
	List<String> findDuplicateNames(Long id, Collection<String> names);

}
