package org.revolutio.jasb.workbook.internal;

import org.apache.poi.ss.usermodel.Sheet;
import org.revolutio.jasb.workbook.SpreadsheetWrapper;
import org.revolutio.jasb.workbook.WorkbookWrapper;

public class Spreadsheets {

	public static SpreadsheetWrapper newInstance(Sheet sheet, POIWorkbook workbook) {
		return POISpreadsheet.newInstance(sheet, workbook);
	}
	
}
