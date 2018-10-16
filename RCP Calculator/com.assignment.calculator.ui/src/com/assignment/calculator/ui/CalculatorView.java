package com.assignment.calculator.ui;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.wb.swt.SWTResourceManager;

public class CalculatorView
{
	private Text 		operandOne;
	private Text 		operandTwo;
	private Text 		resultText;
	private Button 		btnAdd;
	private Button 		btnSubtr;
	private String 		addOperation = "Add";
	private String 		subOperation = "Subtract";

	
	public CalculatorView()
	{
	}

	/**
	 * Create contents of the view part.
	 */
	@PostConstruct
	public void createControls(Composite parent)
	{
		int widthHint  = 100;
		int heightHint = 50;
		parent.setFont(SWTResourceManager.getFont("Segoe UI", 8, SWT.NORMAL));
		parent.setLayout(new GridLayout(4, false));

		
		operandOne 			= new Text(parent, SWT.BORDER);
		GridData grid_1 	= new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		grid_1.widthHint 	= widthHint;
		grid_1.heightHint 	= heightHint;
		operandOne.setLayoutData(grid_1);
		
		operandTwo 			= new Text(parent, SWT.BORDER);
		GridData grid_2 	= new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		grid_2.widthHint 	= widthHint;
		grid_2.heightHint 	= heightHint;
		operandTwo.setLayoutData(grid_2);
		
		Group group 		= new Group(parent, SWT.NONE);
		GridData grid3 		= new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		grid3.widthHint 	= widthHint;
		grid3.heightHint 	= heightHint;
		group.setLayoutData(grid3);
		

		
		btnAdd = new Button(group, SWT.NONE);
		btnAdd.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{
				calculateResult(addOperation);
			}
		});
		btnAdd.setBounds(15, 10, 75, 20);
		btnAdd.setText(addOperation);
		
		btnSubtr = new Button(group, SWT.NONE);
		btnSubtr.addSelectionListener(new SelectionAdapter() 
		{
			@Override
			public void widgetSelected(SelectionEvent e) 
			{
				calculateResult(subOperation);
			}
		});
		btnSubtr.setBounds(15, 40, 75, 20);
		btnSubtr.setText(subOperation);
		
		resultText 			= new Text(parent, SWT.BORDER);
		GridData grid4 		= new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		grid4.widthHint 	= widthHint;
		grid4.heightHint 	= heightHint;
		resultText.setLayoutData(grid4);
		
		
		Label lblOperand_1 = new Label(parent, SWT.NONE);
		lblOperand_1.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1));
		lblOperand_1.setText("Operand 1");
		
		Label lblOperand_2 = new Label(parent, SWT.NONE);
		lblOperand_2.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1));
		lblOperand_2.setText("Operand 2");
		
		Label lblOperation = new Label(parent, SWT.NONE);
		lblOperation.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1));
		lblOperation.setText("Operation");
		
		Label lblResult = new Label(parent, SWT.NONE);
		lblResult.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1));
		lblResult.setText("Result");

	}

	@PreDestroy
	public void dispose()
	{
	}

	@Focus
	public void setFocus()
	{
	}
	
	private void calculateResult(String calculationOperation)
	{
		Integer operOne = null;
		Integer operTwo = null;
		
		if( isInteger(operandOne.getText().trim()) || isInteger(operandOne.getText().trim()))
		{
			operOne = Integer.parseInt(operandOne.getText().trim()); 
			operTwo = Integer.parseInt(operandTwo.getText().trim()); 			
		}
		else
		{
			MessageDialog.openError(Display.getDefault().getActiveShell(), "Error", "Invalid Operands");
			return;
		}	
		
		if(isValid(calculationOperation))
		{
			if(addOperation.equals(calculationOperation))
			{
				resultText.setText("");
				resultText.setText(new Integer(operOne+operTwo).toString());
			}
			else if (subOperation.equals(calculationOperation))
			{
				resultText.setText("");
				resultText.setText(new Integer(operOne-operTwo).toString());
			}
			else
			{
				MessageDialog.openError(Display.getDefault().getActiveShell(), "Error", "Undefined Operation");
				return;
			}			
		}
		else
		{
			MessageDialog.openError(Display.getDefault().getActiveShell(), "Error", "Invalid Operation");
			return;
		}
	
	}
	
    public static boolean isValid(String stringToCheck) 
    {
        if(stringToCheck != null && !stringToCheck.isEmpty()) return true; return false;
    }
    
    public static boolean isInteger(String integerToCheck) 
    {
        if(integerToCheck != null && !integerToCheck.isEmpty() )
        {
        	try {Integer.parseInt(integerToCheck);}catch(Exception e){return false;}
        	return true; 
        }        
        return false;
    }
}
