package com.bipin.data;

import java.io.*;
import java.util.*;
import java.text.*;

import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.*;
import org.apache.poi.ss.usermodel.*;

import com.bipin.config.FlowVariables;
import com.bipin.config.Logger;

//

public class Excel
{

	private 	String 				fileName 		= "";
	private 	String 				sheetName 		= "";
	private 	int 				cells 			= 0;
	protected 	HSSFWorkbook 		wb 				= null;
	private 	HSSFSheet 			sheet 			= null;
	private 	HSSFCellStyle 		style 			= null;
	private 	String[] 			sheetNames 		= null;
	private 	List<String>[] 		columnsArray 	= null;
	private 	List<String> 		sheets 			= new ArrayList<String>();
	private 	SimpleDateFormat 	sdf 			= new SimpleDateFormat("dd-mmm-yyyy");
	private		Logger				log				= null;
	
	public Excel()
	{
		enableLog();
		fileName = "";
		processWorkBook();
	}

	public Excel(String path)
	{
		enableLog();
		fileName = path;
		processWorkBook();
	}
	
	public void enableLog()
	{
		try
		{ 
			FlowVariables  variables 		=	new FlowVariables();
			log = (Logger) variables.getVar("log"); 
			variables = null;
		} 
		catch(Exception e)
		{ log = null; }	
	}

	/*
	 * Opens the Sheet , Get Total Number of Sheets, Creates List
	 * columnsArray Each element contains list of columns
	 */
	@SuppressWarnings("unchecked")
	private void processWorkBook()
	{
		try
		{

			FileInputStream fis = new FileInputStream(fileName);
			POIFSFileSystem fs = new POIFSFileSystem(fis);
			wb = new HSSFWorkbook(fs);
			style = wb.createCellStyle();
			style.setFillBackgroundColor(HSSFColor.AQUA.index);
			style.setFillPattern(HSSFCellStyle.NO_FILL);

			int sheetCount = wb.getNumberOfSheets();
			columnsArray = new List[sheetCount];

			for (int sheetIndex = 0; sheetIndex < sheetCount; sheetIndex++)
			{
				List<String> columnsList = new ArrayList<String>();
				sheet = wb.getSheetAt(sheetIndex);
				sheetName = wb.getSheetName(sheetIndex);
				sheets.add(sheetIndex, sheetName); //

				HSSFRow row = sheet.getRow(0);
				cells = row.getPhysicalNumberOfCells();

				for (int c = 0; c < cells; c++)
				{
					HSSFCell cell = row.getCell(c);
					cell.setCellType(Cell.CELL_TYPE_STRING);
					HSSFRichTextString value = cell.getRichStringCellValue();
					columnsList.add((value.getString())); //.toUpperCase()
				}
				columnsArray[sheetIndex] = columnsList;
			}

			
			if(log!= null)
			{
				log.debug("Processed Excel Workbook "+fileName+" Succesfully" );
			}
		}
		catch (Exception e)
		{
			if(log!= null)
			{
				log.exception(e,"ExcelProcessingException" );
			}
		}

	}

	/*
	 * Returns name of sheets in the work book
	 */
	public String[] getSheetNames()
	{

		try
		{
			int sheetCount = wb.getNumberOfSheets();
			sheetNames = new String[sheetCount];
			for (int i = 0; i < wb.getNumberOfSheets(); i++)
			{
				sheetNames[i] = wb.getSheetAt(i).getSheetName();
			}

		}
		catch (Exception e)
		{
			if(log!= null)
			{
				log.exception(e,"Unable to Get Sheet Names" );
			}
		}
		return sheetNames;

	}

	/*
	 * returns the number of column in a particular sheet
	 */
	public int getColumns(String sheetName)
	{
		int columnsCount = 0;
		try
		{
			HSSFRow row = wb.getSheet(sheetName).getRow(0);
			columnsCount = row.getPhysicalNumberOfCells();
		}
		catch (Exception e)
		{
			if(log!= null)
			{
				log.exception(e,"Unable to Get Column Count" );
			}
		}
		return columnsCount;

	}

	/*
	 * returns the number of rows in a particular sheet
	 */
	public int getRows(String sheetName)
	{
		int row_count = 0;
		try
		{
			return wb.getSheet(sheetName).getPhysicalNumberOfRows();
		}
		catch (Exception e)
		{
			if(log!= null)
			{
				log.exception(e,"Unable to Get Row Numbers" );
			}
		}
		return row_count;
	}

	/*
	 * Returns the list of columns in a particular sheet
	 */
	public List<String> getColumnList(String sheetName)
	{
		return columnsArray[sheets.indexOf(sheetName)]; //.toUpperCase()
	}

