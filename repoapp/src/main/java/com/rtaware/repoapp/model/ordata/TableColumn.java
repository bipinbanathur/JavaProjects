package com.rtaware.repoapp.model.ordata;

public class TableColumn
{
	public String getFieldName()
	{
		return fieldName;
	}
	public void setFieldName(String fieldName)
	{
		this.fieldName = fieldName;
	}
	public String getColumnValue()
	{
		return columnValue;
	}
	public void setColumnValue(String columnValue)
	{
		this.columnValue = columnValue;
	}
	public String getDataType()
	{
		return dataType;
	}
	public void setDataType(String dataType)
	{
		this.dataType = dataType;
	}
	private String fieldName 	= "";
	private String columnValue 	= "";
	private String dataType 	= "";
	
	public String toString()
	{
		return "{ \"fieldName\" : \""+this.getFieldName() +"\" ,  \"columnValue\" : \"" + this.getColumnValue()+"\"}";
	}
}
