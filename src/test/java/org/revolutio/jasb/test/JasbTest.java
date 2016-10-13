package org.revolutio.jasb.test;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.AclEntry;
import java.nio.file.attribute.AclFileAttributeView;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.revolutio.jasb.AmbiguousAnnotatedTabularClass;
import org.revolutio.jasb.ColumnNameEmptyHeaderlessClass;
import org.revolutio.jasb.ColumnNameIllegalCharHeaderlessClass;
import org.revolutio.jasb.ColumnNameOutOfBoundsXlsHeaderlessClass;
import org.revolutio.jasb.ColumnNameOutOfBoundsXlsxHeaderlessClass;
import org.revolutio.jasb.DuplicatedColumnNameHeaderlessClass;
import org.revolutio.jasb.DuplicatedColumnNameTableClass;
import org.revolutio.jasb.EmptySpreasheetTableClass;
import org.revolutio.jasb.IllegalColumnNameException;
import org.revolutio.jasb.Jasb;
import org.revolutio.jasb.JasbBuilder;
import org.revolutio.jasb.JasbUnderlyingApiException;
import org.revolutio.jasb.NonExistentSpreadsheetTableClass;
import org.revolutio.jasb.OnlyHeaderTableClass;
import org.revolutio.jasb.RegularHeaderlessClass;
import org.revolutio.jasb.RegularTableClass;
import org.revolutio.jasb.SpreadsheetHasNotHeaderException;

public class JasbTest {

	@Rule
	public ExpectedException exception = ExpectedException.none();

	private Path validPath = Paths.get("src/test/resources/brasileirao.xlsx");
	private Path validXlsPath = Paths.get("src/test/resources/brasileirao.xls");
	private Path notFilePath = Paths.get("src/test/resources/");
	private Path nonExistentPath = Paths.get("non_existent.xlsx");
	private Path testsPath = Paths.get("src/test/resources/tests.xlsx");
	private Path notWorkbookPath = Paths.get("src/test/resources/not_workbook.txt");

	private static Path notReadablePath = Paths.get("src/test/resources/not_readable.xlsx");
	private static List<AclEntry> acl;
	private static Set<PosixFilePermission> perms;

