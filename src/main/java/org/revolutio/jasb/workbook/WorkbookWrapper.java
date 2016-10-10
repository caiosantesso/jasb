package org.revolutio.jasb.workbook;

import java.nio.file.Path;

import org.revolutio.jasb.FileFormat;

public interface WorkbookWrapper {

	FileFormat getFileFormat();
	
	Path getPath();

	SpreadsheetWrapper getSpreadsheet(String sheetName);
	
}
