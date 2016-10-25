package org.revolutio.jasb;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;

import org.revolutio.jasb.annotation.Column;
import org.revolutio.jasb.annotation.HeaderlessTable;

@HeaderlessTable(value = "valid_headerless")
public class ValidHeaderlessClass {


	@Column("a")
	public BigDecimal aBigDecimal;
	@Column("B")
	public BigInteger aBigInteger;
	@Column("C")
	public String aString;
	@Column("D")
	public char aChar;
	@Column("e")
	public Character aCharacter;
	@Column("f")
	public int aInt;
	@Column("G")
	public Integer aInteger;
	@Column("h")
	public long aLong;
	@Column("i")
	public Long aLongWrapper;
	@Column("J")
	public double aDouble;
	@Column("K")
	public Double aDoubleWrapper;
	@Column("L")
	public float aFloat;
	@Column("m")
	public Float aFloatWrapper;
	@Column("N")
	public boolean aBoolean;
	@Column("O")
	public Boolean aBooleanWrapper;
	@Column("P")
	public LocalDate aLocalDate;
	public Object aObject;
	public String doNotGetThis;
	

}
