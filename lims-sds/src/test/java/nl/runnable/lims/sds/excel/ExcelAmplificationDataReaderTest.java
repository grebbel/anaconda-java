package nl.runnable.lims.sds.excel;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.runnable.lims.domain.Amplification;
import nl.runnable.lims.domain.AmplificationImport;
import nl.runnable.lims.domain.DomainConstants;
import nl.runnable.lims.domain.Location;
import nl.runnable.lims.sds.AmplificationDataHandler;
import nl.runnable.lims.sds.ExperimentMetadata;

import org.junit.Before;
import org.junit.Test;

/**
 * {@link ExcelAmplificationDataReader} unit test. This test loads an Excel sheet and verifies that its contents match
 * the expected values.
 * 
 * @author Laurens Fridael
 * 
 */
public class ExcelAmplificationDataReaderTest extends AbstractDataReaderTest {

	private ExperimentMetadata metadata;

	private final Map<LocationTarget, List<Amplification>> amplificationsByLocationAndTarget = new HashMap<LocationTarget, List<Amplification>>();

	private final Map<Location, List<Amplification>> amplificationsByLocation = new HashMap<Location, List<Amplification>>();

	@Before
	public void readAmplificationDataExcelSheet() throws IOException {
		final ExcelAmplificationDataReader dataReader = new ExcelAmplificationDataReader();
		dataReader.initRequiredColumns();
		dataReader.readExcelWorkbook(getClass().getResourceAsStream("ExcelAmplificationDataReader-fixture.xls"),
				new AmplificationDataHandler() {

					@Override
					public void handleExperimentMetadata(final ExperimentMetadata metadata) {
						ExcelAmplificationDataReaderTest.this.metadata = metadata;
					}

					@Override
					public void handleAmplification(final AmplificationImport amplificationImport) {
						final Amplification amplification = amplificationImport.getAmplification();
						final Location location = amplificationImport.getLocation();
						final LocationTarget locationTarget = new LocationTarget(location, amplificationImport
								.getTargetName());
						if (amplificationsByLocationAndTarget.containsKey(locationTarget) == false) {
							amplificationsByLocationAndTarget.put(locationTarget, new ArrayList<Amplification>());
						}
						amplificationsByLocationAndTarget.get(locationTarget).add(amplification);
						if (amplificationsByLocation.containsKey(location) == false) {
							amplificationsByLocation.put(location, new ArrayList<Amplification>());
						}
						amplificationsByLocation.get(location).add(amplification);
					}
				});

	}

	@Test
	public void verifyContents() {
		verifyExperimentMetadata(metadata);
		verifyAmplificationData();
	}

	private void verifyAmplificationData() {
		for (int row = 1; row <= 8; row++) {
			for (int column = 1; column <= 12; column++) {
				if (row == 1 && (column == 5 || column == 8)) {
					/* Wells A5 and A8 contains no data in the test fixture. */
					break;
				}
				assertTrue(amplificationsByLocation.containsKey(Location.forRowAndColumn(row, column)));
			}
		}
		/* Compare some random RN values. */
		assertEquals(3.05186676979064, amplification("A1", "Martineau", 1).getRn(),
				DomainConstants.MAXIMUM_RN_DIFFERENCE);
		assertEquals(0.532314479351043, amplification("H12", "TBC", 16).getRn(), DomainConstants.MAXIMUM_RN_DIFFERENCE);
		assertEquals(0.674397468566894, amplification("F11", "Legionella species", 18).getRn(),
				DomainConstants.MAXIMUM_RN_DIFFERENCE);
	}

	private Amplification amplification(final String location, final String targetName, final int cycle) {
		return amplificationsByLocationAndTarget.get(new LocationTarget(Location.forString(location), targetName)).get(
				cycle - 1);
	}

	/**
	 * Composite key for locating a given amplification data by well and target name.
	 * 
	 * @author Laurens Fridael
	 * 
	 */
	private static class LocationTarget {

		private final Location location;

		private final String targetName;

		LocationTarget(final Location location, final String targetName) {
			super();
			this.location = location;
			this.targetName = targetName;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((location == null) ? 0 : location.hashCode());
			result = prime * result + ((targetName == null) ? 0 : targetName.hashCode());
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
			final LocationTarget other = (LocationTarget) obj;
			if (location == null) {
				if (other.location != null) {
					return false;
				}
			} else if (!location.equals(other.location)) {
				return false;
			}
			if (targetName == null) {
				if (other.targetName != null) {
					return false;
				}
			} else if (!targetName.equals(other.targetName)) {
				return false;
			}
			return true;
		}

	}
}
