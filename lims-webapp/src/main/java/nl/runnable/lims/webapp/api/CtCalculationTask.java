package nl.runnable.lims.webapp.api;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import javax.annotation.ManagedBean;
import javax.inject.Inject;

import nl.runnable.lims.domain.Analysis;
import nl.runnable.lims.domain.AnalysisRepository;
import nl.runnable.lims.domain.Ct;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@ManagedBean
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Transactional
public class CtCalculationTask implements Callable<CtCalculationResponse> {

	@Inject
	private AnalysisRepository analysisRepository;

	private CtCalculationRequest request;

	public void setRequest(final CtCalculationRequest request) {
		this.request = request;
	}

	@Override
	@Transactional
	public CtCalculationResponse call() {
		final List<CtCalculation> ctCalculations = new ArrayList<CtCalculation>();
		for (final Analysis analysis : findAnalysesToCalculate()) {
			final Ct ct;
			if (request.getTreshold() != null) {
				ct = analysis.calculateCt(request.getTreshold());
			} else {
				ct = analysis.calculateCt();
			}
			if (ct != null) {
				ctCalculations.add(new CtCalculation(analysis.getId(), ct));
				if (request.isStore()) {
					analysis.setCt(ct);
					analysisRepository.save(analysis);
				}
			}

		}
		final CtCalculationResponse response = new CtCalculationResponse();
		response.setCtCalculations(ctCalculations);
		return response;
	}

	private List<Analysis> findAnalysesToCalculate() {
		List<Analysis> analyses = null;
		final List<Long> analysisIds = request.getAnalysisIds();
		if (CollectionUtils.isEmpty(analysisIds) == false) {
			analyses = analysisRepository.findByIdIn(analysisIds);
		} else if (request.isAnalysesWithoutCt()) {
			analyses = analysisRepository.findByCtIsNull();
		} else {
			analyses = analysisRepository.findAll();
		}
		return analyses;
	}

}
