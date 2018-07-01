package com.rtaware.repoapp;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.rtaware.repoapp.model.ordata.DBResultSet;
import com.rtaware.repoapp.model.ordata.TableColumn;
import com.rtaware.repoapp.model.ordata.TableRecord;

@RestController
@EnableAutoConfiguration
public class MainController 
{
	@RequestMapping("/generate/{name}")
	String getMessage(@PathVariable String name) 
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
						tableColumn.setColumnValue(""+recordIndex);
						tableColumn.setDataType("numeric");
					}
					else
					{
						tableColumn.setFieldName("name");
						tableColumn.setColumnValue("Name "+recordIndex);
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
	    return resutlSet.toString();
	}
	
	
	
	@RequestMapping("/report/{repoType}")
	String generateReport(@PathVariable String repoType) 
	{
	    return "{ \"type\"  : \""+repoType+"\"}";
	}
	
	@RequestMapping(value="/repo", method = RequestMethod.POST)

    public ResponseEntity<String> process(@RequestBody String requestBody) 
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
		return new ResponseEntity<String>(resutlSet.toString(), HttpStatus.OK);
    }
	

	public static void main(String[] args) throws Exception
	{		
		SpringApplication.run(MainController.class, args);
	}
}