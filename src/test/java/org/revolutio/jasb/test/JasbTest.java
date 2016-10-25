package org.revolutio.jasb.test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.AclEntry;
import java.nio.file.attribute.AclFileAttributeView;
import java.nio.file.attribute.PosixFilePermission;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.junit.AfterClass;
import org.junit.Assume;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.revolutio.jasb.AmbiguousAnnotatedTabularClass;
import org.revolutio.jasb.EmptySpreasheetTableClass;
import org.revolutio.jasb.Jasb;
import org.revolutio.jasb.JasbBuilder;
import org.revolutio.jasb.JasbUnderlyingApiException;
import org.revolutio.jasb.NonExistentSpreadsheetTableClass;
import org.revolutio.jasb.workbook.FileFormatNotSupportedException;

public class JasbTest {

	@Rule
	public ExpectedException exception = ExpectedException.none();

	private Path validPath = Paths.get("src/test/resources/tests.xlsx");
	private Path notFilePath = Paths.get("src/test/resources/");
	private Path nonExistentPath = Paths.get("non_existent.xlsx");
	private Path notWorkbookPath = Paths.get("src/test/resources/not_workbook.txt");
	private Jasb jasb = JasbBuilder.getInstance().build();
	

	private static Path notReadablePath = Paths.get("src/test/resources/not_readable.xlsx");
	private static List<AclEntry> acl;
	private static Set<PosixFilePermission> perms;

	@BeforeClass
	public static void setUp() {

		//Makes the path unredeable.
		try {
			if (System.getProperty("os.name").contains("Linux")) {
				perms = Files.getPosixFilePermissions(notReadablePath);
				Files.setPosixFilePermissions(notReadablePath, Collections.emptySet());
			} else if (System.getProperty("os.name").contains("Windows")) {
				AclFileAttributeView attView = Files.getFileAttributeView(notReadablePath, AclFileAttributeView.class);
				acl = attView.getAcl();
				attView.setAcl(Collections.emptyList());
			} else {
				Assume.assumeTrue(false);
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
		exception.expect(NullPointerException.class);
		jasb.loadAll(null, null);
	}

	@Test
	public void shouldThrowExceptionBecauseTabularClassIsNull() {
		exception.expect(NullPointerException.class);
		jasb.loadAll(validPath, null);
	}

	@Test
	public void shouldThrowExceptionBecausePathDoesNotExist() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("does not exist");
		jasb.loadAll(nonExistentPath, null);
	}

	@Test
	public void shouldThrowExceptionBecausePathIsNotAFile() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("is not a file");
		jasb.loadAll(notFilePath, null);
	}

	@Test
	public void shouldThrowExceptionBecausePathIsNotReadable() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("is not readable");
		jasb.loadAll(notReadablePath, null);
	}

	@Test
	public void shouldThrowExceptionBecauseTabularClassHasNeitherHeaderlessTableNorTableAnnotations() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("Tabular class not annotated with @HeaderlessTable neither @Table.");
		jasb.loadAll(validPath, Object.class);
	}

	@Test
	public void shouldThrowExceptionBecauseTabularClassHasBothHeaderlessTableAndTableAnnotations() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("Tabular class conflicting annotated.");
		jasb.loadAll(validPath, AmbiguousAnnotatedTabularClass.class);
	}

	@Test
	public void shouldThrowExceptionBecauseWorkbookHasNotTheSpecifiedSpreadsheet() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("Can't find spreadsheet");
		jasb.loadAll(validPath, NonExistentSpreadsheetTableClass.class);
	}

	@Test
	public void shouldThrowExceptionBecausePathIsNotAWorkbook() {
		exception.expect(FileFormatNotSupportedException.class);
		jasb.loadAll(notWorkbookPath, EmptySpreasheetTableClass.class);
	}

}
