package com.rtaware.repoapp.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

import com.rtaware.repoapp.model.ordata.DBResultSet;
import com.rtaware.repoapp.model.ordata.TableColumn;
import com.rtaware.repoapp.model.ordata.TableRecord;

public class ReportGenerator
{
	
	
	public String sampleReport()
	{
		DBResultSet 	resutlSet = new DBResultSet();
		try
		{
			for (int recordIndex = 0; recordIndex < 10 ; recordIndex++)
			{
				TableRecord 	tableRecord = new TableRecord();	
				
				for (int columnIndex = 0; columnIndex < 2 ; columnIndex++)
				{
					TableColumn tableColumn = new TableColumn();;
					if(columnIndex ==0 )
					{
						tableColumn.setFieldName("id");
						tableColumn.setColumnValue(""+recordIndex);
						tableColumn.setDataType("numeric");
					}
					else
					{
						tableColumn.setFieldName("name");
						tableColumn.setColumnValue("Name "+recordIndex);
						tableColumn.setDataType("text");
					}
					tableRecord.addColumn(tableColumn);
				}				
				resutlSet.addRecord(tableRecord);
			}
			return resutlSet.toString();
		}
		catch(Exception e)
		{
			 return "{\"message\" : \"Report Generation Failed\"}";
		}
	}
	public String generateReport(String tableName)
	{
	  Connection 	conn = null;
	  Statement 	stmt = null;	  
	  try 
	  {
		 Class.forName("org.sqlite.JDBC");
		 conn = DriverManager.getConnection("jdbc:sqlite:test.db");
		 stmt = conn.createStatement();
		 ResultSet rs = stmt.executeQuery("SELECT * FROM "+tableName);
		 ResultSetMetaData rsmd = rs.getMetaData();
		 Integer colCount = rsmd.getColumnCount();
		 DBResultSet 	resutlSet = new DBResultSet();
	     while(rs.next())
	     {
			 TableRecord 	tableRecord = new TableRecord();
	    	 for(int index=0;index < colCount;index++)
	    	 {
				TableColumn tableColumn = new TableColumn();
				tableColumn.setFieldName(rsmd.getColumnName(index+1));
				tableColumn.setColumnValue(rs.getObject(index+1).toString());
				tableColumn.setDataType(rsmd.getColumnTypeName(index+1));
				tableRecord.addColumn(tableColumn);
	    	 }
	    	 resutlSet.addRecord(tableRecord);
	     }		     
	     rs.close();
	     stmt.close();
	     conn.close();
	     return resutlSet.toString();
	  }
	  catch ( Exception e ) 
	  {
		 e.printStackTrace();
		 return "{\"message\" : \"Report Generation Failed\"}";
		 
	  }
	}
}
