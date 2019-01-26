package com.rtaware.sacredthread.providers;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;

import com.rtaware.sacredthread.model.ThreadDump;
import com.rtaware.sacredthread.model.ThreadDumpList;

public class TBContentProvider implements ITreeContentProvider
{
	protected TreeViewer viewer;
	private static Object[] EMPTY_ARRAY	 	= new Object[0];
	
	@Override
	public Object[] getElements(Object inputElement)
	{
		return getChildren(inputElement);
	}
	
	@Override
	public Object[] getChildren(Object parentElement)
	{
		if(parentElement instanceof ThreadDumpList)
		{
			ThreadDumpList threadDumpList = (ThreadDumpList)parentElement;
			return threadDumpList.getThreadDumpList().toArray();
		}
		else if (parentElement instanceof ThreadDump)
		{
			ThreadDump threadDump = (ThreadDump)parentElement;
			return threadDump.getThreadList().toArray();
		}
		else return EMPTY_ARRAY;
	}
	
	@Override
	public Object getParent(Object element)
	{
		if(element instanceof ThreadDumpList)
		{
			return ( (ThreadDumpList) element);
		}
		else if(element instanceof ThreadDump) 
		{
			return ( (ThreadDump) element);
		}
		else  return null;
	}
	
	@Override
	public boolean hasChildren(Object element)
	{
		return getChildren(element).length > 0;
	}
}
