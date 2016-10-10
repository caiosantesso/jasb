package org.revolutio.jasb;

import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.revolutio.jasb.annotation.HeaderlessTable;
import org.revolutio.jasb.annotation.Table;
import org.revolutio.jasb.workbook.SpreadsheetWrapper;
import org.revolutio.jasb.workbook.WorkbookWrapper;
import org.revolutio.jasb.workbook.internal.Workbooks;

public class JasbImpl implements Jasb {

	private JasbImpl(JasbBuilder jasbBuilder) {
	}

	public static Jasb newInstance(JasbBuilder jasbBuilder) {
		return new JasbImpl(jasbBuilder);
	}

	private boolean isTable(Class<?> jasbElement) {
		boolean isTable = jasbElement.isAnnotationPresent(Table.class);
		boolean isHeaderless = jasbElement.isAnnotationPresent(HeaderlessTable.class);

		if (!isHeaderless && !isTable)
			throw new IllegalArgumentException("Tabular class not annotated with @HeaderlessTable neither @Table.");

		if (isHeaderless && isTable)
			throw new IllegalArgumentException("Tabular class conflicting annotated.");

		return isTable;
	}

	private String resolveSpreadsheetName(Class<?> tabularObject, boolean isTable) {
		String annotationValue = "";

		if (isTable)
			annotationValue = tabularObject.getDeclaredAnnotation(Table.class).value();
		else
			annotationValue = tabularObject.getDeclaredAnnotation(HeaderlessTable.class).value();

		String className = tabularObject.getSimpleName();
		return annotationValue.isEmpty() ? className : annotationValue;

	}

	private void validatePath(Path path) {
		Objects.requireNonNull(path);

		String message = null;

		if (Files.notExists(path)) {
			message = "Path does not exist.";
		} else if (!Files.isRegularFile(path)) {
			message = "Path is not a file.";
		} else if (!Files.isReadable(path)) {
			message = "File is not readable.";
		}

		if (message != null)
			throw new IllegalArgumentException(message);
	}

	private <T> T safeNewInstance(Class<T> tabularClass) {
		try {
			return tabularClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new JasbUnderlyingApiException(e);
		}
	}

	/*
	 * {@inheritDoc}
	 */
	@Override
	// TODO Verificar se tabularClass está em Cache.
	public <T> Map<Integer, T> load(Path fromWorkbook, Class<T> tabularClass) {
		validatePath(fromWorkbook);
		Objects.requireNonNull(tabularClass);

		boolean isTable = isTable(tabularClass);
		String sheetName = resolveSpreadsheetName(tabularClass, isTable);

		WorkbookWrapper workbook = Workbooks.newInstance(fromWorkbook);
		SpreadsheetWrapper spreadsheet = workbook.getSpreadsheet(sheetName);
		if (spreadsheet == null)
			throw new IllegalArgumentException(
					"Can't find spreadsheet " + sheetName + " on workbook " + workbook.getPath());

		FileFormat format = spreadsheet.getFileFormat();

		FieldBinder binder = FieldBinder.newInstance(tabularClass);
		
		Map<Integer, Field> bindedFields = null;
		if (isTable)
			bindedFields = binder.bindFieldsToTableColumns(spreadsheet);
		else
			bindedFields = binder.bindFieldsToHeaderlessColumns(format);

		if (bindedFields.isEmpty())
			return Collections.emptyMap();

		Map<Integer, Field> fields = Collections.unmodifiableMap(bindedFields);
		PropertySetter setter = PropertySetterImpl.newInstance();

		Map<Integer, T> classes = spreadsheet.stream()
				.collect(Collectors.toMap(row -> row.getRowNumber(), row -> safeNewInstance(tabularClass)));

		spreadsheet
			.stream()
			.flatMap(row -> row.getCells().stream())
			.filter(cell -> fields.containsKey(cell.getColumnIndex()))
			.forEach(cell -> setter.set(fields.get(cell.getColumnIndex()), classes.get(cell.getRowNumber()), cell.getValue()));

		return classes;
	}

	@Override
	public <T> T load(Path fromWorkbook, Class<T> toTabularClass, int firstRow, int lastRow) {
		// TODO Auto-generated method stub
		return null;
	}

}
