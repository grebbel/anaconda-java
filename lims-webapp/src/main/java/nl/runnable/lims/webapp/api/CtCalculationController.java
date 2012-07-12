package nl.runnable.lims.webapp.api;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.inject.Inject;
import javax.validation.Valid;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/calculate-cts")
public class CtCalculationController implements ApplicationContextAware {

	/* Dependencies */

	@Inject
	private AsyncTaskExecutor taskExecutor;

	private ApplicationContext applicationContext;

	/* Operations */

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public CtCalculationResponse calculateCts(@Valid @RequestBody final CtCalculationRequest request)
			throws InterruptedException, ExecutionException {
		return doCalculateCts(request);
	}

	@RequestMapping(value = "/form", method = RequestMethod.POST)
	@ResponseBody
	public CtCalculationResponse calculateCtsByForm(final CtCalculationRequest request) throws InterruptedException,
			ExecutionException {
		return doCalculateCts(request);
	}

	/* Utility operations */

	private CtCalculationResponse doCalculateCts(final CtCalculationRequest request) throws InterruptedException,
			ExecutionException {
		final CtCalculationTask task = applicationContext.getBean(CtCalculationTask.class);
		task.setRequest(request);
		final Future<CtCalculationResponse> result = taskExecutor.submit(task);
		return request.isAsynchronous() ? new CtCalculationResponse() : result.get();
	}

	/* Dependencies */

	@Override
	public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

}
