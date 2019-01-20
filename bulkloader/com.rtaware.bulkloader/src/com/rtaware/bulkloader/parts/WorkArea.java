 
package com.rtaware.bulkloader.parts;

import javax.inject.Inject;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;


import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import com.rtaware.bulkloader.model.FlowVariable;



public class WorkArea 
{
	private  TableViewer 				tableViewer;
	private String[] 					titles 			= { "Flow ID","Step ID","Variable Name", "Variable Type", "From", "When","Expression","Format","Connection Name" };
	private List<FlowVariable> 	variableList 	= new ArrayList<>();

	@Inject
	public WorkArea() 
	{
		System.out.println("Injected >>>");
	}
	
	public void setVariableList(List<FlowVariable> variableList) 
	{
		this.variableList = variableList;
		tableViewer.setInput(variableList);
		tableViewer.refresh();
		System.out.println("Injected >>>");
	}

	@PostConstruct
	public void createComposite(Composite parent) 
	{
		showSessionVariables( parent);
	}
	
	@PostConstruct
	public void postConstruct(Composite parent) 
	{
		
	}
		
	public void showSessionVariables(Composite parent)
	{
		tableViewer = new TableViewer(parent,SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		Table table = tableViewer.getTable();
		
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
        int index =0;
        for(String title:titles)
        {
        	createTableViewerColumn(title, 100, index,tableViewer);
        	index++;
        }
		
		tableViewer.setContentProvider(new VariableContentProvider());
		tableViewer.setLabelProvider(new VariableLabelProvider());
		tableViewer.setInput(variableList);
		tableViewer.getTable().setLayoutData(new GridData(GridData.FILL_BOTH));		
	}

	private TableViewerColumn createTableViewerColumn(String title, int bound, final int colNumber,TableViewer viewer) 
	{
        final TableViewerColumn viewerColumn = new TableViewerColumn(viewer, SWT.NONE);
        final TableColumn column = viewerColumn.getColumn();
        column.setText(title);
        column.setWidth(bound);
        column.setResizable(true);
        column.setMoveable(true);

        return viewerColumn;
    }
	
	class VariableLabelProvider implements ITableLabelProvider 
	{

		@Override
		public void addListener(ILabelProviderListener listener)
		{

		}

		@Override
		public void dispose()
		{

		}

		@Override
		public boolean isLabelProperty(Object element, String property)
		{
			return false;
		}

		@Override
		public void removeListener(ILabelProviderListener listener)
		{
		}

		@Override
		public Image getColumnImage(Object element, int columnIndex)
		{
			return null;
		}

		@Override
		public String getColumnText(Object element, int columnIndex)
		{

			FlowVariable flowVariable = (FlowVariable) element;
			switch(columnIndex)
			{
			case	0:	return flowVariable.getFlowID();
			case	1:	return flowVariable.getStepID();
			case	2:	return flowVariable.getVariableName();
			case	3:	return flowVariable.getVariableType();
			case	4:	return flowVariable.getAssingWrom();
			case	5:	return flowVariable.getWhenToAssign();
			case	6:	return flowVariable.getExpressionValue();
			case	7:	return flowVariable.getDataFormat();
			case	8:	return flowVariable.getConnectionName();
			}
			return null;
		}		
	}
	
	class VariableContentProvider implements IStructuredContentProvider 
	{
		@Override
		public Object[] getElements(Object inputElement)
		{
			return variableList.toArray();
		}		
	}
	
}