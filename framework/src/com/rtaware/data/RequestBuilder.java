package com.rtaware.data;

import java.util.ArrayList;

import com.rtaware.config.APIException;

public class RequestBuilder
{	
	Excel excel				=  null;
	String requestContent 	= "";
	
	public String buildRequest(String flowID,String stepID, String excelPath)
	{
		try
		{
			excel= new Excel(excelPath);
			
			String[] 	mainIndices 	= null;
			String 		sheetName 		= "";
			String 		payloadType 	= "";
			
			if(excel.checkSheetExists("Main_OBJECT"))
			{ 
				sheetName 	= "Main_OBJECT";
				payloadType	= "OBJECT";
			}
			else if (excel.checkSheetExists("Main_ARRAY"))
			{
				sheetName 	= "Main_ARRAY";
				payloadType	= "ARRAY";
			}			
			else if (excel.checkSheetExists("Main_XML"))
			{
				sheetName 	= "Main_XML";
				payloadType	= "XML";
			}			
			else
			{
				System.out.println("Invalid Sheet");
				new APIException("InvalidTemplate");
			}
			/* -------------------------------------------------------------------------------------------------------------------- */
			/*	Handle XML Templates Starting*/
			/* -------------------------------------------------------------------------------------------------------------------- */			
			if(payloadType.equals("XML"))
			{
				String[] tc_keys 	= {"#FlowID","#StepID"}; 
				String[] tc_values 	= {flowID,stepID};
				
				mainIndices = excel.getChildRowIndices(sheetName, tc_keys, tc_values);				
				ArrayList<String> 	columnNames	= (ArrayList<String>) excel.getColumnList(sheetName);
				
	        	String cellComment 	= 	"";
	        	String cellData		= 	"";
	        	String pageName		=	"";
	        	String nodeType		=	"";
	        	String primaryKey	=	"";
	        	String attributes	=	"";
	            for(int mainIndex = 0;mainIndex <mainIndices.length;mainIndex++)
	            {
	                int rowIndex 	= Integer.parseInt(mainIndices[mainIndex]);
	                int columnIndex = 0;
	                
	            	primaryKey	=	excel.getValue(sheetName, rowIndex, "#PK");
	                
	                for(String columnName :  columnNames)
	                {

	                	cellComment 	= excel.getComment(sheetName, 0, columnIndex);
	                	cellData		= excel.getValue(sheetName, rowIndex, columnIndex);
	                	attributes 		= excel.getComment(sheetName, rowIndex, columnIndex);		                	
	                	attributes		= attributes.trim();

	                	if(cellData.equalsIgnoreCase("#Empty"))
	                	{
	                		cellData = "";	
	                	}
	                	if(cellComment.indexOf("Type:=")!= -1 &&  cellComment.indexOf("*,")!= -1)
	                	{
	                		nodeType = cellComment.substring(cellComment.indexOf("Type:=")+6, cellComment.indexOf("*,")).trim();
	                	}
	                	
	                	if(cellComment.indexOf("Page:=")!= -1 &&  cellComment.indexOf("#,")!= -1) //#,
	                	{
	                		pageName = cellComment.substring(cellComment.indexOf("Page:=")+6, cellComment.indexOf("#,")).trim();
	                	}

	                	
	                	if( ! columnName.startsWith("#")) 
	                	{              
	                		if(nodeType.equals("NODE"))
	                		{
	                			if(attributes!= null && ! attributes.equals(""))
	                			{
	                				requestContent += "<"+columnName+" "+attributes+">\n";
	                			}
	                			else
	                			{
	                				requestContent += "<"+columnName+">\n";
	                			}
		                		
		                		buildNodes(primaryKey, pageName); 
		                		requestContent +=  "</"+columnName+">"; 	                		
	                		}
	                		else
	                		{
	                			if(attributes!= null && ! attributes.equals(""))
	                			{
	                				requestContent += "<"+columnName+" "+attributes+">"+cellData+"</"+columnName+">\n"; 
	                			}
	                			else
	                			{
	                				requestContent += "<"+columnName+">"+cellData+"</"+columnName+">\n"; ;
	                			}
	                			
	                		}

	                	}
	                	cellComment 	= 	"";
	                	cellData		= 	"";
	                	pageName		=	"";
	                	nodeType		=	"";
	                	attributes		=	"";
	                	columnIndex++;                	
	                }
	            }
				return requestContent;
			}
			/* -------------------------------------------------------------------------------------------------------------------- */
			/*	Handle JSON Object */
			/* -------------------------------------------------------------------------------------------------------------------- */
			if (payloadType.equals("OBJECT"))
			{
				String[] tc_keys 	= {"#FlowID","#StepID"}; 
				String[] tc_values 	= {flowID,stepID};
				
				mainIndices = excel.getChildRowIndices(sheetName, tc_keys, tc_values);				
				ArrayList<String> 	columnNames	= (ArrayList<String>) excel.getColumnList(sheetName);

	        	String cellComment 	= 	"";
	        	String cellData		= 	"";
	        	String dataType		=	"";
	        	String keyName		=	"";
	        	String primaryKey	=	"";

				if(! keyName.equals("")) 			requestContent += formatContent(keyName,"");
				requestContent += "{\n";  
				
	            for(int mainIndex = 0;mainIndex <mainIndices.length;mainIndex++)
	            {
	                int rowIndex 	= Integer.parseInt(mainIndices[mainIndex]);
	                int columnIndex = 0;
	                boolean flag 	= false;
	            	primaryKey	=	excel.getValue(sheetName, rowIndex, "#PK");
	                
	                for(String columnName :  columnNames)
	                {

	                	cellComment 	= excel.getComment(sheetName, 0, columnIndex);
	                	
	                	if( cellComment.indexOf("Key:") != -1 && cellComment.indexOf(",Type:") != -1)
	                	{
	                		keyName  = cellComment.substring(cellComment.indexOf("Key:")+4,  cellComment.indexOf(",Type:")).trim();
	                		dataType = cellComment.substring(cellComment.indexOf(",Type:")+6,cellComment.length()).trim();       
	                	}                		
	                	else
	                	{
	                		dataType = cellComment.trim();
	                	}
	                	cellData		= excel.getValue(sheetName, rowIndex, columnIndex);
	                	
	                	if( ! columnName.startsWith("#")) 
	                	{              
	                		if( dataType.equals("ARRAY") )
	                		{                    
	                			if(! keyName.equals("")) 	requestContent += formatContent(keyName,"");
	                			requestContent += "\n[\n"; 
	                			buildArray(primaryKey,columnName);
	                			requestContent += "\n]\n"; 
	                		}
	                		
	                		else if( dataType.equals("OBJECT") )
	                		{
	                			if(! keyName.equals("")) 	requestContent += formatContent(keyName,"");                			
	                			buildObject(primaryKey, columnName);
	                		}
	                		
	                		else if( dataType.equals("java.lang.String") )
	                		{     
	                			if(cellData.equalsIgnoreCase("#null"))
	                			{
	                					requestContent += "\""+columnName+"\"	: null";
	                			}
	                			else if(cellData.equalsIgnoreCase("#empty"))
	                			{
	                				requestContent += formatStringContent(columnName,"");  
	                			}
	                			else if(! cellData.trim().equals(""))
	                			{
	                				requestContent += formatStringContent(columnName,cellData);                 			
	                			} 
	                			if(cellData.trim().equals("")) flag = true;
	                		}
	                		else if ( dataType.equals("java.lang.Boolean") )
	                		{                			
	                			cellData = cellData.toLowerCase();
	                			if(cellData.equalsIgnoreCase("#null")) 
	                			{
	                				requestContent += "\""+columnName+"\"	: null";
	                			}
	                			else if(! cellData.trim().equals(""))
	                			{
	                				requestContent += formatContent(columnName,cellData);  
	                			}
	                			if(cellData.trim().equals("")) flag = true;
	                		}
	                		else 
	                		{
	                			if(cellData.equalsIgnoreCase("#null")) 
	                			{
	                				requestContent += "\""+columnName+"\"	: null";
	                			}
	                			else  if(! cellData.trim().equals(""))
	                			{
	                				requestContent += formatContent(columnName,cellData);                			
	                			}
	                			
	                			if(cellData.trim().equals("")) flag = true;
	                		}
	                		                		
	                		if(columnIndex != (columnNames.size() -1 )  && ! flag)
	                		{              
	                			requestContent += ",\n";
	                			
	                		}
	                	}
	                	cellComment 	= 	"";
	                	cellData		= 	"";
	                	dataType		=	"";
	                	keyName			=	"";
	                	flag			= 	false;
	                	columnIndex++;                	
	                }
	            }
	            requestContent += "\n}\n"; 
			}
			
			/* -------------------------------------------------------------------------------------------------------------------- */
			/*	Handle JSON Array */
			/* -------------------------------------------------------------------------------------------------------------------- */
			if (payloadType.equals("ARRAY"))
			{
				String[] tc_keys 	= {"#FlowID","#StepID"}; 
				String[] tc_values 	= {flowID,stepID};
				
				mainIndices = excel.getChildRowIndices(sheetName, tc_keys, tc_values);				
				ArrayList<String> 	columnNames	= (ArrayList<String>) excel.getColumnList(sheetName);

	        	String cellComment 	= 	"";
	        	String cellData		= 	"";
	        	String dataType		=	"";
	        	String keyName		=	"";
	        	String primaryKey	=	"";

				if(! keyName.equals("")) 			requestContent += formatContent(keyName,"");
				requestContent += "[\n";  
				
	            for(int mainIndex = 0;mainIndex <mainIndices.length;mainIndex++)
	            {
	                int rowIndex 	= Integer.parseInt(mainIndices[mainIndex]);
	                int columnIndex = 0;
	                boolean flag 	= false;
	            	primaryKey	=	excel.getValue(sheetName, rowIndex, "#PK");
	                
	                for(String columnName :  columnNames)
	                {

	                	cellComment 	= excel.getComment(sheetName, 0, columnIndex);
	                	
	                	if( cellComment.indexOf("Key:") != -1 && cellComment.indexOf(",Type:") != -1)
	                	{
	                		keyName  = cellComment.substring(cellComment.indexOf("Key:")+4,  cellComment.indexOf(",Type:")).trim();
	                		dataType = cellComment.substring(cellComment.indexOf(",Type:")+6,cellComment.length()).trim();       
	                	}                		
	                	else
	                	{
	                		dataType = cellComment.trim();
	                	}
	                	cellData		= excel.getValue(sheetName, rowIndex, columnIndex);
	                	
	                	if( ! columnName.startsWith("#")) 
	                	{              
	                		if( dataType.equals("ARRAY") )
	                		{                    
	                			if(! keyName.equals("")) 	requestContent += formatContent(keyName,"");
	                			requestContent += "\n[\n"; 
	                			buildArray(primaryKey,columnName);
	                			requestContent += "\n]\n"; 
	                		}
	                		
	                		else if( dataType.equals("OBJECT") )
	                		{
	                			if(! keyName.equals("")) 	requestContent += formatContent(keyName,"");                			
	                			buildObject(primaryKey, columnName);
	                		}
	                		
	                		else if( dataType.equals("java.lang.String") )
	                		{   
	                			
	                			if(isNumeric(columnName)) 
	            				{                					
	                				if(cellData.equalsIgnoreCase("#null"))
	                				{
	                					requestContent += "null";
	                				}
	                				else if(cellData.equalsIgnoreCase("#empty"))
	                				{
	                					requestContent += "\"\"";  //"+cellData+"
	                				}
	                				else if(! cellData.equals(""))
	                				{
	                					requestContent += "\""+cellData+"\"";                					
	                				}        
	                				if(cellData.trim().equals("")) flag = true;
	            				}                				
	                			else
	                			{
	                				if(cellData.equalsIgnoreCase("#null"))
	                				{
	                					requestContent += "null";
	                				}
	                				else if(cellData.equalsIgnoreCase("#empty"))
	                				{
	                					requestContent += formatStringContent(columnName,"");  
	                				}
	                				else if(! cellData.trim().equals(""))
	                				{
	                					requestContent += formatStringContent(columnName,cellData);              					
	                				}           
	                				if(cellData.trim().equals("")) flag = true;
	                			} 

	                			if(cellData.trim().equals("")) flag = true;
	                		}
	                		else if ( dataType.equals("java.lang.Boolean") )
	                		{                			
	                			cellData = cellData.toLowerCase();
	                			if(isNumeric(columnName)) 
	            				{                					
	                				if(cellData.equalsIgnoreCase("#null"))
	                				{
	                					requestContent += "null";
	                				}
	                				else if(! cellData.trim().equalsIgnoreCase(""))
	                				{
	                					requestContent += cellData;                					
	                				}                	
	                				if(cellData.trim().equals("")) flag = true;
	            				}                				
	                			else
	                			{
	                				if(cellData.equalsIgnoreCase("#null"))
	                				{
	                					requestContent += "null";
	                				}
	                				else if(! cellData.trim().equals(""))
	                				{
	                					requestContent += formatContent(columnName,cellData);        					
	                				}      
	                				if(cellData.trim().equals("")) flag = true;
	                			} 
	                		}
	                		else 
	                		{
	                			if(isNumeric(columnName)) 
	            				{                					
	                				if(cellData.equalsIgnoreCase("#null"))
	                				{
	                					requestContent += "null";
	                				}
	                				else if(! cellData.trim().equals(""))
	                				{
	                					requestContent += cellData;                					
	                				}      
	                				if(cellData.trim().equals("")) flag = true;
	            				}                				
	                			else
	                			{
	                				if(cellData.equalsIgnoreCase("#null"))
	                				{
	                					requestContent += "null";
	                				}
	                				else if(! cellData.trim().equals(""))
	                				{
	                					requestContent += formatContent(columnName,cellData);              					
	                				} 
	                				if(cellData.trim().equals("")) flag = true;
	                			}
	                				                			
	                		}
	                		                		
	                		if(columnIndex != (columnNames.size() -1 )  && ! flag)
	                		{              
	                			requestContent += ",\n";
	                			
	                		}
	                	}
	                	cellComment 	= 	"";
	                	cellData		= 	"";
	                	dataType		=	"";
	                	keyName			=	"";
	                	flag			= 	false;
	                	columnIndex++;                	
	                }
	            }
	            requestContent += "\n]\n"; 
			}          
		}
		catch(Exception e)
		{
			System.out.println("buildRequest"+e);
		}
		return requestContent;
	}
		
