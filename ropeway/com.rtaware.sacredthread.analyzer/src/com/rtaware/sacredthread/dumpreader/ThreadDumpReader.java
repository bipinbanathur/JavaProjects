package com.rtaware.sacredthread.dumpreader;


import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import com.rtaware.sacredthread.model.ThreadDump;
import com.rtaware.sacredthread.model.ThreadInfo;
import com.rtaware.sacredthread.model.ThreadStatus;
import  com.rtaware.sacredthread.util.STConstants;

public class ThreadDumpReader
{
	private 	ThreadDump 			threadDump		= 	null;
	private		AtomicInteger	 	lineNumber 		= 	null;
	private		List<ThreadInfo> 	threadDetails 	= 	null;
	private		ThreadInfo 			threadInfo 		=  null;
	private		StringBuilder 			threadStack		=	null;
	private 	String 					dateFormat 		=	"yyyy-mm-dd HH:mm:ss";
	private		String 					threadName 		=	"";
		
	public ThreadDumpReader()
	{
		
	}
	
	public ThreadDump getThreadDump(String dumpPath) throws Exception
	{	
			lineNumber 		= 	new AtomicInteger();
			threadDump 		= 	new ThreadDump();
			threadStack		=	new StringBuilder();
			threadDetails 	= 	new ArrayList<>();
			
			try (Stream<String> fileStream = Files.lines(Paths.get(dumpPath)))
			{
					fileStream.filter(line-> (null != line && line.trim().length()>1)).forEach(this::processLine );
					threadDump.setThreadList(threadDetails);
			}
			catch (Exception  e)
			{
				e.printStackTrace();
			}
			
			threadStack    = null;
			threadInfo 	 =  null;
			threadDetails  = null;
			return threadDump;
	}
	
	private void processLine(String currentLine) 
	{		
		lineNumber.incrementAndGet();			
		
		if			(lineNumber.get() == 1 && null != getCreateDate(currentLine)) 	threadDump.setCreateDate(getCreateDate(currentLine));						
		else if	(lineNumber.get() == 2 && null != currentLine)							threadDump.setDumpDescription(currentLine);
		
		else if	( currentLine.indexOf("\"")!=-1)
		{		
			
			if( null != threadInfo && threadName.trim().length() >0  && 	threadStack.length() > 0 )
			{
				threadInfo.setThreadStack(threadStack);
				threadDetails.add(threadInfo);
				threadInfo 	= null;
				threadStack 	= null;
				threadStack	= new StringBuilder();
			}
			
			threadInfo 	= new ThreadInfo();					
			threadName 	= currentLine.substring( currentLine.indexOf("\""),  currentLine.lastIndexOf("\"")+1).trim();
			currentLine 	= currentLine.substring(currentLine.indexOf(threadName)+threadName.length(),currentLine.length());
			threadName 	= threadName.substring(1, threadName.length()-1);
			
			threadInfo.setThreadName(threadName);
			String[] threadParameters = currentLine.split("\\s+");
			
			for(String threadParameter:threadParameters)
			{

					 if(threadParameter.equals(STConstants.daemonIdentifier)) 				threadInfo.setThreadIsDaemon(true);
					 else if(threadParameter.equals(STConstants.owIdentifier)) 				threadInfo.setThreadStatus(ThreadStatus.OBJECT_WAIT);
					 else if(threadParameter.equals(STConstants.waitingIdentifier)) 		threadInfo.setThreadStatus(ThreadStatus.WAITING);
					 else if(threadParameter.equals(STConstants.runningIdentifier)) 		threadInfo.setThreadStatus(ThreadStatus.RUNNING);
					 else if(threadParameter.equals(STConstants.runnableIdentifier)) 		threadInfo.setThreadStatus(ThreadStatus.RUNNABLE);
					 else if(threadParameter.equals(STConstants.blockedIdentifier)) 		threadInfo.setThreadStatus(ThreadStatus.BLOCKED);
					
					else if(threadParameter.startsWith(STConstants.threadNoIdentifier)) 		
					{
						threadParameter = threadParameter.substring(threadParameter.indexOf(STConstants.threadNoIdentifier)+STConstants.threadNoIdentifier.length()).trim();
						threadInfo.setThreadNumber(threadParameter);
					}
					
					else if(threadParameter.startsWith(STConstants.priorityIdentifier)) 		
					{
						threadParameter = threadParameter.substring(threadParameter.indexOf(STConstants.priorityIdentifier)+STConstants.priorityIdentifier.length()).trim();
						threadInfo.setThreadPriority(Integer.parseInt(threadParameter));
					}
					else if(threadParameter.startsWith(STConstants.osPriorityIdentifier)) 		
					{
						threadParameter = threadParameter.substring(threadParameter.indexOf(STConstants.osPriorityIdentifier)+STConstants.osPriorityIdentifier.length()).trim();
						threadInfo.setThreadOSPriority(Integer.parseInt(threadParameter));
					}
					else if(threadParameter.startsWith(STConstants.threadIdentifier)) 		
					{
						threadParameter = threadParameter.substring(threadParameter.indexOf(STConstants.threadIdentifier)+STConstants.threadIdentifier.length()).trim();
						threadInfo.setThreadID(threadParameter);
					}
					else if(threadParameter.startsWith(STConstants.nativeIdentifier)) 		
					{
						threadParameter = threadParameter.substring(threadParameter.indexOf(STConstants.nativeIdentifier)+STConstants.nativeIdentifier.length()).trim();
						threadInfo.setThreadNativeID(threadParameter);
					}
			}

		}
		else if(currentLine.startsWith(STConstants.jniGRIdentifier))
		{
			String jniGRF = currentLine.substring( currentLine.indexOf(STConstants.jniGRIdentifier)+STConstants.jniGRIdentifier.length()   ).trim() ;
			threadDump.setJniGlobalReference(Integer.parseInt(jniGRF));
		}
		else
		{
			threadStack.append(currentLine+"\n");
		}
		
	}
	
	private Date getCreateDate(String dateString)
	{
		try
		{
			return new SimpleDateFormat(dateFormat).parse(dateString);  
		}
		catch(Exception e)
		{
			return null;
		}
	}
	
//	private void viewThreadDump()
//	{
//		System.out.printf("%-25s : %-100s \n", 	"Dump Create Date",	threadDump.getCreateDate());
//		System.out.printf("%-25s : %-100s\n", 	"Dump Description",	threadDump.getDumpDescription());
//		System.out.printf("%-25s : %-100s\n", 	"JNI References",		threadDump.getJniGlobalReference());
//		
//		System.out.printf
//		(
//				"%-100s %-25s %-25s %-25s %-25s %-25s %-25s %-25s\n", 
//				"Thread Name",
//				"Thread ID",
//				"Thread Status",
//				"Thread Number",
//				"Thread Priority",
//				"OS Priority",
//				"Native ID",
//				"Daemon Thread"
//		);
//		
//		threadDump.getThreadList().forEach(threadInfo ->
//		{
//			System.out.printf
//			(
//					"%-100s %-25s %-25s %-25s %-25s %-25s %-25s %-25s\n", 
//					threadInfo.getThreadName(),
//					threadInfo.getThreadID(),
//					threadInfo.getThreadStatus(),
//					threadInfo.getThreadNumber(),
//					threadInfo.getThreadPriority(),
//					threadInfo.getThreadOSPriority(),
//					threadInfo.getThreadNativeID(),
//					threadInfo.getThreadIsDaemon()
//			);
//			System.out.println("\n\n Call Stack\n===============\n");
//			System.out.println(threadInfo.getThreadStack());
//			System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
//		});
//	}
//	
}
