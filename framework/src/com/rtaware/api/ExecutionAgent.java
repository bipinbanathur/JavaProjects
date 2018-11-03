package com.rtaware.api;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.rtaware.config.FlowVariables;
import com.rtaware.config.FormatFunctions;
import com.rtaware.config.Logger;
import com.rtaware.config.ValidationEngine;
import com.rtaware.config.Variable;
import com.rtaware.data.DBConnections;
import com.rtaware.data.DBProcessor;
import com.rtaware.data.Excel;
import com.rtaware.data.RequestBuilder;

public class ExecutionAgent
{
	private String 	flowID 			= 	"";
	private String 	stepID 			= 	"";
	private String 	testCase 		= 	"";
	private	String	requiredFag		=	"";
	private String 	apiType 		= 	"";
	private String	urlString 		= 	"";
	private String 	authType 		= 	"";
	private String 	userName 		= 	"";
	private String 	passwordString	= 	"";
	private	String	mediaType 		= 	"";
	private String	mediaTypeAccept = 	"";
	private String	requestMethod	=	"";
	private String	soapAction		= 	"";
	private String	requestContent	= 	"";
	private String  xAuthToken		=	"";
	private	String	toolName		=	"";	
	private String  enableProView	=   "N";
	private String  enableRHdrView	=   "N";
	private String  enableCkView   	=   "N";
	private String  enableCertView  =   "N";
	private String  enableVerView  	=   "N";
	
	private	Excel 						excel 			= 	null;
	private	Logger 						log 			= 	null;
	private	APIEngine 					api				=	null;	
	private	ArrayList<Variable>			variableList 	= 	null;
	private	ArrayList<DBConnections>	dbConnections 	= 	null;

	public ExecutionAgent(String excelPath,String toolName)
	{
		try
		{					
			api 			= 	new APIEngine();
			excel 			= 	new Excel(excelPath);
			variableList 	= 	new ArrayList<Variable>();
			dbConnections	=	new ArrayList<DBConnections>();
			this.toolName	=	toolName;
			
			getVariables();			
			configureExecution();			
			stepVariables("Init");
						
		    if(log!= null)
		    {			    		
		        log.step("Test Configuration Done");
		    }
		       			
			String	sheetName 	= "Main";
			int 	rowCount 	= excel.getRows(sheetName);
			
	        for(int rowIndex = 1;rowIndex <rowCount;rowIndex++)
	        {
	        		try
	        		{
	        			Thread.sleep(1000);
	        		}catch(Exception e){}
	        		/*----------------------------------------------*/
	        		getValues(sheetName,rowIndex);     			        		  	
	        		/*----------------------------------------------*/ 	  
	        		if(requiredFag.equalsIgnoreCase("Y"))
	        		{
	        			assignValues();	   
	        			
	    			    if(log!= null)
	    			    {	
	    			    		log.stepgroup(testCase);
	    			        	log.step("Values from Excel Assigned to Variables ");
	    			    }
	    			       
	        			setCookieHeader();	 
	        			
	    			    if(log!= null)
	    			    {	
	    			        	log.step("Cookies and HTTP Header Values Set");
	    			    }
	    			    
	    			    stepVariables("BeforeExecution");
	    			       
		        		api.showCertificates();	  
		        		
					    if(log!= null)
					    {	
					        	log.step("Viewed SSL Certificates");
					    }
					       
		        		api.executeRequest();		
		        		
					    if(log!= null)
					    {	
					        	log.step("Executed API");
					    }
					       
		        		if(enableProView.equalsIgnoreCase("Y"))
		        		{
		        			api.showProperties();
		        		}
		        		
		        		if(enableCkView.equalsIgnoreCase("Y"))
		        		{
		        			api.showCookies();
		        		}
		        		
		        		if(enableRHdrView.equalsIgnoreCase("Y"))
		        		{
		        			api.showResponseHeaders();
		        		}		        		
		        		getCookieHeader();
		        		
					    if(log!= null)
					    {	
					        	log.step("Recieved Cookies/Headers");
					    }
					       
					    stepVariables("AfterExecution");
		        		
					    if(log!= null)
					    {	
					        	log.step("Captured Values from response");
					    }
					       
					    
		        		testValidations();	      
		        		
					    if(log!= null)
					    {	
					        	log.step("Test Validations Done");
					    }
					       
	        		}

	        		/*----------------------------------------------*/
	        		clearValues();	  
	        		/*----------------------------------------------*/
	        }
	        if(log!= null)
	        {
	        	log.close();
	        }
		}
		catch(Exception e)
		{
			Logger.showMessage("Exception","Unable to Start Execution Agent");
	        if(log!= null)
	        {		        	
	        	log.close();
	        }
		}
	}
	
	public void getValues(String sheetName,int rowIndex)
	{
		try
		{
			
	    	flowID 			=	excel.getValue(sheetName, rowIndex, "Flow ID"); 
	    	stepID 			=	excel.getValue(sheetName, rowIndex, "Step ID"); 
	    	testCase 		=	excel.getValue(sheetName, rowIndex, "Test Case"); 	
	    	requiredFag		=	excel.getValue(sheetName, rowIndex, "Required"); 		
	    	apiType 		=	excel.getValue(sheetName, rowIndex, "API Type"); 	
	    	urlString 		=	excel.getValue(sheetName, rowIndex, "URI"); 	
	    	authType 		=	excel.getValue(sheetName, rowIndex, "Auth Type"); 	
	    	userName 		=	excel.getValue(sheetName, rowIndex, "User Name"); 	
	    	passwordString	=	excel.getValue(sheetName, rowIndex, "Password"); 	
	    	mediaType 		=	excel.getValue(sheetName, rowIndex, "Send Type"); 	
	    	mediaTypeAccept =	excel.getValue(sheetName, rowIndex, "Accept Type"); 	
	    	requestMethod	=	excel.getValue(sheetName, rowIndex, "Method");	
	    	soapAction		= 	excel.getValue(sheetName, rowIndex, "Action");	
	    	requestContent	= 	excel.getValue(sheetName, rowIndex, "Request");	
	    	xAuthToken		=	excel.getValue(sheetName, rowIndex, "XAuthToken");	
	    	
	    	if(log!= null && requiredFag.equalsIgnoreCase("Y"))
	    	{
	    		log.info("Flow ID	:	"+flowID+"	Step ID	:	"+stepID+"	Test Case	:	"+testCase);
	    	}
			
		}
		catch(Exception e)
		{
			Logger.showMessage("Exception","Unable to Process Excel Template");
	        if(log!= null)
	        {	
	        	log.exception(e);
	        	log.close();
	        }
		}


	}
	
