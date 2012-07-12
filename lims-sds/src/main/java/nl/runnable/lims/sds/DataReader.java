package nl.runnable.lims.sds;

import java.io.IOException;
import java.io.InputStream;

import nl.runnable.lims.UnexpectedDataFormatException;

/**
 * Defines operations for reading various SDS data.
 * 
 * @author Laurens Fridael
 * 
 */
public interface DataReader {

	/**
	 * Reads setup data from a given {@link InputStream} and passes it to a {@link SetupDataHandler}.
	 * 
	 * @param data
	 * @param handler
	 * @throws IOException
	 * @throws UnexpectedDataFormatException
	 */
	void readSetupData(InputStream data, SetupDataHandler handler) throws IOException, UnexpectedDataFormatException;

	/**
	 * Reads amplification data from a given {@link InputStream} and passes it to an {@link AmplificationDataHandler}.
	 * 
	 * @param data
	 * @param handler
	 * @throws IOException
	 * @throws UnexpectedDataFormatException
	 */
	void readAmplificationData(InputStream data, AmplificationDataHandler handler) throws IOException,
			UnexpectedDataFormatException;

}
