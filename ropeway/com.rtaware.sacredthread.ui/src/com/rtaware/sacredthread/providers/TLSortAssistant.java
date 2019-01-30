package com.rtaware.sacredthread.providers;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;

import com.rtaware.sacredthread.model.ThreadInfo;



public class TLSortAssistant extends ViewerComparator
{
	private int propertyIndex;
	private static final int DESCENDING = 1;
	private int direction = DESCENDING;

	public TLSortAssistant()
	{
		this.propertyIndex = 0;
		direction = DESCENDING;
	}

	public int getDirection()
	{
		return direction == 1 ? SWT.DOWN : SWT.UP;
	}

	public void setColumn(int column)
	{
		if (column == this.propertyIndex)
		{
			direction = 1 - direction;
		}
		else
		{
			this.propertyIndex = column;
			direction = DESCENDING;
		}
	}

	@Override
	public int compare(Viewer viewer, Object e1, Object e2)
	{
		ThreadInfo threadInfo_1 = (ThreadInfo) e1;
		ThreadInfo threadInfo_2 = (ThreadInfo) e2;
		int rc = 0;
		switch (propertyIndex)
		{
		case 0:
			rc = threadInfo_1.getThreadID().compareTo(threadInfo_2.getThreadID());
			break;
		case 1:
			rc =threadInfo_1.getThreadName().compareTo(threadInfo_2.getThreadName());
			break;
		case 2:
			rc = Integer.valueOf(threadInfo_1.getThreadNumber()).compareTo(Integer.valueOf(threadInfo_2.getThreadNumber()));
			break;
		case 3:
			rc =threadInfo_1.getThreadStatus().compareTo(threadInfo_2.getThreadStatus());
			break;
		case	4:				
			rc =threadInfo_1.getThreadPriority().compareTo(threadInfo_2.getThreadPriority());
			break;
		case	5:				
			rc =threadInfo_1.getThreadOSPriority().compareTo(threadInfo_2.getThreadOSPriority());
			break;
		case	6:				
			rc =threadInfo_1.getThreadNativeID().compareTo(threadInfo_2.getThreadNativeID());
			break;
		case	7:			
			rc =threadInfo_1.getThreadIsDaemon().compareTo(threadInfo_2.getThreadIsDaemon());
			break;
		default:
			rc = 0;
		}
		if (direction == DESCENDING)
		{
			rc = -rc;
		}
		return rc;
	}

}