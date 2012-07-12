package nl.runnable.lims.sds.excel;

import static org.junit.Assert.*;
import nl.runnable.lims.sds.ExperimentMetadata;

/**
 * Convenience abstract base class containing common assertion logic.
 * 
 * @author Laurens Fridael
 * 
 */
abstract class AbstractDataReaderTest {

	protected void verifyExperimentMetadata(final ExperimentMetadata metadata) {
		assertEquals("96well", metadata.getBlockType());
		assertEquals("TAQMAN", metadata.getChemistry());
		assertEquals("C:\\Applied Biosystems\\7500\\bin\\111213 mrsa fec adv plm clos tb cmv",
				metadata.getExperimentFilename());
		assertEquals("sds7500", metadata.getInstrumentType());
		assertEquals("ROX", metadata.getPassiveReference());
	}

}
