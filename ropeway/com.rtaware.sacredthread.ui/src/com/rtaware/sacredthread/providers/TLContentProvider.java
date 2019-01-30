package com.rtaware.sacredthread.providers;

import org.eclipse.jface.viewers.IStructuredContentProvider;

import com.rtaware.sacredthread.model.ThreadDump;

public class TLContentProvider implements IStructuredContentProvider 
{
	public TLContentProvider(ThreadDump threadDump)
	{
		this.threadDump= threadDump;
	}
	ThreadDump threadDump;
	@Override
	public Object[] getElements(Object inputElement)
	{
		if(threadDump!= null) 	return threadDump.getThreadList().toArray();
		else return null;
	}
}