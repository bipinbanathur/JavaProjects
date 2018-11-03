package com.rtaware.config;


import java.util.Iterator;


/**
 * * Class ValidationEngine: <br>
 * The ValidationEngine Class is a Business Entity Level Class. This
 * ValidationEngine class will allow you to validate API's pertaining to CSA
 * application. <br>
 * 
 * @author Apurva Jayalwal
 * @version 1.0
 * @since 2016-06-22
 *
 */
public class ValidationEngine
{

	// APIEngine api = new APIEngine();

	/**
	 * 

	 * @param actual	- the actual value.
	 * @param expected 	- the expected value.
	 * @return - true if two Comparable values are equal
	 */
	@SuppressWarnings("rawtypes")
	public static Boolean areEqual( java.lang.Comparable actual, java.lang.Comparable expected )
	{
		Logger.showMessage("Debug", "Are Equal Comparables");
		if (actual.equals(expected)) return true;

		return false;
	}

	/**
	 * 
	 * @param actual	- the actual value.
	 * @param expected 	- the expected value.
	 * @return - true if two Comparable values are equal
	 */

	public static Boolean areEqual(Integer actual, Integer expected)
	{
		Logger.showMessage("Debug", "Are Equal Integers");
		if (actual.equals(expected)) return true;

		else return false;
	}

	public static Boolean areEqual(Float actual, Float expected)
	{
		Logger.showMessage("Debug", "Are Equal Floats");
		if (actual.equals(expected)) return true;

		else return false;
	}
	
	

	public static Boolean areEqual(Long actual, Long expected)
	{
		Logger.showMessage("Debug", "Are Equal Longs");
		if (actual.equals(expected)) return true;

		else return false;
	}
	
	public static Boolean areEqual(Short actual, Short expected)
	{
		Logger.showMessage("Debug", "Are Equal Shorts");
		if (actual.equals(expected)) return true;

		else return false;
	}
	/**
	 * 
	 * @param actual	- the actual value.
	 * @param expected 	- the expected value.
	 * @return - true if two two String values are equal.
	 */

	public static Boolean areEqual(java.lang.String actual , java.lang.String expected)
	{
		Logger.showMessage("Debug", "Are Equal Strings");
		if (actual.equals(expected)) return true;

		else return false;

	}
	
	
	/**
	 * 
	 * @param actual : the actual value.
	 * @param expected:the expected value.
	 * @return : true if two Double values are equal.
	 */
	public static Boolean areEqual(Double actual,   Double expected)
	{
		if (actual.equals(expected))return true;

		else return false;		
	}
	
	public static Boolean areEqual(java.util.Date actual,   java.util.Date expected)
	{
		Logger.showMessage("Debug", "Date Comparison");
		if (actual.equals(expected))return true;

		else return false;		
	}
	
	public static Boolean areEqual(Object actual,   Object expected)
	{
		Logger.showMessage("Debug", "Object Comparison");
		if (actual.equals(expected)) return true;
		//String operation = "";

		else return false;		
	}
	

	/**
	 * 
	 * @param actual	- the actual value.
	 * @param expected 	- the expected value.
	 * @return - true if two String values are different.
	 */
	public static Boolean areNotEqual(java.lang.String actual, java.lang.String expected)
	{
		Logger.showMessage("Debug", "areNotEqual Strings");
		if (! actual.equals(expected)) 
		{
			return true;
		}

		else
		{
			return false;
		}
			
	}
	

	/**
	 * 
	 * @param actual	- the actual value.
	 * @param expected 	- the expected value.
	 * @return - true if two Integereger values are different.
	 */
	public static Boolean areNotEqual(Integer actual, Integer expected)
	{
		Logger.showMessage("Debug", "areNotEqual Strings");
		if (actual != expected) return true;

		else return false;
	}

	/**
	 * 
	 * @param actual	- the actual value.
	 * @param expected 	- the expected value.
	 * @return - true if two Long values are different.
	 */
	public static Boolean areNotEqual(Long actual, Long expected)
	{
		Logger.showMessage("Debug", "areNotEqual Longs");
		if (actual != expected) return true;

		else return false;
	}

	/**
	 * 
	 * @param actual - the string to check.
	 * @return - true if the specified string is not null or empty.
	 */
	public static Boolean isNotNullOrEmpty(java.lang.String actual)
	{
		Logger.showMessage("Debug", "isNotNullOrEmpty String");
		if(actual == null )
		{
			return false;
		}
		else
		{
			actual = actual.trim();
			if (actual.equals("") )return false;
		}

		return true;
	}

	/**
	 * 
	 * @param collection - the collection to check.
	 * @return - true if the specified collection is null or empty.
	 */
	@SuppressWarnings("rawtypes")
	public static Boolean isNullOrEmpty(java.lang.Iterable collection)
	{
		Logger.showMessage("Debug", "isNotNullOrEmpty Collection");
		if (collection == null  )
		{
			return true;
		} 
		else			
		{
			Iterator iterator = collection.iterator();
			if( ! iterator.hasNext()) 
			{
				return true;
			}
			else
			{
				return false;
			}			  
		}
	}



