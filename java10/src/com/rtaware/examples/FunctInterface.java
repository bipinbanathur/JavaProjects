package com.rtaware.examples;

@FunctionalInterface
public interface FunctInterface
{
	default void print(int number)
	{
		System.out.println(number);
	}
	
	public int square(int number);
}
