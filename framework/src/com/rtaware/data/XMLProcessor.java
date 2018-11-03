package com.rtaware.data;


import java.util.*;
import javax.xml.parsers.*;
import javax.xml.xpath.*;
import org.w3c.dom.*;
import org.xml.sax.InputSource;



import java.io.*;

public class XMLProcessor
{
	
    public static String getValue(String responseXML, String elementPath,int elementIndex)
    {	
    		ArrayList<String> elementValues = new ArrayList<String>();
    		try
    		{
    				DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
    				InputSource xmlStream = new InputSource();
    				xmlStream.setCharacterStream(new StringReader(responseXML));
    				Document xmlDocument = docBuilder.parse(xmlStream);
    				
    				if(elementPath.indexOf('/')!= -1 )
    				{
    					StringTokenizer st = new StringTokenizer(elementPath,"/");
    					String token = "";
    					elementPath = "";
    					while(st.hasMoreTokens())
    					{
    						
    						token = st.nextToken();
    						if(token.indexOf(":") != -1)
    						{
    							token = token.substring(token.indexOf(":")+1,token.length()).trim();
    						}
    						elementPath += "/"+token;
    						token = "";
    						
    					}

    						
    						XPathFactory 	 xPathFactory 	= 	XPathFactory.newInstance();
    						XPath 		 	 xPath 			= 	xPathFactory.newXPath();
    						XPathExpression  xPression 		= 	xPath.compile(elementPath);	    						
    						NodeList 		 nodeList 		= 	(NodeList) xPression.evaluate(xmlDocument, XPathConstants.NODESET);

    						for (int i = 0; i < nodeList.getLength(); ++i) 
    						{	    								
    								Element element = (Element) nodeList.item(i);	    		    							 
    							 	if(	element.getTextContent()!= null	)
    							 	{
    							 			elementValues.add(element.getTextContent());
    							 	
    							 	}
    							 	else		
    							 	elementValues.add("");
    						}
	    			}
    				else
    				{
						if(elementPath.indexOf(":") != -1)
						{
							elementPath = elementPath.substring(elementPath.indexOf(":")+1,elementPath.length()).trim();
						}
    				
					NodeList nodeList 	= xmlDocument.getElementsByTagName(elementPath);

					for (int i = 0; i < nodeList.getLength(); ++i) 
					{
							Element element = (Element) nodeList.item(i);	    	    							 
						 	if(	element.getTextContent()!= null	) 
						 	{							 		
						 		elementValues.add(element.getTextContent());
						 	}
						 		
						 	else		elementValues.add("");
					}
    					
    				}
    				
    				if(elementIndex >= elementValues.size())
    				{
    					System.out.println("Path : "+elementPath+" Index : "+ elementIndex+" Value : Invalid Index");
    					return "";
    				}
    				else 
    				{
    					System.out.println("Path : "+elementPath+" Index : "+ elementIndex+" Value : "+elementValues.get(elementIndex));	    					
    					return elementValues.get(elementIndex);
    				}
    		}
    		catch(Exception e)
    		{
    				
    		}
    		return "";
    }
    
    public static String getValue(String responseXML, String elementPath)
    {	
    		ArrayList<String> elementValues = new ArrayList<String>();
    		try
    		{
    				DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
    				InputSource xmlStream = new InputSource();
    				xmlStream.setCharacterStream(new StringReader(responseXML));
    				Document xmlDocument = docBuilder.parse(xmlStream);
    				
    				xmlDocument.getDocumentElement().normalize();
					NodeList nodes 	= xmlDocument.getElementsByTagName("*");

		            
					for( int index= 0;	index < nodes.getLength();index++)
					{

							Node node 		= 	nodes.item(index);						
							if (! node.hasChildNodes())
							{
									//System.out.println(node.getNodeName());
							}
					}
    					    				
    				if(elementPath.indexOf('/')!= -1 )
    				{
    					StringTokenizer st = new StringTokenizer(elementPath,"/");
    					String token = "";
    					elementPath = "";
    					while(st.hasMoreTokens())
    					{
    						
    						token = st.nextToken();
    						if(token.indexOf(":") != -1)
    						{
    							token = token.substring(token.indexOf(":")+1,token.length()).trim();
    						}
    						elementPath += "/"+token;
    						token = "";
    						
    					}

    						
    						XPathFactory 	 xPathFactory 	= 	XPathFactory.newInstance();
    						XPath 		 	 xPath 			= 	xPathFactory.newXPath();
    						XPathExpression  xPression 		= 	xPath.compile(elementPath);	    						
    						NodeList 		 nodeList 		= 	(NodeList) xPression.evaluate(xmlDocument, XPathConstants.NODESET);

    						for (int i = 0; i < nodeList.getLength(); ++i) 
    						{	    								
    								Element element = (Element) nodeList.item(i);	    		    							 
    							 	if(	element.getTextContent()!= null	)
    							 	{
    							 			elementValues.add(element.getTextContent().trim());
    							 	
    							 	}
    							 	else		
    							 	elementValues.add("");
    						}
	    			}
    				else
    				{
						if(elementPath.indexOf(":") != -1)
						{
							elementPath = elementPath.substring(elementPath.indexOf(":")+1,elementPath.length()).trim();
						}
    				
						NodeList nodeList 	= xmlDocument.getElementsByTagName(elementPath);

						for (int i = 0; i < nodeList.getLength(); ++i) 
						{
							Element element = (Element) nodeList.item(i);	    	    							 
						 	if(	element.getTextContent()!= null	) 
						 	{							 		
						 		elementValues.add(element.getTextContent().trim());
						 	}
						 		
						 	else		elementValues.add("");
						}    					
    				}
    				

    				//System.out.println("Path : "+elementPath+" Value : "+elementValues.get(0));	   
    				return elementValues.get(0);

    		}
    		catch(Exception e)
    		{
    				
    		}
    		return "";
    }

