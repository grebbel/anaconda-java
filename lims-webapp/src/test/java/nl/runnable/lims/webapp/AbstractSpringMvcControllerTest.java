package nl.runnable.lims.webapp;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.After;
import org.junit.Before;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.util.Assert;
import org.springframework.web.context.support.GenericWebApplicationContext;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter;
import org.springframework.web.servlet.mvc.annotation.DefaultAnnotationHandlerMapping;

public abstract class AbstractSpringMvcControllerTest {

	private GenericWebApplicationContext applicationContext;

	@Before
	public void setup() {
		applicationContext = new GenericWebApplicationContext();
		applicationContext.registerBeanDefinition(DefaultAnnotationHandlerMapping.class.getName(),
				new RootBeanDefinition(DefaultAnnotationHandlerMapping.class));
		applicationContext.registerBeanDefinition(AnnotationMethodHandlerAdapter.class.getName(),
				new RootBeanDefinition(AnnotationMethodHandlerAdapter.class));
		registerBeans();
		applicationContext.refresh();
	}

	/**
	 * Callback for allowing subclasses to register controller and service beans. The implementation calls this
	 * operation during {@link #setup()}.
	 */
	protected abstract void registerBeans();

	/**
	 * Registers a bean for a given Class with the {@link ApplicationContext} managing the bean's lifecycle.
	 * 
	 * @param clazz
	 */
	protected void registerBean(final Class<?> clazz) {
		Assert.notNull(clazz, "Class cannot be null.");
		applicationContext.registerBeanDefinition(clazz.getName(), new RootBeanDefinition(clazz));
	}

	/**
	 * Registers an existing bean instance. Unlike {@link #registerBean(Class)}, the{@link ApplicationContext} does NOT
	 * manage the bean's lifecycle, so lifecycle callbacks are not invoked and annotations such as {@link PostConstruct}
	 * have no effect. Instead it is assumed that the caller has fully initialized the bean.
	 * 
	 * @param bean
	 */
	protected void registerBean(final Object bean) {
		Assert.notNull(bean, "Bean cannot be null.");
		applicationContext.getBeanFactory().registerSingleton(bean.getClass().getName(), bean);
	}

	@After
	public void teardown() {
		applicationContext.close();
	}

	/* Utility operations */

	/**
	 * Obtains the {@link HandlerAdapter}.
	 */
	protected HandlerAdapter getHandlerAdapter() {
		return applicationContext.getBean(HandlerAdapter.class);
	}

	/**
	 * Obtains the handler object, which should be a controller.
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	protected Object getHandler(final HttpServletRequest request) throws Exception {
		Assert.notNull(request, "PortletRequest cannot be null.");

		Object handler = null;
		final HandlerExecutionChain chain = applicationContext.getBean(HandlerMapping.class).getHandler(request);
		if (chain != null) {
			handler = chain.getHandler();
		}
		return handler;
	}

	protected ModelAndView handleRequest(final HttpServletRequest request, final HttpServletResponse response) {
		try {
			final Object handler = getHandler(request);
			Assert.state(handler != null, "Could not find request handler.");
			return getHandlerAdapter().handle(request, response, handler);
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected ModelAndView handleRequest(final HttpServletRequest request) {
		return handleRequest(request, new MockHttpServletResponse());
	}
}
