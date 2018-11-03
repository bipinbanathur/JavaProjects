package com.rtaware.data;

import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.rtaware.config.ExcelProcessor;

import org.apache.poi.hssf.usermodel.HSSFSheet;

public class JSONTemplatizer
{
	ExcelProcessor 	excelProcessor 	= null;
	
	
	public JSONTemplatizer(String fileName ,String jsonData)
	{
		try
		{				
			String jsonType = getType(jsonData);
			
			if(jsonType.equals("ARRAY"))
			{
				if(excelProcessor==null)
				excelProcessor 	= new ExcelProcessor(fileName);
				
				HSSFSheet activeSheet = excelProcessor.createMainSheet("Main_"+jsonType);
				JSONArray jsonArray   = new JSONArray(jsonData);						
				iterateJSON(jsonArray,activeSheet,"");
			}
			
			if(jsonType.equals("OBJECT"))
			{
				if(excelProcessor==null)
				excelProcessor 	= new ExcelProcessor(fileName);
				
				HSSFSheet activeSheet =excelProcessor.createMainSheet("Main_"+jsonType);
				JSONObject jsonObject  = new JSONObject(jsonData);	
				iterateJSON(jsonObject,activeSheet);
			}
			
			if(excelProcessor != null)
			excelProcessor.closeStream();
			excelProcessor = null;
			
			System.out.println("====================================================================");
			System.out.println("Created  : "+fileName);
			System.out.println("====================================================================");
			
		}
		catch(Exception e)
		{
			System.out.println(""+e); 
		}
	}
		
	public void iterateJSON(JSONObject jsonObject,HSSFSheet activeSheet)
	{
		try
		{
			for (@SuppressWarnings("rawtypes")
			Iterator iterator =  jsonObject.keys(); iterator.hasNext(); )
			{						
				String jsonKey 		= (String) iterator.next();			
				String jsonType 	= getType(jsonObject, jsonKey);
				String entityName   = "";
				String cellComment  = "";
								
				if (jsonType.equals("OBJECT"))
				{	
					entityName	= createSheetName();
					
					if(jsonKey  != null && ! jsonKey .equals("")  )		cellComment += "Key:"+jsonKey;   else cellComment += "Key: ";
					if(jsonType != null && ! jsonType .equals("") )		cellComment += ",Type:"+jsonType; else cellComment += ",Type: ";
					
					excelProcessor.createColumn(activeSheet,entityName,cellComment);
					iterateJSON(jsonObject.getJSONObject(jsonKey),excelProcessor.createSheet(entityName));
				}
				else if(jsonType.equals("ARRAY"))
				{	
					entityName	= createSheetName();
					if(jsonKey  != null && ! jsonKey .equals("")  )		cellComment += "Key:"+jsonKey;   else cellComment += "Key: "; 
					if(jsonType != null && ! jsonType .equals("") )		cellComment += ",Type:"+jsonType; else cellComment += ",Type: ";
					
					excelProcessor.createColumn(activeSheet,entityName,cellComment);					
					iterateJSON(jsonObject.getJSONArray(jsonKey),excelProcessor.createSheet(entityName),jsonKey);
					
				}				 
				else 
				{
					Object jsonValue = jsonObject.get(jsonKey);
					if(jsonValue!= null )
					{
						jsonType = jsonValue.getClass().getName();
						excelProcessor.createColumn(activeSheet, jsonKey,jsonType);						
					}
					else
					{
						excelProcessor.createColumn(activeSheet, jsonKey,"");
					}									
				}	
				cellComment = "";
				entityName  = "";
				jsonType 	= "";
			 }

			 
		}					
		catch(Exception e)
		{
			System.out.println(""+e); 
		}
	}
	
	public void iterateJSON(JSONArray jsonArray,HSSFSheet activeSheet,String jsonKey)
	{
		String jsonType 	 = "";
		String entityName    = "";
		String cellComment   = "";
		try
		{		
			jsonKey = "";
			for (int index = 0; index < 1; index++)
			{	

				jsonType =  getType(jsonArray.get(index).toString());				
				if(jsonType.equals("OBJECT")) 
				{					
					entityName	= createSheetName();
					cellComment += "Key: ";
					if(jsonType != null && ! jsonType .equals("") )		cellComment += ",Type:"+jsonType; else cellComment += ",Type: ";
					
					excelProcessor.createColumn(activeSheet,entityName,cellComment);
					iterateJSON((JSONObject) jsonArray.get(index),excelProcessor.createSheet(entityName) );
				}
				
				else if(jsonType.equals("ARRAY")) 
				{
					entityName	= createSheetName();
					cellComment += "Key: ";
					if(jsonType != null && ! jsonType .equals("") )		cellComment += ",Type:"+jsonType; else cellComment += ",Type: ";
					
					excelProcessor.createColumn(activeSheet,entityName,cellComment);
					iterateJSON((JSONArray) jsonArray.get(index),excelProcessor.createSheet(jsonKey+"_"+index),jsonKey );		
				}
				else
				{
					excelProcessor.createColumn(activeSheet, new Integer(index).toString(),jsonType);					
				}
				
				jsonType = "";
			}			

		}					
		catch(Exception e)
		{
			System.out.println(""+e); 
		}
	}
	
	public String getType(JSONObject jsonObject, String key) throws JSONException 
	{
		try
		{
			if 		(jsonObject.get(key) instanceof JSONObject) return "OBJECT";
			else if (jsonObject.get(key) instanceof JSONArray)  return "ARRAY";			        
		}
		catch(Exception e)
		{
			System.out.println(""+e); 
		}
		return "DATA";
	}
	
	public String getType(String jsonData)
	{
		String jsonType = "";
		try
		{			
			jsonData = jsonData.trim();			
			if( jsonData.startsWith("{") && jsonData.endsWith("}"))
			{
				jsonType = "OBJECT";
			}
			else if( jsonData.startsWith("[") && jsonData.endsWith("]"))
			{
				jsonType = "ARRAY";
			}
			else
			{	
				Object jsonValue = jsonType ;
				jsonType = jsonValue.getClass().getName();
			}		        
		}
		catch(Exception e)
		{
			System.out.println(""+e); 
		}
		return jsonType;
	}
	
	private String createSheetName()
	{
		int randomNum = 0 + (int)(Math.random() * 1000);
		return "SH"+randomNum;
	}
}
