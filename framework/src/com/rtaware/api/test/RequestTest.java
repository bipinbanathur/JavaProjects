package com.rtaware.api.test;


import com.rtaware.data.JSONProcessor;
import com.rtaware.data.JSONTemplatizer;
import com.rtaware.data.RequestBuilder;
import com.rtaware.data.XMLTemplatizer;


public class RequestTest
{
	public static void main(String[] args)
	{

		String path 		= 	"C:\\Users\\banathur\\Desktop\\Activities\\";
		String filename 	= 	"orders"; //persons //provider sample3
		String action		=	"xmltemplate1";
		String type			=	"xml"; //
		
		if(action.equalsIgnoreCase("template"))
		{
			String data 		= JSONProcessor.readFile(path+filename+"."+type);
			new JSONTemplatizer(filename,data);
		}
		else if (action.equalsIgnoreCase("xmltemplate"))
		{
			String data = JSONProcessor.readFile(path+filename+"."+type);
			new XMLTemplatizer(filename,data);
		}
		else
		{
			RequestBuilder 		rb 				= new RequestBuilder(); //"B:\\Eclipse\\Data\\excel\\sample
			String 				requestContent 	= rb.buildRequest("100", "1", path+filename+".xls" ); //"C:\\Users\\banathur\\Desktop\\Activities\\AddOptions.xls"					
			System.out.println("\n\n"+requestContent+"\n\n");			
		}
	}

}
