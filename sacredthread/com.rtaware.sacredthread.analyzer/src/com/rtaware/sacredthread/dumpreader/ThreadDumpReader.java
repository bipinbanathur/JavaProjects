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


public class ThreadDumpReader
{
	private 	ThreadDump 			threadDump		= new ThreadDump();
	private 	String 					dateFormat 		="yyyy-mm-dd HH:mm:ss";
	private		AtomicInteger	 	lineNumber 		= new AtomicInteger();
	private		List<ThreadInfo> 	threadDetails 	= new ArrayList<>();
	//private		StringBuilder 			threadStack		= new StringBuilder();
	private String 	daemonIdentifier 		= "daemon";
	private String 	priorityIdentifier 		= "prio=";
	private String 	osPriorityIdentifier 	= "os_prio=";
	private String 	threadIdentifier			= "tid=";
	private String	nativeIdentifier			= "nid=";
	private String 	owIdentifier				= "Object.wait()";
	private String 	waitingIdentifier		= "waiting";
	private String 	runningIdentifier		= "running";
	private String 	runnableIdentifier		= "runnable";
	private String 	blockedIdentifier		= "blocked";
	private String 	threadNoIdentifier		= "#";
	private String	jniGRIdentifier			= "JNI global references: ";
	
	
	public ThreadDumpReader()
	{
		
	}
	public ThreadDumpReader(String dumpPath) throws Exception
	{	
		try (Stream<String> fileStream = Files.lines(Paths.get(dumpPath)))
		{
				fileStream.filter(line-> (null != line && line.trim().length()>1)).forEach(this::processLine );
				threadDump.setThreadList(threadDetails);
				
				System.out.printf("%-25s : %-100s \n", "Dump Create Date",threadDump.getCreateDate());
				System.out.printf("%-25s : %-100s\n", "Dump Description",threadDump.getDumpDescription());
				System.out.printf("%-25s : %-100s\n", "JNI References",threadDump.getJniGlobalReference());
				
				System.out.printf
				(
						"%-50s %-25s %-25s %-25s %-25s %-25s %-25s %-25s\n", 
						"Thread Name",
						"Thread ID",
						"Thread Status",
						"Thread Number",
						"Thread Priority",
						"OS Priority",
						"Native ID",
						"Daemon Thread"
				);
				threadDump.getThreadList().forEach(threadInfo ->
				{
					System.out.printf
					(
							"%-50s %-25s %-25s %-25s %-25s %-25s %-25s %-25s\n", 
							threadInfo.getThreadName(),
							threadInfo.getThreadID(),
							threadInfo.getThreadStatus(),
							threadInfo.getThreadNumber(),
							threadInfo.getThreadPriority(),
							threadInfo.getThreadOSPriority(),
							threadInfo.getThreadNativeID(),
							threadInfo.getThreadIsDaemon()
					);
				}
				);
		}
		catch (Exception  e)
		{
			e.printStackTrace();
		}

	}
	
	private void processLine(String currentLine) 
	{
		String 			threadName ="";
		ThreadInfo 	threadInfo = null;
		
		lineNumber.incrementAndGet();			

		if(	lineNumber.get() == 1 && null != getCreateDate(currentLine)) 		threadDump.setCreateDate(getCreateDate(currentLine));						
		else if (lineNumber.get() == 2 && null != currentLine)							threadDump.setDumpDescription(currentLine);
		else if( currentLine.indexOf("\"")!=-1)
		{				
			threadInfo 	= new ThreadInfo();					
			threadName 	= currentLine.substring( currentLine.indexOf("\""),  currentLine.lastIndexOf("\"")+1).trim();
			currentLine 	= currentLine.substring(currentLine.indexOf(threadName)+threadName.length(),currentLine.length());
			threadName 	= threadName.substring(1, threadName.length()-1);
			
			threadInfo.setThreadName(threadName);
			String[] threadParameters = currentLine.split("\\s+");
			
			for(String threadParameter:threadParameters)
			{

					 if(threadParameter.equals(daemonIdentifier)) 				threadInfo.setThreadIsDaemon(true);
					 else if(threadParameter.equals(owIdentifier)) 				threadInfo.setThreadStatus(ThreadStatus.OBJECT_WAIT);
					 else if(threadParameter.equals(waitingIdentifier)) 			threadInfo.setThreadStatus(ThreadStatus.WAITING);
					 else if(threadParameter.equals(runningIdentifier)) 		threadInfo.setThreadStatus(ThreadStatus.RUNNING);
					 else if(threadParameter.equals(runnableIdentifier)) 		threadInfo.setThreadStatus(ThreadStatus.RUNNABLE);
					 else if(threadParameter.equals(blockedIdentifier)) 		threadInfo.setThreadStatus(ThreadStatus.BLOCKED);
					
					else if(threadParameter.startsWith(threadNoIdentifier)) 		
					{
						threadParameter = threadParameter.substring(threadParameter.indexOf(threadNoIdentifier)+threadNoIdentifier.length()).trim();
						threadInfo.setThreadNumber(threadParameter);
					}
					
					else if(threadParameter.startsWith(priorityIdentifier)) 		
					{
						threadParameter = threadParameter.substring(threadParameter.indexOf(priorityIdentifier)+priorityIdentifier.length()).trim();
						threadInfo.setThreadPriority(Integer.parseInt(threadParameter));
					}
					else if(threadParameter.startsWith(osPriorityIdentifier)) 		
					{
						threadParameter = threadParameter.substring(threadParameter.indexOf(osPriorityIdentifier)+osPriorityIdentifier.length()).trim();
						threadInfo.setThreadOSPriority(Integer.parseInt(threadParameter));
					}
					else if(threadParameter.startsWith(threadIdentifier)) 		
					{
						threadParameter = threadParameter.substring(threadParameter.indexOf(threadIdentifier)+threadIdentifier.length()).trim();
						threadInfo.setThreadID(threadParameter);
					}
					else if(threadParameter.startsWith(nativeIdentifier)) 		
					{
						threadParameter = threadParameter.substring(threadParameter.indexOf(nativeIdentifier)+nativeIdentifier.length()).trim();
						threadInfo.setThreadNativeID(threadParameter);
					}
			}
			threadDetails.add(threadInfo);
			threadInfo = null;
		}
		else if(currentLine.startsWith(jniGRIdentifier))
		{
			String jniGRF = currentLine.substring( currentLine.indexOf(jniGRIdentifier)+jniGRIdentifier.length()   ).trim() ;
			threadDump.setJniGlobalReference(Integer.parseInt(jniGRF));
		}
		else
		{
			//threadStack.append(currentLine);
		}
		
	}
	public static void main(String[] args) throws Exception
	{
		 new ThreadDumpReader("C:\\Users\\bipin.banathur\\Desktop\\ThreadDump.txt");
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
	
	
}
