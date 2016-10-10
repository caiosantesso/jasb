package org.revolutio.jasb;

import org.revolutio.jasb.annotation.Column;
import org.revolutio.jasb.annotation.HeaderlessTable;

@HeaderlessTable(value = "2015")
public class DuplicatedColumnNameHeaderlessClass {

	@Column("a")
	public String any;

	@Column("a")
	public String some;
}
