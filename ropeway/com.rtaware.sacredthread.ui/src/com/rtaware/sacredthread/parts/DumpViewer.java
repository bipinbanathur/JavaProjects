
package com.rtaware.sacredthread.parts;

import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;

import com.rtaware.sacredthread.model.ThreadDump;
import com.rtaware.sacredthread.providers.TLLabelProvider;
import com.rtaware.sacredthread.providers.TLModifier;
import com.rtaware.sacredthread.providers.TLSortAssistant;

public class DumpViewer
{
	private Text createDate;
	private Text tdDescription;
	private Text nativeReference;
	private Table table;
	private ThreadDump threadDump;
	private String[] tableColumns = new String[] { "ID", "Name", "Number", "Status", "Priority", "OS Priority","Native", "Is Daemon", "Stack" };
	private TableViewer tableViewer;
	private TLSortAssistant sortAssistant;

	@Inject
	EModelService modelService;
	@Inject
	MApplication app;
	@Inject
	EPartService partService;

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
		/* TD Metadata */
		/*-------------------------------------------------------*/
		Group tdGroup = new Group(composite, SWT.NONE);
		GridData gd_tdGroup = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_tdGroup.heightHint = 150;
		gd_tdGroup.widthHint = 500;

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
		/* Thread List */
		/*-------------------------------------------------------*/
		Group tsGroup = new Group(composite, SWT.NONE);
		FillLayout fl_tsGroup = new FillLayout();
		fl_tsGroup.spacing = 2;
		fl_tsGroup.marginHeight = 2;
		fl_tsGroup.marginWidth = 2;
		tsGroup.setLayout(fl_tsGroup);

		GridData gd_tsGroup = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_tsGroup.widthHint = 1000;
		gd_tsGroup.heightHint = 500;
		tsGroup.setLayoutData(gd_tsGroup);
		tsGroup.setText("Thread List");
		tableViewer = new TableViewer(tsGroup,
				SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		table = tableViewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		AtomicInteger columnIndex = new AtomicInteger();
		for (String tableColumn : tableColumns)
		{
			TableColumn tc = new TableColumn(table, SWT.LEFT);
			tc.setWidth(100);
			tc.setText(tableColumn);
			tc.addSelectionListener(getSelectionAdapter(tc, (Integer) columnIndex.getAndIncrement()));
		}

		tableViewer.setContentProvider(new TLContentProvider());
		tableViewer.setLabelProvider(new TLLabelProvider());
		if (null != threadDump)
		{
			tableViewer.setInput(threadDump.getThreadList());
		}
		tableViewer.getTable().setLayoutData(new GridData(GridData.FILL_BOTH));

		CellEditor[] cellEditor = new CellEditor[tableColumns.length];
		TextCellEditor stackEditor = new TextCellEditor(tableViewer.getTable());

		Listener listener = (Event e) ->
		{
			String callStack = ((Text) e.widget).getText();
			MUIElement window = modelService.find("com.rtaware.sacredthread.ui.callstack", app);
			window.setToBeRendered(true);

			MPart mPart = partService.findPart("com.rtaware.sacredthread.ui.part.callstack");
			partService.activate(mPart, true);

			StackViewer cStack = (StackViewer) mPart.getObject();
			cStack.setCallStack(callStack);
		};
		stackEditor.getControl().addListener(SWT.MouseEnter, listener);
		cellEditor[8] = stackEditor;
		tableViewer.setColumnProperties(tableColumns);
		tableViewer.setCellEditors(cellEditor);
		tableViewer.setCellModifier(new TLModifier(tableViewer));
		sortAssistant = new TLSortAssistant();
		tableViewer.setComparator(sortAssistant);
	}

	private SelectionAdapter getSelectionAdapter(final TableColumn column, final int index)
	{
		SelectionAdapter selectionAdapter = new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{
				sortAssistant.setColumn(index);
				int dir = sortAssistant.getDirection();
				tableViewer.getTable().setSortDirection(dir);
				tableViewer.refresh();
			}
		};
		return selectionAdapter;
	}

	public class TLContentProvider implements IStructuredContentProvider
	{
		@Override
		public Object[] getElements(Object inputElement)
		{
			if (threadDump != null)
				return threadDump.getThreadList().toArray();
			else
				return null;
		}
	}

}