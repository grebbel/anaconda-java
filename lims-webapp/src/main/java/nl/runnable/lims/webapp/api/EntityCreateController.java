package nl.runnable.lims.webapp.api;

import java.io.IOException;

import nl.runnable.lims.webapp.AbstractValidatingEntityController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class EntityCreateController extends AbstractValidatingEntityController {

	/* Request handling operations */

	@RequestMapping(value = "/{entityName}", method = RequestMethod.POST)
	@ResponseBody
	public void update(@ModelAttribute(UPDATE_MODEL_ATTRIBUTE) final Object update,
			@ModelAttribute("entity") final Object entity) throws IOException {
		validateAndUpdateEntity(update, entity);
		getEntityDao().saveEntity(entity);
	}

	/* Binding operations */

	@ModelAttribute("entity")
	protected Object getEntity(@PathVariable final String entityName) throws InstantiationException,
			IllegalAccessException {
		return getEntityDao().createEntity(entityName);
	}

}
