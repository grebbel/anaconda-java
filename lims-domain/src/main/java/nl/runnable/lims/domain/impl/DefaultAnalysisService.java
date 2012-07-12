package nl.runnable.lims.domain.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.ManagedBean;
import javax.inject.Inject;

import nl.runnable.lims.domain.Analysis;
import nl.runnable.lims.domain.AnalysisService;
import nl.runnable.lims.domain.AssayTarget;
import nl.runnable.lims.domain.AssayTargetRepository;
import nl.runnable.lims.domain.Target;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ManagedBean
public class DefaultAnalysisService implements AnalysisService {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	/* Dependencies */

	@Inject
	private AssayTargetRepository assayTargetRepository;

	/* Operations */

	@Override
	public List<Analysis> resolveAnalysesByTargets(final List<Target> targets) {
		final List<Analysis> analyses = new ArrayList<Analysis>();
		for (final Target target : targets) {
			final AssayTarget assayTarget = resolveAssayTarget(target);
			if (assayTarget != null) {
				final Analysis analysis = new Analysis();
				analysis.setAssayTarget(assayTarget);
				analyses.add(analysis);
			} else {
				if (logger.isDebugEnabled()) {
					logger.debug("Cannot find assay for target {}", target.getName());
				}
				analyses.add(null);
			}
		}
		return analyses;
	}

	/* Utility operations */

	protected AssayTarget resolveAssayTarget(final Target target) {
		AssayTarget assayTarget = null;
		final List<AssayTarget> assayTargets = getAssayTargetRepository().findByTargetIn(target);
		if (assayTargets.size() == 1) {
			assayTarget = assayTargets.get(0);
		} else if (assayTargets.size() > 1) {
			if (logger.isDebugEnabled()) {
				logger.debug("Found multiple assays for target {}. Assay must be determined manually.",
						target.getName());
			}
		}
		return assayTarget;
	}

	/* Dependencies */

	public void setAssayTargetRepository(final AssayTargetRepository assayTargetRepository) {
		this.assayTargetRepository = assayTargetRepository;
	}

	protected AssayTargetRepository getAssayTargetRepository() {
		return assayTargetRepository;
	}

}
