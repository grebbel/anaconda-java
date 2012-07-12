package nl.runnable.lims.webapp;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import nl.runnable.lims.domain.Location;

import org.junit.Before;
import org.junit.Test;

public class PlateLayoutTest {

	private PlateLayout plateLayout;

	@Before
	public void createPlateLayout() {
		plateLayout = new PlateLayout();
		final List<Location> wells = new ArrayList<Location>();
		wells.add(Location.forString("A1"));
		wells.add(Location.forString("A13")); /* Column out of range */
		wells.add(Location.forString("H12"));
		wells.add(Location.forString("I1")); /* Row out of range */
		plateLayout.setWells(wells);
	}

	@Test
	public void testGetColumnsByRows() {
		final List<List<WellLayout>> columnsByRows = plateLayout.getColumnsByRows();
		assertEquals(plateLayout.getRowCount(), columnsByRows.size());
		for (final List<WellLayout> columns : columnsByRows) {
			assertEquals(plateLayout.getColumnCount(), columns.size());
		}
		for (int row = 1; row <= plateLayout.getRowCount(); row++) {
			for (int column = 1; column <= plateLayout.getColumnCount(); column++) {
				final WellLayout location = columnsByRows.get(row - 1).get(column - 1);
				if ((row == 1 && column == 1) || (row == 8 && column == 12)) {
					assertNotNull(location);
				} else {
					assertNull(location);
				}
			}
		}
	}

}
