public class SymbolTableRow {
	
	private String name;
	private int lineNumber;
	
	public SymbolTableRow(String n, int l) {
		name = n;
		lineNumber = l;
	}
	
	public String getName() {
		return name;
	}

	public int getLineNumber() {
		return lineNumber;
	}
	
	public void print() {
		System.out.println(getName()+"\t| "+getLineNumber());
	}
}
