package nl.runnable.lims.webapp;

import java.io.EOFException;
import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nl.runnable.lims.domain.UpdateHandler;
import nl.runnable.lims.domain.UpdateResolver;
import nl.runnable.lims.webapp.api.EntityException;

import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.Assert;
import org.springframework.validation.DataBinder;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Abstract base class for controllers that must validate entities.
 * 
 * @author Laurens Fridael
 * 
 */
public class AbstractValidatingEntityController extends AbstractEntityController {

	protected static final String UPDATE_MODEL_ATTRIBUTE = "update";

	/* Dependencies */

	private HttpMessageConverter<Object> messageConverter = new MappingJacksonHttpMessageConverter();

	@Inject
	private Validator validator;

	@Inject
	private UpdateResolver updateResolver;

	/* Request handling */

	/**
	 * Validates and updates a given entity using a given update value object.
	 * 
	 * @param update
	 * @param entity
	 * @throws IllegalArgumentException
	 *             If the update is invalid
	 * @throws EntityException
	 *             If the entity turns out to be invalid after applying the update.
	 */
	protected void validateAndUpdateEntity(final Object update, final Object entity) {
		if (validate(update).hasErrors() == false) {
			updateEntity(update, entity);
			if (validate(entity).hasErrors()) {
				throw new EntityException("Invalid entity.");
			}
		} else {
			throw new IllegalArgumentException("Invalid update.");
		}
	}

	/**
	 * Applies a given updato an entity using the registered {@link UpdateHandler}. Clients should typically call
	 * {@link #validateAndUpdateEntity(Object, Object)}.
	 * 
	 * @param update
	 * @param entity
	 */
	@SuppressWarnings("unchecked")
	protected void updateEntity(final Object update, final Object entity) {
		final Class<? extends Object> updateClass = update.getClass();
		final Class<? extends Object> entityClass = entity.getClass();
		final UpdateHandler<Object, Object> updateHandler = (UpdateHandler<Object, Object>) getUpdateResolver()
				.getUpdateHandler(updateClass, entityClass);
		if (updateHandler == null) {
			throw new IllegalArgumentException(String.format(
					"Cannot find UpdateHandler for update of type '%s' and entity of type '%s'.",
					updateClass.getName(), entityClass.getName()));
		}
		updateHandler.updateEntity(update, entity);
	}

	/* Binding */

	@ModelAttribute(UPDATE_MODEL_ATTRIBUTE)
	protected Object getUpdate(@PathVariable final String entityName, final HttpServletRequest request)
			throws IOException {
		final Class<?> updateClass = getUpdateResolver().getUpdateClass(getEntityDao().getEntityClass(entityName));
		if (updateClass == null) {
			throw new EntityException("Cannot determine update type.");
		}
		return getMessageConverter().read(updateClass, new ServletServerHttpRequest(request));
	}

	/* Exception handling */

	@ExceptionHandler(EOFException.class)
	protected void unexpectedEof(final EOFException e, final HttpServletResponse response) throws IOException {
		response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
	}

	/* Utility operations */

	protected Errors validate(final Object target) {
		final DataBinder binder = new DataBinder(target);
		binder.setValidator(getValidator());
		binder.validate();
		return binder.getBindingResult();
	}

	/* Dependencies */

	protected Validator getValidator() {
		return validator;
	}

	public void setMessageConverter(final HttpMessageConverter<Object> messageConverter) {
		Assert.notNull(messageConverter);
		this.messageConverter = messageConverter;
	}

	protected UpdateResolver getUpdateResolver() {
		return updateResolver;
	}

	protected HttpMessageConverter<Object> getMessageConverter() {
		return messageConverter;
	}

}