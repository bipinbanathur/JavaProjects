package com.rtaware.sacredthread.model;

import java.util.ArrayList;
import java.util.List;

public class ThreadDumpList
{
	private List<ThreadDump> threadDumpList = new ArrayList<>();

	public List<ThreadDump> getThreadDumpList()
	{
		return threadDumpList;
	}

	public void setThreadDumpList(List<ThreadDump> threadDumpList)
	{
		this.threadDumpList = threadDumpList;
	}
	
	public void addThreadDump(ThreadDump threadDump)
	{
		if(null != threadDumpList)
		{
			threadDumpList.add(threadDump);
		}
	}
}
