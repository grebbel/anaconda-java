package nl.runnable.lims.webapp.api;

import java.io.IOException;

import nl.runnable.lims.webapp.AbstractValidatingEntityController;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Handles entity update requests.
 * 
 * @author Laurens Fridael
 * 
 */
@Controller
public class EntityUpdateController extends AbstractValidatingEntityController {

	@RequestMapping(value = "/{entityName}/{id}", method = RequestMethod.PUT)
	@ResponseBody
	@Transactional
	public void update(@ModelAttribute(UPDATE_MODEL_ATTRIBUTE) final Object update,
			@ModelAttribute("entity") final Object entity) throws IOException {
		validateAndUpdateEntity(update, entity);
	}

	@ModelAttribute("entity")
	protected Object getEntity(@PathVariable final String entityName, @PathVariable final Long id) {
		return getEntityDao().findEntity(entityName, id);
	}

}
