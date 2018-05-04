import java.util.ArrayList;

public class SymbolTable {

	private ArrayList<SymbolTableRow> rowList;
	
	public SymbolTable() {
		rowList = new ArrayList<SymbolTableRow>();
	}
	
	public SymbolTableRow get(int i) {
		return rowList.get(i);
	}
	
	public boolean lookup(String lexeme) {
		for (int i = 0; i < rowList.size(); i++) {
			if (rowList.get(i).getName().equals(lexeme)) {
				return true;
			}
		}
		return false;
	}
	
	public void insert(String lexeme, int lineNumber) {
		rowList.add(new SymbolTableRow(lexeme, lineNumber));
	}
	
	public int size() {
		return rowList.size();
	}
	
	public void printRow(int i) {
		rowList.get(i).print();
	}
}
