package com.rtaware.data;

public class DBConnections
{
	private String hostName 		= 	"";
	private String portNo			=	"";
	private String dbName			=	"";
	private String userName			=	"";
	private String password			=	"";
	private String dbType			=	"";
	private String connectionName	=	"";
	
	public String getHostName()
	{
		return hostName;
	}
	public void setHostName(String hostName)
	{
		this.hostName = hostName;
	}
	public String getPortNo()
	{
		return portNo;
	}
	public void setPortNo(String portNo)
	{
		this.portNo = portNo;
	}
	public String getDbName()
	{
		return dbName;
	}
	public void setDbName(String dbName)
	{
		this.dbName = dbName;
	}
	public String getUserName()
	{
		return userName;
	}
	public void setUserName(String userName)
	{
		this.userName = userName;
	}
	
	public String getPassword()
	{
		return password;
	}
	public void setPassword(String password)
	{
		this.password = password;
	}
	public String getDbType()
	{
		return dbType;
	}
	public void setDbType(String dbType)
	{
		this.dbType = dbType;
	}
	public String getConnectionName()
	{
		return connectionName;
	}
	public void setConnectionName(String connectionName)
	{
		this.connectionName = connectionName;
	}


}