	private void buildObject(String forginKey, String sheetName) 
	{
		try
		{	
			
			
			String[] objectIndices 	=	excel.getChildRowIndices(sheetName, "#FK", forginKey);			
			ArrayList<String> 	columnNames	= (ArrayList<String>) excel.getColumnList(sheetName);

        	String cellComment 	= 	"";
        	String cellData		= 	"";
        	String dataType		=	"";
        	String keyName		=	"";
        	String primaryKey	=	"";
        	boolean flag 		=	 false;


            for(int mainIndex = 0;mainIndex <objectIndices.length;mainIndex++)
            {
    			requestContent 		+= 	"\n{\n"; 
                int rowIndex 		= 	Integer.parseInt(objectIndices[mainIndex]);
            	primaryKey			=	excel.getValue(sheetName, rowIndex, "#PK");
                int columnIndex 	= 	0;
                for(String columnName :  columnNames)
                {

                	cellComment 	= excel.getComment(sheetName, 0, columnIndex);
                	if( cellComment.indexOf("Key:") != -1 && cellComment.indexOf(",Type:") != -1)
                	{
                		keyName  = cellComment.substring(cellComment.indexOf("Key:")+4,  cellComment.indexOf(",Type:")).trim();
                		dataType = cellComment.substring(cellComment.indexOf(",Type:")+6,cellComment.length()).trim();       
                	}                		
                	else
                	{
                		dataType = cellComment.trim();
                	}
                	cellData		= excel.getValue(sheetName, rowIndex, columnIndex);
                	
                	if( ! columnName.startsWith("#")) 
                	{              
                		if( dataType.equals("ARRAY") )
                		{
                			if(! keyName.equals(""))                		                		
                			requestContent += formatContent(keyName,"");
                        	requestContent += "\n[\n";
                			buildArray(primaryKey,columnName);
                        	requestContent += "\n]\n";
                		}
                		
                		else if( dataType.equals("OBJECT") )
                		{
                			if(! keyName.equals(""))
                			requestContent += formatContent(keyName,"");
                			buildObject(primaryKey, columnName);
                		}
                		
                		else if( dataType.equals("java.lang.String") )
                		{     
                			if(cellData.equalsIgnoreCase("#null"))
                			{
                				requestContent += "\""+columnName+"\"	: null";
                			}
                			else if (cellData.equalsIgnoreCase("#empty"))
                			{
                				requestContent += formatStringContent(columnName,"");	
                			}
                			else if(! cellData.trim().equals(""))
                			{
                				requestContent += formatStringContent(columnName,cellData);                				
                			}
                			if(cellData.trim().equals("")) flag = true;
                				                 			
                		}
                		else if ( dataType.equals("java.lang.Boolean") )
                		{                			
                			cellData = cellData.toLowerCase();
                			if(cellData.equalsIgnoreCase("#null")) 
                			{
                				requestContent += "\""+columnName+"\"	: null";
                			}
                			else if(! cellData.trim().equals(""))
                			{
                				requestContent += formatContent(columnName,cellData);  
                			}
                			if(cellData.trim().equals("")) flag = true;
                		}
                		else 
                		{
                			if(cellData.equalsIgnoreCase("#null")) 
                			{
                				requestContent += "\""+columnName+"\"	: null";
                			}
                			else if(! cellData.trim().equals(""))
                			{ 
                				requestContent += formatContent(columnName,cellData);                			
                			}
                			if(cellData.trim().equals("")) flag = true;
                		}
                		
                		if(columnIndex != (columnNames.size() -1 ) && ! flag)
                		{
                			requestContent += ",\n";
                		}                		
                	}
                	cellComment = 	"";
                	cellData	= 	"";
                	dataType	=	"";
                	keyName		=	"";
                	columnIndex++;
                	
                }                
                requestContent += "\n}\n"; 
                if(mainIndex != (objectIndices.length -1)) requestContent += ",\n";
            }
		}
		catch(Exception e)
		{
			System.out.println("buildRequest"+e);
		}
	}
	
