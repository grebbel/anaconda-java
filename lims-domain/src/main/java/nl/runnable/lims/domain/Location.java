package nl.runnable.lims.domain;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.util.Assert;

/**
 * Represents the well of a {@link Well} on a {@link Plate}.
 * 
 * @author Laurens Fridael
 * 
 */
public class Location implements Comparable<Location> {

	private static Pattern STRING_PATTERN = Pattern.compile("([A-Z])(\\d+)");

	/**
	 * Creates a {@link Location} for the given row and column.
	 * 
	 * @param row
	 * @param column
	 * @return
	 */
	public static Location forRowAndColumn(final int row, final int column) {
		final Location location = new Location();
		location.setRow(row);
		location.setColumn(column);
		return location;
	}

	public static Location forString(final String str) {
		Assert.hasText(str, "String cannot be empty.");
		final Matcher matcher = STRING_PATTERN.matcher(str.toUpperCase());
		if (matcher.matches() == false) {
			throw new IllegalArgumentException("Unhandled well format: " + str);
		}
		final int row = matcher.group(1).charAt(0) - ('A' - 1);
		final int column = Integer.parseInt(matcher.group(2));
		return forRowAndColumn(row, column);
	}

	private int row = 0;

	private int column = 0;

	public int getRow() {
		return row;
	}

	public void setRow(final int row) {
		this.row = row;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(final int column) {
		this.column = column;
	}

	@Override
	public String toString() {
		return String.format("%d,%d", row, column);
	}

	/**
	 * Obtains the well in String format. Note that this is different from {@link #toString()}.
	 * 
	 * @return
	 */
	public String getString() {
		return String.format("%c%d", ('A' - 1) + (char) row, column);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + row;
		result = prime * result + column;
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
		final Location other = (Location) obj;
		if (row != other.row) {
			return false;
		}
		if (column != other.column) {
			return false;
		}
		return true;
	}

	@Override
	public int compareTo(final Location other) {
		int compare = 0;
		if (this.row < other.row) {
			compare = -1;
		} else if (this.row > other.row) {
			compare = 1;
		} else if (this.column < other.column) {
			compare = -1;
		} else if (this.column > other.column) {
			compare = 1;
		}
		return compare;
	}

}
