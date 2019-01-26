package com.rtaware.sacredthread.providers;


import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.graphics.Image;

import com.rtaware.sacredthread.model.ThreadDump;
import com.rtaware.sacredthread.model.ThreadInfo;

public class TDLabelProvider extends LabelProvider 
{

	  protected TreeViewer viewer;
	  private Image td;
	  private Image th;
	  
	  
	public TDLabelProvider()
	{
		try
		{
			td = new Image(null, new FileInputStream("E:\\Programs\\PhotonRCP\\com.rtaware.sacredthread.ui\\icons\\tdump.png"));
			th = new Image(null, new FileInputStream("E:\\Programs\\PhotonRCP\\com.rtaware.sacredthread.ui\\icons\\thread.gif"));
		}
		catch (FileNotFoundException e)
		{
			// Swallow it; we'll do without images
		}
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
