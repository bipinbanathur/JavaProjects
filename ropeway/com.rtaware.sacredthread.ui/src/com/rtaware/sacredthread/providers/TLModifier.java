package com.rtaware.sacredthread.providers;

import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.Viewer;

import com.rtaware.sacredthread.model.ThreadInfo;

public class TLModifier implements ICellModifier
{
	private Viewer viewer;

	public TLModifier(Viewer viewer)
	{
		this.viewer = viewer;
	}

	public boolean canModify(Object element, String property)
	{
		return true;
	}

	@Override
	public Object getValue(Object element, String property)
	{
		return ((ThreadInfo) element).getThreadStack().toString();
	}

	@Override
	public void modify(Object element, String property, Object value)
	{
		viewer.refresh();
	}
}
