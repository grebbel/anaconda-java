package nl.runnable.lims.webapp;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import nl.runnable.lims.jpa.EntityDao;
import nl.runnable.lims.webapp.api.EntityException;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Abstract base class for Controllers that handle entities.
 * 
 * @author Laurens Fridael
 * 
 */
public class AbstractEntityController extends AbstractController {

	/* Dependencies */

	@Inject
	private EntityDao entityDao;

	/* Operations */

	@Override
	@ExceptionHandler({ EntityException.class, IllegalArgumentException.class })
	@ResponseBody
	public String badRequest(final HttpServletResponse response, final RuntimeException e) throws IOException {
		response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
		return e.getMessage();
	}

	/* Dependencies */

	protected EntityDao getEntityDao() {
		return entityDao;
	}

}
