package nl.runnable.lims.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Represents amplification data of a sample in a given {@link Well} from an experiment cycle.
 * 
 * @author Laurens Fridael
 * 
 */
@Embeddable
public class Amplification implements Comparable<Amplification> {

	public static Amplification forCycleRnAndDrn(final int cycle, final double rn, final double drn) {
		return new Amplification(cycle, rn, drn);
	}

	@Column(name = "cycle")
	private int cycle;

	@Column(name = "rn")
	private double rn;

	@Column(name = "delta_rn")
	private double drn;

	protected Amplification() {
	}

	protected Amplification(final int cycle, final double rn, final double drn) {
		this.cycle = cycle;
		this.rn = rn;
		this.drn = drn;
	}

	public int getCycle() {
		return cycle;
	}

	public void setCycle(final int cycle) {
		this.cycle = cycle;
	}

	public double getRn() {
		return rn;
	}

	public void setRn(final double rn) {
		this.rn = rn;
	}

	public double getDrn() {
		return drn;
	}

	public void setDrn(final double drn) {
		this.drn = drn;
	}

	@Override
	public int compareTo(final Amplification o) {
		int compare = 0;
		if (this.cycle < o.cycle) {
			compare = -1;
		} else if (this.cycle > o.cycle) {
			compare = 1;
		}
		return compare;
	}

}
