package nl.runnable.lims.webapp.api;

public class EntityException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public EntityException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public EntityException(final String message) {
		super(message);
	}
}