	public void getVariables()
	{

		try
		{
			String 	sheetName 		= 	"Variables";
			int 	rowCount 		=   excel.getRows(sheetName);	
			String 	flowID			=	"";
			String 	stepID			=	"";
			String 	fromSource		=	"";
			String 	variableName	=	"";
			String 	variableType	=	"";
			String 	when			=	"";
			String 	expression		=	"";
			String 	format			=	"";
			String 	connectionName	=	"";
			Variable variable 		= 	null;
			
	        for(int rowIndex = 1;rowIndex <rowCount;rowIndex++)
	        {       
				flowID			=	excel.getValue(sheetName, rowIndex, "Flow ID");
				stepID			=	excel.getValue(sheetName, rowIndex, "Step ID");
				variableName	=	excel.getValue(sheetName, rowIndex, "Variable Name");			
				variableType	=	excel.getValue(sheetName, rowIndex, "Variable Type");	
				fromSource		=	excel.getValue(sheetName, rowIndex, "From");
				when			=	excel.getValue(sheetName, rowIndex, "When");
				expression		=	excel.getValue(sheetName, rowIndex, "Expression");
				format			=	excel.getValue(sheetName, rowIndex, "Format");
				connectionName	=	excel.getValue(sheetName, rowIndex, "Connection Name");
				variable 		= 	new Variable();  
				//System.out.println(flowID+ "\t\t"+stepID+"\t\t"+variableName+ "\t\t"+variableType+ "\t\t"+fromSource+"\t\t"+when+"\t\t"+expression+"\t\t"+format+"\t\t"+connectionName );
				if(variableType == null || variableType.trim().equals(""))
				{
					variableType = "String";
				}
					        	    	        	
	        	variable.setFlowID			(flowID);
	        	variable.setStepID			(stepID);
	        	variable.setVariableName	(variableName);
	        	variable.setVariableType	(variableType);
	        	variable.setFrom			(fromSource);
	        	variable.setWhen			(when);
	        	variable.setExpression		(expression);
	        	variable.setFormat			(format);
	        	variable.setConnectionName	(connectionName);	  
	        	
	        	variableList.add(variable);
	        	variable		= null;
				flowID			=	"";
				stepID			=	"";
				fromSource		=	"";
				variableName	=	"";
				variableType	=	"";
				when			=	"";
				expression		=	"";
				format			=	"";
				connectionName	=	"";
	        }   
			
		}
		catch(Exception e)
		{
			Logger.showMessage("Exception","Unable to Process Variables");
	        if(log!= null)
	        {	
	        	log.exception(e);
	        	log.close();
	        }			
		}    
	}
	
	public void displayValues()
	{
    	System.out.println("TestID				"+flowID 			); 	
    	System.out.println("Test Case			"+testCase 			); 							
    	System.out.println("API Type			"+apiType 			); 	
    	System.out.println("URI					"+urlString 		); 	
    	System.out.println("Authentication Type	"+authType 			); 	
    	System.out.println("User Name			"+userName 			); 	
    	System.out.println("Password			"+passwordString	); 	
    	System.out.println("Media Type (Send)	"+mediaType 		); 	
    	System.out.println("Media Type (Accept)	"+mediaTypeAccept	); 	
    	System.out.println("Request Method		"+requestMethod		);	
    	System.out.println("Action				"+soapAction		);	
    	System.out.println("Input Request		"+requestContent	); 
		
	}
	
	public void assignValues()
	{
		try
		{
	    	if(apiType != null || ! apiType.equals(""))
	    	{
	    		api.setApiType(apiType);
	    	}
			if(urlString != null || ! urlString.equals(""))
	    	{
				urlString = replaceVariables(urlString);
	    		api.setUrlString(urlString);
	    	}
			if(authType != null || ! authType.equals(""))
	    	{
	    		api.setAuthType(authType);
	    	}
			if(userName != null || ! userName.equals(""))
	    	{
				userName = replaceVariables(userName);
				api.setUserName(userName);    		
	    	}
			if(passwordString != null || ! passwordString.equals(""))
	    	{
				passwordString = replaceVariables(passwordString);
				api.setPasswordString(passwordString);
	    	}
			
			if(xAuthToken != null || ! xAuthToken.equals(""))
	    	{
				xAuthToken = replaceVariables(xAuthToken);
				api.setxAuthToken(xAuthToken);
	    	}
			
			if(mediaType != null || ! mediaType.equals(""))
	    	{
	    		api.setMediaType(mediaType);
	    	}
			if(mediaTypeAccept != null || ! mediaTypeAccept.equals(""))
	    	{
				api.setAcceptMediaType(mediaTypeAccept);
	    	}
			if(requestMethod != null || ! requestMethod.equals(""))
	    	{
				api.setRequestMethod(requestMethod);
	    	}
			if(apiType.equals("SOAP") && (soapAction != null || ! apiType.equals("")))
	    	{
	    		api.setSoapAction(soapAction);
	    	}
			
			if(requestContent != null || ! requestContent.equals(""))
	    	{			
				requestContent = replaceVariables(requestContent);
	    		if(requestContent.endsWith(".xml") || requestContent.endsWith(".json"))
	    		{    			
	    			requestContent = api.readRequestFrom(requestContent);   
	    			requestContent = replaceVariables(requestContent);
	    			api.setRequestContent(requestContent);
	    		}
	    		else if(requestContent.endsWith(".xls") || requestContent.endsWith(".xlsx"))
	    		{
	    			RequestBuilder rb = new RequestBuilder();    		
	    			requestContent = rb.buildRequest(flowID, stepID, requestContent);   
	    			requestContent = replaceVariables(requestContent);
	    			api.setRequestContent(requestContent);
	    		}
	    		else
	    		{
	    			requestContent = replaceVariables(requestContent);
	    			api.setRequestContent(requestContent);
	    			
	    		}
	    		if(testCase != null || ! testCase.equals(""))
	        	{
	        		api.setCallingFNName(testCase);
	        	}    		
	    	}
			
		}
		catch(Exception e)
		{
			Logger.showMessage("Exception","Unable to Assign Values");
	        if(log!= null)
	        {	
	        	log.exception(e);
	        	log.close();
	        }			
		}	
	}
		
