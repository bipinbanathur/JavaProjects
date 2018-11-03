package com.rtaware.config;

import java.io.FileOutputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.RichTextString;

public class ExcelProcessor
{
	HSSFWorkbook		workbook 	= null;	
	FileOutputStream 	out 		= null;
	
	public ExcelProcessor(String workBook)
	{
		try
		{
			out = new FileOutputStream(workBook);
			workbook = new HSSFWorkbook();			
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
	
	public HSSFSheet createMainSheet(String sheetName) //, String[] columnNames
	{
		HSSFSheet 	sheet = null;
		try
		{
			sheet = workbook.createSheet(sheetName);		
			createColumn(sheet, "#TC","Test Case");
			createColumn(sheet, "#FlowID","Flow ID");
			createColumn(sheet, "#StepID","Step ID");
			createColumn(sheet, "#PK","Primary Key");
			createColumn(sheet, "#FK","Forign Key");
			
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		return sheet;
	}
	
	public HSSFSheet createSheet(String sheetName) //, String[] columnNames
	{
		HSSFSheet 	sheet = null;
		try
		{
			sheet = workbook.createSheet(sheetName);		
			createColumn(sheet, "#PK","Primary Key");
			createColumn(sheet, "#FK","Forign Key");
			
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		return sheet;
	}
	
	public void createColumn(HSSFSheet sheet, String columnName,String cellComment)
	{
		HSSFRow row = sheet.getRow(0);
		if (row == null) row = sheet.createRow(0);
		int colCount = row.getPhysicalNumberOfCells();
		HSSFCell cell = row.createCell(colCount);
		cell.setCellValue(columnName);	
		
		if(!cellComment.equals(""))
		{
			CreationHelper 	factory = workbook.getCreationHelper();
			Drawing 		drawing = sheet.createDrawingPatriarch();
			ClientAnchor 	anchor 	= factory.createClientAnchor();
		    anchor.setCol1(cell.getColumnIndex());
		    anchor.setCol2(cell.getColumnIndex()+1);
		    anchor.setRow1(row.getRowNum());
		    anchor.setRow2(row.getRowNum()+3);

		    // Create the comment and set the text+author
		    Comment comment = drawing.createCellComment(anchor);
		    RichTextString commentString = factory.createRichTextString(cellComment);
		    comment.setString(commentString);
		    comment.setAuthor("Apache POI");

		    // Assign the comment to the cell
		    cell.setCellComment(comment);			
		}
	}
	
	public void closeStream()
	{
		try
		{
			workbook.write(out);
		    out.close();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}

}

