package nl.runnable.lims.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Represents a color, consisting of an RGB triplet.
 * 
 * @author Laurens Fridael
 * 
 */
@Entity
@Table(name = "color")
public class Color extends AbstractEntity {

	@Column(length = 6, nullable = false)
	private String hex;

	@Column(name = "name", nullable = false, length = 50)
	private String name;

	@Column(name = "red")
	private int red;

	@Column(name = "green")
	private int green;

	@Column(name = "blue")
	private int blue;

	@Column(name = "luminance")
	private double luminance;

	protected Color() {
	}

	public Color(final int red, final int green, final int blue) {
		this.red = red;
		this.green = green;
		this.blue = blue;
	}

	public String getHex() {
		return hex;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	protected void setHex(final String hex) {
		this.hex = hex;
	}

	public int getRed() {
		return red;
	}

	public void setRed(final int red) {
		this.red = red;
	}

	public int getGreen() {
		return green;
	}

	public void setGreen(final int green) {
		this.green = green;
	}

	public int getBlue() {
		return blue;
	}

	public void setBlue(final int blue) {
		this.blue = blue;
	}

	public double getLuminance() {
		return luminance;
	}

	public void setLuminance(final double luminance) {
		this.luminance = luminance;
	}

	@Override
	public String toString() {
		return "Color [red=" + red + ", green=" + green + ", blue=" + blue + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + blue;
		result = prime * result + green;
		result = prime * result + red;
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Color other = (Color) obj;
		if (blue != other.blue) {
			return false;
		}
		if (green != other.green) {
			return false;
		}
		if (red != other.red) {
			return false;
		}
		return true;
	}

	public java.awt.Color toAwtColor() {
		return new java.awt.Color(red, green, blue);
	}

}
