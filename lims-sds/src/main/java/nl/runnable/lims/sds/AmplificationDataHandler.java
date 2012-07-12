package nl.runnable.lims.sds;

import nl.runnable.lims.domain.AmplificationImport;

/**
 * Defines callback operations for receiving SDS amplification data from
 * {@link DataReader#readAmplificationData(java.io.InputStream, AmplificationDataHandler)}.
 * 
 * @author Laurens Fridael
 */
public interface AmplificationDataHandler extends DataHandler {

	/**
	 * Callback operation for receiving {@link AmplificationImport}s.
	 * 
	 * @param amplification
	 */
	void handleAmplification(AmplificationImport amplification);

}