package com.rtaware.bulkloader.handlers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

import com.rtaware.bulkloader.model.FlowVariable;
import com.rtaware.bulkloader.parts.WorkArea;
import com.rtaware.datalib.entities.Excel;

public class OpenHandler
{

	@Inject
	EPartService partService;
	
	@Execute
	public void execute(Shell shell)
	{
		FileDialog dialog 	= new FileDialog(shell);
		String[] filterExt 	= { "*.xls", "*.ods" };
		dialog.setFilterExtensions(filterExt);
		String selectedFile = dialog.open();
		String sheetName = "Variables";
		List<FlowVariable> variableList = new ArrayList<>();
		try (Excel excel = new Excel(selectedFile))
		{
			Integer rowCount = excel.getRowCount(sheetName);
			IntStream.range(1, rowCount).forEach(rowIndex ->
			{
				FlowVariable flowVariable 			= new FlowVariable();
				String 			flowID 					= excel.getValue(sheetName, rowIndex, "Flow ID");
				String 			stepID 					= excel.getValue(sheetName, rowIndex, "Step ID");
				String 			variableName 		= excel.getValue(sheetName, rowIndex, "Variable Name");
				String 			variableType 			= excel.getValue(sheetName, rowIndex, "Variable Type");
				String 			assingWrom 			= excel.getValue(sheetName, rowIndex, "From");
				String 			whenToAssign 		= excel.getValue(sheetName, rowIndex, "When");
				String 			expressionValue 	= excel.getValue(sheetName, rowIndex, "Expression");
				String 			dataFormat 			= excel.getValue(sheetName, rowIndex, "Format");
				String 			connectionName 	= excel.getValue(sheetName, rowIndex, "Connection Name");

				flowVariable.setAssingWrom(assingWrom);
				flowVariable.setConnectionName(connectionName);
				flowVariable.setDataFormat(dataFormat);
				flowVariable.setExpressionValue(expressionValue);
				flowVariable.setFlowID(flowID);
				flowVariable.setStepID(stepID);
				flowVariable.setVariableName(variableName);
				flowVariable.setVariableType(variableType);
				flowVariable.setWhenToAssign(whenToAssign);
				variableList.add(flowVariable);
				System.out.println(flowVariable);
			});

			MPart mPart = partService.findPart("com.rtaware.bulkloader.part.workarea");
			partService.activate(mPart, true);
			
			WorkArea workArea = (WorkArea) mPart.getObject();
			workArea.setVariableList(variableList);
		}
	}
}
