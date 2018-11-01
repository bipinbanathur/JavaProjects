package com.rtaware.tests;

import java.util.function.Consumer;

public class MultiplyHelper
{

	public static void main(String[] args)
	{
		Multiplier multiplier = ( oper1,  oper2) ->
		{
			Consumer<String> c = System.out::println;
			c.accept(new Integer(oper1).toString());
			c.accept(new Integer(oper2).toString());
			return oper1*oper2;
		};
		int result = multiplier.multiply(100, 101);
		Consumer<Integer> c = System.out::println;
		c.accept(result);
		
		int aa = 12,bb =65 ,cc =10;
		int biggest = (aa > bb) ? ((aa > cc) ? aa : cc ): ( (bb > cc) ? bb: cc	);
		System.out.println(biggest);
	}
}

