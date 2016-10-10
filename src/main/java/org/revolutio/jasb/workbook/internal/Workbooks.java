package org.revolutio.jasb.workbook.internal;

import java.nio.file.Path;

import org.revolutio.jasb.workbook.WorkbookWrapper;

public class Workbooks {

	public static WorkbookWrapper newInstance(Path workbook) {
		// TODO meta.getFileFormat().equals(XLS) || XLSX
		return POIWorkbook.newInstance(workbook);
	}

}