	@SuppressWarnings("unused")
	private void buildArray(String forginKey, String sheetName)
	{
		try
		{			
			
			String[] 			arrayIndices 	= 	excel.getChildRowIndices(sheetName, "#FK", forginKey);
			ArrayList<String> 	columnNames		= 	(ArrayList<String>) excel.getColumnList(sheetName);

        	String cellComment 	= 	"";
        	String cellData		= 	"";
        	String dataType		=	"";
			String keyName		=	"";
			String primaryKey	=	"";
			boolean flag		=	false;
			
            for(int mainIndex = 0;mainIndex <arrayIndices.length;mainIndex++)
            {
                int rowIndex =  Integer.parseInt(arrayIndices[mainIndex]);
            	primaryKey	 =	excel.getValue(sheetName, rowIndex, "#PK");
                int columnIndex = 0;
                
                
                for(String columnName :  columnNames)
                {

                	cellComment 	= excel.getComment(sheetName, 0, columnIndex);
                	if( cellComment.indexOf("Key:") != -1 && cellComment.indexOf(",Type:") != -1)
                	{
                		keyName  = cellComment.substring(cellComment.indexOf("Key:")+4,  cellComment.indexOf(",Type:")).trim();
                		dataType = cellComment.substring(cellComment.indexOf(",Type:")+6,cellComment.length()).trim();       
                	}                		
                	else
                	{
                		dataType = cellComment.trim();
                	}
                	cellData		= excel.getValue(sheetName, rowIndex, columnIndex);

                	
                	if( ! columnName.equals("#PK") && ! columnName.equals("#FK")) 
                	{              
                		if( dataType.equals("ARRAY") )
                		{	
                        	requestContent += "\n[\n";
                			buildArray(primaryKey,columnName);
                        	requestContent += "\n]\n";
                		}
                		
                		else if( dataType.equals("OBJECT") )
                		{
                			buildObject(primaryKey, columnName);
                		}
                		
                		else if( dataType.equals("java.lang.String") )
                		{     
                			if(isNumeric(columnName)) 
            				{                					
                				if(cellData.equalsIgnoreCase("#null"))
                				{
                					requestContent += "null";
                				}
                				else if(cellData.equalsIgnoreCase("#empty"))
                				{
                					requestContent += "\"\"";  //"+cellData+"
                				}
                				else if(! cellData.equals(""))
                				{
                					requestContent += "\""+cellData+"\"";                					
                				}        
                				if(cellData.trim().equals("")) flag = true;
            				}                				
                			else
                			{
                				if(cellData.equalsIgnoreCase("#null"))
                				{
                					requestContent += "null";
                				}
                				else if(cellData.equalsIgnoreCase("#empty"))
                				{
                					requestContent += formatStringContent(columnName,"");  
                				}
                				else if(! cellData.trim().equals(""))
                				{
                					requestContent += formatStringContent(columnName,cellData);              					
                				}           
                				if(cellData.trim().equals("")) flag = true;
                			}   
                		}
                		else if ( dataType.equals("java.lang.Boolean") )
                		{                			
                			cellData = cellData.toLowerCase();
                			if(isNumeric(columnName)) 
            				{                					
                				if(cellData.equalsIgnoreCase("#null"))
                				{
                					requestContent += "null";
                				}
                				else if(! cellData.trim().equalsIgnoreCase(""))
                				{
                					requestContent += cellData;                					
                				}                	
                				if(cellData.trim().equals("")) flag = true;
            				}                				
                			else
                			{
                				if(cellData.equalsIgnoreCase("#null"))
                				{
                					requestContent += "null";
                				}
                				else if(! cellData.trim().equals(""))
                				{
                					requestContent += formatContent(columnName,cellData);        					
                				}      
                				if(cellData.trim().equals("")) flag = true;
                			} 
                		}
                		else 
                		{
                			if(isNumeric(columnName)) 
            				{                					
                				if(cellData.equalsIgnoreCase("#null"))
                				{
                					requestContent += "null";
                				}
                				else if(! cellData.trim().equals(""))
                				{
                					requestContent += cellData;                					
                				}      
                				if(cellData.trim().equals("")) flag = true;
            				}                				
                			else
                			{
                				if(cellData.equalsIgnoreCase("#null"))
                				{
                					requestContent += "null";
                				}
                				else if(! cellData.trim().equals(""))
                				{
                					requestContent += formatContent(columnName,cellData);              					
                				} 
                				if(cellData.trim().equals("")) flag = true;
                			}
                				                			
                		}

                		
                		if(columnIndex != (columnNames.size() -1 ) && ! flag )
                		{
                			requestContent +=",\n";
                			
                		}                		
                	}                	
                	cellComment = 	"";
                	cellData	= 	"";
                	dataType	=	"";
                	keyName		=	""; 
                	flag		=	false;
                	columnIndex++;                	
                }                              
                if(mainIndex != (arrayIndices.length -1))  requestContent +=",\n"; 
            }		
		}
		catch(Exception e)
		{
			System.out.println("buildArray"+e);
		}
	}
	
