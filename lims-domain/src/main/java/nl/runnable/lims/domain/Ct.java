package nl.runnable.lims.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Ct {

	public static Ct forValueAndTreshold(final double value, final double treshold) {
		return new Ct(value, treshold);
	}

	@Column(name = "ct_value")
	private double value;

	@Column(name = "ct_treshold")
	private double treshold;

	Ct() {
		this(0, 0);
	}

	protected Ct(final double value, final double treshold) {
		this.value = value;
		this.treshold = treshold;
	}

	public void setValue(final double value) {
		this.value = value;
	}

	public double getValue() {
		return value;
	}

	public void setTreshold(final double treshold) {
		this.treshold = treshold;
	}

	public double getTreshold() {
		return treshold;
	}

	@Override
	public String toString() {
		return "Ct [value=" + value + ", treshold=" + treshold + "]";
	}

}
