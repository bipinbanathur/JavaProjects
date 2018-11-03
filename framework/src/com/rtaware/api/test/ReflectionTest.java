package com.rtaware.api.test;
//package com.hpe.api.test;
//
//import java.lang.reflect.Method;
//import java.lang.reflect.Parameter;
//
//import com.hpe.config.FlowVariables;
//import com.hpe.config.ValidationEngine;
//
//public class ReflectionTest
//{
//
//	public static void main(String[] args)
//	{
//		ValidationEngine validator = new ValidationEngine();
//		Class validationEngine	=validator.getClass();    
//		
//		System.out.println(validationEngine.getName());  
//		
//		for(Method method:validationEngine.getMethods() )
//		{
//			System.out.println(method.getName() +"   "+method.getReturnType());
//			for(Parameter parameter : method.getParameters())
//			{
//				System.out.println(parameter.getName() +"   "+parameter.getType()+ "  "+parameter.toString());
//			}
//			
//		}
//		
//	}
//}
//
//
//
////if(operation.equals("isNotNullOrEmpty")) 		booleanResult = ValidationEngine.isNotNullOrEmpty(actualValue);
////if(operation.equals("startsWith")) 		 		booleanResult = ValidationEngine.startsWith(actualValue,expectedValue);
////if(operation.equals("areEqual")) 		 		booleanResult = ValidationEngine.areEqual(actualValue,expectedValue);
////if(operation.equals("isNumeric")) 		 		booleanResult = ValidationEngine.areEqual(actualValue,expectedValue);
////if(operation.equals("isMatch")) 		 		booleanResult = ValidationEngine.areEqual(actualValue,expectedValue);
////if(operation.equals("endsWith")) 		 		booleanResult = ValidationEngine.areEqual(actualValue,expectedValue);
////if(operation.equals("contains")) 				booleanResult = ValidationEngine.areEqual(actualValue,expectedValue);
////if(operation.equals("isFalse")) 				booleanResult = ValidationEngine.areEqual(actualValue,expectedValue);
////if(operation.equals("isTrue")) 		 			booleanResult = ValidationEngine.areEqual(actualValue,expectedValue);
////if(operation.equals("lessThan")) 		 		booleanResult = ValidationEngine.areEqual(actualValue,expectedValue);
////if(operation.equals("greaterThan")) 		 	booleanResult = ValidationEngine.areEqual(actualValue,expectedValue);
////if(operation.equals("areNotEqualIgnoringCase")) booleanResult = ValidationEngine.areEqual(actualValue,expectedValue);
////if(operation.equals("areEqualIgnoringCase")) 	booleanResult = ValidationEngine.areEqual(actualValue,expectedValue);
////if(operation.equals("areNotEqual")) 		 	booleanResult = ValidationEngine.areEqual(actualValue,expectedValue);
////if(operation.equals("isNotNullOrEmpty")) 		booleanResult = ValidationEngine.areEqual(actualValue,expectedValue);
////if(operation.equals("areEqual")) 		 		booleanResult = ValidationEngine.areEqual(actualValue,expectedValue);
////if(operation.equals("areEqual")) 		 		booleanResult = ValidationEngine.areEqual(actualValue,expectedValue);
////if(operation.equals("areEqual")) 		 		booleanResult = ValidationEngine.areEqual(actualValue,expectedValue);
////if(operation.equals("areEqual")) 		 		booleanResult = ValidationEngine.areEqual(actualValue,expectedValue);
////if(operation.equals("areEqual")) 		 		booleanResult = ValidationEngine.areEqual(actualValue,expectedValue);
////if(operation.equals("areEqual")) 		 		booleanResult = ValidationEngine.areEqual(actualValue,expectedValue);
//
////FlowVariables.set("bipin", "Bipin");
////System.out.println(FlowVariables.get("bipin"));
////FlowVariables.set("bipin", "BipinB");
////String value = (String) FlowVariables.get("bipin").toString();
////System.out.println(value);
////FlowVariables.set("bipin", new Integer(100));
////value = (String) FlowVariables.get("bipin").toString();
////System.out.println(value);
////FlowVariables.set("bipin", new Double(100.00));			
//// value = (String) FlowVariables.get("bipin").toString();
////System.out.println(value);
////FlowVariables.set("bipin", new Boolean(true));			
//// value = (String) FlowVariables.get("bipin").toString();
////System.out.println(value);
////FlowVariables.set("bipin", new java.util.Date());
//// value = (String) FlowVariables.get("bipin").toString();
////System.out.println(value);
//
