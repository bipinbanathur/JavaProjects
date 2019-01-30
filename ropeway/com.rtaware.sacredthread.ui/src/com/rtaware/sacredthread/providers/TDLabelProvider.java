package com.rtaware.sacredthread.providers;


import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.graphics.Image;
import org.osgi.framework.Bundle;

import com.rtaware.sacredthread.model.ThreadDump;
import com.rtaware.sacredthread.model.ThreadInfo;


public class TDLabelProvider extends LabelProvider 
{

	  protected TreeViewer viewer;
	  private Image td;
	  private Image th;
	  
	  
	public TDLabelProvider()
	{
			Bundle bundle = Platform.getBundle("com.rtaware.sacredthread.ui");
			td  = ImageDescriptor.createFromURL( bundle.getEntry("icons/tdump.png")).createImage();
			th  = ImageDescriptor.createFromURL( bundle.getEntry("icons/thread.gif")).createImage();
	}
	  
	   public Image getImage(Object element) 
		{
			if (element instanceof ThreadDump)
				return td;
			else return th;
		}
		
		public String getText(Object element)
		{
			if (element instanceof ThreadDump)
			{
				return ((ThreadDump) element).getCreateDate().toString();
			}
			else if (element instanceof ThreadInfo)
			{
				return ((ThreadInfo) element).getThreadName();
			}
			else			
			{
				throw unknownElement(element);
			}
		}
		
		protected RuntimeException unknownElement(Object element) 
		{
			return new RuntimeException("Unknown type of element in tree of type " + element.getClass().getName());
		}
		
		public void dispose() 
		{

		}

}
