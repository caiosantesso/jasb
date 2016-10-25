package org.revolutio.jasb.workbook.internal;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.revolutio.jasb.FileFormat;
import org.revolutio.jasb.JasbUnderlyingApiException;
import org.revolutio.jasb.workbook.FileFormatNotSupportedException;
import org.revolutio.jasb.workbook.SpreadsheetWrapper;
import org.revolutio.jasb.workbook.WorkbookWrapper;

class POIWorkbook implements WorkbookWrapper {

	private Workbook workbook;
	private Map<String, SpreadsheetWrapper> spreadsheets = new HashMap<>();
	private FileFormat format;
	private Path path;
	private FormulaEvaluator evaluator;

	public static WorkbookWrapper newInstance(Path path) {
		return new POIWorkbook(path);
	}

	private POIWorkbook(Path path) {
		this.path = Objects.requireNonNull(path);

		String fileExtesion = path.getFileName().toString();
		if (fileExtesion.endsWith(".xlsx"))
			format = FileFormat.XLSX;
		else if (fileExtesion.endsWith(".xls"))
			format = FileFormat.XLS;
		else
			throw new FileFormatNotSupportedException(path + " with extesion" + fileExtesion);
		
		try {
			workbook = WorkbookFactory.create(path.toFile());
			workbook.close();
		} catch (EncryptedDocumentException e) {
			throw new JasbUnderlyingApiException(e);
		} catch (InvalidFormatException e) {
			throw new JasbUnderlyingApiException(e);
		} catch (IOException e) {
			throw new JasbUnderlyingApiException(e);
		}

		evaluator = workbook.getCreationHelper().createFormulaEvaluator();
		
		Iterator<Sheet> iterator = workbook.sheetIterator();

		while (iterator.hasNext()) {
			Sheet s = iterator.next();
			spreadsheets.put(s.getSheetName(), Spreadsheets.newInstance(s, this));
		}

	}

	FormulaEvaluator getFormulaEvaluator() {
		return evaluator;
	}

	@Override
	public FileFormat getFileFormat() {
		return format;
	}

	@Override
	public Path getPath() {
		return path;
	}

	@Override
	public SpreadsheetWrapper getSpreadsheet(String sheetName) {
		return spreadsheets.get(sheetName);
	}

}