	public void clearValues()
	{
    	flowID 			=	"";
    	testCase 		=	"";
    	apiType 		=	"";
    	urlString 		=	"";
    	authType 		=	""; 	
    	userName 		=	"";
    	passwordString	=	"";
    	mediaType 		=	"";
    	mediaTypeAccept =	""; 	
    	requestMethod	=	"";
    	soapAction		= 	"";
    	requestContent	= 	"";
    	api.clearAll();
	}
	
	public void stepVariables(String atCondition)
	{
		try
		{
			String   	connectionName 	= 	"";
			String 		derivedValue 	= 	"";
			String 		dataFormat		=	"";
			String 		dataType		=	"";
			String 		variableName	=	"";
			
			for(Variable variable : variableList)
			{	
				connectionName 	= 	"";
				derivedValue 	= 	"";
				dataFormat		=	variable.getFormat();
				dataType		=	variable.getVariableType();
				variableName	=	variable.getVariableName();
				connectionName	= 	variable.getConnectionName();
				derivedValue	=	variable.getExpression();
				if(	flowID.equals(variable.getFlowID())  && stepID.equals(variable.getStepID()) && atCondition.equalsIgnoreCase(variable.getWhen()))
				{						
					if("Response".equals(variable.getFrom()))
					{
						derivedValue = api.getParamValue(derivedValue);						
					}					
					if("DBQuery".equals(variable.getFrom()))
					{
						if( connectionName.trim().equals(""))
						{
							Logger.showMessage("Exception", "Connection Name Can't be Empty");
						}
						else
						{
							derivedValue = DBProcessor.getValue(derivedValue, connectionName);
						}
					}									
					createVariable(dataType,variableName,derivedValue, dataFormat);					
					connectionName 	= 	"";
					derivedValue 	= 	"";
					dataFormat		=	"";
					dataType		=	"";
					variableName	=	"";
				}
			}				
		}
		catch(Exception e)
		{
			Logger.showMessage("Exception","Unable to Process Before API Execution");
			
	        if(log!= null)
	        {	
	        	log.exception(e);
	        	log.close();
	        }					
		}	
	}
	
	public void setCookieHeader()
	{
	
		String[] 	mainIndices =null;
		String[] 	tc_keys 	= {"Flow ID","Step ID"}; 
		String[] 	tc_values 	= {flowID,stepID};
		String 		sheetName	= "Header-Cookies";
		
		try
		{			
			mainIndices = excel.getChildRowIndices(sheetName, tc_keys, tc_values);	

	        for(int mainIndex = 0;mainIndex <mainIndices.length;mainIndex++)
	        {
	        	int 	rowIndex 		= 	Integer.parseInt(mainIndices[mainIndex]);
	        	
	        	String  entityType		= 	excel.getValue(sheetName, rowIndex, "Entity");	
	        	String 	entityName		= 	excel.getValue(sheetName, rowIndex, "Name");
	        	String 	entityValue 	= 	excel.getValue(sheetName, rowIndex, "Value");   
	        	String  action 			=	excel.getValue(sheetName, rowIndex, "Action");
	        			entityValue 	= 	replaceVariables(entityValue);

	        	if("Set".equals(action))
	        	{
	        		if(entityType.equalsIgnoreCase("Header"))
	        		{
	        			api.setHeader(entityName,entityValue);
	        			if(log!= null)
	        			{
	        				log.debug("Setting Header : "+ entityName+" with Value : "+entityValue);
	        			}
	        		}
	        		else
	        		{
	        			api.setCookie(entityValue);
	        			if(log!= null)
	        			{
	        				log.debug("Setting Cookie : "+ entityValue);
	        			}
	        		}	        			        	
	        	}
	        }
		}
		catch(Exception e)
		{
			Logger.showMessage("Exception","Unable to Set Header/Cookie");
	        if(log!= null)
	        {	
	        	log.exception(e);
	        	log.close();
	        }	
		}

	}
	
