package org.revolutio.jasb.workbook;

import java.util.Set;

public interface RowWrapper {
	
	Set<CellWrapper> getCells();

	int getRowNumber();

}
