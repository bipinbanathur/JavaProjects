package com.rtaware.repoapp.model.ordata;

//import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.ResultSet;
//import java.sql.ResultSetMetaData;

public class DBResultSet
{
	public String getFileName()
	{
		return fileName;
	}
	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}
	public String getSheetName()
	{
		return viewName;
	}
	public void setSheetName(String sheetName)
	{
		this.viewName = sheetName;
	}
	private String fileName = "";
	private String viewName = "";
	
	private List<TableRecord> recordList = new ArrayList<TableRecord>();
	
	public List<TableRecord> getRecordList()
	{
		return recordList;
	}
	public void setRecordList(List<TableRecord> recordList)
	{
		this.recordList = recordList;
	}
	
	
	public void addRecord(TableRecord tableRecord)
	{
		if(null != recordList)
		{
			recordList.add(tableRecord);
		}
	}
	public void processRecords()
	{
		try
	    {
			if(null != this.getFileName() || null != this.getSheetName() )
			{
//		        Class.forName( "sun.jdbc.odbc.JdbcOdbcDriver" );
//		        Connection con = DriverManager.getConnection("jdbc:odbc:;Driver={Microsoft Excel Driver(*.xls)};DBQ="+this.getFileName());
//
//		        Statement 				stmt	=	con.createStatement();
//		        ResultSet 				rs   	=	stmt.executeQuery("SELECT * FROM "+this.getSheetName());
//		        //ResultSetMetaData 	rsmd 	= 	rs.getMetaData();
//		        while(rs.next())
//		        {
//		            System.out.println(rs.getString(1));
//		        }
//		        con.close();
//		        rs.close();
//		        stmt.close();
			}
	    }
	    catch(Exception e)
	    {
	        e.printStackTrace();
	    }
	}
	
	public String toString()
	{
		String resultSetData = "{\n \"records\" : [";
//		if(null != recordList)
//		{

//			recordList.forEach(record -> 
//			{
//				resultSetData += record.toString();
//			});
//		}
		int recordSize = recordList.size();
		int recordIndex = 0;
		for(TableRecord tableRecord: recordList)
		{	
			resultSetData += tableRecord.toString();
			recordIndex++;
			if ( recordIndex != recordSize) resultSetData += ", \n";
		}
		return resultSetData+"\t]\n}";
	}
	public static void main(String[] args)
	{
		DBResultSet resultSet = new DBResultSet();
		resultSet.setFileName("D:\\Data.xls");
		resultSet.setSheetName("Employee");
		resultSet.processRecords();
		
	}
}
