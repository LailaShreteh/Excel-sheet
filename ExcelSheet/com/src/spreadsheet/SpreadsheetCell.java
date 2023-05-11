package spreadsheet;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


public class SpreadsheetCell {
	private int row;
	private char column;
	private Coordinate coordinate; 
	private float value = 0;
	private String formulaString = "";
	public List<Coordinate> dependsOn;
	private SpreadsheetData spreadsheetData ;
	
	public SpreadsheetCell(char column, int row) {
		this.row = row;
		this.column = column;
		this.coordinate = new Coordinate(column,row);
	}
	public SpreadsheetCell(char column,int row, String formulaString) {
		this.row = row;
		this.column = column;
		this.coordinate = new Coordinate(column,row);
		if(parseFormula(formulaString)) {
			this.formulaString = formulaString;
		}
	}
	
	public Coordinate getCoordinate() {
		return coordinate;
	}
	public void setCoordinate(Coordinate coordinate) {
		this.coordinate = coordinate;
	}
	public String getFormula() {
		return formulaString;
	}
	public void setFormula(String formula) {
		if(parseFormula(formulaString)) {
			this.formulaString = formula;
		}
	}
	public SpreadsheetData getSpreadsheetData() {
		return spreadsheetData;
	}
	public void setSpreadsheetData(SpreadsheetData spreadsheetData) {
		this.spreadsheetData = spreadsheetData;
	}
	public int getRow() {
		return row;
	}
	public int getColumn() {
		return column;
	}
	public float getValue() {
		return value;
	}
	private List getDependsOn() {
		return dependsOn;
	}
	private void parseCellDependencies(String s) {
		dependsOn = new ArrayList<>();
		 if (s == null || s.isEmpty()) return ;
	       int len = s.length();
	       Stack<Float> stack = new Stack<Float>();
	       StringBuilder currentNumber = new StringBuilder();
	       char operation = '+';
	       boolean isAlphabetic = false;
	       for (int i = 0; i < len; i++) {
	           char currentChar = s.charAt(i);
	           if (Character.isAlphabetic(currentChar)) {  
	        	   isAlphabetic = true;
	        	   currentNumber.append(currentChar);
	           }
	           if (Character.isDigit(currentChar)) {
	               currentNumber.append(currentChar);
	           }
	           if (!Character.isDigit(currentChar) && currentChar != '.' 
	        		   && !Character.isAlphabetic(currentChar) && 
	        		   !Character.isWhitespace(currentChar) || i == len - 1) {
	        	   if(isAlphabetic) {
	        		   // added to dependon
	        		   char col = currentNumber.charAt(0);
	        		   Integer row = Integer.valueOf(currentNumber.substring(1));
	        		   dependsOn.add(new Coordinate(col,row));
	        		   isAlphabetic = false;
	                   currentNumber.delete(0, currentNumber.length());
	        		   continue;
	        	   }
	               currentNumber.delete(0, currentNumber.length());
	           }
	       }
	}
	private boolean parseFormula(String formula) {
		parseCellDependencies(formula);
		if(dependsOn.size() == 0)
		value = calculate(formula);
		
		// parse dependency 
		// coordinate.toString() first element in edgs
		// map(key(coordinate.tostring), [] all dependincies) --> adjacent list 
		return true;
		
	}
	private Float calculate(String s) {
	       if (s == null || s.isEmpty()) return (float) 0;
	       int len = s.length();
	       Stack<Float> stack = new Stack<Float>();
	       StringBuilder currentNumber = new StringBuilder();
	       char operation = '+';
	       boolean isAlphabetic = false;
	       for (int i = 0; i < len; i++) {
	           char currentChar = s.charAt(i);
	           if(currentChar == '=') continue;
	           if (Character.isAlphabetic(currentChar)) {  
	        	   isAlphabetic = true;
	        	   currentNumber.append(currentChar);
	           }
	           if (Character.isDigit(currentChar)|| currentChar=='.') {
	               currentNumber.append(currentChar);
	           }
	           if (!Character.isDigit(currentChar) && currentChar != '.' 
	        		   && !Character.isAlphabetic(currentChar) && !Character.isWhitespace(currentChar) || i == len - 1) {
	        	   if(isAlphabetic) {
	        		   // get it's value
	        		   
	        		   isAlphabetic = false;
	                   stack.push(spreadsheetData.getCell(currentNumber.charAt(0), 
	        				   Integer.parseInt(currentNumber.substring(1))).getValue());
	                   currentNumber.delete(0, currentNumber.length());
	        		   continue;
	        	   }
	               if (operation == '-') {
	                   stack.push(-Float.valueOf(currentNumber.toString()));
	               }
	               else if (operation == '+') {
	                   stack.push(Float.valueOf(currentNumber.toString()));
	               }
	               else if (operation == '*') {
	                   stack.push(stack.pop() * Float.valueOf(currentNumber.toString()));
	               }
	               else if (operation == '/') {
	                   stack.push(stack.pop() / Float.valueOf(currentNumber.toString()));
	               }
	               operation = currentChar;
	               currentNumber.delete(0, currentNumber.length());
	           }
	       }
	       float result = 0;
	       while (!stack.isEmpty()) {
	           result += stack.pop();
	       }
		
	       return result;
	   }
	public void evaluate() {
		// calculate the value
		value = calculate(formulaString);
	}
	@Override
	public String toString() {
		return "SpreadsheetCell [row=" + row + ", column=" + column + ", coordinate=" + coordinate + ", value=" + value
				+ ", formula=" + ", FormulaString=" + formulaString + ", dependsOn=" + dependsOn
				+ ", spreadsheetData=" + spreadsheetData + "]";
	}
	
	
	
}
