package nl.runnable.lims;

/**
 * Indicates that the given data is not in the expected format.
 * 
 * @author Laurens Fridael
 * 
 */
public class UnexpectedDataFormatException extends LimsException {

	private static final long serialVersionUID = 6866362172100076443L;

	public UnexpectedDataFormatException(final String message) {
		super(message);
	}

	public UnexpectedDataFormatException(final String message, final Throwable cause) {
		super(message, cause);
	}
}
