package nl.runnable.lims.util;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Unit test for {@link GeometryUtil}.
 * 
 * @author Laurens Fridael
 * 
 */
public class GeometryUtilTest {

	@Test
	public void testFindIntersection1() {
		final Point a1 = new Point(0, 5);
		final Point a2 = new Point(10, 5);
		final Point b1 = new Point(5, 0);
		final Point b2 = new Point(5, 10);
		final Point intersection = GeometryUtil.findIntersection(a1, a2, b1, b2);
		assertEquals(5, intersection.getX(), 0.0);
		assertEquals(5, intersection.getY(), 0.0);
	}

	@Test
	public void testFindIntersection2() {
		final Point a1 = new Point(0, 0);
		final Point a2 = new Point(10, 10);
		final Point b1 = new Point(10, 0);
		final Point b2 = new Point(0, 10);
		final Point intersection = GeometryUtil.findIntersection(a1, a2, b1, b2);
		assertEquals(5, intersection.getX(), 0.0);
		assertEquals(5, intersection.getY(), 0.0);
	}

	@Test
	public void testFindIntersection3() {
		final Point a1 = new Point(6, 0);
		final Point a2 = new Point(6, 10);
		final Point b1 = new Point(7, 0);
		final Point b2 = new Point(5, 2);
		final Point intersection = GeometryUtil.findIntersection(a1, a2, b1, b2);
		assertEquals(6, intersection.getX(), 0.0);
		assertEquals(1, intersection.getY(), 0.0);
	}
}
