JASB - Java API for Spreadsheet Binding
---------------------------------------

JASB is an API that reads workbooks on XLS or XLSX file format and instantiate a POJO for each matching row.

This is a work on progress, don't use it on production.


### You have this workbook. 

  | A | B | C | D
-- | -- | -- | -- | -- 
1 | name | Last major version | Initial Launch Date | Last version | Other data  
2 | Java | 8 | 2014-03-18 | 8.101 | xxx
3 | Kotlin | 1 | 2016-09-22 | 1.04 | xxx

### Choose your columns and write them on a tabular class... 
```java
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
```

### Run it!
```java
public static void main(String... args) {
	Path workbook = Paths.get("/path/to/workbook.xlsx");

	Jasb jasb = JasbBuilder.getInstance().build();
	Map<Integer, Language> classes = jasb.load(workbook, Language.class);
	System.out.println(classes);
}
```
### And you'll get this.
    {2=Language [name= Java  8, Launch Date=2014-03-18, Last Version=8.101], 3=Language [name= Kotlin  1, Launch Date=2016-09-22, Last Version=1.04]}