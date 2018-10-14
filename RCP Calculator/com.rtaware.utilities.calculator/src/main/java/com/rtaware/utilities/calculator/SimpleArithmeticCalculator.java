package com.rtaware.utilities.calculator;

import org.apache.log4j.Logger;

public class SimpleArithmeticCalculator implements ICalculator
{
	private int 					operandOne 			= 0;
	private int 					operandTwo 			= 0;
	private int 					operationResult   	= 0;	
	private OperationStatus			operationStatus     = OperationStatus.FAIL;
	private Operators				calculationOperator = null;				
	private String  				errorMessage 		= "";
	private Logger logger = Logger.getLogger(SimpleArithmeticCalculator.class.getName());
	public Integer getOperandOne()
	{
		return operandOne;
	}

	public void setOperandOne(Integer operandOne)
	{
		this.operandOne = operandOne;
	}

	public Integer getOperandTwo() 
	{
		return operandTwo;
	}

	public void setOperandTwo(Integer operandTwo) 
	{
		this.operandTwo = operandTwo;
	}

	public Integer getOperationResult()
	{
		return operationResult;
	}

	private void setOperationResult(Integer operationResult) 
	{
		this.operationResult = operationResult;
	}

	public OperationStatus getOperationStatus()
	{
		return operationStatus;
	}

	private void setOperationStatus(OperationStatus operationStatus) 
	{
		this.operationStatus = operationStatus;
	}

	public String getErrorMessage()
	{
		return errorMessage;
	}

	private void setErrorMessage(String errorMessage) 
	{
		this.errorMessage = errorMessage;
	}
	
	public Operators getCalculationOperator() {
		return calculationOperator;
	}

	public void setCalculationOperator(Operators calculationOperator)
	{
		this.calculationOperator = calculationOperator;
	}

	public void calcuateResult() 
	{
		//Long temporaryResult = new Long(0);
		long temporaryResult = 0;
		logger.info("***************************************************");
		logger.info("Operation "+this.getCalculationOperator());
		logger.info("Operand 1 "+this.getOperandOne());
		logger.info("Operand 2 "+this.getOperandTwo());

		
		if ( null == calculationOperator) //null == operandOne  || null == operandTwo ||
		{
			this.setOperationStatus(OperationStatus.FAIL);
			this.setErrorMessage("Invalid Operands or Operator");
			this.setOperationResult(0);
		}
		else
		{
			switch (calculationOperator) 
			{
		        case ADDITION : 		        	
		            try
		            {
		    			this.setOperationStatus(OperationStatus.PASS);
		    			this.setErrorMessage("Operation Succesful");
		    			this.setOperationResult( Math.addExact(operandOne, operandTwo));
		            }
		            catch (ArithmeticException ex)
		            {
		    			this.setOperationStatus(OperationStatus.FAIL);
		    			this.setErrorMessage("Result Over/Underflow");
		    			this.setOperationResult(0);
		            }
		            break;
		
		        case SUBTRACTION: 
		            try
		            {
		    			this.setOperationStatus(OperationStatus.PASS);
		    			this.setErrorMessage("Operation Succesful");
		    			this.setOperationResult( Math.subtractExact(operandOne, operandTwo));
		            }
		            catch (ArithmeticException ex)
		            {
		    			this.setOperationStatus(OperationStatus.FAIL);
		    			this.setErrorMessage("Result Over/Underflow");
		    			this.setOperationResult(0);
		            }
		            break;
		        case MULTIPLICATION:
		        	
		            try
		            {
		    			this.setOperationStatus(OperationStatus.PASS);
		    			this.setErrorMessage("Operation Succesful");
		    			this.setOperationResult( Math.multiplyExact(operandOne, operandTwo));
		            }
		            catch (ArithmeticException ex)
		            {
		    			this.setOperationStatus(OperationStatus.FAIL);
		    			this.setErrorMessage("Result Over/Underflow");
		    			this.setOperationResult(0);
		            }
		            break;
		        case DIVISION: 
		        	temporaryResult = (long) (operandOne  / operandTwo);
		        	if(temporaryResult <= Integer.MAX_VALUE || temporaryResult >=  Integer.MIN_VALUE)
		        	{
		    			this.setOperationStatus(OperationStatus.PASS);
		    			this.setErrorMessage("Operation Succesful");
//		    			this.setOperationResult( temporaryResult.intValue());
		    			this.setOperationResult( (int) temporaryResult);
		        	}
		        	else
		        	{
		    			this.setOperationStatus(OperationStatus.FAIL);
		    			this.setErrorMessage("Result Over/Underflow");
		    			this.setOperationResult(0);
		        	}
		            break;
		            
		        default:
	    			this.setOperationStatus(OperationStatus.FAIL);
	    			this.setErrorMessage("Operation Undefined");    
	    			this.setOperationResult(0);
			}

		}
		temporaryResult = 0;
		logger.info("Error Message "+this.getErrorMessage());
		logger.info("Result "+this.getOperationResult());
	}

}
