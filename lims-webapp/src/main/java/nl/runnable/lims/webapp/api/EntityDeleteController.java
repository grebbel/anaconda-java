package nl.runnable.lims.webapp.api;

import nl.runnable.lims.webapp.AbstractEntityController;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class EntityDeleteController extends AbstractEntityController {

	@RequestMapping(value = "/{entityName}/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public void delete(@PathVariable final String entityName, @PathVariable final Long id) {
		final Object entity = getEntityDao().findEntity(entityName, id);
		if (entity == null) {
			throw new EmptyResultDataAccessException(1);
		}
		getEntityDao().deleteEntity(entityName, id);
	}

}
