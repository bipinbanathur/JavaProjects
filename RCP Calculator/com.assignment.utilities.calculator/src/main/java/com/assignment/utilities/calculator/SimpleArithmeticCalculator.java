package com.assignment.utilities.calculator;

import org.apache.log4j.Logger;

public class SimpleArithmeticCalculator implements ICalculator
{
	private Integer 				operandOne 			= 0;
	private Integer 				operandTwo 			= 0;
	private Integer 				operationResult   	= 0;	
	private OperationStatus			operationStatus     = OperationStatus.FAIL;
	private Operators				calculationOperator = null;				
	private String  				outputMessage 		= "";
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

	public String getOutputMessage()
	{
		return outputMessage;
	}

	private void setErrorMessage(String errorMessage) 
	{
		this.outputMessage = errorMessage;
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
		logger.info("***************************************************");
		logger.info("Operation "+this.getCalculationOperator());
		logger.info("Operand 1 "+this.getOperandOne());
		logger.info("Operand 2 "+this.getOperandTwo());
		
		if ( null == calculationOperator || null == this.getOperandOne()  || null == this.getOperandTwo()) 
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
		    			this.setOperationResult( Math.addExact(this.getOperandOne(), this.getOperandTwo()));
		    			this.setOperationStatus(OperationStatus.PASS);
		    			this.setErrorMessage("Operation Succesful");
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
		    			this.setOperationResult( Math.subtractExact(this.getOperandOne(), this.getOperandTwo()));
		    			this.setOperationStatus(OperationStatus.PASS);
		    			this.setErrorMessage("Operation Succesful");
		            }
		            catch (ArithmeticException ex)
		            {
		    			this.setOperationStatus(OperationStatus.FAIL);
		    			this.setErrorMessage("Result Over/Underflow");
		    			this.setOperationResult(0);
		            }
		            break;
		        case MULTIPLICATION:
		        	if ( null == this.getOperandOne() || null ==this.getOperandTwo())
		        	{
		    			this.setOperationStatus(OperationStatus.FAIL);
		    			this.setErrorMessage("Operation Undefined");
		    			this.setOperationResult(0);
		    			break;
		        	}
		            try
		            {
		    			this.setOperationResult( Math.multiplyExact(this.getOperandOne(), this.getOperandTwo()));
		    			this.setOperationStatus(OperationStatus.PASS);
		    			this.setErrorMessage("Operation Succesful");
		            }
		            catch (ArithmeticException ex)
		            {
		    			this.setOperationStatus(OperationStatus.FAIL);
		    			this.setErrorMessage("Result Over/Underflow");
		    			this.setOperationResult(0);
		            }
		            break;
		        case DIVISION: 
		        default:
	    			this.setOperationStatus(OperationStatus.FAIL);
	    			this.setErrorMessage("Operation Undefined");    
	    			this.setOperationResult(0);
			}
		}
		logger.info("Error Message "+this.getOutputMessage());
		logger.info("Result "+this.getOperationResult());
	}

}
