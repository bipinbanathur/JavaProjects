package com.rtaware.bulkloader.model;


import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.graphics.Image;

import com.rtaware.personinfo.ContactInfo;
import com.rtaware.personinfo.PersonInfo;

public class MainTreeeLabelProvider  extends LabelProvider 
{
	protected TreeViewer viewer;

	public Image getImage(Object element) 
	{
		return null;
	}


	public String getText(Object element)
	{
		if (element instanceof PersonInfo)
		{
			return ((PersonInfo) element).getPersonFirstName();
		}
		else if (element instanceof ContactInfo)
		{
			return ((ContactInfo) element).getContactType().toString();
		}
		else			
		{
			throw unknownElement(element);
		}
	}

	
	
	public void dispose() 
	{

	}

	
	
	protected RuntimeException unknownElement(Object element) 
	{
		return new RuntimeException("Unknown type of element in tree of type " + element.getClass().getName());
	}


}
