package com.rtaware.api.test;

import java.io.File;

import com.rtaware.api.ExecutionAgent;
import com.rtaware.config.FlowVariables;


public class ExecutionTest
{
	public static void main(String[] args)
	{
		String path = new File("").getAbsoluteFile().toString()+"\\data\\API.xls";	
		//System.out.println(path);
		
		
		new ExecutionAgent(path,""); //LFT
		
		
		System.out.println("random	"+FlowVariables.get("random"));
		System.out.println("ceil	"+FlowVariables.get("ceil"));
		System.out.println("round	"+FlowVariables.get("round"));
		System.out.println("absolute"+FlowVariables.get("absolute"));
		System.out.println("floor	"+FlowVariables.get("floor"));
		
//		System.out.println(FlowVariables.get("dateVal"));
//		System.out.println(FlowVariables.get("integerVal"));
//		System.out.println(FlowVariables.get("floatVal"));
//		System.out.println(FlowVariables.get("doubleVal"));
//		System.out.println(FlowVariables.get("booleanVal"));		
//		System.out.println(FlowVariables.get("dateResult"));
//		System.out.println(FlowVariables.get("intResult"));
//		System.out.println(FlowVariables.get("floatResult"));
//		System.out.println(FlowVariables.get("doubleResult"));
//		System.out.println(FlowVariables.get("boolResult"));
//		System.out.println(FlowVariables.get("longResult"));
//		System.out.println(FlowVariables.get("dateResult"));
//		System.out.println(FlowVariables.get("dateResult"));
		System.out.println(FlowVariables.get("artifactID"));
		System.out.println(FlowVariables.get("desc"));
		System.out.println(FlowVariables.get("artifID"));
		System.out.println(FlowVariables.get("description"));
		System.out.println(FlowVariables.get("artID"));
		
		
		


	}
}
