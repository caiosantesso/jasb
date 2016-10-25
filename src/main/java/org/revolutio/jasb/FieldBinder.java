package org.revolutio.jasb;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.revolutio.jasb.annotation.Column;
import org.revolutio.jasb.annotation.Header;
import org.revolutio.jasb.annotation.HeaderlessTable;
import org.revolutio.jasb.annotation.NotAHeader;
import org.revolutio.jasb.annotation.Table;
import org.revolutio.jasb.workbook.CellWrapper;
import org.revolutio.jasb.workbook.SpreadsheetWrapper;

class FieldBinder {

	private final List<Field> fields;

	private FieldBinder(Class<?> tabular) {
		Objects.requireNonNull(tabular);

		Field[] declaredFields = null;
		try {
			declaredFields = Class.forName(tabular.getName()).getDeclaredFields();
		} catch (SecurityException | ClassNotFoundException e) {
			throw new JasbUnderlyingApiException(e);
		}
		fields = new ArrayList<>(Arrays.asList(declaredFields));

	}

	public static FieldBinder newInstance(Class<?> tabular) {
		return new FieldBinder(tabular);
	}

	/**
	 * Vincula campos à colunas para entidades {@link Table}.
	 * 
	 * @param tabularObject
	 * @param spreadsheet
	 * @return
	 */
	Map<Integer, Field> bindFieldsToTableColumns(SpreadsheetWrapper spreadsheet) {
		if (spreadsheet.isEmpty())
			throw new SpreadsheetHasNotHeaderException(
					"Spreadsheet " + spreadsheet.getName() + " from workbook " + spreadsheet + " is empty.");

		Map<Integer, Field> bindedFields = new HashMap<>();
		Set<CellWrapper> headerCells = spreadsheet.getHeader();

		fields.removeIf(field -> field.isAnnotationPresent(NotAHeader.class));
		checkColumnDuplicity(fields);

		for (Field field : fields) {
			if (field.isAnnotationPresent(Header.class)) {
				for (CellWrapper cell : headerCells) {
					if (cell.toString().equals(field.getAnnotation(Header.class).value())) {
						bindedFields.put(cell.getColumnIndex(), field);
						headerCells.remove(cell);
						break;
					}
				}
			} else {
				for (CellWrapper cell : headerCells) {
					String cellName = cell.toString().replace(" ", "");
					if (cellName.equalsIgnoreCase(field.getName())) {
						bindedFields.put(cell.getColumnIndex(), field);
						headerCells.remove(cell);
						break;
					}
				}
			}
		}

		return bindedFields;
	}

	/**
	 * Check if there are duplication of column names between field names and
	 * {@link Header} name.
	 * 
	 * @param fields
	 */
	private void checkColumnDuplicity(List<Field> list) {
		Map<Boolean, List<Field>> map = new ArrayList<>(list).stream()
				.collect(Collectors.partitioningBy(field -> field.isAnnotationPresent(Header.class)));

		Set<String> varNames = map.get(false).stream().map(field -> field.getName().toLowerCase())
				.collect(Collectors.toSet());

		Set<String> annValues = map.get(true).stream()
				.map(field -> field.getAnnotation(Header.class).value().replace(" ", "").toLowerCase())
				.collect(Collectors.toSet());

		for (String columnName : annValues) {
			if (varNames.contains(columnName))
				throw new IllegalColumnNameException("Column name duplicated: " + columnName);
		}

	}

	/**
	 * Vincula campos à colunas para entidades {@link HeaderlessTable}.
	 * 
	 * @param tabularObject
	 * @return
	 */
	Map<Integer, Field> bindFieldsToHeaderlessColumns(FileFormat format) {
		Map<Integer, Field> bindedFields = new HashMap<>();

		Set<String> names = new HashSet<>();
		fields.removeIf(field -> !field.isAnnotationPresent(Column.class));

		for (Field field : fields) {
			String columnName = field.getDeclaredAnnotation(Column.class).value().toUpperCase();
			if (!names.contains(columnName)) {
				names.add(columnName);
				int index = columnNameToNumber(columnName, format);
				bindedFields.put(index, field);
			} else {
				throw new IllegalColumnNameException("Column name duplicated: " + columnName);
			}
		}

		return bindedFields;
	}

	/**
	 * name has to be uppercase.
	 * 
	 * @param name
	 * @return
	 */
	private int columnNameToNumber(String name, FileFormat format) {
		if (name.length() > 3)
			throw new IllegalColumnNameException("Column name out of bounds: " + name);
		if (name.trim().isEmpty())
			throw new IllegalColumnNameException("Empty column name.");

		int index = 0;
		name = name.toUpperCase();
		char[] letters = name.toCharArray();

		for (int i = 0; i < letters.length; i++) {
			int codePoint = (int) letters[i];

			if (codePoint < 65 || codePoint > 90) {
				throw new IllegalColumnNameException("Illegal character on column name: " + (char) codePoint);
			}
			int digit = (int) Math.pow(26, letters.length - 1 - i);
			index += (codePoint - 64) * digit;
		}

		int maxRows = format.maxColumnIndex();

		if (index > maxRows)
			throw new IllegalColumnNameException("Column name out of bounds: " + name);

		return --index;
	}

}