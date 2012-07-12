package nl.runnable.lims.sds;

/**
 * Defines callback operations for receiving common data from a {@link DataReader}.
 * 
 * @author Laurens Fridael
 * 
 */
public interface DataHandler {
	/**
	 * Callback operation for receiving {@link ExperimentMetadata}.
	 * <p>
	 * {@link DataReader} implementations should NOT call this method if they cannot find any metadata.
	 * {@link DataHandler} implementations should NOT assume that all properties (or, in fact, any properties) are
	 * actually available.
	 * 
	 * @param metadata
	 */
	void handleExperimentMetadata(ExperimentMetadata metadata);
}
