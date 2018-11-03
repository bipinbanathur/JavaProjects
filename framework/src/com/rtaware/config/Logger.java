package com.rtaware.config;



import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Logger
{

	public  String 				MODE  			= 	""; // NEW/APPEND
	public  String 				PATH  			= 	"";
	public  String 				NAME  			= 	"";
	
	public  String 				LEVEL 			= 	""; // FINEST, FINER, FINE, CONFIG, INFO, WARNING, ERROR, SEVERE,RESULT
	
	public  String 				TYPE 			= 	""; // EXECPTION, MESSAGE, STEP, ERROR,RESULT, WARNING, SEVERE	
	private BufferedWriter 		writer 			= 	null;
	private String 				callingClass	=	"";
	private String 				callingMethod	=	"";
	private String				formatPattern	=	"%-10s\t %-30s\t %-25s\t %-10s\t %-10s\t %-10s\t  %-10s\t  %-10s\n";	
	private List<String> 		fineList 		= 	(List<String>) Arrays.asList( new String[] {"MESSAGE", "STEP","STEPGROUP", "ERROR", "RESULT", "WARNING", "SEVERE","INFO","DEBUG"} );
	private List<String> 		configList 		= 	(List<String>) Arrays.asList( new String[] {"CONFIG"} );
	private List<String> 		infoList 		= 	(List<String>) Arrays.asList( new String[] {"INFO"} );
	private List<String> 		errorList 		= 	(List<String>) Arrays.asList( new String[] {"ERROR"} );
	private List<String> 		warningList 	= 	(List<String>) Arrays.asList( new String[] {"WARNING"} );
	private List<String> 		resultList 		= 	(List<String>) Arrays.asList( new String[] {"RESULT"});
	private List<String> 		severeList 		= 	(List<String>) Arrays.asList( new String[] {"ERROR","SEVERE"});
	private List<String> 		debugList 		= 	(List<String>) Arrays.asList( new String[] {"DEBUG","SEVERE"});
	
	public Logger()
	{
		
		
	}
	
	public void enableLog()
	{
		try
		{			
//			System.out.println("----------------------------------------------------------------------");
//			System.out.println("After Instantiation");
//			System.out.println("----------------------------------------------------------------------");
//			System.out.println("Mode 	: "+MODE);
//			System.out.println("Level 	: "+LEVEL);
//			System.out.println("Name 	: "+NAME);
//			System.out.println("Path 	: "+PATH);		
//			System.out.println("----------------------------------------------------------------------");
			
			if(MODE.equals("APPEND") && ( NAME.equals("") ||  PATH.equals("")))
			{				
				new Exception("Append Failed Invalid Log Name or Path");				
			}
			
			DateFormat  dateFormat = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
			Date 		systemDate = new Date();
			
			if(MODE.equals("")) 	MODE 	= "NEW";
			if(NAME.equals("")) 	NAME 	= dateFormat.format(systemDate)+".log";
			if(PATH.equals("")) 	PATH 	= new File("").getAbsoluteFile().toString()+"\\log\\";
			
			if(LEVEL.equals("")) 	LEVEL 	= "FINEST";
			if(TYPE.equals("")) 	TYPE 	= "MESSAGE";
			
			if(MODE.equals("APPEND"))
			{
				try
				{
						File file = new File(PATH +"\\"+NAME);
						FileWriter fileWriter = new FileWriter(file.getAbsoluteFile(),true);
						writer = new BufferedWriter(fileWriter);																
				}
				catch(Exception e)
				{
					showMessage("Exception","Invalid Log Path : "+PATH +"\\"+NAME+" ("+MODE+")");
					MODE 	= "NEW";
				}
			}
			
			else if(MODE.equals("NEW"))
			{ 				
				try
				{
						NAME 	= dateFormat.format(systemDate)+".log";
						PATH 	= new File("").getAbsoluteFile().toString();					
						File file = new File(PATH +"\\"+NAME);
						FileWriter fileWriter = new FileWriter(file.getAbsoluteFile());
						writer = new BufferedWriter(fileWriter);					
						String writeMessage = String.format(formatPattern ,"TYPE","CALLING CLASS","CALLING METHOD","DATE","TIME","MESSAGE","KEY","VALUE");
						writer.write(writeMessage);						
				}
				catch(Exception e)
				{
					showMessage("Exception","Invalid Log Path : "+PATH +"\\"+NAME+" ("+MODE+")");
				}
			}						
			else
			{
				showMessage("Exception","Invalid Log Mode");
				new Exception("Append Failed Invalid Mode");
			}
		}
		catch(Exception e)
		{
			showMessage("Exception","Logger Exception : "+e);
		}
	}

	public void info(String message)
	{	
		
		Exception e 	= 	new Exception();
		callingClass	=	e.getStackTrace()[1].getClassName();
		callingMethod	=	e.getStackTrace()[1].getMethodName();		
		TYPE = "INFO";
		message(message, "", "");
	}

	public void info(String message,String key,String value)
	{		
		Exception e 	= 	new Exception();
		callingClass	=	e.getStackTrace()[1].getClassName();
		callingMethod	=	e.getStackTrace()[1].getMethodName();		
		TYPE = "INFO";
		message(message, key, value);
	}
	
	public void message(String message)
	{
		Exception e 	= 	new Exception();
		callingClass	=	e.getStackTrace()[1].getClassName();
		callingMethod	=	e.getStackTrace()[1].getMethodName();
		TYPE = "MESSAGE";
		message(message, "", "");
	}

	public void message(String message, String key, String value)
	{		
		try
		{
			
			if(LEVEL.equals("FINE") 	&& !fineList.contains(TYPE)) 	return;
			if(LEVEL.equals("CONFIG") 	&& !configList.contains(TYPE)) 	return;
			if(LEVEL.equals("INFO") 	&& !infoList.contains(TYPE)) 	return;
			if(LEVEL.equals("ERROR") 	&& !errorList.contains(TYPE)) 	return;
			if(LEVEL.equals("WARNING") 	&& !warningList.contains(TYPE)) return;
			if(LEVEL.equals("SEVERE") 	&& !severeList.contains(TYPE)) 	return;
			if(LEVEL.equals("RESULT") 	&& !resultList.contains(TYPE)) 	return;
			if(LEVEL.equals("DEBUG") 	&& !debugList.contains(TYPE)) 	return;
						
			Date 		currentDate = new Date();
			DateFormat 	dateFormat 	= new SimpleDateFormat("dd/MM/yyyy");
			DateFormat 	timeFormat 	= new SimpleDateFormat("HH:mm:ss");

			message = message.replaceAll("\t","   ");
			key 	= key.replaceAll("\t","   ");
			value 	= value.replaceAll("\t","   ");
			String writeMessage = String.format(formatPattern ,TYPE,callingClass,callingMethod,dateFormat.format(currentDate),timeFormat.format(currentDate),message,key,value);
			//System.out.println(writeMessage);
			if(writer != null)
			writer.write(writeMessage);
		}
		catch (IOException e)
		{
			
		}

	}

	public void warning(String message)
	{
		Exception e 	= 	new Exception();
		callingClass	=	e.getStackTrace()[1].getClassName();
		callingMethod	=	e.getStackTrace()[1].getMethodName();
		TYPE = "WARNING";
		message(message, "", "");
	}
	
	public void step(String message)
	{
		Exception e 	= 	new Exception();
		callingClass	=	e.getStackTrace()[1].getClassName();
		callingMethod	=	e.getStackTrace()[1].getMethodName();
		TYPE = "STEP";
		message(message, "", "");
	}
	
	public void config(String message)
	{
		Exception e 	= 	new Exception();
		callingClass	=	e.getStackTrace()[1].getClassName();
		callingMethod	=	e.getStackTrace()[1].getMethodName();
		TYPE = "CONFIG";
		message(message, "", "");
	}
	
	public void stepgroup(String message)
	{
		Exception e 	= 	new Exception();
		callingClass	=	e.getStackTrace()[1].getClassName();
		callingMethod	=	e.getStackTrace()[1].getMethodName();
		TYPE = "STEPGROUP";
		message(message, "", "");
	}

	public void result(String message, String key, String value)
	{
		Exception e 	= 	new Exception();
		callingClass	=	e.getStackTrace()[1].getClassName();
		callingMethod	=	e.getStackTrace()[1].getMethodName();
		TYPE = "RESULT";
		
		message(message, key, value);

	}

	public void error(String message)
	{
		Exception e 	= 	new Exception();
		callingClass	=	e.getStackTrace()[1].getClassName();
		callingMethod	=	e.getStackTrace()[1].getMethodName();
		TYPE = "ERROR";
		message(message, "", "");
	}
	
	public void debug(String message)
	{
		Exception e 	= 	new Exception();
		callingClass	=	e.getStackTrace()[1].getClassName();
		callingMethod	=	e.getStackTrace()[1].getMethodName();
		TYPE = "DEBUG";
		message(message, "", "");
	}

	public void exception(Exception ex,String message)
	{		
		try
		{
			// Type Class Function Date Time Message
			Date 		currentDate = new Date();
			DateFormat 	dateFormat 	= new SimpleDateFormat("yyyy/MM/dd");
			DateFormat 	timeFormat 	= new SimpleDateFormat("HH:mm:ss");			
			Exception e 			= 	new Exception();
			callingClass			=	e.getStackTrace()[1].getClassName();
			callingMethod			=	e.getStackTrace()[1].getMethodName() + " ["+ e.getStackTrace()[1].getLineNumber()+" ]";
			
			String writeMessage = String.format(formatPattern ,"EXCEPTION",callingClass,callingMethod,dateFormat.format(currentDate),timeFormat.format(currentDate),message,"","");
			//System.out.println(writeMessage);
			if(writer != null && writeMessage != null)
			{
				writer.write(writeMessage);
			}
			

			for (StackTraceElement ste : ex.getStackTrace())
			{
				writeMessage = String.format(formatPattern ,"STACK",ste.getClassName(),ste.getMethodName(),dateFormat.format(currentDate),timeFormat.format(currentDate),ste.getLineNumber(),"","");
				if(writer != null)
				writer.write(writeMessage);			
			}
		}
		catch (IOException e)
		{
//			e.printStackTrace();
		}		
	}
	
	public void exception(Exception ex)
	{
		try
		{			
			Date 		currentDate = new Date();
			DateFormat 	dateFormat 	= new SimpleDateFormat("dd/MM/yyyy");
			DateFormat 	timeFormat 	= new SimpleDateFormat("HH:mm:ss");
			
			Exception e 			= 	new Exception();
			callingClass			=	e.getStackTrace()[1].getClassName();
			callingMethod			=	e.getStackTrace()[1].getMethodName() + " ["+ e.getStackTrace()[1].getLineNumber()+" ]";
			
			String writeMessage = String.format(formatPattern ,"EXCEPTION",callingClass,callingMethod, dateFormat.format(currentDate),timeFormat.format(currentDate),ex.getMessage(),"","");

			if(writer!= null)
			writer.write(writeMessage);

			for (StackTraceElement ste : ex.getStackTrace())
			{
				writeMessage = String.format(formatPattern ,"STACK",ste.getClassName(),ste.getMethodName(),dateFormat.format(currentDate),timeFormat.format(currentDate),ste.getLineNumber(),"","");
				if(writer != null)
				writer.write(writeMessage);
			}
		}
		catch(Exception e)
		{
			//System.out.println(e);
		}
	}
		
	public void close()
	{
		try
		{
			writer.flush();
			writer.close();
		}
		catch(Exception e) { System.out.println(" : "+e);}
		
	}
	
	public void writeFile(String filePath , String fileContents)
	{
		BufferedWriter writer = null;
		try
		{
			writer = new BufferedWriter( new FileWriter(filePath));
		    writer.write( fileContents);
		    writer.close( );
		}
		catch ( IOException e)
		{
			System.out.println(e);
		}
		finally
		{
		    try
		    {
		        if ( writer != null)
		        writer.close( );
		    }
		    catch ( IOException e)
		    {
		    	System.out.println(e);
		    }
		}
	}
	
	public static void showMessage(String key , String value)
	{
		try
		{
			if(key != null && value != null )
			{
	        	String message = String.format("%-30s\t\t %-100s" ,key, ":\t"+value);       	
	        	System.out.println(message);
			}				
		}
		catch(Exception e)
		{
			System.out.println(e);
			e.printStackTrace();
		}           	         
  	}
}
