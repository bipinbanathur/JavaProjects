<%@page import="java.io.*" %> 
<%@page import="java.util.*" %> 
        <%!  
		
				public void GetDirectory(String a_Path, Vector a_files, Vector a_folders) throws Exception
				{
					File l_Directory = new File(a_Path);
					File[] l_files = l_Directory.listFiles();

					for (int c = 0; c < l_files.length; c++)
					{
						if (l_files[c].isDirectory()) 
						{
							a_folders.add(l_files[c].getName());
						} 
						else 
						{
							a_files.add(l_files[c].getName());
						}
					}


            }
        %> 

        <%
			try
			{
				Vector l_Files = new Vector(), l_Folders = new Vector();
				GetDirectory("C:\\Apache\\apache-tomcat-8.0.35\\webapps\\reports\\Execution001\\", l_Files, l_Folders);
				
				out.println("<ul>");
				for (int a = 0; a < l_Files.size(); a++) 
				{
					out.println("<li> <a href=http://localhost:8080/reports/"+ l_Files.elementAt(a).toString()+">"+l_Files.elementAt(a).toString()+"</a></li>");
				}
				out.println("</ul>");
				out.println("<ul>");
				for (int a = 0; a < l_Folders.size(); a++) 
				{
					out.println("<li> <a href=http://localhost:8080/reports/"+ l_Folders.elementAt(a).toString()+">"+l_Folders.elementAt(a).toString()+"</a></li>");
				}
				out.println("</ul>");
				
			}
			catch(Exception e)
			{
				out.println(e);
			}
        %> 