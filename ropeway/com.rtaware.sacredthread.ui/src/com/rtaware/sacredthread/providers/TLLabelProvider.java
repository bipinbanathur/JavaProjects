package com.rtaware.sacredthread.providers;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;

import com.rtaware.sacredthread.model.ThreadInfo;

public class TLLabelProvider implements ITableLabelProvider 
{

	@Override
	public void addListener(ILabelProviderListener listener)
	{
		
	}

	@Override
	public void dispose()
	{
		
	}

	@Override
	public boolean isLabelProperty(Object element, String property)
	{
		return false;
	}

	@Override
	public void removeListener(ILabelProviderListener listener)
	{
		
	}

	@Override
	public Image getColumnImage(Object element, int columnIndex)
	{
		return null;
	}

	@Override
	public String getColumnText(Object element, int columnIndex)
	{
		if(element instanceof ThreadInfo)
		{
			ThreadInfo threadInfo = (ThreadInfo) element;			
			switch (columnIndex) 
			{
				case	0:	return threadInfo.getThreadID();
				case	1:	return threadInfo.getThreadName();
				case	2:	return threadInfo.getThreadNumber();
				case	3:	return threadInfo.getThreadStatus().toString();
				case	4:	return threadInfo.getThreadPriority().toString();
				case	5:	return threadInfo.getThreadOSPriority().toString();
				case	6:	return threadInfo.getThreadNativeID();
				case	7:	return threadInfo.getThreadIsDaemon().toString();
				case	8:	return  threadInfo.getThreadStack().toString();
			}
		}
		return null;
	}

}
