
package com.rtaware.sacredthread.parts;

import javax.inject.Inject;
import javax.annotation.PostConstruct;

import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import com.rtaware.sacredthread.model.ThreadDump;
import com.rtaware.sacredthread.model.ThreadDumpList;
import com.rtaware.sacredthread.providers.TBContentProvider;
import com.rtaware.sacredthread.providers.TDLabelProvider;

public class DumpBrowser
{
	
	@Inject
	EPartService 							partService;
	private TreeViewer 				treeViewer;
	private ThreadDumpList 		threadDumpList 		= new ThreadDumpList();

	public ThreadDumpList getThreadDumpList()
	{
		return threadDumpList;
	}

	public void setThreadDumpList(ThreadDumpList threadDumpList)
	{
		this.threadDumpList = threadDumpList;
		treeViewer.setInput( threadDumpList);
		treeViewer.refresh();
	}

	@Inject
	public DumpBrowser()
	{

	}

	@PostConstruct
	public void postConstruct(Composite parent)
	{
		GridLayout layout 			= new GridLayout();
		layout.numColumns 		= 1;
		layout.verticalSpacing 	= 1;
		layout.marginWidth 		= 0;
		layout.marginHeight 		= 1;
		
		GridData layoutData 								= new GridData();
		layoutData.grabExcessHorizontalSpace 	= true;
		layoutData.grabExcessVerticalSpace	 	= true;
		layoutData.horizontalAlignment 				= GridData.FILL;
		layoutData.verticalAlignment 					= GridData.FILL;
		
		parent.setLayout(layout);
		
		treeViewer = new TreeViewer(parent);
		treeViewer.getControl().setLayoutData(layoutData);
		
		treeViewer.setContentProvider	(new TBContentProvider ());
		treeViewer.setLabelProvider		(new TDLabelProvider	());

		treeViewer.addDoubleClickListener(new IDoubleClickListener()
		{
			@Override
			public void doubleClick(DoubleClickEvent event)
			{
				IStructuredSelection thisSelection = (IStructuredSelection) event.getSelection();
				Object selectedNode = thisSelection.getFirstElement();
				if (selectedNode instanceof ThreadDump)
				{
					ThreadDump threadDumpt = (ThreadDump) selectedNode;
					
					MPart mPart2 = partService.findPart("com.rtaware.sacredthread.ui.part.dumpanalyzer");
					partService.activate(mPart2, true);

					DumpAnalyzer dumpAnalyzer = (DumpAnalyzer) mPart2.getObject();
					dumpAnalyzer.setThreadDump(threadDumpt);
					
					MPart mPart = partService.findPart("com.rtaware.sacredthread.ui.part.dumpviewer");
					partService.activate(mPart, true);

					DumpViewer dumpViewer = (DumpViewer) mPart.getObject();
					dumpViewer.setThreadDump(threadDumpt);

				}
			}
		});
	}
}