	/*
	 * Returns the Value of cell Pass Sheet Name , Row Index and Column Index
	 */
	public String getValue(String sheetName, int rowIndex, int columnIndex)
	{

		String cellData = "";

		try
		{
			sheet 			= wb.getSheet(sheetName);
			HSSFRow row		= sheet.getRow(rowIndex);
			HSSFCell cell 	= row.getCell(columnIndex);
			if (cell != null) return getCellData(cell);

		}
		catch (Exception e)
		{
			if(log!= null)
			{
				log.exception(e,"Unable to Get Cell Value" );
			}
		}
		return cellData;
	}

	/*
	 * Get Cell Comment /tool Tip
	 */
	public String getComment(String sheetName, int rowIndex, int columnIndex)
	{

		String cellComment = "";

		try
		{
			sheet = wb.getSheet(sheetName);
			HSSFRow row = sheet.getRow(rowIndex);
			HSSFCell cell = row.getCell(columnIndex);
			if (cell != null) return getCellComment(cell);

		}
		catch (Exception e)
		{
			if(log!= null)
			{
				log.exception(e,"Unable to Get Cell Comment" );
			}
		}
		return cellComment;
	}

	/*
	 * Used Internally to get the column index of a Column name in a sheet
	 */
	private int getColumnindex(String sheetName, String columnName)
	{
		return columnsArray[sheets.indexOf(sheetName)].indexOf(columnName);
	}

	/*
	 * Override of get value , Returns the cell data by specifying the row index
	 * and colum name of a particular sheet
	 */
	public String getValue(String sheetname, int row, String columnname)
	{
		return getValue(sheetname, row, getColumnindex(sheetname, columnname));
	}

	/*
	 * Get Cell Tool Tip / Comment
	 */
	public String getComment(String sheetName, int rowIndex, String columnName)
	{
		return getComment(sheetName, rowIndex, getColumnindex(sheetName, columnName));
	}

	/*
	 * Overridden Function of : public void setvalue(String sheetname,int rw,int
	 * cl ,String data) Write a new Value or Override the existing value of a
	 * particular cell in a particular sheet Specify Sheet Name ,Row Index and
	 * Column Name
	 */

	public void setValue(String sheetName, int rowIndex, String columnName, String cellData)
	{
		setValue(sheetName, rowIndex, getColumnindex(sheetName, columnName), cellData);
	}

	/*
	 * Returns a List of Records, Implementing Parent Child Relationship here
	 */
	public List<Hashtable<String, String>> getChild(String sheetName, String keycolumn, String keyvalue)
	{
		List<Hashtable<String, String>> listht = new ArrayList<Hashtable<String, String>>();

		try
		{
			Sheet sheet = wb.getSheetAt(sheets.indexOf(sheetName));//.toUpperCase()
			int row_count = sheet.getPhysicalNumberOfRows();
			for (int r = 1; r < row_count; r++)
			{
				String value = getValue(sheetName, r, keycolumn);

				if (value.equals(keyvalue))
				{
					Hashtable<String, String> ht = new Hashtable<String, String>();
					HSSFRow rw = (HSSFRow) sheet.getRow(0);
					int col_count = rw.getPhysicalNumberOfCells();

					for (int c = 0; c < col_count; c++)
					{

						String key = (String) columnsArray[sheets.indexOf(sheetName)].get(c);//.toUpperCase()
						HSSFRow row = (HSSFRow) sheet.getRow(r);
						HSSFCell cell = row.getCell(c);
						if (cell != null) ht.put(key, getCellData(cell));
						else ht.put(key, "");
					}
					ht.put("PHYSICAL_ROW_NUM", new Integer(r).toString());
					listht.add(ht);

				}

			}

		}
		catch (Exception e)
		{
			if(log!= null)
			{
				log.exception(e,"Unable to Get Child Sheet Values" );
			}
		}
		return listht;
	}

	/*
	 * Returns a String array of Physical Number of Rows, Implementing Parent
	 * Child Relationship here
	 */
	public String[] getChildRowIndices(String sheetName, String keyColumn, String keyValue)
	{
		List<String> al = new ArrayList<String>();
		String[] rowNumbers = null;

		try
		{
			Sheet sheet = wb.getSheetAt(sheets.indexOf(sheetName)); //.toUpperCase()
			int rowCount = sheet.getPhysicalNumberOfRows();

			// int j=0;

			for (int r = 1; r < rowCount; r++)
			{
				String value = getValue(sheetName, r, keyColumn);
				if (value.equals(keyValue)) al.add(new Integer(r).toString());
			}

			// (String[]) al.toArray();

			rowNumbers = new String[al.size()];
			for (int i = 0; i < al.size(); i++)
			rowNumbers[i] = (String) al.get(i);

		}
		catch (Exception e)
		{
			if(log!= null)
			{
				log.exception(e,"Unable to Get Child Row Indices" );
			}
		}
		return rowNumbers;
	}

