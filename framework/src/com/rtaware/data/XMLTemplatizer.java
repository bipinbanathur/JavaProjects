package com.rtaware.data;

import java.io.StringReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.rtaware.config.ExcelProcessor;

public class XMLTemplatizer
{
	ExcelProcessor excelProcessor = null;
	
	public XMLTemplatizer(String fileName ,String xmlContent) //String folder , 
	{
		DocumentBuilder docBuilder;
		try
		{
			xmlContent = xmlContent.replaceAll("\\<\\?xml(.*.)\\?\\>","");
			if(excelProcessor==null)
			excelProcessor 	= new ExcelProcessor(fileName);
			
			HSSFSheet activeSheet = excelProcessor.createMainSheet("Main_XML");
			
			docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			InputSource xmlStream = new InputSource();
			xmlStream.setCharacterStream(new StringReader(xmlContent));
			Document xmlDocument = docBuilder.parse(xmlStream);
			
			xmlDocument.getDocumentElement().normalize();
			NodeList nodeList 	= xmlDocument.getElementsByTagName("*");
			for( int index= 0;	index < 1 ;index++) 
			{
					Node 	node 		= 	nodeList.item(index);
					String 	attributes 	= 	"";
					String 	cellComment = 	"";
					if(node.hasAttributes())
					{
				      	NamedNodeMap attrs = node.getAttributes();
						for(int j = 0 ; j<attrs.getLength() ; j++) 
						{
							Attr attribute = (Attr)attrs.item(j);     
							attributes += 	" "+attribute.getName()+"= '"+attribute.getValue()+"'";
						}	
					}
					
					
					if (! node.hasChildNodes())
					{						
						String elementName = node.getNodeName();
						if(! elementName.equals("#text"))
						{
							if(attributes!= null && ! attributes.equals(""))
							{
								cellComment = "Attribs:="+attributes;
								excelProcessor.createColumn(activeSheet,elementName,cellComment);	
							}
							else
							{
								excelProcessor.createColumn(activeSheet,elementName,"");	
							}
													
						}
						elementName = "";
						cellComment = "";
					}
					else
					{
						String sheetName = createSheetName();						
						cellComment ="Type:=NODE*,Page:="+sheetName+"#,Attribs:="+attributes;						
						excelProcessor.createColumn(activeSheet,node.getNodeName(),cellComment);
						iterateNode(	node,	excelProcessor.createSheet(sheetName)	);
						sheetName = "";
						cellComment = "";
					}

			}
			if(excelProcessor != null)
			excelProcessor.closeStream();
			excelProcessor = null;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}
	
	private void iterateNode(Node node,HSSFSheet activeSheet)
	{
		
		NodeList nodeList 	=  node.getChildNodes();
		for( int index= 0;	index < nodeList.getLength();index++)
		{
				Node 	childNode 		= 	nodeList.item(index);	
				String 	attributes 		= 	"";
				String 	cellComment 	= 	"";
				if(childNode.hasAttributes())
				{
			      	NamedNodeMap attrs = childNode.getAttributes();
					for(int j = 0 ; j<attrs.getLength() ; j++) 
					{
						Attr attribute = (Attr)attrs.item(j);     
						attributes += 	" "+attribute.getName()+"= '"+attribute.getValue()+"'";
					}		
				}

				
				if (! childNode.hasChildNodes())
				{	
					String elementName = childNode.getNodeName();
					if(! elementName.equals("#text"))
					{
						if(attributes!= null && ! attributes.equals(""))
						{
							cellComment = "Attribs:="+attributes;
							excelProcessor.createColumn(activeSheet,elementName,cellComment);	
						}
						else
						{
							excelProcessor.createColumn(activeSheet,elementName,"");	
						}
											
					}	
					elementName = "";
					cellComment = "";
				}
				else
				{
					
					String sheetName = createSheetName();
					cellComment ="Type:=NODE*,Page:="+sheetName+"#,Attribs:="+attributes;	
					excelProcessor.createColumn(activeSheet,childNode.getNodeName(),cellComment);
					iterateNode(	childNode,	excelProcessor.createSheet(sheetName)	);
					sheetName 	= "";
					cellComment = "";
				}
				
		}		
	}
	
	private String createSheetName()
	{
		int randomNum = 0 + (int)(Math.random() * 10000);
		return "ND"+randomNum;
	}


}
