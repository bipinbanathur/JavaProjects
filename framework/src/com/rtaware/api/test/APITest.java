package com.rtaware.api.test;
import com.rtaware.api.APIEngine;
import com.rtaware.config.ValidationEngine;


public class APITest
{

	public static void main(String[] args)
	{	
		
 		APIEngine 	api = new APIEngine();
 		
		api.setApiType("REST");
		api.setCallingFNName(new Object(){}.getClass().getEnclosingMethod().getName());
		api.setUrlString("https://16.103.22.59:8444/csa/api/resource/provider");
		api.setUserName("admin");
		api.setPasswordString("cloud"); 
		api.setRequestType("application/json"); 
		api.setAcceptMediaType("application/json");
		
		
		 String requestContent = api.readRequestFrom("B:\\Eclipse\\Data\\json\\provider.json");
				
		 requestContent = "";
		 
		api.setRequestMethod("POST");
		api.setRequestContent(requestContent);	
		api.showCertificates();
		
		api.executeRequest();			
		api.showProperties();
		
		System.out.println("HTTP  Response 	: "+api.getResponseCode());
		System.out.println("Provider Name 	: "+api.getParamValue("ext.csa_name_key"));
		System.out.println("Provider Type 	: "+api.getParamValue("type.name"));
		System.out.println("Provider ID  	: "+api.getParamValue("@self"));
		
		if(ValidationEngine.areEqual(api.getParamValue("type.name"), "VMware vCenter"))
		{
			
		}
		api.clearAll();	
	}

}













































//RequestBuilder     rb 				= new RequestBuilder(); 
//String 			   requestContent 	= rb.buildRequest("100", "1", "C:\\Users\\banathur\\Desktop\\Activities\\DDT\\provider.xls" );
//api.readRequestFrom("B:\\Eclipse\\Data\\json\\provider.json")
//
//RequestBuilder     rb 				= new RequestBuilder(); 
//String 			requestContent 	= rb.buildRequest("100", "1", "C:\\Users\\banathur\\Desktop\\Activities\\DDT\\provider.xls" );		 
//System.out.println(requestContent);
//import com.otsi.config.FlowVariables;
//import com.otsi.csa.provider.Provider;
//import com.otsi.data.JSONProcessor;
//import com.otsi.data.RequestBuilder;
//		APIEngine 	api = new APIEngine();
//		api.setApiType("REST");
//		api.setCallingFNName(new Object(){}.getClass().getEnclosingMethod().getName());
//		api.setUrlString("https://16.103.22.59:8444/csa/api/resource/provider");
//		api.setUserName("admin");
//		api.setPasswordString("cloud"); 
//		api.setRequestType("application/json"); 
//		api.setRequestMethod("POST");
//		api.setRequestContent(api.readRequestFrom("B:\\Eclipse\\Data\\json\\provider.json"));	
//		api.showCertificates();
//		api.executeRequest();				
//		api.showProperties();
//		System.out.println("HTTP  Response 	: "+api.getResponseCode());
//		System.out.println("Provider Name 	: "+api.getParamValue("ext.csa_name_key"));
//		System.out.println("Provider Type 	: "+api.getParamValue("type.name"));
//		System.out.println("Provider ID  	: "+api.getParamValue("@self"));
//		api.clearAll();	
//
//		
//		
//	FlowVariables.set("api", api);
//	FlowVariables.set("baseURI", "https://16.103.22.59:8444/csa/");
//	FlowVariables.set("userName", "admin");
//	FlowVariables.set("passwordString", "cloud");
//	RequestBuilder     rb 				= new RequestBuilder(); 
//	String 			requestContent 	= rb.buildRequest("100", "1", "C:\\Users\\banathur\\Desktop\\Activities\\DDT\\provider.xls" );		 
//	String responseContent = Provider.createProvider(requestContent);
//	System.out.println(responseContent);
//
//import com.otsi.config.FlowVariables;
//import com.otsi.csa.provider.Provider;
//import com.otsi.data.JSONProcessor;
//(APIEngine) FlowVariables.get("api");
//FlowVariables.set("api", api);
//FlowVariables.set("baseURI", "https://16.103.22.59:8444/csa/");
//FlowVariables.set("userName", "admin");
//FlowVariables.set("passwordString", "cloud");
//String requestContent = JSONProcessor.readFile("B:\\Eclipse\\Data\\json\\provider.json");
//String response = Provider.createProvider(requestContent);
//System.out.println(response);
//	api.setApiType("REST");
//	api.setCallingFNName(new Object(){}.getClass().getEnclosingMethod().getName());
//	api.setUrlString("https://16.103.22.59:8444/csa/api/organization");
//	api.setUserName("admin");
//	api.setPasswordString("cloud"); 
//	api.setRequestType("application/json"); 
//	api.setRequestMethod("GET");
//	api.setRequestContent("");	
//	api.showCertificates();
//	api.executeRequest();				
//	api.showProperties();	
//				
//	System.out.println("Total Count  : "+api.getParamValue("@total_results"));		
//	for(int i=0;i<5;i++) 
//	{ 
//		System.out.println("Member Organization : "+api.getParamValue("members.name["+i+"]") + " ( " +api.getParamValue("members.ext["+i+"].csa_name_key")+" ) " );				
//	}
//	api.clearAll();	
//
