package nl.runnable.lims.webapp.api;

import java.util.Collections;
import java.util.List;

/**
 * Defines the response from a {@link CtCalculationController}.
 * 
 * @author Laurens Fridael
 * 
 */
class CtCalculationResponse {

	private List<CtCalculation> ctCalculations = Collections.emptyList();

	public List<CtCalculation> getCtCalculations() {
		return ctCalculations;
	}

	public void setCtCalculations(final List<CtCalculation> ctCalculations) {
		this.ctCalculations = ctCalculations;
	}

}
