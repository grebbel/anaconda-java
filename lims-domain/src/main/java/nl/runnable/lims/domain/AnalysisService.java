package nl.runnable.lims.domain;

import java.util.List;

public interface AnalysisService {

	List<Analysis> resolveAnalysesByTargets(List<Target> targets);

}
