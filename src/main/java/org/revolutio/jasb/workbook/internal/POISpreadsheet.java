package org.revolutio.jasb.workbook.internal;

import java.util.Iterator;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Stream;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.revolutio.jasb.FileFormat;
import org.revolutio.jasb.SpreadsheetHasNotHeaderException;
import org.revolutio.jasb.workbook.CellWrapper;
import org.revolutio.jasb.workbook.RowWrapper;
import org.revolutio.jasb.workbook.SpreadsheetWrapper;

class POISpreadsheet implements SpreadsheetWrapper {

	private POIWorkbook workbook;
	private Sheet sheet;
	private SortedMap<Integer, RowWrapper> rows = new TreeMap<>();
	private RowWrapper header;

	static SpreadsheetWrapper newInstance(Sheet sheet, POIWorkbook workbook) {
		return new POISpreadsheet(sheet, workbook);
	}
	
	private  POISpreadsheet(Sheet sheet, POIWorkbook workbook) {
		this.sheet = sheet;
		this.workbook = workbook;

		Iterator<Row> it = sheet.rowIterator();
		while (it.hasNext()) {
			Row row = it.next();
			rows.put(row.getRowNum(), Rows.newInstance(row, this));
		}
		

	}

	@Override
	public boolean equals(Object obj) {
		SpreadsheetWrapper spreadsheet = (SpreadsheetWrapper) obj;
		return sheet.getSheetName().equals(spreadsheet.getName());
	}

	@Override
	public String getName() {
		return sheet.getSheetName();
	}
	
	POIWorkbook getWorkbook() {
		return workbook; 
	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	public Set<CellWrapper> getHeader() {
		if (rows.isEmpty())
			throw new SpreadsheetHasNotHeaderException();

		RowWrapper row = header;
		if (header == null) {
			row = rows.get(rows.firstKey());
			header = rows.remove(rows.firstKey());
		}

		return row.getCells();
	}
	
	
	
	public boolean hasHeader() {
		return header != null;
	}
	
	public Iterator<RowWrapper> rowIterator() {
		return rows.values().iterator();
	}

	public Stream<RowWrapper> stream() {
		return rows.values().stream();
	}


	@Override
	public FileFormat getFileFormat() {
		return workbook.getFileFormat();
	}

	@Override
	public boolean isEmpty() {
		return rows.isEmpty();
	}

	@Override
	public int getTotalRowCount() {
		return rows.size();
	}

}
