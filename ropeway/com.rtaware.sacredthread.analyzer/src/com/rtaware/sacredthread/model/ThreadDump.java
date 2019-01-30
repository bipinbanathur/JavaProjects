package com.rtaware.sacredthread.model;

import java.util.Date;
import java.util.List;

public class ThreadDump
{
	public Date getCreateDate()
	{
		return createDate;
	}
	public void setCreateDate(Date createDate)
	{
		this.createDate = createDate;
	}
	public String getDumpDescription()
	{
		return dumpDescription;
	}
	public void setDumpDescription(String dumpDescription)
	{
		this.dumpDescription = dumpDescription;
	}
	public Integer getJniGlobalReference()
	{
		return jniGlobalReference;
	}
	public void setJniGlobalReference(Integer jniGlobalReference)
	{
		this.jniGlobalReference = jniGlobalReference;
	}
	public List<ThreadInfo> getThreadList()
	{
		return threadList;
	}
	public void setThreadList(List<ThreadInfo> threadList)
	{
		this.threadList = threadList;
	}
	private 	Date 						createDate;
	private 	String 					dumpDescription;
	private 	Integer					jniGlobalReference;
	private 	List<ThreadInfo> 	threadList;
}
