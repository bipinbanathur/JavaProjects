package repoapp;

import com.rtaware.repoapp.model.ordata.DBResultSet;
import com.rtaware.repoapp.model.ordata.TableColumn;
import com.rtaware.repoapp.model.ordata.TableRecord;

public class TestClass
{

	
	public static void main(String[] args)
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
		System.out.println(resutlSet);
	}
}
