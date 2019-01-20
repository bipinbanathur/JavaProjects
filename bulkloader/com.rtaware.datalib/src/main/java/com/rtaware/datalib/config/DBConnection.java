package com.rtaware.datalib.config;

import java.util.Arrays;

public class DBConnection
{
	@Override
	public String toString()
	{
		return "DBConnection [hostName=" + hostName + ", portNo=" + portNo + ", dbName=" + dbName + ", userName="
				+ userName + ", password=" + Arrays.toString(password) + ", dbType=" + dbType + ", connectionName="
				+ connectionName + ", dbVersion=" + dbVersion + "]";
	}
	
	public DBConnection(String hostName, String portNo, String dbName, String userName, char[] password, String dbType,
			String connectionName, String dbVersion)
	{
		super();
		this.hostName = hostName;
		this.portNo = portNo;
		this.dbName = dbName;
		this.userName = userName;
		this.password = password;
		this.dbType = dbType;
		this.connectionName = connectionName;
		this.dbVersion = dbVersion;
	}

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

	public char[] getPassword()
	{
		return password;
	}

	public void setPassword(char[] password)
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

	public String getDbVersion()
	{
		return dbVersion;
	}

	public void setDbVersion(String dbVersion)
	{
		this.dbVersion = dbVersion;
	}

	private String hostName 		;
	private String portNo			;
	private String dbName			;
	private String userName			;
	private char[] password			;
	private String dbType			;
	private String connectionName	;
	private String dbVersion		;

}

