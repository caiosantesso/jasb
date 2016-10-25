package org.revolutio.jasb.workbook.internal;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.Row;
import org.revolutio.jasb.workbook.CellWrapper;
import org.revolutio.jasb.workbook.RowWrapper;

class POIRow implements RowWrapper {

	private Row row;
	private POISpreadsheet spreadsheet;

	static RowWrapper newInstance(Row row, POISpreadsheet spreadsheet) {
		return new POIRow(row, spreadsheet);
	}

	private POIRow(Row row, POISpreadsheet spreadsheet) {
		this.row = row;
		this.spreadsheet = spreadsheet;
	}

	@Override
	public Set<CellWrapper> getCells() {
		Set<CellWrapper> cells = new HashSet<>();

		Iterator<Cell> cellIterator = row.cellIterator();
		while (cellIterator.hasNext()) {
			Cell cell = cellIterator.next();
			cells.add(Cells.newInstance(cell));
		}
		return cells;
	}

	@Override
	public int getRowNumber() {
		return row.getRowNum() + 1;
	}

}
