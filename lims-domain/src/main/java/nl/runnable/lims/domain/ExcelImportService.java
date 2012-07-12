package nl.runnable.lims.domain;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Defines operations for importing data from Excel sheets.
 * 
 * @author Laurens Fridael
 * 
 */
public interface ExcelImportService {

	List<RequestImport> importRequests(InputStream excelWorksheet) throws IOException;

}
