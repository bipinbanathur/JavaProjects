package com.rtaware.bulkloader.model;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;

import com.rtaware.personinfo.ContactInfo;
import com.rtaware.personinfo.PersonDetails;
import com.rtaware.personinfo.PersonInfo;

public class MainTreeContentProvider 	 implements ITreeContentProvider
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
		if(parentElement instanceof PersonDetails)
		{
			PersonDetails personDetails = (PersonDetails)parentElement;
			return personDetails.getPersonDetails().toArray();
		}
		else if (parentElement instanceof PersonInfo)
		{
			PersonInfo personInfo = (PersonInfo)parentElement;
			return personInfo.getContactDetails().toArray();
		}
		else return EMPTY_ARRAY;
	}
	
	@Override
	public Object getParent(Object element)
	{
		if(element instanceof PersonDetails)
		{
			return ( (PersonDetails) element);
		}
		else if(element instanceof PersonInfo) 
		{
			return ( (PersonInfo) element);
		}
		else if(element instanceof ContactInfo) 
		{
			return ( (ContactInfo) element);
		}
		else  return null;
	}
	
	@Override
	public boolean hasChildren(Object element)
	{
		return getChildren(element).length > 0;
	}
}
