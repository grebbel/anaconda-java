package nl.runnable.lims.util;

/**
 * Provides static utility methods for geometry calculations.
 * <p>
 * Code taken from: http://workshop.evolutionzone.com/2007/09/10/code-2d-line-intersection/
 * 
 * @author Laurens Fridael
 * 
 */
public class GeometryUtil {

	// calculates intersection and checks for parallel lines.
	// also checks that the intersection point is actually on
	// the line segment p1-p2
	public static Point findIntersection(final Point p1, final Point p2, final Point p3, final Point p4) {
		double xD1, yD1, xD2, yD2, xD3, yD3;
		double dot, deg, len1, len2;
		double segmentLen1, segmentLen2;
		double ua, ub, div;

		// calculate differences
		xD1 = p2.x - p1.x;
		xD2 = p4.x - p3.x;
		yD1 = p2.y - p1.y;
		yD2 = p4.y - p3.y;
		xD3 = p1.x - p3.x;
		yD3 = p1.y - p3.y;

		// calculate the lengths of the two lines
		len1 = Math.sqrt(xD1 * xD1 + yD1 * yD1);
		len2 = Math.sqrt(xD2 * xD2 + yD2 * yD2);

		// calculate angle between the two lines.
		dot = (xD1 * xD2 + yD1 * yD2); // dot product
		deg = dot / (len1 * len2);

		// if abs(angle)==1 then the lines are parallell,
		// so no intersection is possible
		if (Math.abs(deg) == 1) {
			return null;
		}

		// find intersection Pt between two lines
		final Point pt = new Point(0, 0);
		div = yD2 * xD1 - xD2 * yD1;
		ua = (xD2 * yD3 - yD2 * xD3) / div;
		ub = (xD1 * yD3 - yD1 * xD3) / div;
		pt.x = p1.x + ua * xD1;
		pt.y = p1.y + ua * yD1;

		// calculate the combined length of the two segments
		// between Pt-p1 and Pt-p2
		xD1 = pt.x - p1.x;
		xD2 = pt.x - p2.x;
		yD1 = pt.y - p1.y;
		yD2 = pt.y - p2.y;
		segmentLen1 = Math.sqrt(xD1 * xD1 + yD1 * yD1) + Math.sqrt(xD2 * xD2 + yD2 * yD2);

		// calculate the combined length of the two segments
		// between Pt-p3 and Pt-p4
		xD1 = pt.x - p3.x;
		xD2 = pt.x - p4.x;
		yD1 = pt.y - p3.y;
		yD2 = pt.y - p4.y;
		segmentLen2 = Math.sqrt(xD1 * xD1 + yD1 * yD1) + Math.sqrt(xD2 * xD2 + yD2 * yD2);

		// if the lengths of both sets of segments are the same as
		// the lenghts of the two lines the point is actually
		// on the line segment.

		// if the point isn't on the line, return null
		if (Math.abs(len1 - segmentLen1) > 0.01 || Math.abs(len2 - segmentLen2) > 0.01) {
			return null;
		}

		// return the valid intersection
		return pt;
	}

}