	public void getCookieHeader()
	{
	
		String[] 	mainIndices =null;
		String[] 	tc_keys 	= {"Flow ID","Step ID"}; 
		String[] 	tc_values 	= {flowID,stepID};
		String 		sheetName	= "Header-Cookies";
		
		try
		{
			
			mainIndices = excel.getChildRowIndices(sheetName, tc_keys, tc_values);	

	        for(int mainIndex = 0;mainIndex <mainIndices.length;mainIndex++)
	        {
	        	int 	rowIndex 		= 	Integer.parseInt(mainIndices[mainIndex]);
	        	
	        	String  entityType		= 	excel.getValue(sheetName, rowIndex, "Entity");	
	        	String 	entityName		= 	excel.getValue(sheetName, rowIndex, "Name");
	        	String 	entityValue 	= 	excel.getValue(sheetName, rowIndex, "Value");   
	        	String  action 			=	excel.getValue(sheetName, rowIndex, "Action");
	        	String  variableName	=	excel.getValue(sheetName, rowIndex, "AssignTo");	
	        			entityValue 	= 	replaceVariables(entityValue);

	        	if("Get".equals(action))
	        	{
	        		if(entityType.equalsIgnoreCase("Cookie"))
	        		{
	        			entityValue = api.getCookie(entityName);
	        			
	        			if(log!= null)
	        			{
	        				log.debug("Assigning Cookie : "+ entityName+" with Value : "+entityValue+" to variable : "+variableName);
	        			}
	        			FlowVariables.set(variableName, entityValue);
	        		}
	        		else if(entityType.equalsIgnoreCase("Header"))
	        		{
	        			
	        			Map<String, List<String>> respHeaders = api.getResponseHeaders();
	        			
	       			 	if (respHeaders != null)
	       			 	{ 	    					
	       			 		Iterator<String> headerKeys = respHeaders.keySet().iterator(); 
	       			 		while (headerKeys.hasNext())
	       			 		{ 
	       			 			String headerKey 	= (String) headerKeys.next();
	    	                 
	       			 			if(headerKey!= null)
	       			 			{
	       			 				List<String> headerValues = respHeaders.get(headerKey);
	       			 				if(headerValues!= null)
	       			 				{
	       			 					for(String headerValue:headerValues)
	       			 					{
	       			 						System.out.println(headerKey+"\t:\t"+headerValue);
	       			 					}
	       			 				}
	       			 			}	                 
	       			 		} 	             
	       			 	}
	       			 
	        		}
	        		
	        	}	
	        }
		}
		catch(Exception e)
		{
			Logger.showMessage("Exception","Unable to Get Header/Cookie");
	        if(log!= null)
	        {	
	        	log.exception(e);
	        	log.close();
	        }	
		}

	}

	public String replaceVariables(String content)
	{
		try
		{
			if(content != null)
			{
				String variableName 	= "";
				String variableValue 	= "";
				String replaceString 	= "";		
				
				/* the replaced value will always be a String , that's not an issue 
				 * but if there's a format specification then , its going to be ignored while replacing variable value
				 */
				while( content.indexOf("${") != -1 && content.indexOf("}") != -1) 
				{					
					variableName 	= content.substring( content.indexOf("${")+2, content.indexOf("}"));						
					replaceString 	= "${"+variableName+"}";
					variableValue 	= (String) FlowVariables.get(variableName.trim()).toString();
					if(variableValue == null ) variableValue = "";
					content 		= content.replace(replaceString,variableValue);	
				}				
			}
			else
			{
				return "";
			}			
		}
		catch(Exception e)
		{
				Logger.showMessage("Exception", "Invalid/NULL Content");
				if(log != null)
				{
					log.exception(e);
				}
		}

		return content;
	}
	
