package org.revolutio.jasb;

import java.time.LocalDate;

import org.revolutio.jasb.annotation.Header;
import org.revolutio.jasb.annotation.Table;

@Table(value = "2015")
public class RegularTableClass {

	public LocalDate data;
	@Header("Mandante")
	public String mandante;
	public int golsMandante;
	public int golsVisitante;
	public String visitante;

	@Override
	public String toString() {
		return data + " " + mandante + " " +  golsMandante + " X " + golsVisitante + " " + visitante;
	}

}
