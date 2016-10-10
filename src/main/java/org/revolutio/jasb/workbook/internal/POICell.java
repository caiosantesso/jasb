package org.revolutio.jasb.workbook.internal;

import org.apache.poi.ss.usermodel.Cell;
import org.revolutio.jasb.workbook.CellWrapper;

class POICell implements CellWrapper {

	private Cell cell;
	
	private POICell(Cell cell) {
		this.cell = cell;
	}
	
	public static CellWrapper newInstance(Cell cell) {
		return new POICell(cell);
	}

	@Override
	public String toString() {
		return cell.toString();
	}

	@Override
	public int getColumnIndex() {
		return cell.getColumnIndex();
	}

	@Override
	public int getRowNumber() {
		return cell.getRowIndex() + 1;
	}

	@Override
	public String getValue() {
		return cell.toString();
	}
}
