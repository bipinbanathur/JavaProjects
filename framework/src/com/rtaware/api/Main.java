package com.rtaware.api;



import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.UIManager.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.*;

import com.rtaware.config.Console;
import com.rtaware.data.JSONTemplatizer;
import com.rtaware.data.XMLProcessor;
import com.rtaware.data.XMLTemplatizer;





public class Main implements ActionListener, TreeSelectionListener
{

	
	//Menu Related
	 private JMenuBar 					menuBar;	  
	 private JMenu 						fileMenu;
	 private JMenu 						helpMenu;	  
	 private JMenuItem 					openMenuItem;
	 private JMenuItem 					saveMenuItem;
	 private JMenuItem 					exitMenuItem;
	 private JMenuItem 					aboutInsight;	
	  
	// ToolBar Related
	 private JToolBar					toolBar;
	 private JButton 					executeButton;	  
	 private JButton 					clearButton;	  
	 private JButton 					searchButton;
	 private JButton 					generateButton;	  
	 private JButton 					selectAllButton;
	 private JButton 					removeDataValues;	
	 private JButton 					saveButton;
	 private JProgressBar				generationStatus;	  

	 //Templatizer Tree
	 private JTree						tempPayload; 		
	 DefaultMutableTreeNode 			tempRoot;
	 private JScrollPane 				tempScroll;
	 private JScrollPane 				statusScroll;
	 private JScrollPane 				consoleScroll;
	  
	 //Status Text
	 private JTextArea 					statusText;
	 private JProgressBar				progressBar;	  
	 private JButton	   				compGenerate;
	 private JButton	   				compCancel;
	 private String						basePath;
	 private String						imagePath;	  	  
	 private JTextArea 					mainConsole; 	  
	 private String						mediaType	= "";
	  
/*
 * Save Main Console Content
 * Clear Content
 * Remove Data from XML , Remove Repeating Nodes
 * View Generated Request Request Builder
 * Format XML, JSON
 * Check Validity XML JSON
 * Tree View
 * View Log Files
 * 
 * 
 */

	  
	public static void main(String[] args) {new Main();}
	JFrame 		apiUI 	= null;
	public Main()
	{		
			try
			{
				    
					SetLookAndFeel();
					

					basePath  		= 	new File(".").getCanonicalPath();
					imagePath 		= 	basePath+"\\images\\";		
					
					apiUI 	= new JFrame("API Automator");
					Container 	container 				= apiUI.getContentPane();												
					Border  		border 					= BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
					
				    container.setLayout(new BorderLayout());
				    ((JComponent) container).setBorder(border);							
				    try
				    {

				    	Image icon = Toolkit.getDefaultToolkit().getImage( ClassLoader.getSystemResource("images/rt.png"));
				    	apiUI.setIconImage(icon);
				    }
				    catch(Exception e){}
				    
				    apiUI.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
					apiUI.addWindowListener(new java.awt.event.WindowAdapter() {public void windowClosing(java.awt.event.WindowEvent windowEvent){}});							
					apiUI.setFont(new Font("Garamond",Font.BOLD,15));

					//Menu
					ViewMenu(apiUI);		
					
					//ToolBar
					ViewToolBar(container);	
					
					//Connection
					tempScroll 			= new JScrollPane();	
					tempRoot 			= new DefaultMutableTreeNode("Media Types");			
					tempPayload 		= new JTree();		
					tempPayload.setName("Payload Templates");
				
					tempPayload.addTreeSelectionListener(this);  
					tempPayload.setPreferredSize(new Dimension(200, 400));
					tempScroll.getViewport().add( tempPayload );
					
					DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();			
					renderer.setLeafIcon(new ImageIcon(imagePath+ "rm.png"));
					
					
					tempPayload.setCellRenderer(renderer);	
					
					String[] mediaTypes = new String[] {"xml","json"};
					for(int i=0;i<mediaTypes.length;i++)
					{				
						DefaultMutableTreeNode node = new DefaultMutableTreeNode(mediaTypes[i]);			
						tempRoot.add(node);				
					}	
					
					DefaultTreeModel model = (DefaultTreeModel) tempPayload.getModel();
				    model.setRoot(tempRoot);
				    
				    for (int i = 0; i < tempPayload.getRowCount(); i++)  tempPayload.expandRow(i);
													
				    //Console
				    mainConsole 		= new JTextArea();			
					consoleScroll 		= new JScrollPane(mainConsole);
						
				
					new Console(mainConsole);


					//Status Text
				    statusText = new JTextArea();				    
				    statusText.setMaximumSize(new Dimension(100, 800));
				    statusScroll		= new JScrollPane(statusText);				    
				    statusText.setText("");

					container.add(tempScroll,BorderLayout.WEST);
					container.add(consoleScroll,BorderLayout.CENTER);
					container.add(statusScroll,BorderLayout.SOUTH);
					
					
					SwingUtilities.invokeLater(new Runnable() 
					{
					    public void run() 
					    {					    	
				    	  	apiUI.pack();	
							apiUI.setResizable(true);
							apiUI.setSize(800, 600);
							apiUI.setLocation(10, 10);
							apiUI.setVisible( true );
					    }
					  });
	
											  				  
			}
			catch(Exception e)
			{
				JOptionPane.showMessageDialog(null, "Exception : "+e.toString());
				e.printStackTrace();
			}
	}
	
