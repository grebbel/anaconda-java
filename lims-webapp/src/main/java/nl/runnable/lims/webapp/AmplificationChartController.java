package nl.runnable.lims.webapp;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import nl.runnable.lims.chart.AmplificationChartService;
import nl.runnable.lims.chart.ChartConfiguration;
import nl.runnable.lims.chart.ChartType;
import nl.runnable.lims.domain.Amplification;
import nl.runnable.lims.domain.Analysis;
import nl.runnable.lims.domain.AnalysisRepository;
import nl.runnable.lims.domain.AnalysisStatus;
import nl.runnable.lims.domain.Request;
import nl.runnable.lims.domain.RequestRepository;

import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.Range;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AmplificationChartController {

	private static final String ANALYSES_PARAM = "analyses[]";

	private static final String STATUS_PARAM = "status[]";

	private static final String TRESHOLDS_PARAM = "tresholds[]";

	/* Dependencies */

	@Inject
	private AmplificationChartService chartService;

	@Inject
	private RequestRepository requestRepository;

	@Inject
	private AnalysisRepository analysisRepository;

	/* Operations */

	@RequestMapping("/amplification-chart")
	public void getAmplificationChart(
			@RequestParam(value = ANALYSES_PARAM, required = false) final List<Long> analysisIds,
			@RequestParam(value = TRESHOLDS_PARAM, required = false) final List<Double> tresholds,
			@RequestParam(value = STATUS_PARAM, required = false) final List<AnalysisStatus> statuses,
			final ChartSettings chartSettings, final HttpServletResponse response) throws IOException {
		final List<Analysis> analyses;
		if (CollectionUtils.isEmpty(analysisIds)) {
			analyses = Collections.emptyList();
		} else {
			analyses = analysisRepository.findByIdIn(analysisIds);
		}
		if (tresholds != null && tresholds.size() == analyses.size()) {
			for (int i = 0; i < tresholds.size(); i++) {
				final Long analysisId = analysisIds.get(i);
				for (final Analysis analysis : analyses) {
					if (analysis.getId().equals(analysisId)) {
						analysis.setTreshold(tresholds.get(i));
						analysis.setCt(analysis.calculateCt(tresholds.get(i)));
					}
				}
			}
		}
		if (statuses != null && statuses.size() == analyses.size()) {
			for (int i = 0; i < statuses.size(); i++) {
				final Long analysisId = analysisIds.get(i);
				for (final Analysis analysis : analyses) {
					if (analysis.getId().equals(analysisId)) {
						analysis.setStatus(statuses.get(i));
					}
				}
			}
		}
		renderAmplificationChart(analyses, chartSettings, response);
	}

	@RequestMapping("/requests/{id}/amplification-chart")
	public void getAmplificationChart(final ChartSettings chartSettings, @PathVariable("id") final long requestId,
			@RequestParam(value = ANALYSES_PARAM, required = false) final List<Long> showAnalyses,
			final HttpServletResponse response) throws IOException {

		final Request request = requestRepository.findOne(requestId);
		if (request != null) {
			final Collection<Analysis> analyses = request.getAnalyses();
			if (CollectionUtils.isEmpty(showAnalyses) == false) {
				filterAnalyses(showAnalyses, analyses);
			}
			renderAmplificationChart(analyses, chartSettings, response);
		} else {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
	}

	private void renderAmplificationChart(final Collection<Analysis> analyses, final ChartSettings chartSettings,
			final HttpServletResponse response) throws IOException {
		final ChartConfiguration configuration = chartSettings.getConfiguration();
		if (configuration.getRange() == null && configuration.getChartType() == ChartType.LINEAR) {
			configuration.setRange(calculateRange(analyses, 0.01));
		}
		final JFreeChart chart = chartService.createAnalysisChart(analyses, configuration);
		chart.setBackgroundPaint(chartSettings.getBackgroundColor());
		response.setContentType("image/png");
		response.addHeader("Pragma", "no-cache");
		response.addHeader("Cache-Control", "no-cache");
		response.addIntHeader("Expires", -1);
		ChartUtilities.writeChartAsPNG(response.getOutputStream(), chart, chartSettings.getWidth(),
				chartSettings.getHeight());
	}

	private void filterAnalyses(final List<Long> idsOfAnalysesToRemove, final Collection<Analysis> analyses) {
		for (final Iterator<Analysis> it = analyses.iterator(); it.hasNext();) {
			if (idsOfAnalysesToRemove.contains(it.next().getId()) == false) {
				it.remove();
			}
		}
	}

	private Range calculateRange(final Collection<Analysis> analyses, double margin) {
		double min = 0;
		double max = 0;
		for (final Analysis analysis : analyses) {
			for (final Amplification amplification : analysis.getAmplifications()) {
				if (amplification.getDrn() < min) {
					min = amplification.getDrn();
				}
				if (amplification.getDrn() > max) {
					max = amplification.getDrn();
				}
			}
		}
		margin = (max - min) * margin;
		return new Range(min - margin, max + margin);
	}

}
