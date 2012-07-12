package nl.runnable.lims.jpa;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import nl.runnable.lims.domain.Tag;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EntityDao {

	Class<?> getEntityClass(String entityName);

	JpaRepository<Object, Serializable> getJpaRepository(String entityName);

	Object findEntity(final String entityName, final Object id);

	Page<?> findAll(final String entityName, final Pageable pageable);

	Object createEntity(final String entityName) throws InstantiationException, IllegalAccessException;

	Object saveEntity(final Object entity);

	void deleteEntity(String entityName, Long id);

	void deleteEntity(Object entity);

	boolean supportsTagFiltering(String entityName);

	Page<?> findAllWithAnyTags(String entityName, Pageable pageable, Set<String> tags);

	List<Tag> findTags(String entityName);

}