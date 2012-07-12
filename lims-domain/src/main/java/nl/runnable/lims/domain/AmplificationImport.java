package nl.runnable.lims.domain;

public class AmplificationImport {

	private final Amplification amplification;

	private final String targetName;

	private final Location location;

	public AmplificationImport(final Amplification amplification, final String targetName, final Location location) {
		this.amplification = amplification;
		this.targetName = targetName;
		this.location = location;
	}

	public Amplification getAmplification() {
		return amplification;
	}

	public String getTargetName() {
		return targetName;
	}

	public Location getLocation() {
		return location;
	}

}
