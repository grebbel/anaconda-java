package nl.runnable.lims.webapp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import nl.runnable.lims.domain.Location;

import org.springframework.util.Assert;

/**
 * Represents the layout of a plate. This class is used as a utility for constructing the plate layout in an HTML view.
 * 
 * @author Laurens Fridael
 * 
 */
public class PlateLayout {

	/* Configuration */

	private int rowCount = 8;

	private int columnCount = 12;

	private List<Location> wells = Collections.emptyList();

	/**
	 * Returns a nested {@link List} that is sized the number of rowCount by the number of columnCount.
	 * 
	 * @return
	 */
	public List<List<WellLayout>> getColumnsByRows() {
		final List<List<WellLayout>> columnsByRow = new ArrayList<List<WellLayout>>();
		final List<Location> wellsInRange = getWellsInRange();
		int wellIndex = 0;
		for (int row = 1; row <= rowCount; row++) {
			final List<WellLayout> columns = new ArrayList<WellLayout>();
			columnsByRow.add(columns);
			for (int column = 1; column <= columnCount; column++) {
				final Location location = wellsInRange.get(wellIndex);
				if (location.getRow() == row && location.getColumn() == column) {
					columns.add(new WellLayout(location));
					wellIndex++;
				} else {
					columns.add(null);
				}
			}
		}
		return columnsByRow;
	}

	/* Configuration */

	public void setRowCount(final int rows) {
		this.rowCount = rows;
	}

	public int getRowCount() {
		return rowCount;
	}

	public void setColumnCount(final int columns) {
		this.columnCount = columns;
	}

	public int getColumnCount() {
		return columnCount;
	}

	public void setWells(final List<Location> wells) {
		Assert.notNull(wells);
		this.wells = new ArrayList<Location>(wells);
		Collections.sort(this.wells);
	}

	public List<Location> getWells() {
		return wells;
	}

	/**
	 * Obtains a list of well location that are in range.
	 * 
	 * @return
	 */
	protected List<Location> getWellsInRange() {
		final List<Location> wells = new ArrayList<Location>();
		for (final Location well : getWells()) {
			if (well.getColumn() <= getColumnCount() && well.getRow() <= getRowCount()) {
				wells.add(well);
			}
		}
		return wells;
	}

}
