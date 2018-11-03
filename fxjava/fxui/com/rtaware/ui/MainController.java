package com.rtaware.ui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class MainController implements Initializable 
{
    
	@FXML
    private void handleFileNew(ActionEvent event)
	{
		System.out.println("Hello World");
    }
	
	@FXML
    private void onFileClose(ActionEvent event)
	{
		System.out.println("File Close");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb)
	{
	
    }    
	
}