	/*
	 * Closing the Streams Here
	 */
	public void closeStream()
	{
		try
		{
			// fos.close();
			// fis.close();

		}
		catch (Exception e)
		{
			if(log!= null)
			{
				log.exception(e,"Unable to Close Data Stream" );
			}
		}
	}

	/*
	 * Implementation for getting Comment/Tool Tip from cell
	 */

	private String getCellComment(HSSFCell cell)
	{
		String cellComment = "";
		HSSFComment comment = cell.getCellComment();
		if(comment!= null)
		{
			if (comment.getString() != null)
			{
				HSSFRichTextString rich_text = comment.getString();
				cellComment = rich_text.toString();
			}
		}
			
		return cellComment;
	}


	private String getCellData(HSSFCell cell)
	{

		String data = "";
		try
		{
			if (cell.getCellType() == 0 && HSSFDateUtil.isCellDateFormatted(cell))
			{
				return sdf.format(cell.getDateCellValue()).toString();
			}
			else
			{
				cell.setCellType(Cell.CELL_TYPE_STRING);
				switch (cell.getCellType())
				{
				case Cell.CELL_TYPE_NUMERIC:
					return new Double(cell.getNumericCellValue()).toString();
				case Cell.CELL_TYPE_STRING:
					return cell.getRichStringCellValue().getString();
				case Cell.CELL_TYPE_BOOLEAN:
					return new Boolean(cell.getBooleanCellValue()).toString();
				case Cell.CELL_TYPE_BLANK:
					System.out.print("Blank");
					return data;
				case Cell.CELL_TYPE_ERROR:
					System.out.print("Error");
					return data;
				case Cell.CELL_TYPE_FORMULA:
					System.out.print("Formula");
					return data;
				default:
					System.out.print("Default");
					return data;
				}

			}
		}
		catch (Exception e)
		{
			if(log!= null)
			{
				log.exception(e,"Unable to Get Cell Value" );
			}
			return data;
			
		}
	}

	/*
	 * Write a new Value or Override the existing value of a particular cell in
	 * a particular sheet Specify Sheet Name ,Row Index and Column Index
	 */
	public void setValue(String sheetName, int rowIndex, int columnIndex, String cellData)
	{

		try
		{
			sheet = wb.getSheet(sheetName);
			HSSFRow row = sheet.getRow(rowIndex);
			HSSFCell cell = row.createCell(columnIndex);
			cell.setCellValue(cellData);
			FileOutputStream fos = new FileOutputStream(fileName);
			wb.write(fos);
			fos.close();
		}
		catch (Exception e)
		{
			if(log!= null)
			{
				log.exception(e,"Unable to Set Cell Value" );
			}
		}

	}

	public String[] getChildRowIndices(String sheetName, String[] keyColumns, String[] keyValues)
	{
		List<String> 	al 			= new ArrayList<String>();
		String[] 		rowNumbers 	= null;
		boolean 		flag 		= false;

		if (keyColumns.length != keyValues.length) return rowNumbers;

		try
		{
			Sheet sheet = wb.getSheetAt(sheets.indexOf(sheetName)); //.toUpperCase()
			int rowCount = sheet.getPhysicalNumberOfRows();

			for (int rowIndex = 1; rowIndex < rowCount; rowIndex++)
			{
				for (int columnIndex = 0; columnIndex < keyColumns.length; columnIndex++)
				{
					String columnValue = getValue(sheetName, rowIndex, keyColumns[columnIndex]);
					if (columnValue.equals(keyValues[columnIndex]) && (columnValue != "") && (columnValue != null))
					{
						flag = true;
					}
					else
					{
						flag = false;
						break;
					}
				}

				if (flag) al.add(new Integer(rowIndex).toString());

				flag = false;
			}

			rowNumbers = new String[al.size()];
			for (int i = 0; i < al.size(); i++)
			{
				rowNumbers[i] = (String) al.get(i);
			}

		}
		catch (Exception e)
		{
			if(log!= null)
			{
				log.exception(e,"Unable to Get Child Row Indices" );
			}
		}
		return rowNumbers;
	}


	public boolean checkSheetExists(String sheetName)
	{
		if (sheets.contains(sheetName)) return true;				
		return false;
	}

}
