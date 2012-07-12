package nl.runnable.lims;

public class UnexpectedExcelSheetCount extends UnexpectedDataFormatException {

	private static final long serialVersionUID = -2942255229302854355L;

	private final int expectedCount;

	private final int actualCount;

	public UnexpectedExcelSheetCount(final int expectedCount, final int actualCount) {
		super("Expected to find " + expectedCount + " sheet(s) in the Excel workbook, but found " + actualCount
				+ " sheet(s). " + "Make sure you provide the correct Excel sheet.");
		this.expectedCount = expectedCount;
		this.actualCount = actualCount;
	}

	public int getExpectedCount() {
		return expectedCount;
	}

	public int getActualCount() {
		return actualCount;
	}
}