    public static String getValue(File xmlFile, String elementPath)
    {	
    		ArrayList<String> elementValues = new ArrayList<String>();
    		try
    		{
    				String responseContent = readFile(xmlFile);
    				
    				responseContent = responseContent.replaceAll("&lt;", "<");
    				responseContent = responseContent.replaceAll("&gt;", ">");								
    				responseContent = responseContent.replaceAll(">", ">\n");				
    				responseContent = responseContent.replaceAll("\\<\\?xml(.*.)\\?\\>","");
    				
    				DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
    				InputSource xmlStream = new InputSource();
    				xmlStream.setCharacterStream(new StringReader(responseContent));
    				Document xmlDocument = docBuilder.parse(xmlStream);
    					    				
    				if(elementPath.indexOf('/')!= -1 )
    				{
    					StringTokenizer st = new StringTokenizer(elementPath,"/");
    					String token = "";
    					elementPath = "";
    					while(st.hasMoreTokens())
    					{
    						
    						token = st.nextToken();
    						if(token.indexOf(":") != -1)
    						{
    							token = token.substring(token.indexOf(":")+1,token.length()).trim();
    						}
    						elementPath += "/"+token;
    						token = "";
    						
    					}

    						
    						XPathFactory 	 xPathFactory 	= 	XPathFactory.newInstance();
    						XPath 		 	 xPath 			= 	xPathFactory.newXPath();
    						XPathExpression  xPression 		= 	xPath.compile(elementPath);	    						
    						NodeList 		 nodeList 		= 	(NodeList) xPression.evaluate(xmlDocument, XPathConstants.NODESET);

    						for (int i = 0; i < nodeList.getLength(); ++i) 
    						{	    								
    								Element element = (Element) nodeList.item(i);	    		    							 
    							 	if(	element.getTextContent()!= null	)
    							 	{
    							 			elementValues.add(element.getTextContent().trim());
    							 	
    							 	}
    							 	else		
    							 	elementValues.add("");
    						}
	    			}
    				else
    				{
						if(elementPath.indexOf(":") != -1)
						{
							elementPath = elementPath.substring(elementPath.indexOf(":")+1,elementPath.length()).trim();
						}
    				
					NodeList nodeList 	= xmlDocument.getElementsByTagName(elementPath);

					for (int i = 0; i < nodeList.getLength(); ++i) 
					{
							Element element = (Element) nodeList.item(i);	    	    							 
						 	if(	element.getTextContent()!= null	) 
						 	{							 		
						 		elementValues.add(element.getTextContent().trim());
						 	}
						 		
						 	else		elementValues.add("");
					}
    					
    				}
    				

    				//System.out.println("Path : "+elementPath+" Value : "+elementValues.get(0));	   
    				return elementValues.get(0);

    		}
    		catch(Exception e)
    		{
    				
    		}
    		return "";
    }
    
