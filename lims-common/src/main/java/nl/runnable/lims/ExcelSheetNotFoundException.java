package nl.runnable.lims;

/**
 * Indicates a particular Excel sheet could not be found.
 * 
 * @author Laurens Fridael
 * 
 */
public class ExcelSheetNotFoundException extends UnexpectedDataFormatException {

	private static final long serialVersionUID = -8499865704995619198L;

	private final String sheetName;

	public ExcelSheetNotFoundException(final String sheetName) {
		super("Cannot find sheet \"" + sheetName + ". Make sure you provide the correct Excel workbook.");
		this.sheetName = sheetName;
	}

	public String getSheetName() {
		return sheetName;
	}
}
