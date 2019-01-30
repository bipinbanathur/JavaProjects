
package com.rtaware.sacredthread.parts;

import javax.inject.Inject;

import java.util.List;

import javax.annotation.PostConstruct;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import com.rtaware.sacredthread.model.CallStack;
import com.rtaware.sacredthread.model.ResourceInfo;
import com.rtaware.sacredthread.model.StackTrace;
import com.rtaware.sacredthread.model.ThreadStatus;

public class StackViewer
{
	private String 						stateIdentifier 	= 	"java.lang.Thread.State: ";	
	private StackTrace 					stackTrace 			= 	new StackTrace();
	private List<ResourceInfo> 			resourceDetails 	=	stackTrace.getResourceDetails();
	private List<CallStack> 			stackList 			= 	stackTrace.getStackList();
	private String 						callStack 			=	"";
	private Text 						stackStatus;
	private TableViewer 				traceViewer;
	private TableViewer 				resourceViewer;
	
	String packageName 			=	"";
	String className 			=	"";
	String lineNumber			=	"";
	String resourceStatus 		=	"";
	String resourceID 			=	"";
	String resourceType 		=	"";
	private String[] 					resourceColumns 	= new String[]	{"Resource ID","Resource Status","Resource Type"};
	private String[] 					stackColumns 		= new String[]  {"Package Name","Class Name","Line Number"}; 
	private Table traceTable;
	private Table resourceTable;
	private Text txtOnEntity;
	
	public String getCallStack()
	{
		return callStack;
	}

	public void setCallStack(String callStack)
	{
		this.callStack = callStack;		
		String[] lines = callStack.split(System.getProperty("line.separator"));
		for(String line:lines)
		{
			processStackTrace(line);
		}
		stackTrace.setResourceDetails(resourceDetails);
		stackTrace.setStackList(stackList);
		stackStatus.setText("");
		txtOnEntity.setText("");
		resourceTable.removeAll();
		traceTable.removeAll();
		
		if(null != stackTrace)
		{
			stackStatus.setText(stackTrace.getThreadStatus().toString());
			txtOnEntity.setText(stackTrace.getOnEntity());
			stackTrace.getResourceDetails().forEach(resource ->
			{
			    TableItem item = new TableItem(resourceTable, SWT.NONE);
			    item.setText(new String[] { resource.getResourceID(), resource.getResourceStatus (),resource.getResourceType()});
			});
			stackTrace.getStackList().forEach(stack ->
			{
			    TableItem item = new TableItem(traceTable, SWT.NONE);
			    item.setText(new String[] { stack.getPackageName(), stack.getClassName(),stack.getLineNumber()});
			});
		}
		
		
	}

	private void processStackTrace(String line)
	{
		String packageName 			=	"";
		String className 			=	"";
		String lineNumber			=	"";
		String resourceStatus 		=	"";
		String resourceID 			=	"";
		String resourceType 		=	"";
		String threadStatus			=	"";
		String onEntity				=	"";
		line 						= line.trim();
		
		if(line.startsWith(stateIdentifier))
		{
				threadStatus = line.substring( line.indexOf(stateIdentifier)+stateIdentifier.length()).trim();
				if(threadStatus.indexOf(" ") != -1) threadStatus = threadStatus.substring(0,threadStatus.indexOf(" ")).trim();
				System.out.println(threadStatus);
				switch(threadStatus)
				{
						case  "BLOCKED"			: stackTrace.setThreadStatus(ThreadStatus.BLOCKED); 			break;
						case  "RUNNING"			: stackTrace.setThreadStatus(ThreadStatus.RUNNING);				break;
						case  "WAITING"		 	: stackTrace.setThreadStatus(ThreadStatus.WAITING);				break;
						case  "RUNNABLE"		: stackTrace.setThreadStatus(ThreadStatus.RUNNABLE);			break;
						case  "OBJECT_WAIT"		: stackTrace.setThreadStatus(ThreadStatus.OBJECT_WAIT);			break;
						case  "TIMED_WAITING"	: stackTrace.setThreadStatus(ThreadStatus.TIMED_WAITING);		break;
						default  				: stackTrace.setThreadStatus(ThreadStatus.UNKNOWN);				break;
				};
				
				if (line.indexOf("(") != -1 && line.indexOf(")") != -1)
				{
					onEntity		= line.substring(line.indexOf("(") +1, line.indexOf(")")).trim();
				}
				stackTrace.setOnEntity(onEntity);
				onEntity ="";
				threadStatus="";
				
		}
		else if(	line.startsWith("at ") && line.indexOf("(")!= -1 &&  line.indexOf(")")!= -1)
		{
			CallStack stack 	= new CallStack();
			packageName 	= line.substring(3, line.indexOf("(") ).trim();
			className			= line.substring(line.indexOf("(") +1, line.indexOf(")")).trim();
			if(className.indexOf(":") != -1)
			{
				lineNumber 	= className.substring(className.indexOf(":")+1, className.length()).trim();
				className 	= className.substring(0,className.indexOf(":"));
			}					
			stack.setClassName(className);
			stack.setLineNumber(lineNumber);
			stack.setPackageName(packageName);
			stackList.add(stack);
			
			className 		=	"";
			lineNumber		=	"";
			packageName	=	"";
			stack 				= 	null;
		}
		else if(	line.startsWith("- ") && line.indexOf("(")!= -1 &&  line.indexOf(")")!= -1 && line.indexOf("<")!= -1 &&  line.indexOf(">") != -1)
		{
			ResourceInfo resource 	= new ResourceInfo();
			resourceStatus 		= line.substring(2, line.indexOf("<") ).trim();
			resourceID			= line.substring(line.indexOf("<") +1, line.indexOf(">")).trim();
			resourceType		= line.substring(line.indexOf("(") +1, line.indexOf(")")).trim();
			
			resource.setResourceID(resourceID);
			resource.setResourceStatus(resourceStatus);
			resource.setResourceType(resourceType);
			
			resourceDetails.add(resource);
			resourceStatus 		=	"";
			resourceID				=	"";
			resourceType			=	"";
			resource 				= 	null;
		}
	}
	
