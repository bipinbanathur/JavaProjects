package com.rtaware.repoapp.model.ordata;


import java.util.ArrayList;
import java.util.List;

public class DBResultSet
{

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

	public String toString()
	{
		String resultSetData = "{\n \"records\" : [";
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
}
