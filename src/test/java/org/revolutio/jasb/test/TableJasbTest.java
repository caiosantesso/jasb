package org.revolutio.jasb.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Collections;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.revolutio.jasb.DuplicatedColumnNameTableClass;
import org.revolutio.jasb.EmptySpreasheetTableClass;
import org.revolutio.jasb.IllegalColumnNameException;
import org.revolutio.jasb.Jasb;
import org.revolutio.jasb.JasbBuilder;
import org.revolutio.jasb.OnlyHeaderTableClass;
import org.revolutio.jasb.SpreadsheetHasNotHeaderException;
import org.revolutio.jasb.ValidTableClass;

public class TableJasbTest {

	@Rule
	public ExpectedException exception = ExpectedException.none();

	private Path testsPath = Paths.get("src/test/resources/tests.xlsx");
	private Jasb jasb = JasbBuilder.getInstance().build();
	private ValidTableClass validTable = jasb.loadRelativeRow(testsPath, ValidTableClass.class, 1);
	

	@Test
	public void shouldThrowExceptionBecauseSpreasheetHasNotHeaderRow() {
		exception.expect(SpreadsheetHasNotHeaderException.class);
		exception.expectMessage("is empty");
		jasb.loadAll(testsPath, EmptySpreasheetTableClass.class);
	}

	@Test
	public void shouldReturnAEmptyMapBecauseSpreasheetIsBlank() {
		assertEquals(jasb.loadAll(testsPath, OnlyHeaderTableClass.class), Collections.emptyMap());
	}

	@Test
	public void shouldThrowExceptionBecauseColumnNameIsDuplicated() {
		exception.expect(IllegalColumnNameException.class);
		exception.expectMessage("Column name duplicated");
		jasb.loadAll(testsPath, DuplicatedColumnNameTableClass.class);
	}

	@Test
	public void shouldReturnAChar() {
		assertEquals('c', validTable.aChar);
	}

	@Test
	public void shouldReturnACharacter() {
		assertEquals(Character.valueOf('C'), validTable.aCharacter);
	}

	@Test
	public void shouldReturnABooleanWrapper() {
		assertEquals(Boolean.FALSE, validTable.aBooleanWrapper);
	}

	@Test
	public void shouldReturnABoolean() {
		assertEquals(false, validTable.aBoolean);
	}

	@Test
	public void shouldReturnAString() {
		assertEquals("Some text", validTable.aString);
	}

	@Test
	public void shouldReturnADouble() {
		assertEquals(0.01234567890123, validTable.aDouble, 0.0);
	}

	@Test
	public void shouldReturnADoubleWrapper() {
		assertEquals(1.01234567890123, validTable.aDoubleWrapper, 0.0);
	}

	@Test
	public void shouldReturnAFloat() {
		assertEquals(2.0123456F, validTable.aFloat, 0.0F);
	}

	@Test
	public void shouldReturnAFloatWrapper() {
		assertEquals(3.0123456F, validTable.aFloatWrapper, 0.0F);
	}

	@Test
	public void shouldReturnAInt() {
		assertEquals(Integer.MAX_VALUE, validTable.aInt);
	}

	@Test
	public void shouldReturnAInteger() {
		assertEquals(Integer.MIN_VALUE, validTable.aInteger.intValue());
	}

	@Test
	public void shouldReturnALong() {
		assertEquals(Long.MAX_VALUE, validTable.aLong);
	}

	@Test
	public void shouldReturnALongWrapper() {
		assertEquals(Long.MIN_VALUE, validTable.aLongWrapper.longValue());
	}

	@Test
	public void shouldReturnALocalDate() {
		assertEquals(LocalDate.of(1989, 5, 8), validTable.aLocalDate);
	}

	@Test
	public void shouldReturnABigInteger() {
		assertEquals(new BigInteger("42"), validTable.aBigInteger);
	}
	
	@Test
	public void shouldReturnABigDecimal() {
		assertEquals(new BigDecimal("51.5151"), validTable.aBigDecimal);
	}

	@Test
	public void shouldReturnNullBecauseFieldHasNoMatchesOnSpreadsheet() {
		assertNull(validTable.aObject);
	}

	@Test
	public void shouldReturnNullBecauseFieldIsNotAHeaderAnnotated() {
		assertNull(validTable.doNotGetThis);
	}

}
