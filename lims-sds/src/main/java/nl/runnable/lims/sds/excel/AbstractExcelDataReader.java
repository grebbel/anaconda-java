package nl.runnable.lims.sds.excel;

import java.io.IOException;
import java.io.InputStream;

import nl.runnable.lims.UnexpectedExcelSheetCount;
import nl.runnable.lims.sds.DataHandler;
import nl.runnable.lims.sds.DataReader;
import nl.runnable.lims.sds.ExperimentMetadata;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.util.Assert;

/**
 * Abstract base class for Excel data readers. Somewhat confusingly this class does NOT implement the {@link DataReader}
 * interface.
 * 
 * @author Laurens Fridael
 * 
 */
abstract class AbstractExcelDataReader<DataHandlerType extends DataHandler> {

	/* Configuration */

	private MetadataLabels metadataLabels = new MetadataLabels();

	/* Operations */

	/**
	 * Reads an Excel workbook and passes information to a given {@link DataHandler}. This implementation assumes the
	 * workbook contains a single sheet.
	 * 
	 * @param data
	 * @param handler
	 * @throws IOException
	 */
	public void readExcelWorkbook(final InputStream data, final DataHandlerType handler) throws IOException {
		Assert.notNull(data, "Data cannot be null.");
		Assert.notNull(handler, "Handler cannot be null.");

		final HSSFWorkbook workbook = new HSSFWorkbook(data);
		if (workbook.getNumberOfSheets() != 1) {
			throw new UnexpectedExcelSheetCount(1, workbook.getNumberOfSheets());
		}
		final HSSFSheet sheet = workbook.getSheetAt(0);
		processSheet(sheet, handler);
	}

	/**
	 * Processes the given {@link HSSFSheet}, passing information to a given {@link DataHandler}. This implementation
	 * processes the sheet on a row-by-row basis, starting with {@link ExperimentMetadata} (which it assumed to be at
	 * the top of the sheet), followed by processing of the actual data, for which subclasses must provide an
	 * implementation.
	 * 
	 * @param sheet
	 * @param handler
	 */
	protected void processSheet(final HSSFSheet sheet, final DataHandlerType handler) {
		if (sheet.getPhysicalNumberOfRows() == 0) {
			// Nothing to read.
			return;
		}
		ParseState parseState = ParseState.METADATA;
		final ExperimentMetadata metadata = new ExperimentMetadata();
		for (int i = sheet.getFirstRowNum(), n = sheet.getLastRowNum(); i <= n; i++) {
			final HSSFRow row = sheet.getRow(i);
			switch (parseState) {
			case METADATA:
				if (row != null && row.getPhysicalNumberOfCells() > 0) {
					parseExperimentMetadataRow(row, metadata);
				} else {
					// Encountered an empty row, which acts as a separator between metadata and data.
					handler.handleExperimentMetadata(metadata);
					parseState = ParseState.DATA;
				}
				break;
			case DATA:
				parseDataRow(row, handler);
				break;
			}
		}

	}

	/**
	 * Parses a row from the experiment metadata section of the Excel sheet. The implementation should populate the
	 * {@link ExperimentMetadata} accordingly.
	 * 
	 * @param row
	 * @param metadata
	 */
	protected void parseExperimentMetadataRow(final HSSFRow row, final ExperimentMetadata metadata) {
		final int firstCellNum = row.getFirstCellNum();
		if (firstCellNum > -1) {
			final String label = row.getCell(firstCellNum).getStringCellValue();
			final HSSFCell valueCell = row.getCell(firstCellNum + 1);
			if (valueCell != null) {
				final String value = valueCell.getStringCellValue();
				final MetadataLabels labels = getMetadataLabels();
				if (labels.getBlockTypeLabel().equalsIgnoreCase(label)) {
					metadata.setBlockType(value);
				} else if (labels.getChemistryLabel().equalsIgnoreCase(label)) {
					metadata.setChemistry(value);
				} else if (labels.getExperimentFilenameLabel().equalsIgnoreCase(label)) {
					metadata.setExperimentFilename(value);
				} else if (labels.getInstrumentTypeLabel().equalsIgnoreCase(label)) {
					metadata.setInstrumentType(value);
				} else if (labels.getPassiveReferenceLabel().equalsIgnoreCase(label)) {
					metadata.setPassiveReference(value);
				}
			}
		}
	}

	protected abstract void parseDataRow(HSSFRow row, DataHandlerType handler);

	/* Configuration */

	public MetadataLabels getMetadataLabels() {
		return metadataLabels;
	}

	public void setMetadataLabels(final MetadataLabels metadataLabels) {
		Assert.notNull(metadataLabels);
		this.metadataLabels = metadataLabels;
	}

	/* Utility classes */

	/**
	 * Configuration containing labels for fields in the Excel sheet that describe sample setup metadata.
	 * 
	 * @author Laurens Fridael
	 * 
	 */
	public static class MetadataLabels {

		private String blockTypeLabel = "Block Type";

		private String chemistryLabel = "Chemistry";

		private String experimentFilenameLabel = "Experiment File Name";

		private String instrumentTypeLabel = "Instrument Type";

		private String passiveReferenceLabel = "Passive Reference";

		public String getBlockTypeLabel() {
			return blockTypeLabel;
		}

		public void setBlockTypeLabel(final String blockTypeLabel) {
			this.blockTypeLabel = blockTypeLabel;
		}

		public String getChemistryLabel() {
			return chemistryLabel;
		}

		public void setChemistryLabel(final String chemistryLabel) {
			this.chemistryLabel = chemistryLabel;
		}

		public String getExperimentFilenameLabel() {
			return experimentFilenameLabel;
		}

		public void setExperimentFilenameLabel(final String experimentFilenameLabel) {
			this.experimentFilenameLabel = experimentFilenameLabel;
		}

		public String getInstrumentTypeLabel() {
			return instrumentTypeLabel;
		}

		public void setInstrumentTypeLabel(final String instrumentTypeLabel) {
			this.instrumentTypeLabel = instrumentTypeLabel;
		}

		public String getPassiveReferenceLabel() {
			return passiveReferenceLabel;
		}

		public void setPassiveReferenceLabel(final String passiveReferenceLabel) {
			this.passiveReferenceLabel = passiveReferenceLabel;
		}
	}

}
