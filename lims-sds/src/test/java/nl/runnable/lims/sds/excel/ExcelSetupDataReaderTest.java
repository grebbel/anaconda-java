package nl.runnable.lims.sds.excel;

import java.io.IOException;

import nl.runnable.lims.sds.ExperimentMetadata;
import nl.runnable.lims.sds.SetupDataHandler;

import org.junit.Before;
import org.junit.Test;

/**
 * {@link ExcelSetupDataReader} unit test. This test loads an Excel sheet and verifies that its contents match the
 * expected values.
 * 
 * @author Laurens Fridael
 * 
 */
public class ExcelSetupDataReaderTest extends AbstractDataReaderTest {

	private ExperimentMetadata metadata;

	@Before
	public void readSetupExcelSheet() throws IOException {
		final ExcelSetupDataReader dataReader = new ExcelSetupDataReader();
		dataReader.readExcelWorkbook(getClass().getResourceAsStream("ExcelSetupDataReaderTest-fixture.xls"),
				new SetupDataHandler() {

					@Override
					public void handleExperimentMetadata(final ExperimentMetadata metadata) {
						ExcelSetupDataReaderTest.this.metadata = metadata;
					}
				});
	}

	@Test
	public void verifyContents() {
		verifyExperimentMetadata(metadata);
	}
}
