package nl.runnable.lims.sds.excel;

/**
 * Internal utility class that contains the column index and the expected type.
 * 
 * @author Laurens Fridael
 * 
 */
class ColumnDefinition {

	/**
	 * Static factory method for creating a {@link ColumnDefinition}.
	 * 
	 * @param index
	 * @param cellType
	 * @return
	 */
	static ColumnDefinition forIndexAndCellType(final int index, final int cellType) {
		final ColumnDefinition columnDefinition = new ColumnDefinition();
		columnDefinition.setIndex(index);
		columnDefinition.setCellType(cellType);
		return columnDefinition;
	}

	private int index;

	private int cellType;

	public int getIndex() {
		return index;
	}

	public void setIndex(final int index) {
		this.index = index;
	}

	public int getCellType() {
		return cellType;
	}

	public void setCellType(final int cellType) {
		this.cellType = cellType;
	}

}