	/**
	 * 
	 * @param actual	- the actual value.
	 * @param expected 	- the expected value.
	 * @return - true if two string values are equal, ignoring case.
	 */
	public static Boolean areEqualIgnoringCase(java.lang.String actual, java.lang.String expected)
	{
		Logger.showMessage("Debug", "areEqualIgnoringCase Strings");
		if (actual.equalsIgnoreCase(expected)) return true;

		else return false;
	}


	/**
	 * 
	 * @param actual	- the actual value.
	 * @param expected 	- the expected value.
	 * @return - true if two string values are different, ignoring case.
	 */
	public static Boolean areNotEqualIgnoringCase(java.lang.String actual, java.lang.String expected)
	{
		Logger.showMessage("Debug", "areNotEqualIgnoringCase Strings");
		if (actual.equalsIgnoreCase(expected)) return false;

		else

			return true;
	}

	/**
	 * 
	 * @param arg1 - the first value, expected to be the larger number.
	 * @param arg2 - the second value, expected to be the smaller number.
	 * @return - true if the first number specified is greater than the second
	 *         for Integereger values.
	 */
	public static Boolean greaterThan(Integer arg1, Integer arg2)
	{
		Logger.showMessage("Debug", "greaterThan Integers");
		if (arg1 > arg2) return true;

		else return false;
	}

	/**
	 * 
	 * @param arg1 - the first value, expected to be the smaller number.
	 * @param arg2 - the second value, expected to be the larger number.
	 * @return - true if the first number specified is less than the second for
	 *         Integereger values.
	 */
	public static Boolean lessThan(Integer arg1, Integer arg2)
	{
		Logger.showMessage("Debug", "Less than Integers");
		if (arg1 < arg2) return true;

		else return false;
	}

	/**
	 * 
	 * @param arg1  - the first value, expected to be the smaller number.
	 * @param arg2 - arg2 - the second value, expected to be the larger number.
	 * @return - true if the first number specified is less than the second for
	 *         Float values.
	 */
	public static Boolean lessThan(Float arg1, Float arg2)
	{
		Logger.showMessage("Debug", "lessThan Flots");
		if (arg1 < arg2) return true;

		else return false;

	}

	/**
	 * 
	 * @param condition  - the condition to check.
	 * @return - true if the specified condition is true.
	 */

	public static Boolean isTrue(Boolean condition)
	{		
		if(condition) return true; else return false;
	}

	/**
	 * 
	 * @param condition - the condition to check.
	 * @return - true if the specified condition is false.
	 */
	public static Boolean isFalse(Boolean condition)
	{
		if(! condition)return true; else return false;
	}

	/**
	 * 
	 * @param actual - the string to check.
	 * @param expected  - the string to search for.
	 * @return - true if the first string specified is contained within the  second string.
	 */
	public static Boolean contains(java.lang.String actual, java.lang.String expected)
	{
		if (actual.contains(expected))

			return true;

		else

			return false;

	}

	/**
	 * 
	 * @param actual   - the string to check.
	 * @param expected  - the string to search for.
	 * @return - true if the 'expected' string is the last part of the 'actual'  string.
	 */
	public static Boolean endsWith(java.lang.String actual, java.lang.String expected)
	{
		if (actual.endsWith(expected)) return true;
		else return false;

	}

	/**
	 * 
	 * @param actual - the actual string.
	 * @param regexPattern - the regular expression value to search for. Use standard	Java regular expression syntax.
	 * @return - true if the specified regular expression matches the specified string value.
	 * 
	 */
	public static Boolean isMatch(java.lang.String actual, java.lang.String regexPattern)
	{
		if (actual.matches(regexPattern)) return true;

		else return false;
	}

	/**
	 * 
	 * @param str
	 * @return
	 */
	@SuppressWarnings("unused")
	public static Boolean isNumeric(String str)
	{
		try
		{
			Double d = Double.parseDouble(str);
		}
		catch(NumberFormatException e)
		{
			return false;
		}
		return true;

	}
	
	/**
	 * 
	 * @param str: the string to check
	 * @return: length of the string
	 */

	public static Integer length(String str)
	{
		return str.length();
	}

	
	/**
	 * 
	 * @param aString:  the string to check
	 * @return: true if the specified string is null or empty.
	 */
	public static Boolean isNullOrEmpty(String aString)
	{
		if(aString != null )
		{
			aString = aString.trim();
			if (aString.equals("") ) return true;
			else return false;
			
		}
		else
		{
			return false;
		}		
	}
	
	
	/**
	 * 
	 * @param actual:-  the string to check
	 * @param expected :- the string to search for
	 * @return:- true if the 'expected' string is the first part of the 'actual' string
	 */
	public static Boolean startsWith(java.lang.String actual,java.lang.String expected)
	{
		Logger.showMessage("Debug", actual+ "  starts With "+expected);
		if (actual.startsWith(expected)) 
		{
			return true;
		}
			
		else
		{
			return false;
		}
			
		
	}


	/**
	 * 
	 * @param arg1: the first value, expected to be the smaller number.
	 * @param arg2 : the second value, expected to be the larger number.
	 * @return :true if the first number specified is less than the second for Float values.
	 */
	public static Boolean less(Float arg1, Float arg2)
	{
		if (arg1 == arg2) return true;

		else return false;
		
	}

}
