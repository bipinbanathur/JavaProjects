package com.rtaware.datalib.entities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;


import com.rtaware.datalib.DataProvider;
import com.rtaware.datalib.config.EntityType;
import com.rtaware.datalib.config.ErrorCodes;
import com.rtaware.datalib.config.DataException;

public class Excel implements DataProvider
{
	
	protected 	HSSFWorkbook 					workBook 		= null;
	private 	String 							fileName 		= "";	
	private 	EntityType 						entityType 		= EntityType.EXCEL;
	private 	List<String> 					sheetNames 		= new ArrayList<>();
	private 	DataFormatter 					dataFormatter	= new DataFormatter();
	private		HashMap<String,Set<String>> 	sheetColumns	= new HashMap<>();
	private		Integer							rowHeaderIndex	= 0;
	
	public Excel() 
	{
		processExel();
	}
	
	public Excel(String fileName) 
	{
		this.fileName = fileName;
		processExel();
	}

	public HSSFWorkbook getWorkBook()
	{
		return workBook;
	}

	public void setWorkBook(HSSFWorkbook workBook)
	{
		this.workBook = workBook;
	}

	public String getFileName()
	{
		return fileName;
	}

	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}
	
	public List<String> getSheetNames()
	{
		return sheetNames;
	}

	public EntityType getEntityType()
	{
		return entityType;
	}
	
	public Integer getRowHeaderIndex()
	{
		return rowHeaderIndex;
	}

	public void setRowHeaderIndex(Integer rowHeaderIndex)
	{
		this.rowHeaderIndex = rowHeaderIndex;
	}

	private void processExel() 
	{
		
		try(HSSFWorkbook workBook = new HSSFWorkbook(new POIFSFileSystem(new FileInputStream(fileName))))
		{
			workBook.forEach(sheet ->
			{
				String sheetName = sheet.getSheetName();
				sheetNames.add(sheetName);
				Set<String> 	columnNames = new HashSet<>();
				for(Cell cell :	sheet.getRow(rowHeaderIndex) )
				{
					String columnName = dataFormatter.formatCellValue(cell);
					try
					{
						columnNames.add(columnName);
					}
					catch(Exception e)
					{
						throwException(ErrorCodes.DUPLICATE_COLUMN_NAMES);
					}
	            }
				sheetColumns.put(sheetName,columnNames);
				columnNames = null;
			});
			setWorkBook(workBook);
		}
		catch (Exception e)
		{
			throwException(ErrorCodes.INVALID_ENTITY_FORMAT);
		}
	}
	
	public void close()
	{
		try
		{
			if (null != sheetColumns) sheetColumns = null;
			if (null != sheetNames)   sheetNames = null;
			workBook.close();
		}
		catch (IOException e)
		{
			throwException(ErrorCodes.ENTITY_NOT_CLOSED);
		}
	}

	public void setEntityType(EntityType entityType)
	{
		this.entityType = entityType;
		if (getEntityType() != EntityType.EXCEL) throwException (ErrorCodes.UNSUPPORTED_ENTITY);
	}

	public String getValue(String entitySpecifier, Integer rowIndex, Integer columnIndex)
	{
		if (! isAccessible())  { return ""; };
		try
		{
			if (sheetNames.contains(entitySpecifier))
			{
				HSSFSheet 	sheet 		= workBook.getSheet(entitySpecifier);
				HSSFRow 	row			= sheet.getRow(rowIndex);
				HSSFCell 	cell 		= row.getCell(columnIndex);			
				return dataFormatter.formatCellValue(cell);
			}			
		}
		catch(Exception e)
		{
			throwException(ErrorCodes.ENTITIY_NON_ACCESSIBLE);
		}
		return "";
	}
	
	public String getValue(String sheetName, Integer rowIndex, String columnName)
	{
		if (! isAccessible())  {  return ""; };
		int columnIndex = 0;
		if (sheetNames.contains(sheetName))
		{
			HSSFSheet 	sheet 	= workBook.getSheet(sheetName);
			for(Cell cell :	sheet.getRow(rowHeaderIndex) )
			{
				if(dataFormatter.formatCellValue(cell).equals(columnName))
				{
					return getValue(sheetName,rowIndex,columnIndex).trim();
				}
				columnIndex++;
            }			
		}
		return "";
	}
	
	public Integer getRowCount(String sheetName)
	{
		if (! isAccessible()) { return 0; };
		try 
		{
			return workBook.getSheet(sheetName).getPhysicalNumberOfRows();
		} 
		catch (Exception e)
		{
			throwException(ErrorCodes.ENTITIY_NON_ACCESSIBLE);
			return 0;
		}
	}
		
	public Integer getColumnCount(String sheetName)
	{
		if (! isAccessible())  { return 0; };
		try 
		{
			return sheetColumns.get(sheetName).size();
		} 
		catch (Exception e)
		{
			throwException(ErrorCodes.ENTITIY_NON_ACCESSIBLE);
			return 0;
		}
	}
	
	public List<Integer>  getMatchingIndices(String sheetName, String columnName, String columnValue)
	{
		if (! isAccessible()) { return null ; };
			
		if (sheetNames.contains(sheetName))
		{
			HSSFSheet 	sheet 	= workBook.getSheet(sheetName);
			Integer columnIndex = 0;
			for(Cell cell :	sheet.getRow(rowHeaderIndex) )
			{
				if(dataFormatter.formatCellValue(cell).equals(columnName))
				{
					return getMatchingIndices(sheetName,columnIndex,columnValue);
				}
				columnIndex++;
	        }	
		}
		return null ;
	}
	
	public List<Integer> getMatchingIndices(String sheetName, Integer columnIndex, String columnValue)
	{
		if (! isAccessible())  { return null; };
		List<Integer> matchingIndices = new ArrayList<>();
		if (sheetNames.contains(sheetName))
		{
			AtomicInteger rowIndex = new AtomicInteger();
			HSSFSheet 	sheet 	= workBook.getSheet(sheetName);
			sheet.forEach(row ->
			{
				Cell cell = row.getCell(columnIndex);
				if(dataFormatter.formatCellValue(cell).equals(columnValue))
				{
					matchingIndices.add(rowIndex.get());
				}
				rowIndex.incrementAndGet();
			});
			return matchingIndices;
		}
		return null;
	}

	private boolean isAccessible()
	{
		if ( null == sheetNames || null == workBook || null == dataFormatter || null == sheetColumns) 
		{
			throwException(ErrorCodes.ENTITIY_NON_ACCESSIBLE);
			return false;
		}
		return true;
	}
	
	private void throwException(String exMessage)
	{
		try
		{
			throw new DataException( exMessage );
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
	}

}
