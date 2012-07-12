package nl.runnable.lims.webapp.helpers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * Spring MVC {@link HandlerInterceptor} that calls {@link TimeHelper#clearCurrentTime()} after completion of every
 * request.
 * 
 * @author Laurens Fridael
 * 
 */
public class TimeHelperHandlerInterceptor extends HandlerInterceptorAdapter {

	@Override
	public void afterCompletion(final HttpServletRequest request, final HttpServletResponse response,
			final Object handler, final Exception ex) throws Exception {
		TimeHelper.clearCurrentTime();
	}
}
