package com.assignment.utilities.calculator.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.assignment.utilities.calculator.OperationStatus;
import com.assignment.utilities.calculator.Operators;
import com.assignment.utilities.calculator.SimpleArithmeticCalculator;

public class TestAddition
{
	private SimpleArithmeticCalculator simpleCalculator = null;
	
	@Before
	public void beforeTest()
	{
		simpleCalculator = new SimpleArithmeticCalculator();
	}
	@Test
	public void testAdditionBoundaryOverflow()
	{
		
		simpleCalculator.setOperandOne(2147483646);
		simpleCalculator.setOperandTwo(2);
		simpleCalculator.setCalculationOperator(Operators.ADDITION);
		simpleCalculator.calcuateResult();		

		assertEquals("Operation Status",  simpleCalculator.getOperationStatus(), OperationStatus.FAIL);
		assertEquals("Operation Message", simpleCalculator.getOutputMessage(), "Result Over/Underflow");
		assertEquals("Operation Resullt", simpleCalculator.getOperationResult(), new Integer(0));		
	}

	@Test
	public void testAdditionBoundaryUnderflow()
	{
		simpleCalculator.setOperandOne(-2147483646);
		simpleCalculator.setOperandTwo(-3);
		simpleCalculator.setCalculationOperator(Operators.ADDITION);
		simpleCalculator.calcuateResult();		

		assertEquals("Operation Status",  simpleCalculator.getOperationStatus(), OperationStatus.FAIL);
		assertEquals("Operation Message", simpleCalculator.getOutputMessage(), "Result Over/Underflow");
		assertEquals("Operation Resullt", simpleCalculator.getOperationResult(), new Integer(0));		
	}

	@Test
	public void testAdditionOperandNull()
	{
		simpleCalculator.setOperandOne(null);
		simpleCalculator.setOperandTwo(2);
		simpleCalculator.setCalculationOperator(Operators.ADDITION);
		simpleCalculator.calcuateResult();		

		assertEquals("Operation Status",  simpleCalculator.getOperationStatus(), OperationStatus.FAIL);
		assertEquals("Operation Message", simpleCalculator.getOutputMessage(), "Invalid Operands or Operator");
		assertEquals("Operation Resullt", simpleCalculator.getOperationResult(), new Integer(0));		
	}
	
	@Test
	public void testAdditionOperationNull()
	{
		simpleCalculator.setOperandOne(34);
		simpleCalculator.setOperandTwo(2);
		simpleCalculator.setCalculationOperator(null);
		simpleCalculator.calcuateResult();		

		assertEquals("Operation Status",  simpleCalculator.getOperationStatus(), OperationStatus.FAIL);
		assertEquals("Operation Message", simpleCalculator.getOutputMessage(), "Invalid Operands or Operator");
		assertEquals("Operation Resullt", simpleCalculator.getOperationResult(), new Integer(0));		
	}
	
	@Test
	public void testAdditionOperatorUndefined()
	{
		simpleCalculator.setOperandOne(34);
		simpleCalculator.setOperandTwo(2);
		simpleCalculator.setCalculationOperator(Operators.DIVISION);
		simpleCalculator.calcuateResult();		

		assertEquals("Operation Status",  simpleCalculator.getOperationStatus(), OperationStatus.FAIL);
		assertEquals("Operation Message", simpleCalculator.getOutputMessage(), "Operation Undefined");
		assertEquals("Operation Resullt", simpleCalculator.getOperationResult(), new Integer(0));		
	}
	
	@Test
	public void testAdditionPositive1()
	{
		simpleCalculator.setOperandOne(2147483645);
		simpleCalculator.setOperandTwo(2);
		simpleCalculator.setCalculationOperator(Operators.ADDITION);
		simpleCalculator.calcuateResult();		

		assertEquals("Operation Status",  simpleCalculator.getOperationStatus(), OperationStatus.PASS);
		assertEquals("Operation Message", simpleCalculator.getOutputMessage(), "Operation Succesful");
		assertEquals("Operation Resullt", simpleCalculator.getOperationResult(), new Integer(2147483647));		
	}
	
	@Test
	public void testAdditionPositive2()
	{
		simpleCalculator.setOperandOne(-2147483645);
		simpleCalculator.setOperandTwo(-2);
		simpleCalculator.setCalculationOperator(Operators.ADDITION);
		simpleCalculator.calcuateResult();		

		assertEquals("Operation Status",  simpleCalculator.getOperationStatus(), OperationStatus.PASS);
		assertEquals("Operation Message", simpleCalculator.getOutputMessage(), "Operation Succesful");
		assertEquals("Operation Resullt", simpleCalculator.getOperationResult(), new Integer(-2147483647));		
	}
	
	@Test
	public void testAdditionNonNumeric()
	{
		try
		{
			simpleCalculator.setOperandOne(-2147483646);
			simpleCalculator.setOperandTwo(Integer.parseInt("*"));
			simpleCalculator.setCalculationOperator(Operators.ADDITION);
			simpleCalculator.calcuateResult();		

			assertEquals("Operation Status",  simpleCalculator.getOperationStatus(), OperationStatus.FAIL);
			assertEquals("Operation Message", simpleCalculator.getOutputMessage(), "Result Over/Underflow");
			assertEquals("Operation Resullt", simpleCalculator.getOperationResult(), new Integer(0));
			
		}
		catch(Exception e)
		{
			System.out.println("Non Numeric");
		}
		
	}
	
	@After
	public void afterTest()
	{
		simpleCalculator = null;
	}
}
