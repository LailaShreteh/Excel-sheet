package spreadsheet;
/**
 * 
 * Class Spreadsheet which have all data sheets inside
 * @author lailaShreteh
 *
 */
public class Spreadsheet {
	private String name;
	private SpreadsheetData data;
	
	public Spreadsheet(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public SpreadsheetData getData() {
		return data;
	}
	public void setData(SpreadsheetData data) {
		this.data = data;
	}
	@Override
	public String toString() {
		return "Spreadsheet [name=" + name + ", data=" + data + "]";
	}
	
	
}
