package org.revolutio.jasb;

import org.revolutio.jasb.annotation.Column;
import org.revolutio.jasb.annotation.HeaderlessTable;

@HeaderlessTable("valid")
public class ColumnNameIllegalCharHeaderlessClass {

	@Column("ç")
	public String any;
}
