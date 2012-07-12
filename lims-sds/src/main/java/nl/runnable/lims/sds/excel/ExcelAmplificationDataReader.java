package nl.runnable.lims.sds.excel;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;

import nl.runnable.lims.domain.Amplification;
import nl.runnable.lims.domain.AmplificationImport;
import nl.runnable.lims.domain.Location;
import nl.runnable.lims.sds.AmplificationDataHandler;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.springframework.util.Assert;

/**
 * Reads amplification data from SDS Excel exports.
 * 
 * @author Laurens Fridael
 * @see ExcelDataReader
 */
@ManagedBean
public class ExcelAmplificationDataReader extends AbstractExcelDataReader<AmplificationDataHandler> {

	/* Configuration */

	private DataColumnConfiguration dataColumnConfiguration = new DataColumnConfiguration();

	/**
	 * Holds {@link ColumnDefinition}s for all required columns. This is initialized by {@link #initRequiredColumns()}
	 * and used by {@link #parseDataRow(HSSFRow, AmplificationDataHandler)}.
	 */
	private List<ColumnDefinition> requiredColumns = Collections.emptyList();

	/* Operations */

	@Override
	protected void parseDataRow(final HSSFRow row, final AmplificationDataHandler handler) {
		if (isDataRow(row) && (isDataHeaderRow(row) || containsRequiredColumnsWithData(row) == false)) {
			return;
		}
		final DataColumnConfiguration dcc = getDataColumnConfiguration();

		final int cycle = (int) row.getCell(dcc.getCycleColumn().getIndex()).getNumericCellValue();
		final double rn = row.getCell(dcc.getRnColumn().getIndex()).getNumericCellValue();
		final double drn = row.getCell(dcc.getDeltaRnColumn().getIndex()).getNumericCellValue();
		final Amplification amplification = Amplification.forCycleRnAndDrn(cycle, rn, drn);
		final String targetName = row.getCell(dcc.getTargetNameColumn().getIndex()).getStringCellValue();
		final Location location = Location.forString(row.getCell(dcc.getLocationColumn().getIndex())
				.getStringCellValue());
		handler.handleAmplification(new AmplificationImport(amplification, targetName, location));
	}

	/*
	 * Tests if a given {@link HSSFRow} contains enough physical cells to actually contain the well column. This usually
	 * happens for empty separator rows between the experiment metadata and the amplification data.
	 */
	protected boolean isDataRow(final HSSFRow row) {
		return getDataColumnConfiguration().getLocationColumn().getIndex() + 1 < row.getPhysicalNumberOfCells();
	}

	/**
	 * Tests if a given {@link HSSFRow} is a header row. The implementation considers the row to be a header if the cell
	 * at the well column contains the expected header label. ("Well" by default.)
	 * 
	 * @param row
	 * @return
	 */
	protected boolean isDataHeaderRow(final HSSFRow row) {
		return getDataColumnConfiguration().getWellHeader().equalsIgnoreCase(
				row.getCell(getDataColumnConfiguration().getLocationColumn().getIndex()).getStringCellValue());
	}

	/**
	 * Tests if the row contains required columns and if the columns contain actual data.
	 * 
	 * @param row
	 * @return
	 */
	protected boolean containsRequiredColumnsWithData(final HSSFRow row) {
		for (final ColumnDefinition column : getRequiredColumns()) {
			final HSSFCell cell = row.getCell(column.getIndex());
			if (cell == null || cell.getCellType() != column.getCellType()) {
				return false;
			}
		}
		return true;
	}

	/* Configuration */

	public void setDataColumnConfiguration(final DataColumnConfiguration dataColumnConfiguration) {
		Assert.notNull(dataColumnConfiguration);
		this.dataColumnConfiguration = dataColumnConfiguration;
	}

	protected DataColumnConfiguration getDataColumnConfiguration() {
		return dataColumnConfiguration;
	}

	protected List<ColumnDefinition> getRequiredColumns() {
		return requiredColumns;
	}

	/**
	 * Initializes the required columns. Currently all columns are required.
	 */
	@PostConstruct
	public void initRequiredColumns() {
		final DataColumnConfiguration cdc = getDataColumnConfiguration();
		requiredColumns = Arrays.asList(cdc.getLocationColumn(), cdc.getCycleColumn(), cdc.getTargetNameColumn(),
				cdc.getRnColumn(), cdc.getDeltaRnColumn());
	}

	/* Utility classes */

	/**
	 * Contains configuration for the data columns.
	 * 
	 * @author Laurens Fridael
	 * 
	 */
	public static class DataColumnConfiguration {

		private String wellHeader = "Well";

		private ColumnDefinition locationColumn = ColumnDefinition.forIndexAndCellType(0, HSSFCell.CELL_TYPE_STRING);

		private ColumnDefinition cycleColumn = ColumnDefinition.forIndexAndCellType(1, HSSFCell.CELL_TYPE_NUMERIC);

		private ColumnDefinition targetNameColumn = ColumnDefinition.forIndexAndCellType(2, HSSFCell.CELL_TYPE_STRING);

		private ColumnDefinition rnColumn = ColumnDefinition.forIndexAndCellType(3, HSSFCell.CELL_TYPE_NUMERIC);

		private ColumnDefinition deltaRnColumn = ColumnDefinition.forIndexAndCellType(4, HSSFCell.CELL_TYPE_NUMERIC);

		public String getWellHeader() {
			return wellHeader;
		}

		public void setWellHeader(final String wellHeader) {
			this.wellHeader = wellHeader;
		}

		public ColumnDefinition getLocationColumn() {
			return locationColumn;
		}

		public void setLocationColumn(final ColumnDefinition locationColumn) {
			this.locationColumn = locationColumn;
		}

		public ColumnDefinition getCycleColumn() {
			return cycleColumn;
		}

		public void setCycleColumn(final ColumnDefinition cycleColumn) {
			this.cycleColumn = cycleColumn;
		}

		public ColumnDefinition getTargetNameColumn() {
			return targetNameColumn;
		}

		public void setTargetNameColumn(final ColumnDefinition targetNameColumn) {
			this.targetNameColumn = targetNameColumn;
		}

		public ColumnDefinition getRnColumn() {
			return rnColumn;
		}

		public void setRnColumn(final ColumnDefinition rnColumn) {
			this.rnColumn = rnColumn;
		}

		public ColumnDefinition getDeltaRnColumn() {
			return deltaRnColumn;
		}

		public void setDeltaRnColumn(final ColumnDefinition deltaRnColumn) {
			this.deltaRnColumn = deltaRnColumn;
		}

	}

}