	public String[] getValues(String responseXML, String elementPath)
    {	
    		String valuesArray[] = null;
    		ArrayList<String> elementValues = new ArrayList<String>();
    		try
    		{
    				DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
    				InputSource xmlStream = new InputSource();
    				xmlStream.setCharacterStream(new StringReader(responseXML));
    				Document xmlDocument = docBuilder.parse(xmlStream);
    					    				
    				if(elementPath.indexOf('/')!= -1 )
    				{
    					StringTokenizer st = new StringTokenizer(elementPath,"/");
    					String token = "";
    					elementPath = "";
    					while(st.hasMoreTokens())
    					{
    						
    						token = st.nextToken();
    						if(token.indexOf(":") != -1)
    						{
    							token = token.substring(token.indexOf(":")+1,token.length()).trim();
    						}
    						elementPath += "/"+token;
    						token = "";
    						
    					}

    						
    					XPathFactory 	 xPathFactory 	= 	XPathFactory.newInstance();
    					XPath 		 xPath 		= 	xPathFactory.newXPath();
    					XPathExpression xPression 	= 	xPath.compile(elementPath);	    						
    					NodeList 		 nodeList 	= 	(NodeList) xPression.evaluate(xmlDocument, XPathConstants.NODESET);

    					for (int i = 0; i < nodeList.getLength(); ++i) 
    					{	    								
    							 Element element = (Element) nodeList.item(i);	    		    							 
    							 if(	element.getTextContent()!= null	)
    							 {
    							 			elementValues.add(element.getTextContent());
    							 	
    							 }
    							 else		
    							 elementValues.add("");
    					}
	    			}
    				else
    				{
						if(elementPath.indexOf(":") != -1)
						{
							elementPath = elementPath.substring(elementPath.indexOf(":")+1,elementPath.length()).trim();
						}
    				
						NodeList nodeList 	= xmlDocument.getElementsByTagName(elementPath);

						for (int i = 0; i < nodeList.getLength(); ++i) 
						{
							Element element = (Element) nodeList.item(i);	    	    							 
						 	if(	element.getTextContent()!= null	) 
						 	{							 		
						 		elementValues.add(element.getTextContent());
						 	}
						 		
						 	else		elementValues.add("");
						}
    					
    				}
    				valuesArray =  (String[]) elementValues.toArray();
    				
    		}
    		catch(Exception e)
    		{
    				System.out.println("Exception : "+e);
    		}
    		return valuesArray;
    }
	
    public void verifyValue(String responseXML, String elementPath,int elementIndex,String expectedValue)
    {	
    		ArrayList<String> elementValues = new ArrayList<String>();
    		try
    		{
    				DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
    				InputSource xmlStream = new InputSource();
    				xmlStream.setCharacterStream(new StringReader(responseXML));
    				Document xmlDocument = docBuilder.parse(xmlStream);
    				
    				
    				if(elementPath.indexOf('/')!= -1 )
    				{
    					StringTokenizer st = new StringTokenizer(elementPath,"/");
    					String token = "";
    					elementPath = "";
    					while(st.hasMoreTokens())
    					{
    						
    						token = st.nextToken();
    						if(token.indexOf(":") != -1)
    						{
    							token = token.substring(token.indexOf(":")+1,token.length()).trim();
    						}
    						elementPath += "/"+token;
    						token = "";
    						
    					}

    						
    						XPathFactory 	 xPathFactory 	= 	XPathFactory.newInstance();
    						XPath 		 xPath 		= 	xPathFactory.newXPath();
    						XPathExpression xPression 	= 	xPath.compile(elementPath);	    						
    						NodeList 		 nodeList 	= 	(NodeList) xPression.evaluate(xmlDocument, XPathConstants.NODESET);

    						for (int i = 0; i < nodeList.getLength(); ++i) 
    						{	    								
    								Element element = (Element) nodeList.item(i);	    		    							 
    							 	if(	element.getTextContent()!= null	)
    							 	{
    							 			elementValues.add(element.getTextContent());
    							 	
    							 	}
    							 	else		
    							 	elementValues.add("");
    						}
	    			}
    				else
    				{
						if(elementPath.indexOf(":") != -1)
						{
							elementPath = elementPath.substring(elementPath.indexOf(":")+1,elementPath.length()).trim();
						}
    				
					NodeList nodeList 	= xmlDocument.getElementsByTagName(elementPath);

					for (int i = 0; i < nodeList.getLength(); ++i) 
					{
							Element element = (Element) nodeList.item(i);	    	    							 
						 	if(	element.getTextContent()!= null	) 
						 	{							 		
						 		elementValues.add(element.getTextContent());
						 	}							 		
						 	else		elementValues.add("");
					}
    					
    				}
    				
    				if(elementIndex >= elementValues.size()){ System.out.println("Path : "+elementPath+" Index : "+ elementIndex+" Value : Invalid Index");}
    				else 
    				{
    					String actualValue = elementValues.get(elementIndex);
    					System.out.println("Path : "+elementPath+" Index : "+ elementIndex+" Actual Value : "+actualValue+ " Expected Value :  "+expectedValue);
    					if(actualValue.equals(expectedValue))
    					{
    						System.out.println("Pass : Actual and Expected Values are Matching");
    					}
    					else
    					{
    						System.out.println("Fail : Actual and Expected Values are not Matching");
    					}
    				}
    		}
    		catch(Exception e)
    		{
    			System.out.println(e);
    		}	    		
    }
    
    public static String readFile(File xmlFile) throws IOException
    {


        StringBuilder fileContents = new StringBuilder((int)xmlFile.length());
        Scanner scanner = new Scanner(xmlFile);
        String lineSeparator = System.getProperty("line.separator");

        try
        {
            while(scanner.hasNextLine()) 
            {        
                fileContents.append(scanner.nextLine() + lineSeparator);
            }
            return fileContents.toString();
        } 
        finally
        {
            scanner.close();
        }
    }

}
