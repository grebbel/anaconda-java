package nl.runnable.lims.domain;

import java.util.List;

/**
 * Represents a raw import of {@link Request} data from an {@link ExcelImportService} operation.
 * 
 * @author Laurens Fridael
 * 
 */
public class RequestImport {

	private final Request request;

	private final List<String> targets;

	public RequestImport(final Request request, final List<String> targets) {
		this.request = request;
		this.targets = targets;
	}

	public Request getRequest() {
		return request;
	}

	public List<String> getTargets() {
		return targets;
	}
}
