package com.rtaware.examples;

import java.util.*;  
public class LambdaExpressionExample
{  
    public static void main(String[] args) 
	{  
          
        List<String> list=new ArrayList<String>();  
        list.add("ankit");  
        list.add("mayank");  
        list.add("bipin");  
        list.add("jai");  
          
        list.forEach((n)->System.out.println(n) );  
    }  
    

}