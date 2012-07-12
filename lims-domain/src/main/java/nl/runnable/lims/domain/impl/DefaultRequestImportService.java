package nl.runnable.lims.domain.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.ManagedBean;
import javax.inject.Inject;

import nl.runnable.lims.domain.Analysis;
import nl.runnable.lims.domain.AnalysisService;
import nl.runnable.lims.domain.Request;
import nl.runnable.lims.domain.RequestImport;
import nl.runnable.lims.domain.RequestImportService;
import nl.runnable.lims.domain.RequestRepository;
import nl.runnable.lims.domain.Target;
import nl.runnable.lims.domain.TargetRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ManagedBean
public class DefaultRequestImportService implements RequestImportService {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	/* Dependencies */

	@Inject
	private RequestRepository requestRepository;

	@Inject
	private TargetRepository targetRepository;

	@Inject
	private AnalysisService analysisService;

	/* Operations */

	@Override
	public Request importRequest(final RequestImport requestImport) {
		final Request request = resolveTargetsAndAnalyses(requestImport);
		getRequestRepository().save(request);
		return request;
	}

	private Request resolveTargetsAndAnalyses(final RequestImport requestImport) {
		final List<Target> targets = new ArrayList<Target>();
		for (final String targetCode : requestImport.getTargets()) {
			final Target target = getTargetRepository().findByCodesIn(targetCode);
			if (target != null) {
				targets.add(target);
			} else {
				if (logger.isDebugEnabled()) {
					logger.debug("Cannot find target for code {}", targetCode);
				}
			}
		}

		final Request request = requestImport.getRequest();
		for (final Analysis analysis : getAnalysisService().resolveAnalysesByTargets(targets)) {
			if (analysis != null) {
				if (request.getDate() != null) {
					analysis.setDate(request.getDate());
				}
				request.addAnalysis(analysis);
			}
		}
		for (final Target target : targets) {
			request.getTargets().add(target);
		}
		return request;
	}

	/* Dependencies */

	public void setRequestRepository(final RequestRepository requestRepository) {
		this.requestRepository = requestRepository;
	}

	protected RequestRepository getRequestRepository() {
		return requestRepository;
	}

	public void setTargetRepository(final TargetRepository targetRepository) {
		this.targetRepository = targetRepository;
	}

	protected TargetRepository getTargetRepository() {
		return targetRepository;
	}

	public void setAnalysisService(final AnalysisService analysisService) {
		this.analysisService = analysisService;
	}

	protected AnalysisService getAnalysisService() {
		return analysisService;
	}
}
