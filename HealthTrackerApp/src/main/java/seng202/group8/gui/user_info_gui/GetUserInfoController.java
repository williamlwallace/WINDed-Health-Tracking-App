package seng202.group8.gui.user_info_gui;

import seng202.group8.user.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import org.apache.commons.lang3.ObjectUtils;

import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import seng202.group8.user.user_stats.Sex;

//import javax.xml.soap.Text;
import java.awt.*;

public class GetUserInfoController {

    @FXML
    private Button enterDetailsButton;

    @FXML
    private TextField nameField;

    @FXML
    private TextField ageField;

    @FXML
    private TextField weightField;

    @FXML
    private TextField heightField;

    @FXML
    private Text errorText;

    private User user;

    public void enterDetails(ActionEvent event) {
        System.out.println("Pressed");
        String name = null;
        int age = 0;
        double weight = 0.0;
        double height = 0.0;
        String errorMessage = "";
        errorText.setText("");
        if (nameField.getText() != null && !nameField.getText().isEmpty() && !nameField.getText().matches(".*\\d+.*")) {
            name = nameField.getText();
        }
        //System.out.println("hello: " + ageField.getText());
        if (ageField.getText() != null && !ageField.getText().isEmpty()) {
            System.out.println("hello: " + ageField.getText());
            try {
                age = Integer.parseInt(ageField.getText());
                System.out.println(age);
            } catch(NumberFormatException e) {
                age = 0;
                System.out.println("the age is: " + ageField.getText());
            }
        }
        if (weightField.getText() != null && !weightField.getText().isEmpty()) {
            try {
                weight = Double.parseDouble(weightField.getText());
            } catch(NumberFormatException e) {
                weight = 0.0;
            }
        }
        if (heightField.getText() != null && !heightField.getText().isEmpty()) {
            try {
                height = Double.parseDouble(heightField.getText());
            } catch (NumberFormatException e) {
                height = 0.0;
            }
        }
        if (name == null) {
            errorMessage += "You must enter a name to proceed.";
        }
        if (age == 0) {
            if (errorMessage.length() > 0) {
                errorMessage += " Your age must be a number.";
            } else {
                errorMessage += "Your age must be a number.";
            }
        }
        if (weight == 0.0){
            if (errorMessage.length() > 0) {
                errorMessage += " Your weight must be a number.";
            } else {
                errorMessage += "Your weight must be a number.";
            }
        }
        if (height == 0.0) {
            if (errorMessage.length() > 0) {
                errorMessage += " Your height must be a number.";
            } else {
                errorMessage += "Your height must be a number.";
            }
        }
        if (errorMessage.length() > 0) {
            errorText.setText(errorMessage);
        } else {
            user = new User(name, age, weight, height, Sex.MALE);
        }
    }
    public User getUser() {
        return user;
    }
}
