package com.rtaware.ui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class FormController implements Initializable {
    
    @FXML
    private TextField field;

    @FXML
    private Label label;
    
    @FXML
    private void handleAction(ActionEvent event) {
        String text = field.getText();
        label.setText(text);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    
}
