package org.revolutio.jasb.workbook.internal;

import org.apache.poi.ss.usermodel.Cell;
import org.revolutio.jasb.workbook.CellWrapper;

public class Cells {

	public static CellWrapper newInstance(Cell cell) {
		return POICell.newInstance(cell);
	}

}
