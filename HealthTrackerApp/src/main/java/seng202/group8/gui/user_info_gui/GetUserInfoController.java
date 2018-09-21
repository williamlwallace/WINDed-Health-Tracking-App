package seng202.group8.gui.user_info_gui;

import com.jfoenix.controls.JFXButton;
import java_sqlite_db.SQLiteJDBC;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.application.HostServices;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import seng202.group8.gui.GUIController;
import seng202.group8.user.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import seng202.group8.user.user_stats.Sex;

//import javax.xml.soap.Text;
import java.io.IOException;

public class GetUserInfoController {


    @FXML
    private ComboBox sexChoice;

    @FXML
    private JFXButton enterDetailsButton;

    @FXML
    private JFXTextField nameField;

    @FXML
    private JFXTextField ageField;

    @FXML
    private JFXTextField weightField;

    @FXML
    private JFXTextField heightField;

    @FXML
    private Label errorText;



    private User user;
    private Stage stage;
    private HostServices host;

    @FXML
    public void initialize() {
        sexChoice.getItems().removeAll(sexChoice.getItems());
        sexChoice.getItems().addAll("Male", "Female");
        sexChoice.getSelectionModel().select("Male");
    }

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
        else if (age == 0) {
            if (errorMessage.length() > 0) {
                errorMessage += " Your age must be a number.";
            } else {
                errorMessage += "Your age must be a number.";
            }
        }
        else if (weight == 0.0){
            if (errorMessage.length() > 0) {
                errorMessage += " Your weight must be a number.";
            } else {
                errorMessage += "Your weight must be a number.";
            }
        }
        else if (height == 0.0) {
            if (errorMessage.length() > 0) {
                errorMessage += " Your height must be a number.";
            } else {
                errorMessage += "Your height must be a number.";
            }
        }

        if (errorMessage.length() > 0) {
            errorText.setText(errorMessage);
        } else {
            String sex = (String) sexChoice.getSelectionModel().getSelectedItem();
            if (sex.equals("Female")) {
                user = new User(name, age, weight, height, Sex.FEMALE);
            } else {
                user = new User(name, age, weight, height, Sex.MALE);
            }
            try {
                loadMainFrame();
            } catch (IOException e) {
                System.out.println("Something wrong in GUI: " + e);
            }
        }

    }

    private void loadMainFrame() throws IOException {
        SQLiteJDBC database = new SQLiteJDBC();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../../../resources/views/mainFrame.fxml"));
        Parent root = loader.load();
        GUIController guiController = loader.getController();
        guiController.setUser(user);

        database.saveUser(user, 1);

        guiController.setStage(stage);
        guiController.setHostServices(host);
        stage.setTitle("WINded");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        guiController.setToHome();
        stage.show();
    }

    @FXML
    private void quitApp(ActionEvent event) {
        stage.close();
    }

    public User getUser() {
        return user;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setHostServices(HostServices host) {
        this.host = host;
    }
}
