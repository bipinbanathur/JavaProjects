package com.rtaware.repoapp.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.rtaware.repoapp.model.ordata.DBResultSet;
import com.rtaware.repoapp.model.ordata.TableColumn;
import com.rtaware.repoapp.model.ordata.TableRecord;

public class TestClass
{
	@Test
	private void testMode()
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
						tableColumn.setColumnValue(""+columnIndex);
						tableColumn.setDataType("numeric");
					}
					else
					{
						tableColumn.setFieldName("name");
						tableColumn.setColumnValue("Name "+columnIndex);
						tableColumn.setDataType("text");
					}
					tableRecord.addColumn(tableColumn);
				}				
				resutlSet.addRecord(tableRecord);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
		//JSONAssert.assertEquals("{\"id\":\"1\",name:\"Name 1\"}", , JSONCompareMode.LENIENT);
		//new JSONObject(resutlSet.toString());
		//Assertions.assertArrayEquals(JSONObject, new JSONObject(resutlSet.toString()), "{}");
		System.out.println(resutlSet);
		
	}
	

}
