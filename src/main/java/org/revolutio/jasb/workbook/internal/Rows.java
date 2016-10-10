package org.revolutio.jasb.workbook.internal;

import org.apache.poi.ss.usermodel.Row;
import org.revolutio.jasb.workbook.RowWrapper;

public class Rows {

	public static RowWrapper newInstance(Row row, POISpreadsheet spreadsheet) {
		return POIRow.newInstance(row, spreadsheet);
	}

}
