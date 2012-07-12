package nl.runnable.lims.util;

/**
 * Utility class used by {@link GeometryUtil}.
 * 
 * @author Laurens Fridael
 * 
 */
public class Point {

	double x, y;

	public Point(final double x, final double y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public String toString() {
		return "Point [x=" + x + ", y=" + y + "]";
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

}
