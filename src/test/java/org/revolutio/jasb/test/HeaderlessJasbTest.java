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
import org.revolutio.jasb.ColumnNameEmptyHeaderlessClass;
import org.revolutio.jasb.ColumnNameIllegalCharHeaderlessClass;
import org.revolutio.jasb.ColumnNameOutOfBoundsXlsHeaderlessClass;
import org.revolutio.jasb.ColumnNameOutOfBoundsXlsxHeaderlessClass;
import org.revolutio.jasb.DuplicatedColumnNameHeaderlessClass;
import org.revolutio.jasb.IllegalColumnNameException;
import org.revolutio.jasb.Jasb;
import org.revolutio.jasb.JasbBuilder;
import org.revolutio.jasb.OnlyHeaderTableClass;
import org.revolutio.jasb.ValidHeaderlessClass;

public class HeaderlessJasbTest {

	@Rule
	public ExpectedException exception = ExpectedException.none();

	private Path validXlsPath = Paths.get("src/test/resources/tests.xls");
	private Path testsPath = Paths.get("src/test/resources/tests.xlsx");
	
	private Jasb jasb = JasbBuilder.getInstance().build();
	private ValidHeaderlessClass validHeaderless = jasb.loadRelativeRow(testsPath, ValidHeaderlessClass.class, 1);

	@Test
	public void shouldReturnAEmptyMapBecauseSpreasheetIsBlank() {
		assertEquals(jasb.loadAll(testsPath, OnlyHeaderTableClass.class), Collections.emptyMap());
	}
	
	@Test
	public void shouldThrowExceptionBecauseColumnNameIsDuplicated() {
		exception.expect(IllegalColumnNameException.class);
		exception.expectMessage("Column name duplicated");
		jasb.loadAll(testsPath, DuplicatedColumnNameHeaderlessClass.class);
	}

	@Test
	public void shouldThrowExceptionBecauseColumnNameIsOutOfBoundsOnXlsx() {
		exception.expect(IllegalColumnNameException.class);
		exception.expectMessage("Column name out of bounds");
		jasb.loadAll(testsPath, ColumnNameOutOfBoundsXlsxHeaderlessClass.class);
	}

	@Test
	public void shouldThrowExceptionBecauseColumnNameIsOutOfBoundsOnXls() {
		exception.expect(IllegalColumnNameException.class);
		exception.expectMessage("Column name out of bounds");
		jasb.loadAll(validXlsPath, ColumnNameOutOfBoundsXlsHeaderlessClass.class);
	}

	@Test
	public void shouldThrowExceptionBecauseColumnNameIsEmpty() {
		exception.expect(IllegalColumnNameException.class);
		exception.expectMessage("Empty column name");
		jasb.loadAll(testsPath, ColumnNameEmptyHeaderlessClass.class);
	}

	@Test
	public void shouldThrowExceptionBecauseColumnNameHasIllegalChar() {
		exception.expect(IllegalColumnNameException.class);
		exception.expectMessage("Illegal character on column name");
		jasb.loadAll(testsPath, ColumnNameIllegalCharHeaderlessClass.class);
	}

	@Test
	public void shouldReturnAChar() {
		assertEquals('c', validHeaderless.aChar);
	}

	@Test
	public void shouldReturnACharacter() {
		assertEquals(Character.valueOf('C'), validHeaderless.aCharacter);
	}

	@Test
	public void shouldReturnABooleanWrapper() {
		assertEquals(Boolean.FALSE, validHeaderless.aBooleanWrapper);
	}

	@Test
	public void shouldReturnABoolean() {
		assertEquals(false, validHeaderless.aBoolean);
	}

	@Test
	public void shouldReturnAString() {
		assertEquals("Some text", validHeaderless.aString);
	}

	@Test
	public void shouldReturnADouble() {
		assertEquals(0.01234567890123, validHeaderless.aDouble, 0.0);
	}

	@Test
	public void shouldReturnADoubleWrapper() {
		assertEquals(1.01234567890123, validHeaderless.aDoubleWrapper, 0.0);
	}

	@Test
	public void shouldReturnAFloat() {
		assertEquals(2.0123456F, validHeaderless.aFloat, 0.0F);
	}

	@Test
	public void shouldReturnAFloatWrapper() {
		assertEquals(3.0123456F, validHeaderless.aFloatWrapper, 0.0F);
	}

	@Test
	public void shouldReturnAInt() {
		assertEquals(Integer.MAX_VALUE, validHeaderless.aInt);
	}

	@Test
	public void shouldReturnAInteger() {
		assertEquals(Integer.MIN_VALUE, validHeaderless.aInteger.intValue());
	}

	@Test
	public void shouldReturnALong() {
		assertEquals(Long.MAX_VALUE, validHeaderless.aLong);
	}

	@Test
	public void shouldReturnALongWrapper() {
		assertEquals(Long.MIN_VALUE, validHeaderless.aLongWrapper.longValue());
	}

	@Test
	public void shouldReturnALocalDate() {
		assertEquals(LocalDate.of(1989, 5, 8), validHeaderless.aLocalDate);
	}

	@Test
	public void shouldReturnABigInteger() {
		assertEquals(new BigInteger("42"), validHeaderless.aBigInteger);
	}
	
	@Test
	public void shouldReturnABigDecimal() {
		assertEquals(new BigDecimal("51.5151"), validHeaderless.aBigDecimal);
	}

	@Test
	public void shouldReturnNullBecauseFieldHasNoMatchesOnSpreadsheet() {
		assertNull(validHeaderless.aObject);
	}

	@Test
	public void shouldReturnNullBeucauseFieldIsNotAHeaderAnnotated() {
		assertNull(validHeaderless.doNotGetThis);
	}

}
