package nl.runnable.lims.domain;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * {@link Location} unit test.
 * 
 * @author Laurens Fridael
 * 
 */
public class LocationTest {

	@Test
	public void testConstructionForStringA10() {
		final Location location = Location.forString("A10");
		assertEquals(1, location.getRow());
		assertEquals(10, location.getColumn());
	}

	@Test
	public void testConstructionForStringZ16() {
		final Location location = Location.forString("Z16");
		assertEquals(26, location.getRow());
		assertEquals(16, location.getColumn());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructionForStringThatIsInvalid() {
		Location.forString("16z");
	}

	public void testEquals() {
		assertEquals(Location.forString("A1"), Location.forString("A1"));
	}

	@Test
	public void testAsStringWithA10() {
		assertEquals("A1", Location.forRowAndColumn(1, 1).getString());
	}

	@Test
	public void testAsStringWithZ16() {
		assertEquals("Z16", Location.forRowAndColumn(26, 16).getString());
	}

	@Test
	public void testCompareToWithEqualLocations() {
		assertEquals(0, Location.forString("A1").compareTo(Location.forString("A1")));
	}

	@Test
	public void testCompareToOnTheSameRowWithLowerFirst() {
		assertEquals(-1, Location.forString("A1").compareTo(Location.forString("A2")));
	}

	@Test
	public void testCompareToOnTheSameRowWithHigherFirst() {
		assertEquals(1, Location.forString("A2").compareTo(Location.forString("A1")));
	}

	@Test
	public void testCompareToInTheSameColumnWithLowerFirst() {
		assertEquals(-1, Location.forString("A1").compareTo(Location.forString("B1")));
	}

	@Test
	public void testCompareToInTheSameColumnWithHigherFirst() {
		assertEquals(1, Location.forString("B1").compareTo(Location.forString("A1")));
	}
}
