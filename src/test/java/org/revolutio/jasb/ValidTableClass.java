package org.revolutio.jasb;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;

import org.revolutio.jasb.annotation.NotAHeader;
import org.revolutio.jasb.annotation.Table;

@Table(value = "valid")
public class ValidTableClass {

	public String aString;
	public char aChar;
	public Character aCharacter;
	public int aInt;
	public Integer aInteger;
	public long aLong;
	public Long aLongWrapper;
	public double aDouble;
	public Double aDoubleWrapper;
	public float aFloat;
	public Float aFloatWrapper;
	public boolean aBoolean;
	public Boolean aBooleanWrapper;
	public LocalDate aLocalDate;
	public BigDecimal aBigDecimal;
	public BigInteger aBigInteger;
	public Object aObject;
	@NotAHeader
	public String doNotGetThis;
	

}
