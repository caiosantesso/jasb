package org.revolutio.jasb;

import java.nio.file.Path;
import java.util.Map;

public interface Jasb {

	/**
	 * Load all rows from workbook specified on <strong>fromWorkbook</strong>
	 * into <b>toTabularClass</b> class.
	 * <p>
	 * Whether there are not fields binded with workbook columns this methods
	 * returns <code>null</code>.
	 * 
	 * @param fromWorkbook
	 *            workbook path.
	 * @param toTabularClass
	 *            class that receives data.
	 * @return a map with keys matching the row number and values matching the
	 *         tabular classes.
	 */
	<T> Map<Integer, T> loadAll(Path fromWorkbook, Class<T> toTabularClass);

	/**
	 * Load rows between <b>firstRow</b> and <b>lastRow</b> from workbook
	 * specified on <strong>fromWorkbook</strong> into <b>toTabularClass</b>
	 * class.
	 * <p>
	 * Whether there are not fields binded with workbook columns this methods
	 * returns <code>null</code>.
	 * 
	 * @param firstRow
	 * @param lastRow
	 * @param fromWorkbook
	 *            workbook path.
	 * @param toTabularClass
	 *            class that receives data.
	 * @return
	 */
	//<T> Map<Integer, T> load(Path fromWorkbook, Class<T> toTabularClass, int firstRowIndex, int lastRowIndex);
	//<T> Map<Integer, T> loadSome(Path fromWorkbook, Class<T> toTabularClass, Set<Integer> rowIndexes);
	//<T> Map<Integer, T> loadToTheLast(Path fromWorkbook, Class<T> toTabularClass, int firstRowIndex);
	//<T> Map<Integer, T> loadFromTheFirst(Path fromWorkbook, Class<T> toTabularClass, int lastRowIndex);
	
//	SortedSet<Integer> indexes();

	<T> T loadRelativeRow(Path fromWorkbook, Class<T> toTabularClass, int position);
	
	//TODO Set<> allIndexes(Path fromWorkbook, Class<T> toTabularClass);
	//TODO Policy null rows: instantiate or skip 
	
	
}
