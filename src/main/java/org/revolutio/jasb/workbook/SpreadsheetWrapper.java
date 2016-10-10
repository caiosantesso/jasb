package org.revolutio.jasb.workbook;

import java.util.Iterator;
import java.util.Set;
import java.util.stream.Stream;

import org.revolutio.jasb.FileFormat;

public interface SpreadsheetWrapper {

	String getName();

	/**
	 * Retrieves a set with cells of the first row. Removes the pointer to the row.
	 * 
	 * @return
	 */
	Set<CellWrapper> getHeader();

	Iterator<RowWrapper> rowIterator();
	
	FileFormat getFileFormat();

	boolean isEmpty();

	int getTotalRowCount();

	Stream<RowWrapper> stream();

}
