package nl.runnable.lims;

/**
 * Base class of the LIMS-specific exception hierarchy. Clients should preferably use a subclass to indicate more
 * detail.
 * 
 * @author Laurens Fridael
 * 
 */
public class LimsException extends RuntimeException {

	private static final long serialVersionUID = -2752873753120900683L;

	public LimsException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public LimsException(final String message) {
		super(message);
	}

	public LimsException(final Throwable cause) {
		super(cause);
	}

	public LimsException() {
	}

}
