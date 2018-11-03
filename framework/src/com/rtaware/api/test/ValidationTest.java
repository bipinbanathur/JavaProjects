package com.rtaware.api.test;

import com.rtaware.config.ValidationEngine;

public class ValidationTest
{

	public static void main(String[] args)
	{

		if (ValidationEngine.areEqual(11, 12))
		{
			System.out.println("areEqual Integer:- Pass");
		}
		else
		{
			System.out.println("areEqual Integer:- fail");
		}

		if (ValidationEngine.areEqual("/csa/api/catalog/90d9650a36988e5d0136988f03ab000f", "/csa/api/catalog/90d9650a36988e5d0136988f03ab000f"))
		{ 
			System.out.println("areEqual String:- Pass");
		}
		else
		{
			System.out.println("areEqual String:- fail");

		}

		if (ValidationEngine.areEqualIgnoringCase("/csa/api/catalog/90d9650a36988e5d0136988f03ab000f", "/csa/api/catalog/90D9650A36988E5D0136988F03AB000F")) // Ignore
																																								// case
																																								// string
		{
			System.out.println("Ignoring String :- Pass");
		}
		else
		{
			System.out.println("String are differrnt:- fail");

		}

		if (ValidationEngine.areNotEqual(11, 12)) 
		{
			System.out.println("Not Equal :- Pass");
		}
		else
		{
			System.out.println("Equal:- fail");

		}

		long no1 = (long) 1000000;
		long no2 = (long) 4000000;
		if (ValidationEngine.areNotEqual(no1, no2)) // long
		{
			System.out.println("Long Not Equal :- Pass");
		}
		else
		{
			System.out.println("Long Equal:- fail");

		}

		if (ValidationEngine.areNotEqual("/csa/api/catalog/90d9650a36988e5d0136988f03ab000f", "/csa/api/catalog/90d9650a36988e5d0136988f03ab000"))// string
		{

			System.out.println("String Not Equal :- Pass");
		}
		else
		{
			System.out.println("String Equal:- fail");

		}

		if (ValidationEngine.areNotEqualIgnoringCase("/csa/api/catalog/90d9650a36988e5d0136988f03ab000f", "/csa/api/catalog/90d9650a36988e5d0136988f03a")) // String
		{
			System.out.println("String are Different Ignore :- Pass");
		}
		else
		{
			System.out.println("String same:- fail");

		}

		if (ValidationEngine.contains("/csa/api/catalog/90d9650a36988e5d0136988f03ab000f", "90d9650a36988e5d0136988f03ab000f")) // contains
																																// String
		{
			System.out.println("first String contains second string :- Pass");
		}
		else
		{

			System.out.println("first String different from second string:- fail");

		}

		if (ValidationEngine.endsWith("/csa/api/catalog/90d9650a36988e5d0136988f03ab000f","90d9650a36988e5d0136988f03ab000f")) // String
		{
			System.out.println("actual string ends with expected string  :- Pass");
		}
		else
		{

			System.out.println("actual String different from expected string:- fail");

		}

		if (ValidationEngine.greaterThan(14, 12))// int
		{
			System.out.println("14 is greater than 12  :- Pass");
		}
		else
		{

			System.out.println("Not Greater :- fail");

		}
		if(ValidationEngine.isFalse(false)) 
		{
		
		}

		if (ValidationEngine.isMatch("@self", "@.*")) // string
		{
			System.out.println("regular expression matches the specified string value  :- Pass");
		}
		else
		{

			System.out.println("regular expression matches the specified string value  :- Fail");
		}

		 if(ValidationEngine.isNotNullOrEmpty("MMMM"))// string
			{
				System.out.println("NULL or Empty - Pass");
			}
			else
			{

				System.out.println("NULL or Empty - Fail");
			}
		 
		 if(ValidationEngine.isNullOrEmpty(""))
			{
				System.out.println("NULL or Empty - Fail");
			}
			else
			{

				System.out.println("NULL or Empty - Pass");
			}
		 
		 if(ValidationEngine.isNumeric("23f89-"))
			{
				System.out.println("isNumeric - Fail");
			}
			else
			{

				System.out.println("isNumeric - Pass");
			}


		if (ValidationEngine.lessThan(1, 4))
		{
			System.out.println("Less - Pass");
		}
		else
		{

			System.out.println("less :- fail");
		}

		// }

		if (ValidationEngine.lessThan(0.1f, 0.2f)) /// int
		{
			System.out.println("Less  float - Pass");
		}
		else
		{

			System.out.println("less float  :- fail");
		}

		System.out.println("False 	Condition is 		" + ValidationEngine.isFalse(false));
		System.out.println("True 	Condition is 		" + ValidationEngine.isFalse(true));
		System.out.println("False 	Condition is not 	" + ValidationEngine.isTrue(false));
		System.out.println("True 	Condition is not 	" + ValidationEngine.isTrue(true));

		System.out.println("Null  	is not NULL			" + ValidationEngine.isNotNullOrEmpty(null));
		System.out.println("Emtpy 	is not Empty		" + ValidationEngine.isNotNullOrEmpty(""));
		System.out.println("Full  	is not Empty		" + ValidationEngine.isNotNullOrEmpty("Full"));

	}

}
