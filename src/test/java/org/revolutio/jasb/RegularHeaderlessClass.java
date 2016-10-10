package org.revolutio.jasb;

import org.revolutio.jasb.annotation.Column;
import org.revolutio.jasb.annotation.HeaderlessTable;

@HeaderlessTable(value = "2015")
public class RegularHeaderlessClass {

	@Column("a")
	public String data;

	@Column("b")
	public String mandante;

	@Column("c")
	public String golsMandante;

	@Column("d")
	public String golsVisitante;

	@Column("e")
	public String visitante;

	@Override
	public String toString() {
		return mandante + " " +  golsMandante + " X " + golsVisitante + " " + visitante;
	}

}
