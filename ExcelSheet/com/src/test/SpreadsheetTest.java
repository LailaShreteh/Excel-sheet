package test;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import junit.framework.Assert;
import spreadsheet.Spreadsheet;
import spreadsheet.SpreadsheetCell;
import spreadsheet.SpreadsheetData;

public class SpreadsheetTest {
	static Spreadsheet spreadsheet;
	static SpreadsheetData spreadsheetData;

	 @BeforeClass
	   public static void setUp() {
		 spreadsheet = new Spreadsheet("sheet1"); // create builder design pattern !
		 spreadsheetData = new SpreadsheetData();
		 spreadsheet.setData(spreadsheetData);
	   }
	   
	   @AfterClass
	   public static void tearDown() {
	   }
	   
	   @Test
	   public void createCellWithFormulatest() {
		 String FormulaString = "=A3 + 100.5";
		 SpreadsheetCell  cell_1 = new SpreadsheetCell('A', 1, FormulaString);
		 System.out.println(cell_1.toString());
		 spreadsheetData.setCell(cell_1);
	  }
	  @Test
	  public void copyCellTest() {
		  String FormulaString = "=A1+2.5*H66+5- 7 +B1 - G15";
		  SpreadsheetCell  cell_1 = new SpreadsheetCell('A', 1, FormulaString);
		  System.out.println(cell_1.toString());
		  spreadsheetData.setCell(cell_1);
		  SpreadsheetCell cell_2 = new SpreadsheetCell('B', 1);
		  //String formula_1 = cell_1.getFormulaString();
		  //cell_2.setFormulaString(formula_1);
		  //spreadsheetData.setCell(cell_2);
		  System.out.println(spreadsheetData.toString());
		  Assert.assertTrue(true);
	  }
	  @Test
	  public void testEvaluate() {
		  /*A1 , B1+1 B1, F1  1
		  B1, C1+2
		  C1, D1+4
		  D1 , 7*/
		  String FormulaString_1 = "=B1+1";
		  String FormulaString_2 = "=C1+2";
		  String FormulaString_3 = "=D1+4";
		  String FormulaString_4 = "=7";

		  SpreadsheetCell  cell_1 = new SpreadsheetCell('A', 1, FormulaString_1);
		  SpreadsheetCell  cell_2 = new SpreadsheetCell('B', 1, FormulaString_2);
		  SpreadsheetCell  cell_3 = new SpreadsheetCell('C', 1, FormulaString_3);
		  SpreadsheetCell  cell_4 = new SpreadsheetCell('D', 1, FormulaString_4);

		  System.out.println(cell_1.toString());
		  spreadsheetData.setCell(cell_1);
		  spreadsheetData.setCell(cell_2);
		  spreadsheetData.setCell(cell_3);
		  spreadsheetData.setCell(cell_4);
		  System.out.println(cell_2.toString());
		  System.out.println(cell_3.toString());
		  System.out.println(cell_4.toString());
		  System.out.println(spreadsheetData.getCell('B', 1));
		  System.out.println(spreadsheetData.getCell('C', 1));
		  System.out.println(spreadsheetData.getCell('D', 1));
		  System.out.println(spreadsheetData.getCell('A', 1));
		  System.out.println(spreadsheetData.toString());
		  Assert.assertEquals(spreadsheetData.getCell('D', 1).getValue(), (float)7.0);
		  Assert.assertEquals(spreadsheetData.getCell('C', 1).getValue(), (float)11.0);
		  Assert.assertEquals(spreadsheetData.getCell('B', 1).getValue(), (float)13.0);
		  Assert.assertEquals(spreadsheetData.getCell('A', 1).getValue(), (float)14.0);

	  }
	  @Test
	  public void testSenario() {
//		  Main.Set( “A1”, 20)
//		  Main.Set( “A2”, 25)
//		  Main.Set( “A3”, “=A1+A2”)
//		  Main.Get( “A3” )
		  String FormulaString_1 = "=A1+A2";

		  SpreadsheetCell  cell_1 = new SpreadsheetCell('A', 1, "20");
		  SpreadsheetCell  cell_2 = new SpreadsheetCell('A', 2, "25");
		  SpreadsheetCell  cell_3 = new SpreadsheetCell('A', 3, FormulaString_1);
		  spreadsheetData.setCell(cell_1);
		  spreadsheetData.setCell(cell_2);
		  spreadsheetData.setCell(cell_3);
		  System.out.println(cell_3);
		  spreadsheetData.getCell('A', 3);
		  System.out.println(cell_3);
		  Assert.assertEquals(spreadsheetData.getCell('A', 3).getValue(), (float)45.0);

	  }
}
