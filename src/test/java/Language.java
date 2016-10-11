import java.time.LocalDate;

import org.revolutio.jasb.annotation.Table;

@Table
public class Language {
	private String name;
	private int lastMajorVersion;
	private LocalDate initialLaunchDate;
	private double lastVersion;

	@Override
	public String toString() {
		return "Language [name=" + name + " " + lastMajorVersion + ", Launch Date=" + initialLaunchDate
				+ ", Last Version=" + lastVersion + "]";
	}

	
	
}
