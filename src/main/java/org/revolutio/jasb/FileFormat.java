package org.revolutio.jasb;

/**
 * https://wiki.openoffice.org/wiki/Documentation/FAQ/Calc/Miscellaneous/What's_the_maximum_number_of_rows_and_cells_for_a_spreadsheet_file%3F
 * 
 * @author Caio Santesso
 *
 */
public enum FileFormat {
	/**
	 * Max column name = IV.
	 */
	XLS(65_536, 256),
	/**
	 * Max column name = XFD.
	 */
	XLSX(1_048_576, 16_384),
	/**
	* 
	*/
	ODS_1_1(32_000, 256),
	/**
	* 
	*/
	ODS_3_0(65_536, 256),
	/**
	* 
	*/
	ODS(1_048_576, 1_024);

	private final int maxRows;
	private final int maxColumns;

	private FileFormat(int maxRowIndex, int maxColumnIndex) {
		this.maxRows = maxRowIndex;
		this.maxColumns = maxColumnIndex;
	}

	public int maxRowIndex() {
		return maxRows;
	}

	public int maxColumnIndex() {
		return maxColumns;
	}
}