	@BeforeClass
	public static void setUp() {
		try {

			if (System.getProperty("os.name").contains("Linux")) {
				perms = Files.getPosixFilePermissions(notReadablePath);
				Files.setPosixFilePermissions(notReadablePath, Collections.emptySet());
			} else {
				AclFileAttributeView attView = Files.getFileAttributeView(notReadablePath, AclFileAttributeView.class);
				acl = attView.getAcl();
				attView.setAcl(Collections.emptyList());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@AfterClass
	public static void tearDown() {
		try {
			
			if (System.getProperty("os.name").contains("Linux")) {
				Files.setPosixFilePermissions(notReadablePath, perms);
			} else {

				AclFileAttributeView attView = Files.getFileAttributeView(notReadablePath, AclFileAttributeView.class);
				attView.setAcl(acl);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void shouldThrowExceptionBecausePathIsNull() {
		Jasb jasb = JasbBuilder.getInstance().build();
		exception.expect(NullPointerException.class);
		jasb.load(null, null);
	}

	@Test
	public void shouldThrowExceptionBecauseTabularClassIsNull() {
		Jasb jasb = JasbBuilder.getInstance().build();
		exception.expect(NullPointerException.class);
		jasb.load(validPath, null);
	}

	@Test
	public void shouldThrowExceptionBecausePathDoesNotExist() {
		Jasb jasb = JasbBuilder.getInstance().build();
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("does not exist");
		jasb.load(nonExistentPath, null);
	}

	@Test
	public void shouldThrowExceptionBecausePathIsNotAFile() {
		Jasb jasb = JasbBuilder.getInstance().build();
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("is not a file");
		jasb.load(notFilePath, null);
	}

	@Test
	public void shouldThrowExceptionBecausePathIsNotReadable() {
		Jasb jasb = JasbBuilder.getInstance().build();
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("is not readable");
		jasb.load(notReadablePath, null);
	}

	@Test
	public void shouldThrowExceptionBecauseTabularClassHasNeitherHeaderlessTableNorTableAnnotations() {
		Jasb jasb = JasbBuilder.getInstance().build();
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("Tabular class not annotated with @HeaderlessTable neither @Table.");
		jasb.load(validPath, Object.class);
	}

	@Test
	public void shouldThrowExceptionBecauseTabularClassHasBothHeaderlessTableAndTableAnnotations() {
		Jasb jasb = JasbBuilder.getInstance().build();
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("Tabular class conflicting annotated.");
		jasb.load(validPath, AmbiguousAnnotatedTabularClass.class);
	}

	@Test
	public void shouldThrowExceptionBecauseWorkbookHasNotTheSpecifiedSpreadsheet() {
		Jasb jasb = JasbBuilder.getInstance().build();
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("Can't find spreadsheet");
		jasb.load(validPath, NonExistentSpreadsheetTableClass.class);
	}

	@Test
	public void shouldThrowExceptionBecauseSpreasheetHasNotHeaderRow() {
		Jasb jasb = JasbBuilder.getInstance().build();
		exception.expect(SpreadsheetHasNotHeaderException.class);
		jasb.load(testsPath, EmptySpreasheetTableClass.class);
	}

	@Test
	public void shouldThrowExceptionBecausePathIsNotAWorkbook() {
		Jasb jasb = JasbBuilder.getInstance().build();
		exception.expect(JasbUnderlyingApiException.class);
		jasb.load(notWorkbookPath, EmptySpreasheetTableClass.class);
	}

	@Test
	public void shouldReturnAEmptyMapBecauseSpreasheetIsBlank() {
		Jasb jasb = JasbBuilder.getInstance().build();
		assertEquals(jasb.load(testsPath, OnlyHeaderTableClass.class), Collections.emptyMap());
	}

	@Test
	public void shouldThrowExceptionBecauseColumnNameIsDuplicatedOnHeaderlessClass() {
		Jasb jasb = JasbBuilder.getInstance().build();
		exception.expect(IllegalColumnNameException.class);
		exception.expectMessage("Column name duplicated");
		jasb.load(validPath, DuplicatedColumnNameHeaderlessClass.class);
	}

	@Test
	public void shouldThrowExceptionBecauseColumnNameIsDuplicatedOnTableClass() {
		Jasb jasb = JasbBuilder.getInstance().build();
		exception.expect(IllegalColumnNameException.class);
		exception.expectMessage("Column name duplicated");
		jasb.load(validPath, DuplicatedColumnNameTableClass.class);
	}

	@Test
	public void shouldThrowExceptionBecauseColumnNameIsOutOfBoundsOnXlsx() {
		Jasb jasb = JasbBuilder.getInstance().build();
		exception.expect(IllegalColumnNameException.class);
		exception.expectMessage("Column name out of bounds");
		jasb.load(validPath, ColumnNameOutOfBoundsXlsxHeaderlessClass.class);
	}

	@Ignore
	@Test
	public void shouldThrowExceptionBecauseColumnNameIsOutOfBoundsOnXls() {
		Jasb jasb = JasbBuilder.getInstance().build();
		exception.expect(IllegalColumnNameException.class);
		exception.expectMessage("Column name out of bounds");
		jasb.load(validXlsPath, ColumnNameOutOfBoundsXlsHeaderlessClass.class);
	}

	@Test
	public void shouldThrowExceptionBecauseColumnNameIsEmpty() {
		Jasb jasb = JasbBuilder.getInstance().build();
		exception.expect(IllegalColumnNameException.class);
		exception.expectMessage("Empty column name");
		jasb.load(validPath, ColumnNameEmptyHeaderlessClass.class);
	}

	@Test
	public void shouldThrowExceptionBecauseColumnNameHasIllegalChar() {
		Jasb jasb = JasbBuilder.getInstance().build();
		exception.expect(IllegalColumnNameException.class);
		exception.expectMessage("Illegal character on column name");
		jasb.load(validPath, ColumnNameIllegalCharHeaderlessClass.class);
	}

	@Test
	public void shouldBeOk() {
		Jasb jasb = JasbBuilder.getInstance().build();
		Map<Integer, RegularTableClass> map = jasb.load(validPath, RegularTableClass.class);
		System.out.println(map);

	}

	@Test
	public void shouldBeOkHeaderless() {
		Jasb jasb = JasbBuilder.getInstance().build();
		Map<Integer, RegularHeaderlessClass> map = jasb.load(validPath, RegularHeaderlessClass.class);
		System.out.println(map);
	}

}
