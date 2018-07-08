package com.rtaware.repoapp;

import org.json.JSONObject;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.rtaware.repoapp.service.ReportGenerator;

@RestController
@EnableAutoConfiguration
public class MainController 
{
	@RequestMapping("/report/sample")
	public ResponseEntity<String> getRepoSkeleton() 
	{
		return new ResponseEntity<String>( new ReportGenerator().sampleReport(), HttpStatus.ACCEPTED);
	}

	@RequestMapping("/report/{repoType}")
	String getReportType(@PathVariable String repoType) 
	{
	    return "{ \"type\"  : \""+repoType+"\"}";
	}
	
	
	@RequestMapping(value="/report", method = RequestMethod.POST)
	@CrossOrigin(allowedHeaders = "*", origins = "*")
    public ResponseEntity<String> generateReport(@RequestBody String requestBody) 
	{
		System.out.println(requestBody);
		String tableName = "";
		try 
		{
			tableName 	= new JSONObject(requestBody).getString("tableName");			
			if(null != tableName &&  tableName.trim().length() > 0 )
			{
				return new ResponseEntity<String>( new ReportGenerator().generateReport(tableName), HttpStatus.ACCEPTED);
			}
			return new ResponseEntity<String>("{\"message\" : \"Invalid Table\"}", HttpStatus.PRECONDITION_FAILED);			
		}
		catch(Exception e)
		{
			return new ResponseEntity<String>("{\"message\" : \"Invalid Input\"}", HttpStatus.PRECONDITION_FAILED);	
		}
    }
	
	public static void main(String[] args) throws Exception
	{		
		SpringApplication.run(MainController.class, args);
	}
}