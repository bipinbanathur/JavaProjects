package com.rtaware.repoapp.test;

import java.sql.*;

import com.rtaware.repoapp.model.ordata.DBResultSet;
import com.rtaware.repoapp.model.ordata.TableColumn;
import com.rtaware.repoapp.model.ordata.TableRecord;

public class PreSetup
{

   public static void main( String args[] )
   {
      Connection conn = null;
      Statement stmt = null;
      
      try 
      {
         Class.forName("org.sqlite.JDBC");
         conn = DriverManager.getConnection("jdbc:sqlite:test.db");
         System.out.println("Opened database successfully");

//         stmt = conn.createStatement();
//         String sql = "CREATE TABLE PERSON " +
//                        "(ID 			 INT 	 PRIMARY KEY," +
//                        " NAME           TEXT    NOT NULL, " + 
//                        " AGE            INT     NOT NULL, " + 
//                        " ADDRESS        CHAR(50), " + 
//                        " SALARY         INT)"; 
//         stmt.executeUpdate(sql);
//         stmt.close();
//         
//         System.out.println("Table Created");
         
//         PreparedStatement pst = conn.prepareStatement("INSERT INTO PERSON VALUES (?,?,?,?,?)");
//         for(int index=0;index<10;index++)
//         {
//             pst.setInt(1, (index+1)); 
//             pst.setString(2, "Name "+(index+1));
//             pst.setInt(3, (30+index+1)); 
//             pst.setString(4, "Address "+(index+1));
//             pst.setInt(5, 38781);
//             pst.executeUpdate();
//         }
         stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery("SELECT * FROM PERSON");
         ResultSetMetaData rsmd = rs.getMetaData();
         Integer colCount = rsmd.getColumnCount();
         System.out.println("Column Count : "+colCount);
    	 for(int index=0;index < colCount;index++)
    	 {
             System.out.print(rsmd.getColumnName(index+1)+"\t\t");
    	 }
    	 System.out.print("\n");
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
         System.out.println(resutlSet);
         rs.close();
         stmt.close();
         conn.close();
      } 
      catch ( Exception e ) 
      {
    	  e.printStackTrace();
         System.err.println( e.getClass().getName() + ": " + e.getMessage() );
         System.exit(0);
      }

   }
}
