package com.rtaware.utilities.calculator.test;
import org.junit.Test;

import com.rtaware.utilities.calculator.Operators;
import com.rtaware.utilities.calculator.SimpleArithmeticCalculator;

public class TestOperations 
{
	@Test
	public void testMultiplication()
	{
		SimpleArithmeticCalculator simpleCalculator = new SimpleArithmeticCalculator();
		simpleCalculator.setOperandOne(125);
		simpleCalculator.setOperandTwo(7);
		simpleCalculator.calcuateResult();		
		System.out.println(simpleCalculator.getOperationStatus());
		System.out.println(simpleCalculator.getErrorMessage());
		System.out.println(simpleCalculator.getOperationResult());		
	}
	
	@Test
	public void testMultiplicationNeg1()
	{
		SimpleArithmeticCalculator simpleCalculator = new SimpleArithmeticCalculator();
		simpleCalculator.setOperandOne(2147483647);
		simpleCalculator.setOperandTwo(2147483647);
		simpleCalculator.setCalculationOperator(Operators.MULTIPLICATION);
		simpleCalculator.calcuateResult();		
		System.out.println(simpleCalculator.getOperationStatus());
		System.out.println(simpleCalculator.getErrorMessage());
		System.out.println(simpleCalculator.getOperationResult());		
	}
	
	@Test
	public void testAddition()
	{
		SimpleArithmeticCalculator simpleCalculator = new SimpleArithmeticCalculator();
		simpleCalculator.setOperandOne(2147447);
		simpleCalculator.setOperandTwo(21474847);
		simpleCalculator.setCalculationOperator(Operators.ADDITION);
		simpleCalculator.calcuateResult();		
		System.out.println(simpleCalculator.getOperationStatus());
		System.out.println(simpleCalculator.getErrorMessage());
		System.out.println(simpleCalculator.getOperationResult());		
	}
	
	@Test
	public void testAdditionNeg1()
	{
		SimpleArithmeticCalculator simpleCalculator = new SimpleArithmeticCalculator();
		simpleCalculator.setOperandOne(2147447789);
		simpleCalculator.setOperandTwo(2147447789);
		simpleCalculator.setCalculationOperator(Operators.ADDITION);
		simpleCalculator.calcuateResult();		
		System.out.println(simpleCalculator.getOperationStatus());
		System.out.println(simpleCalculator.getErrorMessage());
		System.out.println(simpleCalculator.getOperationResult());		
	}
	
	@Test
	public void testSubtractNeg1()
	{
		SimpleArithmeticCalculator simpleCalculator = new SimpleArithmeticCalculator();
		simpleCalculator.setOperandOne(-2147447789);
		simpleCalculator.setOperandTwo(-2147447789);
		simpleCalculator.setCalculationOperator(Operators.SUBTRACTION);
		simpleCalculator.calcuateResult();		
		System.out.println(simpleCalculator.getOperationStatus());
		System.out.println(simpleCalculator.getErrorMessage());
		System.out.println(simpleCalculator.getOperationResult());		
	}
	
	@Test
	public void testSubtraction()
	{
		SimpleArithmeticCalculator simpleCalculator = new SimpleArithmeticCalculator();
		simpleCalculator.setOperandOne(214);
		simpleCalculator.setOperandTwo(2147483647);
		simpleCalculator.setCalculationOperator(Operators.SUBTRACTION);
		simpleCalculator.calcuateResult();		
		System.out.println(simpleCalculator.getOperationStatus());
		System.out.println(simpleCalculator.getErrorMessage());
		System.out.println(simpleCalculator.getOperationResult());		
	}
	
	@Test
	public void testMultiplicationPositive()
	{
		SimpleArithmeticCalculator simpleCalculator = new SimpleArithmeticCalculator();
		simpleCalculator.setOperandOne(10);
		simpleCalculator.setOperandTwo(234);
		simpleCalculator.setCalculationOperator(Operators.MULTIPLICATION);
		simpleCalculator.calcuateResult();		
		System.out.println(simpleCalculator.getOperationStatus());
		System.out.println(simpleCalculator.getErrorMessage());
		System.out.println(simpleCalculator.getOperationResult());		
	}
	
	
	@Test
	public void testDivision()
	{
		SimpleArithmeticCalculator simpleCalculator = new SimpleArithmeticCalculator();
		simpleCalculator.setOperandOne(234);
		simpleCalculator.setOperandTwo(10);
		simpleCalculator.setCalculationOperator(Operators.DIVISION);
		simpleCalculator.calcuateResult();		
		System.out.println(simpleCalculator.getOperationStatus());
		System.out.println(simpleCalculator.getErrorMessage());
		System.out.println(simpleCalculator.getOperationResult());		
	}
	
	@Test
	public void testDefaultop()
	{
		SimpleArithmeticCalculator simpleCalculator = new SimpleArithmeticCalculator();
		simpleCalculator.setOperandOne(234);
		simpleCalculator.setOperandTwo(10);
		simpleCalculator.setCalculationOperator(Operators.NONSENSE);
		simpleCalculator.calcuateResult();		
		System.out.println(simpleCalculator.getOperationStatus());
		System.out.println(simpleCalculator.getErrorMessage());
		System.out.println(simpleCalculator.getOperationResult());		
	}
}