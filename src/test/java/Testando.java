import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import org.revolutio.jasb.Jasb;
import org.revolutio.jasb.JasbBuilder;

public class Testando {
	public static void main(String[] args) {
		Path workbook = Paths.get("/home/caio/testando.xlsx");

		Jasb jasb = JasbBuilder.getInstance().build();
		Map<Integer, Language> classes = jasb.load(workbook, Language.class);
		
		System.out.println(classes);
	}
}
