package org.revolutio.jasb;

import org.revolutio.jasb.annotation.Header;
import org.revolutio.jasb.annotation.Table;

@Table(value = "2015")
public class DuplicatedColumnNameTableClass {

	public String anyTitle;
	@Header("any title")
	public String some;
}