	public void buildNodes(String forginKey, String sheetName)
	{
		String[] 			arrayIndices 	= 	excel.getChildRowIndices(sheetName, "#FK", forginKey);
		ArrayList<String> 	columnNames		= 	(ArrayList<String>) excel.getColumnList(sheetName);

    	String cellComment 	= 	"";
    	String cellData		= 	"";
    	String pageName		=	"";
    	String nodeType		=	"";
    	String primaryKey	=	"";
    	String attributes	=	"";
        for(int mainIndex = 0;mainIndex <arrayIndices.length;mainIndex++)
        {
            int rowIndex 	= Integer.parseInt(arrayIndices[mainIndex]);
            int columnIndex = 0;
            
        	primaryKey	=	excel.getValue(sheetName, rowIndex, "#PK");
            
            for(String columnName :  columnNames)
            {

            	cellComment 	= 	excel.getComment(sheetName, 0, columnIndex);
            	cellData		= 	excel.getValue(sheetName, rowIndex, columnIndex);
            	attributes 		= 	excel.getComment(sheetName, rowIndex, columnIndex);	
            	attributes		=	attributes.trim();
            	if(cellData.equalsIgnoreCase("#Empty"))
            	{
            		cellData = "";	
            	}
            	if(cellComment.indexOf("Type:=")!= -1 &&  cellComment.indexOf("*,")!= -1)
            	{
            		nodeType = cellComment.substring(cellComment.indexOf("Type:=")+6, cellComment.indexOf("*,")).trim();
            	}
            	
            	if(cellComment.indexOf("Page:=")!= -1 &&  cellComment.indexOf("#,")!= -1) 
            	{
            		pageName = cellComment.substring(cellComment.indexOf("Page:=")+6, cellComment.indexOf("#,")).trim();
            	}

            	if( ! columnName.startsWith("#")) 
            	{              
            		if(nodeType.equals("NODE"))
            		{
            			if(attributes!= null && ! attributes.equals(""))
            			{
            				requestContent += "<"+columnName+" "+attributes+ ">\n";
            			}
            			else
            			{
            				requestContent += "\n<"+columnName+">\n";
            			}
                		
                		buildNodes(primaryKey, pageName); 
                		requestContent +=  "\n</"+columnName+">"; 	                		
            		}
            		else
            		{
            			if(attributes!= null && ! attributes.equals(""))
            			{
            				requestContent += "<"+columnName+" "+attributes+">"+cellData+"</"+columnName+">\n"; 
            			}
            			else
            			{
            				requestContent += "<"+columnName+">"+cellData+"</"+columnName+">\n"; ;
            			}
            			
            		}

            	}
            	cellComment 	= 	"";
            	cellData		= 	"";
            	pageName		=	"";
            	nodeType		=	"";
            	attributes		=	"";
            	columnIndex++;                	
            }
        }
		
	}
		
	String formatContent(String key,String value)
	{
		return String.format("%-20s\t %-1s" ,"\t\""+key+"\"", ":\t"+value);
	}
	
	String formatStringContent(String key,String value)
	{
		return String.format("%-20s\t %-1s" ,"\t\""+key+"\"", ":\t\""+value+"\"");
	}
		
	@SuppressWarnings("unused")
	public  boolean isNumeric(String str)
	  {
	    try
	    {
	      double d = Double.parseDouble(str);
	    }
	    catch(NumberFormatException nfe)
	    {
	      return false;
	    }
	    return true;
	  }
	

	
}

