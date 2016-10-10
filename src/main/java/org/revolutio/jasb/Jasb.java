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
	<T> Map<Integer, T> load(Path fromWorkbook, Class<T> toTabularClass);

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
	<T> T load(Path fromWorkbook, Class<T> toTabularClass, int firstRow, int lastRow);

}
