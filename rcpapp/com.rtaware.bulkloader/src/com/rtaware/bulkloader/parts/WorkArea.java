 
package com.rtaware.bulkloader.parts;

import javax.inject.Inject;

import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import com.rtaware.bulkloader.model.SessionVariable;

public class WorkArea 
{
	private TableViewer tableViewer;
	@Inject
	public WorkArea() 
	{
		
	}
	
	@PostConstruct
	public void createComposite(Composite parent) 
	{
		showSessionVariables( parent);
	}
	public void showSessionVariables(Composite parent)
	{
		tableViewer = new TableViewer(parent,SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		final Table table = tableViewer.getTable();
		
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		createColumns(parent,tableViewer);
		tableViewer.setContentProvider(ArrayContentProvider.getInstance());
		tableViewer.setInput(createInitialDataModel());
		tableViewer.getTable().setLayoutData(new GridData(GridData.FILL_BOTH));
		
//		CellEditor[] editors = new CellEditor[2]; 
//		editors[0] = new TextCellEditor(table); 
//		editors[1] = new TextCellEditor(table);
//
//		tableViewer.setCellEditors(editors); 
//		tableViewer.setCellModifier(new TableCellModifier()); 
		
	}
	
	private List<SessionVariable> createInitialDataModel() 
	{
		java.util.List<SessionVariable> variableList = new java.util.ArrayList<>();
		variableList.add( new SessionVariable("myInt","100", "Integer","Sample Integer Variable"));
		variableList.add( new SessionVariable("myFloat","10.902", "Float","Sample Integer Variable"));
		return variableList;
	}
	
	private void createColumns(final Composite parent, final TableViewer viewer) 
	{
		String[] 	titles = { "Variable Name", "Variable Value", "Variable Type", "Variable Comment" };
        int[] 		bounds = { 100, 100, 100, 100 };


        TableViewerColumn col = createTableViewerColumn(titles[0], bounds[0], 0,viewer);
        col.setLabelProvider(new ColumnLabelProvider()
        {
            @Override
            public String getText(Object element) 
            {
            	SessionVariable p = (SessionVariable) element;
                return p.getVariableName();
            }
        });


        col = createTableViewerColumn(titles[1], bounds[1], 1,viewer);
        col.setLabelProvider(new ColumnLabelProvider() 
        {
            @Override
            public String getText(Object element)
            {
            	SessionVariable p = (SessionVariable) element;
                return p.getVariableValue();
            }
        });

        col = createTableViewerColumn(titles[2], bounds[2], 2,viewer);
        col.setLabelProvider(new ColumnLabelProvider() 
        {
            @Override
            public String getText(Object element) 
            {
            	SessionVariable p = (SessionVariable) element;
                return p.getVariableType();
            }
        });

        col = createTableViewerColumn(titles[3], bounds[2], 2,viewer);
        col.setLabelProvider(new ColumnLabelProvider() 
        {
            @Override
            public String getText(Object element) 
            {
            	SessionVariable p = (SessionVariable) element;
                return p.getVariableComment();
            }
        });


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
	@PostConstruct
	public void postConstruct(Composite parent) 
	{
		
	}
	

	
}