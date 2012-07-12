package nl.runnable.lims.webapp.api;

import nl.runnable.lims.webapp.AbstractEntityController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Handles request for showing an individual entity.
 * 
 * @author Laurens Fridael
 * 
 */
@Controller
public class EntityShowController extends AbstractEntityController {

	/* Request handling operations */

	@RequestMapping(value = "/{entityName}/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Object show(@ModelAttribute("entity") final Object entity) {
		return entity;
	}

	/* Binding operations */

	@ModelAttribute("entity")
	protected Object getEntity(@PathVariable final String entityName, @PathVariable final Long id) {
		return getEntityDao().findEntity(entityName, id);
	}

}
