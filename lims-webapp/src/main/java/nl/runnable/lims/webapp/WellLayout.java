package nl.runnable.lims.webapp;

import java.util.List;

import nl.runnable.lims.domain.Location;

public class WellLayout {

	private final Location location;

	private List<String> targets;

	public WellLayout(final Location location) {
		this.location = location;
	}

	public Location getLocation() {
		return location;
	}

	public void setTargets(final List<String> targets) {
		this.targets = targets;
	}

	public List<String> getTargets() {
		return targets;
	}

}
