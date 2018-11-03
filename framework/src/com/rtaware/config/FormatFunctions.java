package com.rtaware.config;

public class FormatFunctions
{
	//First 	Param  should always be the actual value 
	//Second 	Param  should always be data Format
	//Third 	Param  should always be Data Type
	
	private static Logger 		log = null;
	
	public static void enableLog()
	{
		FlowVariables 			variables 		=	new FlowVariables();		
		try
		{ 
			log = (Logger) variables.getVar("log"); 
		} 
		catch(Exception e)
		{ 
		}
		
		if(log == null)
		{	
			try
			{ 
				log = (Logger) variables.getVar("log"); 
			}
			catch(Exception e)
			{				
			}
			
			if(log== null)
			{
				log = new Logger();
			}
		}

		variables = null;
	}

	public static String extractID(String formatContent)
	{
		if(log == null) enableLog();
		if(formatContent.indexOf("/") != -1)
		{
			formatContent 			= 	formatContent.substring(formatContent.lastIndexOf("/") + 1, formatContent.length());
		}			
		return formatContent;
		
	}
	
	public static String floor(String formatContent)
	{
		if(log == null) enableLog();
		if(log != null) {log.debug("Inside floor");}
		Double returnValue = null;
		try
		{			
			returnValue =  Math.floor(new Double(formatContent));		
			return returnValue.toString();			
		}
		catch(Exception e)
		{
			Logger.showMessage("Exception", "Format Exceotion : floor "+formatContent);
		}
		return returnValue.toString();	
	}
		
	public static String absolute(String formatContent)
	{
		if(log == null) enableLog();
		if(log != null) {log.debug("Inside abs");}
		Double returnValue = null;
		try
		{			
			returnValue =  Math.abs(new Double(formatContent));		
			return returnValue.toString();			
		}
		catch(Exception e)
		{
			Logger.showMessage("Exception", "Format Exceotion : abs "+formatContent);
		}
		return returnValue.toString();	
	}
	
	public static String round(String formatContent)
	{
		if(log == null) enableLog();
		if(log != null) {log.debug("Inside round");}
		Long returnValue = null;
		try
		{			
			returnValue = (Long) Math.round(new Double(formatContent));		
			return returnValue.toString();			
		}
		catch(Exception e)
		{
			Logger.showMessage("Exception", "Format Exceotion : round "+formatContent);
		}
		return returnValue.toString();	
	}
	
	public static String ceil(String formatContent)
	{
		if(log == null) enableLog();
		if(log != null) {log.debug("Inside ceil");}
		Double returnValue = null;
		try
		{
			returnValue = Math.ceil(new Double(formatContent));		
			return returnValue.toString();
			
		}
		catch(Exception e)
		{
			Logger.showMessage("Exception", "Format Exceotion : ciel "+formatContent);
		}
		return returnValue.toString();	
	}
	
	public static String random(String formatContent)
	{
		if(log == null) enableLog();
		if(log != null) {log.debug("Inside random");}
		Integer limit = null;
		
		if(! formatContent.trim().equals(""))
		{
			if(isNumeric(formatContent))
			{
				limit = new Integer(formatContent);
			}
			else
			{
				limit =  1000;
			}
		}	
		else
		{
			limit =  1000;
		}
		Integer returnValue = null;
		try
		{
			returnValue = 0 + (int)(Math.random() * limit);
			return returnValue.toString();			
		}
		catch(Exception e)
		{
			Logger.showMessage("Exception", "Format Exceotion : random "+formatContent);
			e.printStackTrace();
		}
		if(returnValue!= null)
		return returnValue.toString();	
		else 
			return "";
	}
	
	@SuppressWarnings("unused")
	private static Boolean isNumeric(String str)
	{
		try
		{
			Double d = Double.parseDouble(str);
		}
		catch(NumberFormatException e)
		{
			return false;
		}
		return true;
	}
}