	private void SetLookAndFeel()
	{		
		try
		{
			LookAndFeelInfo[] lf_info = UIManager.getInstalledLookAndFeels();
			for (int i = 0; i < lf_info.length; i++)
			{
				if (lf_info[i].getName().equals("Windows"))
				{
					UIManager.setLookAndFeel(lf_info[i].getClassName());
					continue;
				}
			}
		}
		catch (Exception e)
		{
			System.out.println("Exception : " + e);
		}
	}

	private void ViewMenu(JFrame frame)
	{
		try
		{
			// MenuBar
			menuBar 		= new JMenuBar();
			fileMenu 		= new JMenu("File");
			openMenuItem 	= new JMenuItem("Open");
			saveMenuItem 	= new JMenuItem("Save");
			exitMenuItem 	= new JMenuItem("Exit");

			openMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
			saveMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
			exitMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));

			openMenuItem.addActionListener(this);
			saveMenuItem.addActionListener(this);
			exitMenuItem.addActionListener(this);

			fileMenu.add(openMenuItem);
			fileMenu.add(saveMenuItem);
			fileMenu.add(exitMenuItem);



			// View Menu
			helpMenu 		= new JMenu("Help");
			aboutInsight 	= new JMenuItem("About API Automator");
			helpMenu.add(aboutInsight);
			menuBar.add(fileMenu);
			menuBar.add(helpMenu);
			frame.setJMenuBar(menuBar);

		}
		catch (Exception e)
		{
			System.out.println(e);
		}			
	}
		
	private void ViewToolBar(Container container)
	{
			try
			{
					toolBar 			= 		new	 	JToolBar();	
					
					ImageIcon ex = new ImageIcon (Toolkit.getDefaultToolkit().getImage( ClassLoader.getSystemResource("images/ex.png")));
					ImageIcon cp = new ImageIcon (Toolkit.getDefaultToolkit().getImage( ClassLoader.getSystemResource("images/cp.png")));
					ImageIcon rm = new ImageIcon (Toolkit.getDefaultToolkit().getImage( ClassLoader.getSystemResource("images/rm.png")));
					ImageIcon sr = new ImageIcon (Toolkit.getDefaultToolkit().getImage( ClassLoader.getSystemResource("images/sr.png")));
					ImageIcon sl = new ImageIcon (Toolkit.getDefaultToolkit().getImage( ClassLoader.getSystemResource("images/sl.png")));
					ImageIcon sd = new ImageIcon (Toolkit.getDefaultToolkit().getImage( ClassLoader.getSystemResource("images/sd.png")));
					ImageIcon sv = new ImageIcon (Toolkit.getDefaultToolkit().getImage( ClassLoader.getSystemResource("images/sv.png")));
					
					executeButton		= 	new	 	JButton(ex);	
					searchButton		= 	new 	JButton(cp);
					clearButton			= 	new	 	JButton(rm);																
					selectAllButton		= 	new	 	JButton(sr);								
					removeDataValues	= 	new	 	JButton(sl);						
					generateButton		= 	new	 	JButton(sd);	
					saveButton      	= 	new 	JButton(sv);	
					

					toolBar.setFloatable (false);										
					generationStatus		= new JProgressBar(0,100);
					generationStatus.setIndeterminate(true);
					generationStatus.setVisible(false);		
					
					searchButton		.setEnabled(true);
					clearButton			.setEnabled(true);																
					selectAllButton		.setEnabled(true);	
					generateButton		.setEnabled(true);		
					removeDataValues	.setEnabled(true);		
					saveButton			.setEnabled(true);
					
					executeButton	.setToolTipText			("API Execution");
					searchButton	.setToolTipText			("Search Field");
					clearButton		.setToolTipText			("Uncheckt All");
					clearButton		.setToolTipText			("Select All");				
					removeDataValues.setToolTipText			("Remove Duplicates");
					generateButton	.setToolTipText			("Generate Component");
					saveButton		.setToolTipText			("Save Data Template");
					
				    executeButton		.addActionListener(this);				   
				    searchButton		.addActionListener(this);
				    generateButton		.addActionListener(this);				   
				    selectAllButton		.addActionListener(this);
				    clearButton			.addActionListener(this);				
				    removeDataValues	.addActionListener(this);
				    saveButton			.addActionListener(this);

				    
				    toolBar.add(executeButton);				    
				    toolBar.add(searchButton);				   
				    toolBar.add(removeDataValues);		    
				    toolBar.add(selectAllButton);
				    toolBar.add(clearButton);
				    toolBar.add(generateButton);					    
				    toolBar.add(saveButton);	
				    toolBar.add(generationStatus);	

				    


					container.add(toolBar,BorderLayout.NORTH);
			}
			catch(Exception e)
			{
				e.printStackTrace();
				
			}
	}
	
	public void actionPerformed(ActionEvent event) 
	{									
				 /*---------------------------------------------------------------------------------------------------------------------*/
				 if( event.getSource()== exitMenuItem) 		System.exit(0);	
				 /*---------------------------------------------------------------------------------------------------------------------*/
				 if( event.getSource()== executeButton)   		
				 {
						
						statusText.setText("Select API Template");
						JFileChooser fileChooser = new JFileChooser();
						fileChooser.setCurrentDirectory(new File( new File("").getAbsolutePath()+File.separator+"data"));
						fileChooser.setFileFilter(new FileNameExtensionFilter("xls files (*.xls)","xls"));
						int result = fileChooser.showOpenDialog(apiUI);						
						if (result == JFileChooser.APPROVE_OPTION) 
						{
						    File selectedFile = fileChooser.getSelectedFile();						    
						    statusText.setText("Execution Started ..");						   						   
						    new ExecutionAgent(selectedFile.getAbsolutePath(),"");
						    statusText.setText("Execution Completed");
						}					 
				 }
				 /*---------------------------------------------------------------------------------------------------------------------*/
				 if( event.getSource()== compCancel)  		 		 
				 /*---------------------------------------------------------------------------------------------------------------------*/
				 if( event.getSource()== compGenerate)  	
					 
					 new Thread()
				 	{
					 		public void run() 
					 		{
					 				statusText.setText("Searching Component");
					 				progressBar.setVisible(true);					 			
					 		} 
					 		
				 	}.start();	
				 /*---------------------------------------------------------------------------------------------------------------------*/	
				 if( event.getSource()==generateButton ) 
				 {
					 
					 Thread generateThread = new Thread()
					 {
					      public void run() 
					      {
					    	  	String requestContent = mainConsole.getText();		
					    	  	
					    	  	if(mediaType.trim().equals("") || requestContent.trim().equals(""))
					    	  	{
					    	  		JOptionPane.showMessageDialog(apiUI, "Please Select a Valid Media File");
					    	  	}
					    	  	else
					    	  	{
				    				statusText.setBackground(Color.LIGHT_GRAY);		
				    				
				    				
				    				JFileChooser fileChooser = new JFileChooser();
									fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
									fileChooser.setSelectedFile(new File("Data.xls"));
									fileChooser.setFileFilter(new FileNameExtensionFilter("Data Template (*.xls)","xls"));									
									int result = fileChooser.showSaveDialog(apiUI);									
									if (result == JFileChooser.APPROVE_OPTION) 
									{
									    File selectedFile = fileChooser.getSelectedFile();	
									    generationStatus.setVisible(true);	
									    if(mediaType.equals("json"))
									    {
									    	new JSONTemplatizer(selectedFile.getAbsolutePath(),requestContent);
									    }
									    else if(mediaType.equals("xml"))
									    {
									    	new XMLTemplatizer(selectedFile.getAbsolutePath(),requestContent);
									    }
									    
									    statusText.setText("Execution Completed");
									    generationStatus.setVisible(false);	
									}						    	  		
					    	  	}
									        						        	
					      }
					    };
					    generateThread.start();
				}
				 /*---------------------------------------------------------------------------------------------------------------------*/	
				 if( event.getSource()==removeDataValues )  								 
				 /*---------------------------------------------------------------------------------------------------------------------*/	
				 				
				 /*---------------------------------------------------------------------------------------------------------------------*/
				 if( event.getSource()==saveButton )
				 {
					 Thread generateThread = new Thread()
					 {
					      public void run() 
					      {
				    				statusText.setBackground(Color.LIGHT_GRAY);		
				    				generationStatus.setVisible(true);				
					      }
					 };
					 generateThread.start();					 
				 }
				 /*---------------------------------------------------------------------------------------------------------------------*/
	}
	
	
    public void valueChanged(TreeSelectionEvent e)  
    { 
		try
		{			
			JTree tree = (JTree) e.getSource();
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();

			if (node != null) if (node.isLeaf())
			{
				mediaType =node.toString();
				statusText.setText(mediaType + " Selected");
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
				fileChooser.setFileFilter(new FileNameExtensionFilter("Media Files (*."+mediaType+")",mediaType));						
				int result = fileChooser.showOpenDialog(apiUI);
				
				if (result == JFileChooser.APPROVE_OPTION) 
				{
				    File selectedFile = fileChooser.getSelectedFile();
				    String requestContent = XMLProcessor.readFile(selectedFile);
				    mainConsole.setText(requestContent);
				}
			}
		}
		catch (Exception ex)
		{
			System.out.println(ex);
			ex.printStackTrace();
		}
    }

}







