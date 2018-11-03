package com.rtaware.config;


import java.util.Hashtable;

public class FlowVariables
{


	
	private static Hashtable<String,Object>	flowVars			= null;
	
	public static void set(String variableName,Object variableValue)
	{
		try
		{
			if(flowVars== null)
			{
				flowVars = new Hashtable<String,Object>();
			}
			flowVars.put(variableName,variableValue);
		}
		catch(Exception e)
		{
			Logger.showMessage("Exception","Invalid Variables : "+variableName);			
		}
	}
	
	public static Object get(String variableName)
	{
		
		Object variableValue = null;
		try
		{
			if(flowVars== null)
			{
				throw new APIException("Flow Variables Unavailable");
			}
			
			if(flowVars.containsKey(variableName))
			{
				variableValue = flowVars.get(variableName);
			}
			else
			{				
			 return null;	//throw new APIException("Invalid Flow Variable Name : "+variableName);
			}
		}
		catch(Exception e)
		{
			Logger.showMessage("Exception","Invalid Variable : "+variableName);
		}
		return variableValue;
	}
	
	public void setVar(String variableName,Object variableValue)
	{
		try
		{
			if(flowVars== null)
			{
				flowVars = new Hashtable<String,Object>();
			}
			flowVars.put(variableName,variableValue);
		}
		catch(Exception e)
		{
			Logger.showMessage("Exception","Invalid Variables : "+variableName);
		}
	}
	
	public Object getVar(String variableName)
	{
		
		Object variableValue = null;
		try
		{
			
			if(flowVars== null)
			{
				throw new APIException("Flow Variable "+variableName+" Unavailable");
			}
			
			if(flowVars.containsKey(variableName))
			{
				variableValue = flowVars.get(variableName);
			}
			else
			{
				 return null;	//throw new APIException("Invalid Flow Variable Name "+variableName);
				//return null;
			}
		}
		catch(Exception e)
		{
			Logger.showMessage("Exception","Invalid Variables : "+variableName);
		}
		return variableValue;
	}
	
}