	@SuppressWarnings("rawtypes")
	public void testValidations()
	{
		//
		String[] 	mainIndices =null;
		String[] 	tc_keys 	= {"Flow ID","Step ID"}; 
		String[] 	tc_values 	= {flowID,stepID};
		String 		sheetName	= "Validations";
		
		try
		{
			
			mainIndices = excel.getChildRowIndices(sheetName, tc_keys, tc_values);	
			if(mainIndices.length>= 1 && enableVerView.equalsIgnoreCase("Y"))
			{				
	        	String message = String.format("%-30s\t\t %-100s" ,"Step", ":\tTest Verificatons");       	
	        	System.out.println(message);
				System.out.println("------------------------------------------------------------------------------------------------------------------");				
			}
			
	        for(int mainIndex = 0;mainIndex <mainIndices.length;mainIndex++)
	        {
	        	int 			rowIndex 				= 	Integer.parseInt(mainIndices[mainIndex]);
	        	Boolean			booleanResult			=	null;

	        	/*------------------------------------------------------------------------------------------------------*/
	        	/*------------------------------------------------------------------------------------------------------*/
	        	String 	actualValue			= 	excel.getValue(sheetName, rowIndex, "Actual Value");
	        	String	actualType			= 	excel.getValue(sheetName, rowIndex, "Actual Type");	
	        	String	actualFormat		= 	excel.getValue(sheetName, rowIndex, "Actual Format");	
	        	String 	expectedValue 		= 	excel.getValue(sheetName, rowIndex, "Expected Value");
	        	String 	expectedType 		= 	excel.getValue(sheetName, rowIndex, "Expected Type"); 
	        	String 	expectedFormat 		= 	excel.getValue(sheetName, rowIndex, "Expected Format"); 
	        	String 	returnValue 		= 	excel.getValue(sheetName, rowIndex, "Return Value"); 
	        	String 	returnType 			= 	excel.getValue(sheetName, rowIndex, "Return Type"); 	 
	        	String 	returnFormat 		= 	excel.getValue(sheetName, rowIndex, "Return Format"); 
	        	String  operation 			=	excel.getValue(sheetName, rowIndex, "Operation");
	        	String  verificationName	=	excel.getValue(sheetName, rowIndex, "Verification Name");
	        	String  trueCondition 		=	excel.getValue(sheetName, rowIndex, "True Condition");
	        	String  falseCondition 		=	excel.getValue(sheetName, rowIndex, "False Condition");
	        		       	       
	        	actualValue 				=	replaceVariables(actualValue);	   	        	
	        	expectedValue 				=	replaceVariables(expectedValue);
	        	verificationName			=	replaceVariables(verificationName);
	        	
	        	if(actualType.trim().equals(""))	actualType 		= "String";
	        	if(expectedType.trim().equals(""))	expectedType 	= "String";
	        	if(returnType.trim().equals(""))	returnType 		= "Boolean";
	        	
	        	Logger.showMessage("Debug", "Actual Type "+actualType+" "+actualValue);
	        	Logger.showMessage("Debug", "Expected Type "+expectedType+" "+expectedValue);
	        	Logger.showMessage("Debug", "Return Type "+returnType);
	        	
	        	/*------------------------------------------------------------------------------------------------------*/
	        	/*------------------------------------------------------------------------------------------------------*/
	        	
	        	if(actualValue.equals("HTTP_STATUS_CODE"))
	        	{
	        		actualValue 		= api.getResponseCode();
	        		if(actualValue == null )actualValue = "";
	        	}
	        	/*------------------------------------------------------------------------------------------------------*/
	        	/*------------------------------------------------------------------------------------------------------*/
	        	Object actual 	= 	null;
	        	Object expected = 	null;
	        	Object result 	=	null;
	        	try
	        	{
	        		if(actualType.equals("String")) 	actual =  actualValue; 				   if(expectedType.equals("String")) 	expected =  expectedValue;
	        		if(actualType.equals("Long")) 		actual =  new Long		(actualValue); if(expectedType.equals("Long")) 		expected =  new Long	(expectedValue);
		        	if(actualType.equals("Double")) 	actual =  new Double	(actualValue); if(expectedType.equals("Double")) 	expected =  new Double	(expectedValue);
		        	if(actualType.equals("Boolean")) 	actual =  new Boolean	(actualValue); if(expectedType.equals("Boolean"))	expected =  new Boolean	(expectedValue);
		        	if(actualType.equals("Float")) 		actual =  new Float		(actualValue); if(expectedType.equals("Float")) 	expected =  new Float	(expectedValue);
		        	if(actualType.equals("Integer")) 	actual =  new Integer	(actualValue); if(expectedType.equals("Integer")) 	expected =  new Integer	(expectedValue);
		        	if(actualType.equals("Short")) 		actual =  new Short		(actualValue); if(expectedType.equals("Short")) 	expected =  new Short	(expectedValue);
		        	
		        	if(actualType.equals("Date")) 
		        	{
		        		DateFormat formatter = null;
    					if(actualFormat != null && ! actualFormat.trim().equals("") )
    					{
    						try
    						{
    							formatter 	= new SimpleDateFormat(actualFormat);	    						
    							actual 		= formatter.parse(actualValue);
    						}
    						catch(Exception e) { actual= ""; Logger.showMessage("Exception", "Date Conversion Issue"); }
    					}	
    					else
    					{
    						try
    						{
        						formatter 	= DateFormat.getDateInstance();
        						actual 		= formatter.parse(actualValue);
    						}
    						catch(Exception e) { actual= ""; Logger.showMessage("Exception", "Date Conversion Issue"); }

    					}
		        	}
		        	
		        	if(expectedType.equals("Date")) 
		        	{
		        		DateFormat formatter = null;
    					if(expectedFormat != null && ! expectedFormat.trim().equals("") )
    					{
    						try
    						{
    							formatter 	= new SimpleDateFormat(expectedFormat);	    						
    							expected 	= formatter.parse(expectedValue);
    						}
    						catch(Exception e) { expected= ""; Logger.showMessage("Exception", "Date Conversion Issue"); }
    					}	
    					else
    					{
    						try
    						{
        						formatter 	= DateFormat.getDateInstance();
        						expected 	= formatter.parse(expectedValue);
    						}
    						catch(Exception e) { expected=""; Logger.showMessage("Exception", "Date Conversion Issue"); }

    					}
		        	}
	        	}
	        	catch(Exception e)
	        	{
	        		Logger.showMessage("Exception", "Data Type Conversion");
	        	}
	        	/*------------------------------------------------------------------------------------------------------*/
	        	/*------------------------------------------------------------------------------------------------------*/
	        	try
	        	{
		    		ValidationEngine validator = new ValidationEngine();	    		
		    		Class validationEngine	=validator.getClass();  
		    		for(Method method:validationEngine.getMethods() )
		    		{
		    			if(method.getName().equals(operation))
		    			{
		    				try
		    				{
			    				if(method.getParameterCount()== 2)
			    				{
			    					Parameter firstParameter = method.getParameters()[0];
			    					Parameter scondParameter = method.getParameters()[1];
			    					
			    					if(firstParameter.getType().toString().endsWith(actualType) && scondParameter.getType().toString().endsWith(expectedType))
			    					{
			    						result = method.invoke(null, new Object[]{actual,expected});			    						
			    						break;
			    					}			    								    					
			    				}
			    				else if(method.getParameterCount()== 1)
			    				{
			    					Parameter firstParameter = method.getParameters()[0];
			    					if(firstParameter.getType().toString().endsWith(actualType))
			    					{
			    						result = method.invoke(null, new Object[]{actual});
			    					}			    								    				
			    				}		    					
		    				}
		    				catch(Exception e) {e.printStackTrace();}
		    			}	    	
		    		}	        		
	        	}
	        	catch(Exception e)
	        	{
	        		e.printStackTrace();
	        		Logger.showMessage("Exception", "Unable to Invoke Validation Methods");
	        	}

	        	if(!returnValue.trim().equals(""))
	        	{
	        		createVariable(returnType,returnValue,result,returnFormat);
	        	}
	        	
	        	if(enableVerView.equalsIgnoreCase("Y"))
	        	{
	        		System.out.println(String.format("%-30s\t\t %-100s" ,"Actual Value", ":\t"+actualValue));
	        		System.out.println(String.format("%-30s\t\t %-100s" ,"Expected Value", ":\t"+expectedValue));
	        		System.out.println(String.format("%-30s\t\t %-100s" ,"Validation Operation", ":\t"+operation));
	        		System.out.println(String.format("%-30s\t\t %-100s" ,"Validation Result", ":\t"+result.toString()));	        		
	        	}
	        	        		
	        	if(log!= null)
	        	{
	        		log.info("Verification Details", "Actual Value", actualValue);
	        		log.info("Verification Details", "Expected Value", expectedValue);
	        		log.info("Verification Details", "Operation", operation);
	        		log.info("Verification Details", "Result", result.toString());
	        	}
	        	
	        	if(returnType.equals("Boolean")) booleanResult = (Boolean) result;
	        	
	        	if(booleanResult!= null && booleanResult)
	        	{

	        		if(toolName.equals("LFT"))
	        		{	
			        	try
			        	{
//		        			Reporter.reportEvent(verificationName, "Actual Value : "+actualValue+", Performing Operations : "+operation+",  Expected Value :  "+expectedValue+",  Result : "+booleanResult , Status.Passed);
//		        			Reporter.addRunInformation("Actual Value", actualValue);
//		        			Reporter.addRunInformation("Expected Value", expectedValue);
//		        			Reporter.addRunInformation("Validation Operation", operation);
//		        			Reporter.addRunInformation("Validation Result", new Boolean(booleanResult).toString());
		        		}
		        		catch(Exception e)
		        		{
		        			Logger.showMessage("Exception", "Unable to Build LeanFT Report");
		        		}
	        		}
		        	if(log!= null)
		        	{
		        		log.result(verificationName, "Result", trueCondition);
		        	}
		        
		        	if(enableVerView.equalsIgnoreCase("Y"))
		        	{
		        		System.out.println(String.format("%-30s\t\t %-100s" ,"Validation Message", ":\t"+verificationName+" : "+trueCondition));
		        	}
	        			        		
	        	}
	        	else	        		
	        	{
	        		
	        		if(toolName.equals("LFT"))
	        		{
	        			try
		        		{
//	        			Reporter.reportEvent(verificationName, "Actual Value : "+actualValue+", Performing Operations : "+operation+",  Expected Value :  "+expectedValue+",  Result : "+booleanResult , Status.Failed);
//	        			Reporter.addRunInformation("Actual Value", actualValue);
//	        			Reporter.addRunInformation("Expected Value", expectedValue);
//	        			Reporter.addRunInformation("Validation Operation", operation);
//	        			Reporter.addRunInformation("Validation Result", new Boolean(booleanResult).toString());
		        		}
		        		catch(Exception e)
		        		{
		        			Logger.showMessage("Exception", "Unable to Build LeanFT Report");
		        		}
	        		}	        				        			        	
		        	if(enableVerView.equalsIgnoreCase("Y"))
		        	{
		        		System.out.println(String.format("%-30s\t\t %-100s" ,"Validation Message", ":\t"+verificationName+" : "+falseCondition));
		        	}
	        		
		        	if(log!= null)
		        	{
		        		log.result(verificationName, "Result", falseCondition);
		        	}
	        	}
	        	
	          	if(enableVerView.equalsIgnoreCase("Y"))
	        	{
	          		System.out.println("------------------------------------------------------------------------------------------------------------------");	
	        	}
	        	
	        	booleanResult			=	null;
	        }
			if(mainIndices.length>= 1 && enableVerView.equalsIgnoreCase("Y"))
			{
				System.out.println("------------------------------------------------------------------------------------------------------------------");				
			}
		}
		catch(Exception e)
		{
			Logger.showMessage("Exception", "Unable to Perform Validation");
			e.printStackTrace();
			if(log != null)
			{
				log.exception(e);
			}
		}
	}

