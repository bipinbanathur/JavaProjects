
package com.rtaware.sacredthread.parts;

import javax.inject.Inject;
import javax.annotation.PostConstruct;

import org.eclipse.swt.widgets.Composite;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Text;


import com.rtaware.sacredthread.model.ThreadDump;
import com.rtaware.sacredthread.providers.ThreadListLabelProvider;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
public class DumpViewer
{
	private Text 		createDate;
	private Text 		tdDescription;
	private Text 		nativeReference;
	private Table 		table;
	private ThreadDump 	threadDump;
	private String[] 	tableColumns = new String[] {"ID","Name","Number","Status","Priority","OS Priority","Native","Is Daemon","Stack"};
	private TableViewer tableViewer;
	
	public ThreadDump getThreadDump()
	{
		return threadDump;
	}

	public void setThreadDump(ThreadDump threadDump)
	{
		this.threadDump = threadDump;		
		createDate.setText("");
		tdDescription.setText("");
		nativeReference.setText("");
		table.removeAll();
		
		createDate.setText(threadDump.getCreateDate().toString());
		tdDescription.setText(threadDump.getDumpDescription());
		nativeReference.setText(threadDump.getJniGlobalReference().toString());
		tableViewer.setInput(threadDump.getThreadList());		
		tableViewer.refresh();
	}

	
	@Inject
	public DumpViewer()
	{

	}

	@PostConstruct
	public void postConstruct(Composite parent)
	{
		
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(1, false));
		
			
		/*-------------------------------------------------------*/
		/* TD Metadata 											 */
		/*-------------------------------------------------------*/
		Group tdGroup 				= new Group(composite, SWT.NONE);		
		GridData gd_tdGroup 		= new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_tdGroup.heightHint 		= 150;
		gd_tdGroup.widthHint 		= 500;
		
		tdGroup.setLayout(new GridLayout(4, false));
		tdGroup.setLayoutData(gd_tdGroup);
		
		tdGroup.setText("Thread Dump");
		new Label(tdGroup, SWT.NONE);
		
		Label lblCreatedDate = new Label(tdGroup, SWT.NONE);
		lblCreatedDate.setText("Created Date");
		new Label(tdGroup, SWT.NONE);
		
		createDate = new Text(tdGroup, SWT.BORDER);
		createDate.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(tdGroup, SWT.NONE);
		
		Label lblDescription = new Label(tdGroup, SWT.NONE);
		lblDescription.setText("Description");
		new Label(tdGroup, SWT.NONE);
		
		tdDescription = new Text(tdGroup, SWT.BORDER);
		tdDescription.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(tdGroup, SWT.NONE);
		
		Label lblNativeReferences = new Label(tdGroup, SWT.NONE);
		lblNativeReferences.setText("Native References");
		new Label(tdGroup, SWT.NONE);
		
		nativeReference = new Text(tdGroup, SWT.BORDER);
		nativeReference.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		

		/*-------------------------------------------------------*/
		/* Thread List 											 */
		/*-------------------------------------------------------*/
		Group tsGroup 			= new Group(composite, SWT.NONE);
		FillLayout fl_tsGroup 	= new FillLayout();
		fl_tsGroup.spacing 		= 2;
		fl_tsGroup.marginHeight = 2;
		fl_tsGroup.marginWidth 	= 2;
		tsGroup.setLayout(fl_tsGroup);
		
		GridData gd_tsGroup = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_tsGroup.widthHint 	= 1000;
		gd_tsGroup.heightHint 	= 500;
		tsGroup.setLayoutData(gd_tsGroup);
		tsGroup.setText("Thread List");
		tableViewer = new TableViewer(tsGroup,SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);		
		table = tableViewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		for(String tableColumn:tableColumns)
		{
			TableColumn tc = new TableColumn(table, SWT.LEFT);
			tc.setWidth(100);
			tc.setText(tableColumn);
		}

		tableViewer.setContentProvider(new ThreadListContentProvider());
		tableViewer.setLabelProvider(new ThreadListLabelProvider());
		if(null != threadDump)
		{
			tableViewer.setInput(threadDump.getThreadList());		
		}
		tableViewer.getTable().setLayoutData(new GridData(GridData.FILL_BOTH));	
		
	}
	
	public class ThreadListContentProvider implements IStructuredContentProvider 
	{

		@Override
		public Object[] getElements(Object inputElement)
		{
			if(threadDump!= null) 	return threadDump.getThreadList().toArray();
			else return null;
		}
	}
}