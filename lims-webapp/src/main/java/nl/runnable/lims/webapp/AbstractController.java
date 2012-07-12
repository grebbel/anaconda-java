package nl.runnable.lims.webapp;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

public abstract class AbstractController {

	@ExceptionHandler(EmptyResultDataAccessException.class)
	@ResponseBody
	public String notFound(final EmptyResultDataAccessException e, final HttpServletResponse response)
			throws IOException {
		response.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
		return e.getMessage();
	}

	@ExceptionHandler({ IllegalArgumentException.class })
	@ResponseBody
	public String badRequest(final HttpServletResponse response, final RuntimeException e) throws IOException {
		response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
		return e.getMessage();
	}
}
