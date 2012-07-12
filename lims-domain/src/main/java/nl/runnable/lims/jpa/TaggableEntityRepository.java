package nl.runnable.lims.jpa;

import java.util.List;
import java.util.Set;

import nl.runnable.lims.domain.Tag;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TaggableEntityRepository<T> {

	List<Tag> findTags();

	Page<T> findAllWithAnyTags(Pageable pageable, Set<String> tags);

}
