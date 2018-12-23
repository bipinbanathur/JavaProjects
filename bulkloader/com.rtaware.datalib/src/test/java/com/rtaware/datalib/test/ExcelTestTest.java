//package com.rtaware.datalib.test;
//
//import java.util.stream.IntStream;
//
//import org.junit.Test;
//
//
//import com.rtaware.datalib.entities.Excel;
//
//public class ExcelTestTest
//{
//
//	@Test
//	public void test()
//	{
//		try(Excel excel = new Excel("C:\\Users\\bipin.banathur\\Desktop\\excel\\Demo.xls"))
//		{			
//			excel.getSheetNames().forEach(sheetName -> 
//			{
//				System.out.println("Sheet Name : " +sheetName+"\t\t\t\t Rows : "+excel.getRowCount(sheetName)+ "\t\t\t\t Columns "+excel.getColumnCount(sheetName));
//				
//			});			
//			
////			IntStream.range(1,10).forEach(row ->
////			{				
////				System.out.println(excel.getValue("Activities",row, "Activity")+"\t\t\t"+excel.getValue("Activities",row, "Status"));
////			});
//		}
//		catch(Exception e)
//		{
//			System.out.println(e);
//		}
//	}
//
//}