	public void configureExecution()
	{
		String sheetName	= 	"Configurations";	
		String almURI		=	"";
		String almUser		=	"";
		String almPassword	=	"";
		String almDomain	=	"";
		String almProject	=	"";
		String amlFolder	=	"";			
		try
		{
			loggerConfigurations();
			dbConfiguration();
			/*---------------------------------------------------------------------------------------------------------------*/
			/* General Configurations 																						 */
			/*---------------------------------------------------------------------------------------------------------------*/
			if(excel.getValue(sheetName, 1, 8).startsWith("Y")) enableCertView 	= "Y";
			if(excel.getValue(sheetName, 2, 8).startsWith("Y")) enableRHdrView 	= "Y";
			if(excel.getValue(sheetName, 3, 8).startsWith("Y")) enableCkView 	= "Y";
			if(excel.getValue(sheetName, 4, 8).startsWith("Y")) enableProView 	= "Y";
			if(excel.getValue(sheetName, 5, 8).startsWith("Y")) enableVerView 	= "Y";
			
			if(api != null)
			{
				api.setShowCerts(enableCertView);
			}
				
			/*---------------------------------------------------------------------------------------------------------------*/
			/* Proxy Configurations 																						 */
			/*---------------------------------------------------------------------------------------------------------------*/
			
			if(excel.getValue(sheetName, 1, 3).startsWith("Y"))
			{
				try
				{
					System.setProperty("http.proxySet", 	 "true");
					System.setProperty("http.proxyHost",	 excel.getValue(sheetName, 2, 3));
					System.setProperty("http.proxyPort",	 excel.getValue(sheetName, 3, 3));
					System.setProperty("http.proxyUser", 	 excel.getValue(sheetName, 4, 3));
					System.setProperty("http.proxyPassword", excel.getValue(sheetName, 3, 3));
					
					System.setProperty("https.proxySet", 	"true");
					System.setProperty("https.proxyHost",	 excel.getValue(sheetName, 2, 3));
					System.setProperty("https.proxyPort",	 excel.getValue(sheetName, 3, 3));
					System.setProperty("https.proxyUser", 	 excel.getValue(sheetName, 4, 3));
					System.setProperty("https.proxyPassword", excel.getValue(sheetName, 3, 3));
					
				}
				catch(Exception e)
				{
					Logger.showMessage("Exception", "Unable to Set Proxies");
					if(log != null)
					{
						log.exception(e);
					}
				}
			}
			/*---------------------------------------------------------------------------------------------------------------*/
			/* ALM Configurations 																						 */
			/*---------------------------------------------------------------------------------------------------------------*/
			
			almURI		=	excel.getValue(sheetName, 1, 5);
			almUser		=	excel.getValue(sheetName, 2, 5);
			almPassword	=	excel.getValue(sheetName, 3, 5);
			almDomain	=	excel.getValue(sheetName, 4, 5);
			almProject	=	excel.getValue(sheetName, 5, 5);
			amlFolder	=	excel.getValue(sheetName, 6, 5);

			if(almURI 		!= null) 	FlowVariables.set("almURI", 		almURI);
			if(almUser 		!= null) 	FlowVariables.set("almUser", 		almUser);
			if(almPassword 	!= null) 	FlowVariables.set("almPassword", 	almPassword);
			if(almDomain 	!= null) 	FlowVariables.set("almDomain", 		almDomain);
			if(almProject 	!= null) 	FlowVariables.set("almProject", 	almProject);
			if(amlFolder 	!= null) 	FlowVariables.set("amlFolder", 		almURI);
		}
		catch(Exception e)
		{
	        if(log!= null)
	        {	
	        	System.out.println("Unable to Configure Properties");
	        	log.exception(e, "Unable to Configure Properties");	        	
	        }
		}
	}

