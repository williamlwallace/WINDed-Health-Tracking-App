package seng202.group8.gui.user_entry_displayer;

import javafx.fxml.FXML;

import java.awt.*;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;


public class UserEntryController {

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField ageTextField;

    @FXML
    private TextField weightTextField;

    @FXML
    private TextField heightTextField;

    @FXML
    private Button submitButton;

    public void submitUserData() {
        if (!nameTextField.getText().isEmpty()) {
            String Name = nameTextField.getText();
        }
    }

    }
