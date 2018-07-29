package com.rtaware.examples;

@FunctionalInterface
interface InterfacesExample
{
	void printMessage(String message);
}
public class MessageClass 
{
	public static void main(String[] args)
	{						
		InterfacesExample ie = (param) -> System.out.println("Single Param Lambda "+param);
		ie.printMessage("Hello World");			
		java.util.List<Integer> numList = java.util.List.of(1,2,3,4);		
		numList.forEach(MessageClass::printList);
	}
	
	public static void printList(Integer i)
	{
		System.out.println(i);
	}
}