package com.rtaware.sacredthread.model;

public class ThreadInfo
{
	public String getThreadNumber()
	{
		return threadNumber;
	}
	public void setThreadNumber(String threadNumber)
	{
		this.threadNumber = threadNumber;
	}	
	public String getThreadName()
	{
		return threadName;
	}
	public void setThreadName(String threadName)
	{
		this.threadName = threadName;
	}
	public ThreadStatus getThreadStatus()
	{
		return threadStatus;
	}
	public void setThreadStatus(ThreadStatus threadStatus)
	{
		this.threadStatus = threadStatus;
	}
	public Integer getThreadPriority()
	{
		return threadPriority;
	}
	public void setThreadPriority(Integer threadPriority)
	{
		this.threadPriority = threadPriority;
	}
	public Integer getThreadOSPriority()
	{
		return threadOSPriority;
	}
	public void setThreadOSPriority(Integer threadOSPriority)
	{
		this.threadOSPriority = threadOSPriority;
	}
	public String getThreadID()
	{
		return threadID;
	}
	public void setThreadID(String threadID)
	{
		this.threadID = threadID;
	}
	public String getThreadNativeID()
	{
		return threadNativeID;
	}
	public void setThreadNativeID(String threadNativeID)
	{
		this.threadNativeID = threadNativeID;
	}
	public Boolean getThreadIsDaemon()
	{
		return threadIsDaemon;
	}
	public void setThreadIsDaemon(Boolean threadIsDaemon)
	{
		this.threadIsDaemon = threadIsDaemon;
	}
	public StringBuilder getThreadStack()
	{
		return threadStack;
	}
	public void setThreadStack(StringBuilder threadStack)
	{
		this.threadStack = threadStack;
	}
	private  	ThreadStatus 	threadStatus;
	private 	String 				threadNumber		= 		"";
	private  	String 				threadName			= 		"";
	private  	Integer				threadPriority 		= 		0;
	private	 	Integer				threadOSPriority 	=		0;
	private		String				threadID				= 		"";
	private		String				threadNativeID		= 		"";
	private		Boolean				threadIsDaemon 	= 		false;
	private     StringBuilder		threadStack			= 		new StringBuilder();

	
	
}
