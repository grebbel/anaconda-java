package nl.runnable.lims.jpa.impl;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.metamodel.EntityType;

import nl.runnable.lims.domain.Tag;
import nl.runnable.lims.jpa.EntityDao;
import nl.runnable.lims.jpa.TaggableEntityRepository;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.support.Repositories;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

@Transactional
public class EntityDaoImpl implements ApplicationContextAware, EntityDao {

	/* Dependencies */

	@PersistenceContext
	private EntityManager entityManager;

	private Repositories repositories;

	/* Configuration */

	private Map<String, String> entityNamesByPluralForm = Collections.emptyMap();

	/* Operations */

	@Override
	public Class<?> getEntityClass(String entityName) {
		entityName = StringUtils.capitalize(getEntityName(entityName));
		for (final EntityType<?> entityType : entityManager.getMetamodel().getEntities()) {
			if (entityType.getName().equals(entityName)) {
				return entityType.getJavaType();
			}

		}
		return null;
	}

	@Override
	public JpaRepository<Object, Serializable> getJpaRepository(final String entityName) {
		return (JpaRepository<Object, Serializable>) repositories.getRepositoryFor(getEntityClass(entityName));
	}

	@Override
	public Object findEntity(String entityName, final Object id) {
		entityName = getEntityName(entityName);
		final Class<?> clazz = getEntityClass(entityName);
		if (clazz == null) {
			throw new IllegalStateException(String.format("Could not find class for entity name '%s'.", entityName));
		}
		final Object entity = entityManager.find(clazz, id);
		if (entity == null) {
			throw new EmptyResultDataAccessException(String.format("Could not find entity '%s' with id %s ",
					entityName, id), 1);
		}
		return entity;
	}

	@Override
	public Page<?> findAll(final String entityName, final Pageable pageable) {
		final JpaRepository<Object, Serializable> repository = getJpaRepository(entityName);
		return repository.findAll(pageable);
	}

	@Override
	public Object createEntity(final String entityName) throws InstantiationException, IllegalAccessException {
		final Class<?> clazz = getEntityClass(getEntityName(entityName));
		if (clazz == null) {
			throw new IllegalStateException(String.format("Could not find class for entity name '%s'.", entityName));
		}
		return clazz.newInstance();
	}

	@Override
	public Object saveEntity(final Object entity) {
		Assert.notNull(entity, "Entity cannot be null");
		final CrudRepository<Object, Serializable> repository = repositories.getRepositoryFor(entity.getClass());
		return repository.save(entity);
	}

	@Override
	public void deleteEntity(final String entityName, final Long id) {
		final JpaRepository<Object, Serializable> repository = getJpaRepository(entityName);
		repository.delete(id);
	}

	@Override
	public void deleteEntity(final Object entity) {
		Assert.notNull(entity, "Entity cannot be null.");
		entityManager.remove(entity);
	}

	@Override
	public boolean supportsTagFiltering(final String entityName) {
		final JpaRepository<Object, Serializable> repository = getJpaRepository(entityName);
		return (repository instanceof TaggableEntityRepository<?>);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Page<?> findAllWithAnyTags(final String entityName, final Pageable pageable, final Set<String> tags) {
		final TaggableEntityRepository<Object> repository = (TaggableEntityRepository<Object>) repositories
				.getRepositoryFor(getEntityClass(entityName));
		return repository.findAllWithAnyTags(pageable, tags);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Tag> findTags(final String entityName) {
		final TaggableEntityRepository<Object> repository = (TaggableEntityRepository<Object>) repositories
				.getRepositoryFor(getEntityClass(entityName));
		return repository.findTags();
	}

	/* Dependencies */

	@Override
	public void setApplicationContext(final ApplicationContext applicationContext) {
		this.repositories = new Repositories(applicationContext);
	}

	/* Utility operations */

	protected String getEntityName(String entityName) {
		if (getEntityNamesByPluralForm().containsKey(entityName)) {
			entityName = getEntityNamesByPluralForm().get(entityName);
		}
		return entityName;
	}

	/* Configuration */

	public void setEntityNamesByPluralForm(final Map<String, String> entityNamesByPluralForm) {
		Assert.notNull(entityNamesByPluralForm);
		this.entityNamesByPluralForm = entityNamesByPluralForm;
	}

	public Map<String, String> getEntityNamesByPluralForm() {
		return entityNamesByPluralForm;
	}

}
