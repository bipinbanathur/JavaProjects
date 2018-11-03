package com.rtaware.api;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.cert.Certificate;
import java.util.Base64;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;

import com.rtaware.config.APIException;
import com.rtaware.config.FlowVariables;
import com.rtaware.config.Logger;
import com.rtaware.data.*;


@SuppressWarnings({ "deprecation", "unused" })
public class APIEngine 
{
	private		TrustManager[] 			trustAllCerts	=	null;
	private 	HostnameVerifier 		allHostsValid	=	null;	
	private 	String 					urlString		=	"";
	private		String					userName		=	"";
	private		String					passwordString	=	"";
	private  	String 					authenticString	=	""; 	
	private		String					mediaType		=	"";
	private		String					acceptMediaType	=	"";	
	private		String					requestMethod	=	"";
	private		String					soapAction		=	"";
	private 	String					apiType			=	"";
	private 	String					requestContent	=	"";
	private 	String					responseContent	=	"";
	private     String					xAuthToken		=	"";
	private		String					boundary		=	"";
	private		String					responseCode	=	"";
	private		String					authType		=	"";
	private		String					callingFNName	=	"";	
	private  	boolean					isSSL			= 	false;
	private		Map<String, List<String>> respHeaders	=	null;
	private		Hashtable<String,String>httpHeaders		=	null;
	private		Logger					log				=	null;
	private 	HttpsURLConnection  	sslConnection	=	null;
	private 	HttpURLConnection 		urlConnection	=	null;
	private		String					logPath			=	"";	
	private 	String					showCerts		=   "";
	private		CookieManager 			cookieManager 	= 	null;