	private void dbConfiguration()
	{
		/*---------------------------------------------------------------------------------------------------------------*/
		/* DB Configurations 																						 */
		/*---------------------------------------------------------------------------------------------------------------*/
		String sheetName		=	"Configurations";
		String hostName 		= 	"*";
		String portNo			=	"";
		String userName			=	"";
		String password			=	"";
		String dbName			=	"";
		String dbType			=	"";
		String connectionName 	=	"";
		int    rowIndex			=	10;
		try
		{
			while(hostName.equals("*"))
			{
				hostName 		= 	excel.getValue(sheetName, rowIndex,	0);
				portNo			=	excel.getValue(sheetName, rowIndex, 1);
				userName		=	excel.getValue(sheetName, rowIndex, 2);
				password		=	excel.getValue(sheetName, rowIndex, 3);
				dbName			=	excel.getValue(sheetName, rowIndex, 4);
				dbType			=	excel.getValue(sheetName, rowIndex, 5);
				connectionName 	=	excel.getValue(sheetName, rowIndex, 6);
				
				//System.out.println(hostName+" "+portNo+" "+userName+"  "+password+ "  "+dbName+"  "+dbType+"  "+connectionName);
				
				hostName 		= 	replaceVariables(hostName);
				portNo			=	replaceVariables(portNo);
				userName		=	replaceVariables(userName);
				password		=	replaceVariables(password);
				dbName			=	replaceVariables(dbName);
				dbType			=	replaceVariables(dbType);
				connectionName 	=	replaceVariables(connectionName);
				
				DBConnections dbCon = new DBConnections();
				dbCon.setHostName(hostName);
				dbCon.setPortNo(portNo);
				dbCon.setUserName(userName);
				dbCon.setPassword(password);
				dbCon.setDbName(dbName);
				dbCon.setDbType(dbType);
				dbCon.setConnectionName(connectionName);
				FlowVariables.set(connectionName, dbCon);
				
				if(dbConnections!= null)
				{
					dbConnections.add(dbCon);
				}
				
				dbCon = null;
				hostName 		= 	"";
				portNo			=	"";
				userName		=	"";
				password		=	"";
				dbName			=	"";
				dbType			=	"";
				connectionName 	=	"";				
				hostName 		= 	excel.getValue(sheetName, rowIndex+1, 0).trim();

				if(! hostName.equals(""))					
				{
					hostName = "*";
				}
				rowIndex++;
			}
		}
		catch(Exception e)
		{
			Logger.showMessage("Exception", "Unable to Configure DB Connections");
	        if(log!= null)
	        {	
	        	
	        	log.exception(e, "Unable to Configure Properties");	        	
	        }
		}		
	}
	
	private void loggerConfigurations()
	{
		
		String sheetName	= 	"Configurations";
		String enableLog	=	"";
		String logPath 		= 	"";
		String logAppend 	= 	"";
		String logLevel 	= 	"";
		String logName 		= 	"";	
		Logger logger 		= 	null;
		/*---------------------------------------------------------------------------------------------------------------*/
		/* Logger Configurations 																						 */
		/*---------------------------------------------------------------------------------------------------------------*/
		try
		{			
			enableLog		= excel.getValue(sheetName, 1, 1);
			logPath 		= excel.getValue(sheetName, 2, 1);
			logLevel 		= excel.getValue(sheetName, 3, 1);
			logAppend 		= excel.getValue(sheetName, 4, 1);
			logName 		= excel.getValue(sheetName, 5, 1);
			
//			System.out.println("----------------------------------------------------------------------");
//			System.out.println(enableLog+" : "+logPath+" : "+logAppend+" : "+logLevel+" : "+logName);
//			System.out.println("----------------------------------------------------------------------");
							
			if(enableLog	!= null &&  enableLog.equalsIgnoreCase("Yes") )
			{	
				logger = new Logger();
				FlowVariables variable = new FlowVariables();
				variable.setVar("log", logger);			
				
				
				if(logPath	!= null 	&& ! logPath.equals		(""))
				{
					logger.PATH =logPath;
				}			
				if(logAppend!= null 	&& ! logAppend.equals	(""))
				{
					if(logAppend.equalsIgnoreCase("TRUE"))
					{
						logger.MODE 	="APPEND";
					}				
					else
					{
						logger.MODE 	= "NEW";
					}
				}
				if(logLevel	!= null 	&& ! logLevel.equals	("")) 
				{
					logger.LEVEL =logLevel;
				}
				if(logName	!= null 	&& ! logName.equals		(""))
				{
					logger.NAME =logName;
				}
				
				try
				{
					logger.enableLog();
					log = (Logger) variable.getVar("log");
					api.enableLog();
				}
				catch(Exception e)
				{
					enableLog = "No";
					log = null;
					Logger.showMessage("Exception", "Unable to Enable Logging");
					if(log != null)
					{
						log.exception(e);
					}
				}
			}
		}
		catch(Exception e) 
		{
			enableLog = "No"; 
			Logger.showMessage("Exception", "Unable to Configure Test");
			if(log != null)
			{
				log.exception(e);
			}
		}
	}

