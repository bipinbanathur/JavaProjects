package com.rtaware.data;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

import com.rtaware.config.FlowVariables;
import com.rtaware.config.Logger;

public class DBProcessor
{

	private static Connection 	con = null;
	private static Logger 		log = null;
	
	private static void connect()
	{
		try
		{
			if (con == null)
			{
				Class.forName("org.postgresql.Driver");
				String url = "jdbc:postgresql://192.168.1.231:5432/csadb";
				con = DriverManager.getConnection(url, "csa", "csa");
			}
			else
			{
				System.out.println("Already Connected: Reset the connection before connecting again");
			}
		}
		catch (Exception e)
		{
			System.out.println("ex " + e);
		}
	}
	
	public static void enableLog()
	{
		FlowVariables 			variables 		=	new FlowVariables();		
		try
		{ 
			log = (Logger) variables.getVar("log"); 
		} 
		catch(Exception e)
		{ 
		}
		
		if(log == null)
		{	
			try
			{ 
				log = (Logger) variables.getVar("log"); 
			}
			catch(Exception e)
			{				
			}
			
			if(log== null)
			{
				log = new Logger();
			}
		}

		variables = null;
	}

	private static void connect(String connectionName)
	{
		try
		{
			String hostName 		= 	"";
			String portNo			=	"";
			String userName			=	"";
			String password			=	"";			
			String dbName 			=	"";
			String dbType 			=	"";
			String connecitonString =	"";

				
				try
				{
					if(log == null) enableLog();
					
					if(log!= null)
					{
						log.debug("Inside DBProcessor connect function conenctionName : "+connectionName);
					}
					
					//System.out.println("Inside Connection : "+connectionName);
					DBConnections dbCon = (DBConnections) FlowVariables.get(connectionName);
					if (dbCon != null )
					{					
						hostName 		= 	dbCon.getHostName();
						portNo			=	dbCon.getPortNo();
						userName		=	dbCon.getUserName();
						password		=	dbCon.getPassword();			
						dbName 			=	dbCon.getDbName();
						dbType 			=	dbCon.getDbType();
						
						//System.out.println(hostName+" " + portNo +" " +userName+" " +  password +" " + dbName +" " +dbType );
						
						switch(dbType)
						{
							case  	"Vertica"		: 
								
									Class.forName("com.vertica.jdbc.Driver");					
									connecitonString = "jdbc:vertica://"+hostName+":"+portNo+"/"+dbName;									
									break;
							
							case  	"PostgressSQL": 
								
									Class.forName("org.postgresql.Driver");						
									connecitonString = "jdbc:postgresql://"+hostName+":"+portNo+"/"+dbName;
									break;
								
							case  	"Oracle": 
								
									Class.forName("oracle.jdbc.driver.OracleDriver");						
									connecitonString = "jdbc:oracle:thin:@"+hostName+":"+portNo+":"+dbName;
									break;
									
							case  	"MySQL": 
								
								Class.forName("com.mysql.jdbc.Driver");						
								connecitonString = "jdbc:mysql://"+hostName+":"+portNo+"/"+dbName;
								break;
						}
					}
					else
					{
						Logger.showMessage("Exception", "DB Connection Does not Exist");
					}
					
					con = DriverManager.getConnection(connecitonString,userName, password);
				}
				catch(Exception e)
				{
					Logger.showMessage("Exception","Unable to Connect to DB");
			        if(log!= null)
			        {	
			        	log.exception(e);
			        }	
				}
		}
		catch (Exception e)
		{
			Logger.showMessage("Exception","Unable to Connect to DB");
	        if(log!= null)
	        {	
	        	log.exception(e);
	        }	
		}
	}
	
	public static String getValue(String sqlStatement)
	{
		String resultData = "";
		try
		{

			if (con == null)
			{
				connect();
			}
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(sqlStatement);
			while (rs.next())
			{

				resultData = rs.getString(1);
			}

		}
		catch (Exception e)
		{
			Logger.showMessage("Exception","Unable to Retrive to Data from DB");
	        if(log!= null)
	        {	
	        	log.exception(e);
	        }	
		}

		return resultData;
	}

	public static String getValue(String sqlStatement,String connectionName)
	{
		
		if(log!= null)
		{
			log.debug("Inside DBProcessor getValue function ");
			log.debug(sqlStatement);
		}
		
		
		String resultData = "";
		try
		{

			connect(connectionName);

			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(sqlStatement);
			while (rs.next())
			{
				resultData = rs.getString(1);
			}
			st.close();
			con.close();
		}
		catch (Exception e)
		{
			Logger.showMessage("Exception","Unable to Retrive to Data from DB");
	        if(log!= null)
	        {	
	        	log.exception(e);	        	
	        }
		}
		
		return resultData;
	}
	
	public ArrayList<HashMap<Object, Object>> resultSetToHashMap(Connection con,String sqlStatement) throws Exception
	{   
		HashMap<Object, Object> record = new HashMap<Object,Object>();		
		Connection 	conn 	= con;
		ResultSet 	rs 		= null;
		Statement 	st 		= null;
			if(conn==null)
			{
	        	throw new Exception("DB is not connected");
			}
				
			//Execute a query
	        System.out.println("Creating statement...");
	        st = conn.createStatement();
	        rs = st.executeQuery(sqlStatement);
	        ResultSetMetaData md = rs.getMetaData();
	        int columns = md.getColumnCount();
	        
	        //Fetch out rows
	        ArrayList<HashMap<Object, Object>> data = new ArrayList<HashMap<Object,Object>>();

	        while (rs.next()) 
	        {
	        	record = new HashMap<Object,Object>();
	        	for (int i = 1; i <= columns; i++) 
	        	{
		              record.put(md.getColumnName(i), rs.getObject(i));
		        }
	        	data.add(record);
	        }

	        if(data.isEmpty())
	        {
	        	throw new Exception("Fetched zero records");
	        }
	        return data;
	        
	}
		
	public ResultSet getResultSet(Connection con, String sqlStatement)
	{

		Connection 	conn 	= con;
		ResultSet 	rs 		= null;
		Statement 	st 		= null;
		try
		{			
			if (conn == null) 
			{
				throw new Exception("DB is not Connected");
			}
			st 	=	conn.createStatement();
			rs 	= 	st.executeQuery(sqlStatement);

			int rowCount = 0;
			while (rs.next())
			{
				rowCount++;
			}
			System.out.println("Number of Records:" + rowCount);

			if (rowCount == 0) { throw new Exception("Zero records found"); }
			return rs;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return rs;
	}
}