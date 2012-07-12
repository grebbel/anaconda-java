package nl.runnable.lims.domain;

import java.util.List;

public interface AmplificationRepository {

	List<Amplification> findByDrnLessThanOrderByCycleDesc(double treshold);

	List<Amplification> findByDrnGreaterThanAndCycleGreaterThanOrderByCycleAsc(double treshold, int minimumCycle);
}
