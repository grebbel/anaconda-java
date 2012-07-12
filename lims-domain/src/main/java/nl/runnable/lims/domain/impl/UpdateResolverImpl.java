package nl.runnable.lims.domain.impl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;

import nl.runnable.lims.domain.UpdateHandler;
import nl.runnable.lims.domain.UpdateResolver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

@ManagedBean
public class UpdateResolverImpl implements UpdateResolver, BeanFactoryAware {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private static String lookupKey(final Class<?> updateClass, final Class<?> entityClass) {
		return updateClass.getName() + "-" + entityClass.getName();
	}

	/* Dependencies */

	private ConfigurableListableBeanFactory beanFactory;

	/* State */

	private final Map<Class<?>, Class<?>> updateClassesByEntityClass = new HashMap<Class<?>, Class<?>>();

	private final Map<String, UpdateHandler<?, ?>> updateHandlersByUpdateEntityClass = new HashMap<String, UpdateHandler<?, ?>>();

	/* Operations */

	@PostConstruct
	public void initialize() throws ClassNotFoundException {
		for (final UpdateHandler<?, ?> updateHandler : beanFactory.getBeansOfType(UpdateHandler.class).values()) {
			final Type[] types = getUpdateHandlerTypes(updateHandler.getClass());
			final Class<?> updateClass = (Class<?>) types[0];
			final Class<?> entityClass = (Class<?>) types[1];
			if (updateClassesByEntityClass.containsKey(entityClass) == false) {
				updateClassesByEntityClass.put(entityClass, updateClass);
			} else {
				if (logger.isWarnEnabled()) {
					logger.warn("Already registered an update class for '{}'. Can only register one update class.",
							entityClass.getName());
				}
			}
			final String lookupKey = lookupKey(updateClass, entityClass);
			if (updateHandlersByUpdateEntityClass.containsKey(lookupKey) == false) {
				updateHandlersByUpdateEntityClass.put(lookupKey, updateHandler);
			} else {
				if (logger.isWarnEnabled()) {
					logger.warn("Already registered an UpdateHandler for update class '{}' and entity class '{}'.",
							updateClass.getName(), entityClass.getName());
				}
			}
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public <UpdateType, EntityType> UpdateHandler<UpdateType, EntityType> getUpdateHandler(
			final Class<UpdateType> updateClass, final Class<EntityType> entityClass) {
		final String lookupKey = lookupKey(updateClass, entityClass);
		return (UpdateHandler<UpdateType, EntityType>) updateHandlersByUpdateEntityClass.get(lookupKey);
	}

	@Override
	public Class<?> getUpdateClass(final Class<?> entityClass) {
		final Class<?> updateClass = updateClassesByEntityClass.get(entityClass);
		if (updateClass == null) {
			try {
				return Class.forName(entityClass.getName() + "Update");
			} catch (final ClassNotFoundException e) {
				return null;
			}
		}
		return updateClass;
	}

	/* Utility operations */

	private Type[] getUpdateHandlerTypes(final Class<?> beanClass) {
		for (final Type type : beanClass.getGenericInterfaces()) {
			if (type instanceof ParameterizedType) {
				final Class<?> rawType = (Class<?>) ((ParameterizedType) type).getRawType();
				if (UpdateHandler.class.isAssignableFrom(rawType)) {
					return ((ParameterizedType) type).getActualTypeArguments();
				}
			}
		}
		return null;
	}

	/* Dependencies */

	@Override
	public void setBeanFactory(final BeanFactory beanFactory) throws BeansException {
		this.beanFactory = (ConfigurableListableBeanFactory) beanFactory;
	}

}