	@SuppressWarnings("rawtypes")
	private void createVariable(String dataType,String variableName,String variableValue,String dataFormat)
	{
		//System.out.println(dataType+"\t\t\t"+variableName+"\t\t\t"+variableValue+"\t\t\t"+dataFormat);
		
		/*-------------------------------------------------------------------------------------------------------------------*/
		/* Handle Data Format Conversion
		/*-------------------------------------------------------------------------------------------------------------------*/
		FormatFunctions fmtFunctions 	= 	new FormatFunctions();	    		
		Class 			formatFunctions	=	fmtFunctions.getClass();  
		Object			changedFormat	=	null;
		try
		{    		
    		for(Method method:formatFunctions.getMethods() )
    		{
    			if(method.getName().equals(dataFormat))
    			{
					if(method.getParameterCount()== 1)
    				{
						changedFormat = method.invoke(null, new Object[]{variableValue});		    								    				
    				}	
    				
    				else if(method.getParameterCount()== 2)
    				{
    					changedFormat = method.invoke(null, new Object[]{variableValue,dataFormat});			    								    					
    				}    
    				else if(method.getParameterCount()== 3)
    				{
    					changedFormat = method.invoke(null, new Object[]{variableValue,dataFormat,dataType});			    								    					
    				}
					
					if(changedFormat!= null)
					{
						variableValue = changedFormat.toString();
					}
    			}
    		}	        		
    	}
    	catch(Exception e)
    	{
    		Logger.showMessage("Exception", "Unable to Invoke Validation Methods");
    	}
		/*-------------------------------------------------------------------------------------------------------------------*/
		/* Variable Creation																								 */
		/*-------------------------------------------------------------------------------------------------------------------*/	
		try
		{
			switch (dataType)
			{
			case "Integer":

				FlowVariables.set(variableName, new Integer(variableValue));
				break;

			case "Float":

				FlowVariables.set(variableName, new Float(variableValue));
				break;

			case "Boolean":

				FlowVariables.set(variableName, new Boolean(variableValue));
				break;

			case "Date":

				try
				{
					if (!dataFormat.trim().equals(""))
					{
						try
						{
							DateFormat formatter = new SimpleDateFormat(dataFormat);
							java.util.Date date = formatter.parse(variableValue);
							FlowVariables.set(variableName, date);
						}
						catch (Exception e)
						{
							DateFormat formatter = DateFormat.getDateInstance();
							java.util.Date date = formatter.parse(variableValue);
							FlowVariables.set(variableName, date);
						}
					}
					else
					{
						DateFormat formatter = DateFormat.getDateInstance();
						java.util.Date date = formatter.parse(variableValue);
						FlowVariables.set(variableName, date);
					}
				}
				catch (Exception e)
				{
					e.printStackTrace();
					FlowVariables.set(variableName, variableValue);
				}
				break;

			case "Double":

				FlowVariables.set(variableName, new Double(variableValue));
				break;

			case "Short":

				FlowVariables.set(variableName, new Short(variableValue));
				break;

			case "String":

				FlowVariables.set(variableName, variableValue);
				break;

			default:

				FlowVariables.set(variableName, variableValue);
				break;
			}
		}
		catch(Exception e)
		{
			//FlowVariables.set(variableName, variableValue);
			Logger.showMessage("Exception", "Data Type Conversion");
			//e.printStackTrace();
			if(log != null) log.exception(e);
			
		}		
	}

	@SuppressWarnings("rawtypes")
	private void createVariable(String dataType,String variableName,Object variableValue,String dataFormat)
	{
		try
		{
			FormatFunctions fmtFunctions 	= 	new FormatFunctions();	    		
			Class 			formatFunctions	=	fmtFunctions.getClass();  
			Object			changedFormat	=	null;
			try
			{    		
	    		for(Method method:formatFunctions.getMethods() )
	    		{
	    			if(method.getName().equals(dataFormat))
	    			{
						if(method.getParameterCount()== 1)
	    				{
							changedFormat = method.invoke(null, new Object[]{variableValue});		    								    				
	    				}	
	    				
	    				else if(method.getParameterCount()== 2)
	    				{
	    					changedFormat = method.invoke(null, new Object[]{variableValue,dataFormat});			    								    					
	    				}    
	    				else if(method.getParameterCount()== 3)
	    				{
	    					changedFormat = method.invoke(null, new Object[]{variableValue,dataFormat,dataType});			    								    					
	    				}
						
						if(changedFormat!= null)
						{
							variableValue = changedFormat.toString();
						}
	    			}
	    		}	        		
	    	}
	    	catch(Exception e)
	    	{
	    		e.printStackTrace();
	    		Logger.showMessage("Exception", "Unable to Invoke Validation Methods");
	    	}
			
			switch(dataType)
			{
			case "Integer"	:
				
				FlowVariables.set(variableName, (Integer) variableValue);
				break;
				
			case "Float"	:
				
				FlowVariables.set(variableName, (Float) variableValue);
				break;
				
			case "Boolean"	:
				
				FlowVariables.set(variableName, (Boolean) variableValue);
				break;
				
			case "Date"		:

				FlowVariables.set(variableName, (java.util.Date) variableValue);
				break;
				
			case "Double"	:
				
				FlowVariables.set(variableName,  (Double) variableValue);
				break;
				
			case "Short"	:
				
				FlowVariables.set(variableName, (Short) variableValue);
				break;
				
			case "String"	:
				
				FlowVariables.set(variableName, variableValue);
				break;
				
			default : 
					
				FlowVariables.set(variableName, variableValue);
				break;						
			}

			
		}
		catch(Exception e)
		{
			FlowVariables.set(variableName, variableValue);
			Logger.showMessage("Exception", "Data Type Conversion");
			if(log != null) log.exception(e);
			
		}		
	}
}
