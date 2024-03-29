package com.company.utilities;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;

public class ExcelUtils
{

	// Main Directory of the project
	public final String currentDir = System.getProperty("user.dir");

	// Location of Test data excel file
	public String testDataExcelPath;

	// Excel WorkBook
	private XSSFWorkbook excelWBook;

	// Excel Sheet
	private XSSFSheet excelWSheet;

	// Excel cell
	private XSSFCell cell;

	// Excel row
	private XSSFRow row;

	// Row Number
	public int rowNumber;

	// Column Number
	public int columnNumber;

	public void setTestDataExcelPath(String testDataExcelPath)
	{
		this.testDataExcelPath = currentDir + "/" + testDataExcelPath;
	}

	// Setter and Getters of row and columns
	public void setRowNumber(int pRowNumber)
	{
		rowNumber = pRowNumber;
	}

	public int getRowNumber()
	{
		return rowNumber;
	}

	public void setColumnNumber(int pColumnNumber)
	{
		columnNumber = pColumnNumber;
	}

	public int getColumnNumber()
	{
		return columnNumber;
	}

	// This method has two parameters: "Test data excel file name" and "Excel
	// sheet name"
	// It creates FileInputStream and set excel file and excel sheet to
	// excelWBook and excelWSheet variables.
	public void setExcelFileSheet(String sheetName)
	{

		try
		{
			// Open the Excel file

			FileInputStream ExcelFile = new FileInputStream(testDataExcelPath);
			excelWBook = new XSSFWorkbook(ExcelFile);
			excelWSheet = excelWBook.getSheet(sheetName);
		}
		catch (Exception e)
		{
			try
			{
				throw (e);
			}
			catch (IOException e1)
			{
				e1.printStackTrace();
			}
		}
	}

	public int getLastRow()
	{
		//		int lastRow = excelWSheet.getLastRowNum();
		int lastRow = excelWSheet.getPhysicalNumberOfRows();
		return lastRow;
	}

	public int getLastCol()
	{
		int lastCol = excelWSheet.getRow(0).getLastCellNum();
		return lastCol;
	}

	// This method reads the test data from the Excel cell.
	// We are passing row number and column number as parameters.
	public String getCellData(int RowNum, int ColNum)
	{
		try
		{
			cell = excelWSheet.getRow(RowNum).getCell(ColNum);
			DataFormatter formatter = new DataFormatter();
			String cellData = formatter.formatCellValue(cell);
			return cellData;
		}
		catch (Exception e)
		{
			throw (e);
		}
	}

	// This method takes row number as a parameter and returns the data of given
	// row number.
	public XSSFRow getRowData(int RowNum)
	{
		try
		{
			row = excelWSheet.getRow(RowNum);
			return row;
		}
		catch (Exception e)
		{
			throw (e);
		}
	}

}