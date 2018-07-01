package com.rtaware.repoapp.model.ordata;

import java.util.ArrayList;
import java.util.List;

public class TableRecord
{
	public List<TableColumn> getColumnList()
	{
		return columnList;
	}
	public void setColumnList(List<TableColumn> columnList)
	{
		this.columnList = columnList;
	}
	public TableColumn getTableColumn()
	{
		return tableColumn;
	}
	public void setTableColumn(TableColumn tableColumn)
	{
		this.tableColumn = tableColumn;
	}
	private List<TableColumn> columnList = new ArrayList<TableColumn>();
	private TableColumn tableColumn = null;
	
	public void addColumn(TableColumn tableColumn)
	{
		if(null != columnList)
		{
			columnList.add(tableColumn);
		}
	}
	
	public String toString()
	{
		String columnData = "{ \"columns\" : [ \n";
		int columnSize = columnList.size();
		int columnIndex = 0;
		for(TableColumn tableColum: columnList)
		{			
			columnData += tableColum.toString(); 
			columnIndex++;
			if ( columnIndex != columnSize) columnData += ", \n";
		}	
		return columnData+"\n ]\n}";
	}

}
