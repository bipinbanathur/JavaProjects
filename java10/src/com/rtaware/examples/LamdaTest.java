package com.rtaware.examples;

public class LamdaTest
{
	public static void main(String[] args)
	{

		
		FunctInterface fi = (int number) ->
		{
			
			number = number*number;
			return number;
		};
		int number = fi.square(100);
		fi.print(number);

	}
}
