package nl.runnable.lims.domain.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.ManagedBean;

import nl.runnable.lims.ExcelSheetNotFoundException;
import nl.runnable.lims.domain.RequestImport;
import nl.runnable.lims.domain.ExcelImportService;
import nl.runnable.lims.domain.Request;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

@ManagedBean
public class DefaultExcelImportService implements ExcelImportService {

	private static final String DATA_SHEET_NAME = "data";

	private static final int DATE_COLUMN_INDEX = 0;

	private static final int TOKEN_COLUMN_INDEX = 1;

	private static final int DESCRIPTION_COLUMN_INDEX = 2;

	private static final int SAMPLE_TYPE_COLUMN_INDEX = 3;

	private final Logger logger = LoggerFactory.getLogger(getClass());

	/* Main operations */

	@Override
	public List<RequestImport> importRequests(final InputStream excelWorksheet) throws IOException {
		Assert.notNull(excelWorksheet, "Excel worksheet data cannot be null.");

		return createAnalysisImports(getDataSheet(excelWorksheet));
	}

	/* Utility operations */

	private Sheet getDataSheet(final InputStream excelWorksheet) throws IOException {
		final Workbook workbook = new HSSFWorkbook(excelWorksheet);
		final Sheet dataSheet = workbook.getSheet(DATA_SHEET_NAME);
		if (dataSheet == null) {
			throw new ExcelSheetNotFoundException(DATA_SHEET_NAME);
		}
		return dataSheet;
	}

	private List<RequestImport> createAnalysisImports(final Sheet sheet) {
		Assert.notNull(sheet);

		final List<String> targets = getTargetNames(sheet);
		final List<RequestImport> analysImports = new ArrayList<RequestImport>();
		for (int rowIndex = sheet.getFirstRowNum() + 1, n = sheet.getLastRowNum(); rowIndex <= n; rowIndex++) {
			final Row row = sheet.getRow(rowIndex);
			if (row != null) {
				final RequestImport requestImport = createRequestImport(row, targets);
				if (requestImport != null) {
					analysImports.add(requestImport);
				}
			}
		}
		return analysImports;

	}

	private RequestImport createRequestImport(final Row row, final List<String> availableTargets) {
		Assert.notNull(row);

		Date date = null;
		final Cell dateCell = row.getCell(DATE_COLUMN_INDEX);
		if (dateCell != null && dateCell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
			date = dateCell.getDateCellValue();
		}
		if (date == null) {
			date = new Date();
		}

		final String externalId = getExternalId(row);
		if (externalId == null) {
			logger.warn("Could not extract external ID for row {}. Cannot import row.", row.getRowNum());
			return null;
		}
		final String description = row.getCell(DESCRIPTION_COLUMN_INDEX).getStringCellValue();
		final String sampleType = row.getCell(SAMPLE_TYPE_COLUMN_INDEX).getStringCellValue();

		final Request request = new Request();
		request.setDate(date);
		request.setExternalId(externalId);
		request.setDescription(description);
		// TODO: Add and populate sample type
		final List<String> targets = new ArrayList<String>();
		for (int columnIndex = SAMPLE_TYPE_COLUMN_INDEX + 1, n = row.getLastCellNum(); columnIndex <= n; columnIndex++) {
			final Cell targetCell = row.getCell(columnIndex);
			if (targetCell != null) {
				final String targetMark = targetCell.getStringCellValue();
				if (StringUtils.hasText(targetMark)) {
					final int targetIndex = columnIndex - SAMPLE_TYPE_COLUMN_INDEX - 1;
					if (targetIndex < availableTargets.size()) {
						final String target = availableTargets.get(targetIndex);
						if (target != null) {
							targets.add(target);
						}
					}
				}
			}
		}
		return new RequestImport(request, targets);
	}

	private String getExternalId(final Row row) {
		String externalId = null;
		final Cell externalIdCell = row.getCell(TOKEN_COLUMN_INDEX);
		if (externalIdCell != null) {
			switch (externalIdCell.getCellType()) {
			case Cell.CELL_TYPE_NUMERIC:
				externalId = String.valueOf(Math.round(externalIdCell.getNumericCellValue()));
				break;
			case Cell.CELL_TYPE_STRING:
				externalId = externalIdCell.getStringCellValue();
				break;
			}
		}
		return externalId;
	}

	/**
	 * Obtains the target names by scanning the first row of the given {@link Sheet}.
	 * 
	 * @param sheet
	 * @return A List of target names, with null values indicating blank columns.
	 */
	private List<String> getTargetNames(final Sheet sheet) {
		final List<String> targets = new ArrayList<String>();
		final Row headingRow = sheet.getRow(sheet.getFirstRowNum());
		for (int i = SAMPLE_TYPE_COLUMN_INDEX + 1, n = headingRow.getLastCellNum(); i < n; i++) {
			final Cell cell = headingRow.getCell(i);
			String target = cell.getStringCellValue();
			if (StringUtils.hasText(target) == false) {
				target = null;
			}
			targets.add(target);
		}
		return targets;
	}

}