	@Inject	
	public StackViewer()
	{

	}

	@PostConstruct
	public void postConstruct(Composite parent)
	{
		GridLayout gl_parent 	= new GridLayout(1, false);
		gl_parent.marginRight 	= 5;
		gl_parent.marginLeft 	= 5;
		gl_parent.marginBottom 	= 5;
		parent.setLayout(gl_parent);
		/*---------------------------------------------------------------------------------------------------------------------------------------------*/
		/*---------------------------------------------------------------------------------------------------------------------------------------------*/
		Group stGroup 			= new Group(parent, SWT.NONE);		
		GridData stData 		= new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		stData.heightHint 		= 75;
		stData.widthHint 		= 350;
		
		stGroup.setLayout(new GridLayout(2, false));
		stGroup.setLayoutData(stData);
		stGroup.setText("Stack Trace Status");
		
		Label lblStackStatus 		= new Label(stGroup, SWT.NONE);
		GridData gd_lblStackStatus 	= new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_lblStackStatus.widthHint = 80;
		
		lblStackStatus.setLayoutData(gd_lblStackStatus);
		lblStackStatus.setText("Stack Status ");
		stackStatus = new Text(stGroup, SWT.BORDER);
		
		GridData gd_stackStatus = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_stackStatus.widthHint = 200;
		stackStatus.setLayoutData(gd_stackStatus);
		
		GridData gd_lblStackEntity 	= new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_lblStackStatus.widthHint = 80;
		
		Label lblonEntity = new Label(stGroup, SWT.NONE);
		lblonEntity.setText("Entity ");
		lblonEntity.setLayoutData(gd_lblStackEntity);
		
		txtOnEntity = new Text(stGroup, SWT.BORDER);		
		GridData gd_txtOnEntity = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_txtOnEntity.widthHint = 200;
		txtOnEntity.setLayoutData(gd_txtOnEntity);
		/*---------------------------------------------------------------------------------------------------------------------------------------------*/
		/*---------------------------------------------------------------------------------------------------------------------------------------------*/
		Group rsGroup 			= new Group(parent, SWT.NONE);	
		
		GridData rsData 		= new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		rsData.heightHint 		= 100;
		rsData.widthHint 		= 650;
		
		rsGroup.setLayout(new GridLayout(1, false));
		rsGroup.setLayoutData(rsData);
		rsGroup.setText("Resource Details");
		
		resourceViewer 	= new TableViewer(rsGroup,SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);		
		resourceTable = resourceViewer.getTable();
		
		GridData gd_resourceTable = new GridData(SWT.LEFT, SWT.TOP, true, true, 1, 1);
		gd_resourceTable.minimumWidth 	= 75;
		gd_resourceTable.minimumHeight 	= 50;
		resourceTable.setLayoutData(gd_resourceTable);
		resourceTable.setHeaderVisible(true);
		resourceTable.setLinesVisible(true);

		for(String tableColumn:resourceColumns)
		{
			TableColumn tc = new TableColumn(resourceTable, SWT.LEFT);
			if(tableColumn.equals("Resource ID"))tc.setWidth(150);
			else if(tableColumn.equals("Resource Status"))tc.setWidth(100);
			else tc.setWidth(350);
			
			tc.setText(tableColumn);
		}
		/*---------------------------------------------------------------------------------------------------------------------------------------------*/
		/*---------------------------------------------------------------------------------------------------------------------------------------------*/
		Group csGroup 			= new Group(parent, SWT.NONE);		
		GridData csData 		= new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		csData.heightHint 		= 210;
		csData.widthHint 		= 650;
		csGroup.setLayout(new GridLayout(1, false));
		csGroup.setLayoutData(csData);
		csGroup.setText("Call Stack");
		
		GridData gd_traceTable		= new GridData(SWT.LEFT, SWT.TOP, true, true, 1, 1);
		gd_traceTable.minimumWidth 	= 75;
		gd_traceTable.minimumHeight = 200;
		
		traceViewer 	= new TableViewer(csGroup,SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);		
		traceTable 		= traceViewer.getTable();

		traceTable.setLayoutData(gd_traceTable);
		traceTable.setHeaderVisible(true);
		traceTable.setLinesVisible(true);

		for(String tableColumn:stackColumns)
		{
			TableColumn tc = new TableColumn(traceTable, SWT.LEFT);
			if(tableColumn.equals("Line Number"))
			{
				tc.setWidth(100);
			}
			else if(tableColumn.equals("Package Name")) tc.setWidth(300);
			else tc.setWidth(200);
			tc.setText(tableColumn);
		}
		/*---------------------------------------------------------------------------------------------------------------------------------------------*/
		/*---------------------------------------------------------------------------------------------------------------------------------------------*/

	}

}