	public APIEngine()
	{
		try
		{			
			cookieManager 	= 	new CookieManager();
			cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);	    
		    CookieHandler.setDefault(cookieManager);
		}
		catch(Exception e)
		{
			Logger.showMessage("Exception","Unable to Start Cookie Manager");
	        if(log!= null)
	        {	
	        	log.exception(e);
	        	log.close();
	        }	
		}	
	}
	
	public void enableLog()
	{
		FlowVariables 			variables 		=	new FlowVariables();
		try{ log = (Logger) variables.getVar("log"); } catch(Exception e){}
		if(log == null)
		{			
			try{ log = (Logger) variables.getVar("log"); } catch(Exception e){}
			
			if(log== null)
			{
				log = new Logger();
			}
		}
		variables = null;
	}
	
	public void createConnection()
	{
		try
		{
			
	        if(log!= null)
	        {	
	        	log.step("Creating Connection SSL is ( "+isSSL+" )");
	        }
	        
			URL 	url 			= 	new URL(urlString);			
			String 	token 			= 	userName+":"+passwordString;
			Base64.Encoder encoder 	= 	Base64.getEncoder();	 				
			byte[] 	bytes 			= 	encoder.encode(token.getBytes());			
			authenticString 		= 	new String(bytes);				

			if(isSSL)
			{							
				SSLContext sslContext	=	SSLContext.getInstance("SSL");
				sslContext.init(null,trustAllCerts,new java.security.SecureRandom());
				HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());						
				sslConnection = 	(HttpsURLConnection) 	url.openConnection();	
				
				allHostsValid			=	new HostnameVerifier() {public boolean verify(    String hostname,    SSLSession session){return true;}};							
				trustAllCerts 			=	new TrustManager[]{new X509TrustManager()
				{
					public java.security.cert.X509Certificate[] getAcceptedIssuers()																	{return null; }							    
					public void checkClientTrusted	(    java.security.cert.X509Certificate[] certs,    String authType)		{ }							    
					public void checkServerTrusted	(    java.security.cert.X509Certificate[] certs,    String authType)		{ }											    
				}
				};
				
				sslConnection.setUseCaches (false);
				sslConnection.setDoInput(true);
				sslConnection.setDoOutput(true);
				
				if(apiType.equalsIgnoreCase("SOAP") && soapAction != "")
				{
					sslConnection.setRequestProperty("SOAPAction", 	soapAction);
				}

				sslConnection.setHostnameVerifier((javax.net.ssl.HostnameVerifier) allHostsValid);
				
				if(authType.equals("Basic"))
				{
					if(!userName.equals("") || ! passwordString.equals(""))
					{					
						sslConnection.setRequestProperty("Authorization", 		"Basic " + authenticString);					
					}
					
				}
				

				if(httpHeaders != null)
				{
					for (String key : httpHeaders.keySet()) 
					{
						sslConnection.setRequestProperty(key, httpHeaders.get(key));
					}
				}
					
				sslConnection.setRequestProperty("Accept", 				mediaType);													
				sslConnection.setRequestProperty("Content-Type", 		mediaType);		
				sslConnection.setRequestProperty("Content-Language", 	"en-US");
				sslConnection.setRequestProperty("Access-Control-Allow-Methods", "GET, POST, PUT,DELETE");
				
				if(requestMethod.equals("DELETE"))
				{					
					sslConnection.setRequestProperty("Content-Type", 			"application/x-www-form-urlencoded");	
				}
				
				if(mediaType.equals("multipart/form-data") && boundary!="")
				{
					sslConnection.setRequestProperty("Connection", 			"Keep-Alive");
					sslConnection.setRequestProperty("Cache-Control", 		"no-cache");
					sslConnection.setRequestProperty("Content-Type", 		mediaType+"; boundary=----"+boundary);
					sslConnection.setRequestProperty("Accept-Encoding", 	"gzip, deflate, br");				
					sslConnection.setRequestProperty("Accept", 				"application/json, text/plain, */*");
				}
				
				if(acceptMediaType != null || acceptMediaType.equals(""))
				{
					sslConnection.setRequestProperty("Accept", 				acceptMediaType);					
				}
				sslConnection.setRequestMethod(requestMethod);
			}
			else
			{

				urlConnection = (HttpURLConnection)url.openConnection();								
				if(apiType.equalsIgnoreCase("SOAP") && soapAction != null)
				{
					urlConnection.setRequestProperty("SOAPAction", 	soapAction);
				}
				
				
				if(authType.equals("Basic"))
				{
					if(!userName.equals("") || ! passwordString.equals(""))
					{
						urlConnection.setRequestProperty("Authorization", 		"Basic " + authenticString);					
					}						
				}
								
				if(httpHeaders != null)
				{
					for (String key : httpHeaders.keySet()) 
					{
						urlConnection.setRequestProperty(key, httpHeaders.get(key));
					}
				}
					
				urlConnection.setRequestProperty("Accept", 			mediaType);										
				urlConnection.setRequestProperty("Content-Type", 	mediaType);	
				
				if(requestMethod.equals("DELETE"))
				{					
					urlConnection.setRequestProperty("Content-Type", 			"application/x-www-form-urlencoded");	
				}
				
				if(mediaType.equals("multipart/form-data") && boundary!="")
				{
					urlConnection.setRequestProperty("Connection", 			"Keep-Alive");
					urlConnection.setRequestProperty("Cache-Control", 		"no-cache");
					urlConnection.setRequestProperty("Content-Type", 		mediaType+"; boundary = "+boundary);
					urlConnection.setRequestProperty("Accept-Encoding", 	"multipart/form-data,gzip, deflate, br");
					urlConnection.setRequestProperty("Accept", 				"application/json, text/plain, */*");					
				}			
				
				if(acceptMediaType != null || acceptMediaType.equals(""))
				{
					urlConnection.setRequestProperty("Accept", 				acceptMediaType);					
				}
				
				urlConnection.setRequestProperty("Content-Language","en-US");	
				urlConnection.setRequestMethod(requestMethod);
				urlConnection.setUseCaches (false);
				urlConnection.setDoInput(true);
				urlConnection.setDoOutput(true);
			}
			
		}
		catch(Exception  e)
		{
			System.out.println("Connection Exception");
			
			if(log != null)
			{	
				log.exception(e,"Connection Exception");
			}
		}
	}
	
	public void executeRequest()
	{		
		if(urlString == null || urlString.trim().equals("")) { Logger.showMessage("Exception", "Unable to Connect to Empty URL"); return; }
		createConnection();
		
        if(log!= null)
        {	
        	log.step("Executing Request Connection SSL is ( "+isSSL+" )");
        }
        
		OutputStreamWriter writer;	
		try
		{				
			if(isSSL)
			{				
				if(!requestMethod.equals("GET"))
				{	
					try
					{						
						if(!mediaType.equals("multipart/form-data"))
						{		
							writer = new OutputStreamWriter( sslConnection.getOutputStream());	
							writer.write(requestContent.toCharArray());
							writer.flush ();
							writer.close ();
						}						
						else
						{									
							MultipartEntity multipartEntity = new MultipartEntity(HttpMultipartMode.STRICT);
							multipartEntity.addPart("requestForm", new StringBody(requestContent.toString()));								
							sslConnection.setRequestProperty("Content-Type", multipartEntity.getContentType().getValue());											
							OutputStream outStream = sslConnection.getOutputStream();
							multipartEntity.writeTo(outStream);
							outStream.close();
						}
						sslConnection.connect();

					}
					catch(Exception ex) 
					{
						System.out.println("API Invocation Exception");
						if(log != null)
						{	
							log.exception(ex,"API Invocation Exception");
						}
					}
				}								
				
				responseCode	=	new Integer(sslConnection.getResponseCode()).toString();				
				responseContent = 	ReadHttpsResponse(sslConnection);				
				responseContent = 	formatResponse(responseContent);	
				respHeaders 	= 	sslConnection.getHeaderFields();
				sslConnection.disconnect();
			}
			else
			{
				if(urlConnection == null)
				{
					createConnection();	
				}				
				if(!requestMethod.equals("GET"))
				{											
					try
					{				
						urlConnection.setUseCaches (false);
						urlConnection.setDoInput(true);
						urlConnection.setDoOutput(true);
						
						if(!mediaType.equals("multipart/form-data"))
						{		
							writer = new OutputStreamWriter( urlConnection.getOutputStream());	
							writer.write(requestContent.toCharArray());
							writer.flush ();
							writer.close ();
						}						
						else
						{									
							MultipartEntity multipartEntity = new MultipartEntity(HttpMultipartMode.STRICT);
							multipartEntity.addPart("requestForm", new StringBody(requestContent.toString()));								
							urlConnection.setRequestProperty("Content-Type", multipartEntity.getContentType().getValue());											
							OutputStream outStream = sslConnection.getOutputStream();
							multipartEntity.writeTo(outStream);
							outStream.close();
						}
						urlConnection.connect();
					}
					catch(Exception ex) 
					{						
						System.out.println("API Invocation Exception");
						//ex.printStackTrace();
						if(log != null)
						{	
						log.exception(ex,"API Invocation Exception");
						}
					}
				}								
				
				responseCode		=	new Integer(urlConnection.getResponseCode()).toString();
				responseContent 	= 	ReadHttpResponse(urlConnection);		
				responseContent  	= 	formatResponse(responseContent);
				respHeaders 		= 	urlConnection.getHeaderFields();
				urlConnection.disconnect();																		
			}		
		}
		catch(Exception  e)
		{
			System.out.println("API Invocation Exception");
			if(log != null)
			{	
			log.exception(e,"API Invocation Exception");
			}
		}
	}
	
	public void showCertificates()
	{
		if(isSSL)
		{
			try
			{
				if(urlString == null || urlString.trim().equals("")) { Logger.showMessage("Exception", "Unable to Connect to Empty URL"); return; }
				
				createConnection();			
				

		        
				sslConnection.connect();					
				if(showCerts.startsWith("Y"))
				{
			        if(log!= null)
			        {	
			        	log.step("Sowing SSL Certificates");
			        }
			        
					Thread.sleep(1000);
					writeMessage("Step",	"Show SSL Certificate");
					writeLine();
					writeMessage("Response Code",	new Integer(sslConnection.getResponseCode()).toString());
					writeMessage("Cipher Suite",	sslConnection.getCipherSuite());			
					Certificate[] certs = (Certificate[]) sslConnection.getServerCertificates();
					for(Certificate cert : certs)
					{
						writeMessage("Certificate Type ",	((java.security.cert.Certificate) cert).getType().trim());
						writeMessage("Cert Hash Code",		new Integer(cert.hashCode()).toString());
						writeMessage("Key Algorithm",		(cert.getPublicKey().getAlgorithm().trim()));
						writeMessage("Key Format",			(cert.getPublicKey().getFormat()).trim());
					} 				
				}
				sslConnection.disconnect();	
			}
			catch(Exception  e)
			{				
				Logger.showMessage("Exception","SSL Certificate Exception");
				if(log != null)
				{	
					log.exception(e,"SSL Certificate Exception");
				}
			}
			finally
			{ 
				if (sslConnection != null)
				sslConnection.disconnect();				
			}			
		}
	}
	
	private  String ReadHttpResponse(HttpURLConnection conn) 
	{		
		
        if(log!= null)
        {	
        	log.step("Reading HTTP Response");
        }
        
		InputStream 		is 				= null;
		BufferedReader 		rd 				= null;
		StringBuffer 		response 		= new StringBuffer(); 
		try
		{		
			if (conn.getResponseCode() >= 400) 
			{
				is = conn.getErrorStream();
			} 
			else 
			{
				is = conn.getInputStream();
			}
			rd = new BufferedReader(new InputStreamReader(is));
			String line;
			while((line = rd.readLine()) != null) 
			{
				response.append(line);
				response.append('\n');
			}					        
		} 
		catch (IOException ioe)
		{
			response.append(ioe.getMessage());
			System.out.println("HTTP Response Read Exception");
			if(log != null)
			{	
				log.exception(ioe,"HTTP Response Read Exception");
			}
		} 
		finally 
		{
			if (is != null) 
			{
				try { is.close(); } catch (Exception e) {}
			}
			if (rd != null)
			{
				try { rd.close(); } catch (Exception e) {}
			}
		}					    
		return(response.toString());
	}

	private  String ReadHttpsResponse(HttpsURLConnection conn) 
	{			    
		InputStream 		is 				= null;
		BufferedReader 		rd 				= null;
		StringBuffer 		response 		= new StringBuffer(); 
		try
		{		

	        if(log!= null)
	        {	
	        	log.step("Reading HTTPS Response");
	        }
	        
			if (conn.getResponseCode() >= 400) 
			{
				is = conn.getErrorStream();
			} 
			else 
			{
				is = conn.getInputStream();
			}

			rd = new BufferedReader(new InputStreamReader(is));
			String line;
			while((line = rd.readLine()) != null) 
			{
				response.append(line);
				response.append('\n');
			}					        
		} 
		catch (Exception ioe)
		{
			response.append(ioe.getMessage());
			System.out.println("HTTPS Response Read Exception");
			if(log != null)
			{	
				log.exception(ioe,"HTTPS Response Read Exception");
			}
		} 
		finally 
		{
			if (is != null) 
			{
				try { is.close(); } catch (Exception e) {}
			}
			if (rd != null)
			{
				try { rd.close(); } catch (Exception e) {}
			}
		}					    
		return(response.toString());
	}
	
	public String getUrlString()
	{
		return urlString;
	}

	public void setUrlString(String urlString)
	{
		this.urlString = urlString;
		if(urlString.startsWith("https"))
		{
			isSSL 					= 	true;
		}
		else
		{
			isSSL = false;
		}
	}

	public String getUserName()
	{
		return userName;
	}

	public void setUserName(String userName)
	{
		this.userName = userName;
	}

	public String getPasswordString() 
	{
		return passwordString;
	}

	public void setPasswordString(String passwordString)
	{
		this.passwordString = passwordString;
	}

	public String getRequestType() 
	{
		return mediaType;
	}

	public void setRequestType(String requestType)
	{
		this.mediaType = requestType;
	}

	public String getRequestMethod() 
	{
		return requestMethod;
	}

	public void setRequestMethod(String requestMethod)
	{
		this.requestMethod = requestMethod;
	}
	
	public String getMediaType()
	{
		return mediaType;
	}

	public void setMediaType(String mediaType) 
	{
		this.mediaType = mediaType;
	}

	public String getResponseContent()
	{
		return responseContent;
	}

	public void setResponseContent(String responseContent)
	{
		this.responseContent = responseContent;
	}

	public String getRequestContent()
	{
		requestContent= requestContent.replaceAll("[\r\n]+", " ");
		return requestContent;
	}

	public void setRequestContent(String requestContent)
	{
		this.requestContent = requestContent;
	}

	public String getApiType()
	{
		return apiType;
	}

	public void setApiType(String apiType)
	{
		this.apiType = apiType;
	}

	public String getSoapAction() 
	{
		return soapAction;
	}

	public void setSoapAction(String soapAction)
	{
		this.soapAction = soapAction;
	}
	
	public String getResponseCode()
	{
		return responseCode;
	}

	public void setResponseCode(String responseCode)
	{
		this.responseCode = responseCode;
	}
	
	public void clearAll()
	{
		urlString		= "";
		userName		= "";
		passwordString	= "";
		authenticString	= ""; 	
		mediaType		= "";
		requestMethod	= "";
		soapAction		= "";
		apiType			= "";
		responseContent = "";
		responseCode	= "";	
		httpHeaders		= null;
		respHeaders		= null;
		//writeLine();
		//System.out.println("\n\n");
	}
	
	public String getAuthenticString()
	{
		return authenticString;
	}

	public void setAuthenticString(String authenticString) 
	{
		this.authenticString = authenticString;
	}
	
	public String getxAuthToken() 
	{
		return xAuthToken;
	}

	public void setxAuthToken(String xAuthToken) 
	{
		this.xAuthToken = xAuthToken;
	}
	
	public String getBoundary()
	{
		return boundary;
	}

	public void setBoundary(String boundary)
	{
		this.boundary = boundary;
	}
	
	public String getLogPath()
	{
		return logPath;
	}

	public void setLogPath(String logPath)
	{
		this.logPath = logPath;
	}
	
	public String getCallingFNName()
	{
		return callingFNName;
	}

	public void setCallingFNName(String callingFNName)
	{
		this.callingFNName = callingFNName;
	}
	
	public String getAcceptMediaType()
	{
		return acceptMediaType;
	}

	public void setAcceptMediaType(String acceptMediaType)
	{
		this.acceptMediaType = acceptMediaType;
	}
	
	public String getAuthType()
	{
		return authType;
	}

	public void setAuthType(String authType)
	{
		this.authType = authType;
	}
	
	public String getShowCerts()
	{
		return showCerts;
	}

	public void setShowCerts(String showCerts)
	{
		this.showCerts = showCerts;
	}

	public void displayValue(String key , String value)
	{
		responseContent = responseContent.trim();
		if(responseContent.startsWith("{") || responseContent.startsWith("["))
		{
			String message = String.format("%-30s\t\t %-100s" ,key, ":\t"+JSONProcessor.getValue(responseContent, value));
			writeMessage(message);
		}
		else if(responseContent.startsWith("<"))
		{
			String message = String.format("%-30s\t\t %-100s" ,key, ":\t"+XMLProcessor.getValue(responseContent, value));
			writeMessage(message);
		}		
	}
	
	public void showProperties()
	{
		try
		{		
	        if(log!= null)
	        {	
	        	log.step("Displaying API Properties");
	        }
			writeLine();
			writeMessage("Step","Show API Properties");
			writeLine();
			writeMessage("Test Method",	callingFNName);
			writeMessage("apiType",			getApiType());
			writeMessage("responseCode",	getResponseCode());
			writeMessage("urlString",		getUrlString());
			writeMessage("userName",		getUserName());				
			writeMessage("passwordString",	getPasswordString());
			writeMessage("authenticString",	getAuthenticString());
			writeMessage("mediaType",		getMediaType());
			writeMessage("acceptMediaType",	getAcceptMediaType());
			writeMessage("requestMethod",	getRequestMethod());
			
			if(getApiType().equals("SOAP"))
			{
				writeMessage("soapAction",		getSoapAction());				
			}
			writeMessage("requestContent",	getRequestContent());
			writeMessage("responseContent",	getResponseContent());
			
			if(log != null)
			{	
				log.info("API Property","Test Method",		callingFNName);
				log.info("API Property","apiType",			getApiType());
				log.info("API Property","responseCode",		getResponseCode());
				log.info("API Property","urlString",			getUrlString());
				log.info("API Property","userName",			getUserName());				
				log.info("API Property","passwordString",	getPasswordString());
				log.info("API Property","authenticString",	getAuthenticString());
				log.info("API Property","mediaType",			getMediaType());
				log.info("API Property","acceptMediaType",	getAcceptMediaType());
				log.info("API Property","requestMethod",		getRequestMethod());
				log.info("API Property","requestContent",	getRequestContent());
				log.info("API Property","responseContent",	getResponseContent());				
			}			
			writeLine();

		}
		catch(Exception  e)
		{
			System.out.println("API Properties View Exception");
			if(log != null)
			{	
				log.exception(e,"API Properties View Exception");
			}
		}
	}
	
	private void writeMessage(String key , String value)
	{
		try
		{
			if(key != null && value != null )
			{
	        	String message = String.format("%-30s\t\t %-100s" ,key, ":\t"+value);       	
	        	System.out.println(message);
			}				
		}
		catch(Exception e)
		{
			System.out.println(e);
			e.printStackTrace();
		}           	         
  	}

	private void writeMessage(String message)
	{

        	System.out.println(message);	           	          
  	}
	
	private void writeLine()
	{	           	
			System.out.println("------------------------------------------------------------------------------------------------------------------");
  	}
	
	public void retireEngine()
	{
		try
		{
			clearAll();
		}
		catch(Exception  e)
		{
			System.out.println("Retire Engine Exception");
			if(log != null)
			{	
				log.exception(e,"Retire Engine Exception");
			}

		}
	}
		
	public void showCookies()
	{


		try
		{
	        if(log!= null)
	        {	
	        	log.step("Sowing Cookies");
	        }
	        
			CookieStore cookieJar =  cookieManager.getCookieStore();
		    List <HttpCookie> cookies = cookieJar.getCookies();
		    
		    if(! cookies.isEmpty())
		    {
				writeLine();
				writeMessage("Step","Show Cookies");
				writeLine();		    	
		    }
		    
		    for (HttpCookie cookie: cookies) 
		    {	      	    	
		    	String cookieName = cookie.getName();
		    	String cookieValue = cookie.getValue();
		    	if(cookieName!= null && cookieName!= "") 
		    	writeMessage(cookieName,cookieValue);
		    }			
		    if(! cookies.isEmpty())
		    {
				writeLine();		    	
		    }
		}
		catch(Exception e)
		{
			System.out.println("Show Cookie Exception");
			if(log != null)
			{	
				log.exception(e,"Show Cookie Exception");
			}
		}
	}
	
	public String getCookie(String namedCookie)
	{
		String cookieContent = "";
		try
		{
			CookieStore cookieJar =  cookieManager.getCookieStore();
		    List <HttpCookie> cookies = cookieJar.getCookies();
		    
		    for (HttpCookie cookie: cookies) 
		    {	      	    	
		    	String cookieName = cookie.getName();
		    	//String cookieValue = cookie.getValue();		    
		    	if(cookieName.equals(namedCookie))
		    	{
		    		cookieContent = cookie.toString();
		    		return cookieContent;
		    	}			    	 		    		        
		    }		
		}
		catch(Exception e)
		{
			System.out.println("Get Cookie Exception");
			if(log != null)
			{	
				log.exception(e,"Get Cookie Exception");
			}
		}	
	    return  cookieContent;
	}
	
	public void setCookie(String namedCookie)
	{
		try
		{
			if(isSSL)
			{
				if (sslConnection == null)
				{
					createConnection();
					sslConnection.setRequestProperty("Cookie",namedCookie); 
				}
			}
			else
			{
				if (urlConnection== null)
				{
					createConnection();
					urlConnection.setRequestProperty("Cookie",namedCookie); 
				}
			}
			
		}
		catch(Exception e)
		{
			System.out.println("Set Cookie Exception");
			if(log != null)
			{	
				log.exception(e,"Set Cookie Exception");
			}
		}

	}
	
	public void setHeader(String headerKey,String headerValue)
	{
		try
		{
			if(httpHeaders == null)
			{
				httpHeaders = new Hashtable<String,String>();
			}
			httpHeaders.put(headerKey, headerValue);			
		}
		catch(Exception e)
		{
			System.out.println("Set HTTP Header Exception");
			if(log != null)
			{	
				log.exception(e,"Set HTTP Header Exception");
			}
		}

	}

    
	public Map<String, List<String>> getResponseHeaders() 
    { 
        return respHeaders; 
    } 
    
	public void showResponseHeaders()
	{	

		try
		{
			 if (respHeaders != null)
			 { 
				 
			    if(log!= null)
			    {	
			        	log.step("Sowing HTTP Headers");
			    }
			        
				writeLine();
				writeMessage("Step","Show Response Header");
				writeLine();
					
	            Iterator<String> headerKeys = respHeaders.keySet().iterator(); 
	            while (headerKeys.hasNext())
	            { 
	                 String headerKey 	= (String) headerKeys.next();
	                 
	                 if(headerKey!= null)
	                 {
			 				List<String> headerValues = respHeaders.get(headerKey);
			 				if(headerValues!= null)
			 				{
			 					for(String headerValue:headerValues)
			 					{
			 						writeMessage(headerKey,headerValue);
			 					}
			 				}                	 
	                 }	                 
	            }
	            writeLine();
			 }
			
		}
		catch(Exception e)
		{
			System.out.println(e);
			e.printStackTrace();
		}

		
	}
	
	private String formatResponse(String respContent)
	{
		respContent = 	respContent.replaceAll("&lt;", "<");
		respContent = 	respContent.replaceAll("&gt;", ">");								
		respContent = 	respContent.replaceAll(">", ">\n");				
		respContent = 	respContent.replaceAll("\\<\\?xml(.*.)\\?\\>","");
		respContent = 	respContent.replaceAll("\n", " ");
		respContent = 	respContent.trim();
		return respContent;
		
	}
	
	public String getParamValue(String paramPath)
	{
		try
		{			
			if(responseContent == null || responseContent.equals("") )
			{
				new APIException("NoAPIResponse");
				return "";
			}
			else
			{
				if(getResponseMediaType(responseContent).equals("XML"))
					
				return XMLProcessor.getValue(responseContent, paramPath);
				
				else if(getResponseMediaType(responseContent).equals("JSON"))
					return JSONProcessor.getValue(responseContent, paramPath);
				else
				{
					new APIException("InvalidParameter");
					return "";				
				}
					
			}	
		}
		catch(Exception e)
		{
			System.out.println("Response Param Read Exception");
			if(log != null)
			{	
				log.exception(e,"Response Param Read Exception");
			}
		}
		return "";
	}
	
	private String getResponseMediaType(String respConent)
	{
		respConent = respConent.trim();
		if ( ( respConent.startsWith("[") && respConent.endsWith("]") )  || ( respConent.startsWith("{") && respConent.endsWith("}") ) )
			return "JSON";
		if ( respConent.startsWith("<") && respConent.endsWith(">") ) 
			return "XML";
			
			return "";
	}
	
	public String readRequestFrom(String path)
	{
		return JSONProcessor.readFile(path);
	}
	
	public String paramExists(String path)
	{
		return "";
	}
}
