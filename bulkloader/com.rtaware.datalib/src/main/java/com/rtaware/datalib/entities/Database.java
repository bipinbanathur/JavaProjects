package com.rtaware.datalib.entities;


import com.rtaware.datalib.DataProvider;
import com.rtaware.datalib.config.EntityType;

public class Database implements DataProvider, AutoCloseable
{

	public void close() 
	{
	}

	public void setEntityType(EntityType entityType)
	{
	}

	public String getValue(String entitySpecifier, Integer rowIndex, Integer columnIndex)
	{
		return null;
	}

}
