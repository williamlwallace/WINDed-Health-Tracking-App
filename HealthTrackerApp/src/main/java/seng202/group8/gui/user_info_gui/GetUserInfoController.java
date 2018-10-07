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
import seng202.group8.gui.switch_user.SwitchUserController;
import seng202.group8.user.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import seng202.group8.user.user_stats.Sex;

//import javax.xml.soap.Text;
import java.io.IOException;

/**
 * @author lfa69, sgv15
 * GetUserInfo.fxml controller.
 * Deals with adding logic to the login screen. Allows new user to sign up.
 */
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
    private SQLiteJDBC DB = new SQLiteJDBC();

    @FXML
    public void initialize() {
        sexChoice.getItems().removeAll(sexChoice.getItems());
        sexChoice.getItems().addAll("Male", "Female");
        sexChoice.getSelectionModel().select("Male");
    }

    /**
     *
     * @param event
     * Handles the errors due to misfilling some fields, if everything is set correctly,
     * it creates a user object and stores it in the database.
     */
    public void enterDetails(ActionEvent event) {
        //System.out.println("Pressed");
        String name = null;
        int age = 0;
        double weight = 0.0;
        double height = 0.0;
        String errorMessage = "";
        errorText.setText("");
        if (nameField.getText() != null && !nameField.getText().isEmpty() && !nameField.getText().matches(".*\\d+.*")) {
            String newUserName = nameField.getText().trim();
            if (newUserName.length() > 0) {
                name = newUserName;
            }
        }
        if (ageField.getText() != null && !ageField.getText().isEmpty()) {
//            System.out.println("hello: " + ageField.getText());
            try {
                Integer newUserAge = Integer.parseInt(ageField.getText());
                if (newUserAge > 0) {
                    age = newUserAge;
                } else {
                    throw new NumberFormatException();
                }
//                System.out.println(age);
            } catch(NumberFormatException e) {
                age = 0;
//                System.out.println("the age is: " + ageField.getText());
            }
        }
        if (weightField.getText() != null && !weightField.getText().isEmpty()) {
            try {
                Double newUserWeight = Double.parseDouble(weightField.getText());
                if (newUserWeight > 0) {
                    weight = newUserWeight;
                } else {
                    throw new NumberFormatException();
                }
            } catch(NumberFormatException e) {
                weight = 0.0;
            }
        }
        if (heightField.getText() != null && !heightField.getText().isEmpty()) {
            try {
                Double newUserHeight = Double.parseDouble(heightField.getText());
                if (newUserHeight > 0) {
                    height = newUserHeight;
                } else {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                height = 0.0;
            }
        }
        if (name == null) {
            errorMessage += "You must enter a valid name to proceed, no numbers can be present";
        }
        else if (age == 0) {
            if (errorMessage.length() > 0) {
                errorMessage += " Your age must be a positive number.";
            } else {
                errorMessage += "Your age must be a positive number.";
            }
        }
        else if (weight == 0.0){
            if (errorMessage.length() > 0) {
                errorMessage += " Your weight must be a positive number.";
            } else {
                errorMessage += "Your weight must be a positive number.";
            }
        }
        else if (height == 0.0) {
            if (errorMessage.length() > 0) {
                errorMessage += " Your height must be a positive number.";
            } else {
                errorMessage += "Your height must be a positive number.";
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

    /**
     *
     * @throws IOException
     * Loads mainFrame.fxml after the user has been created.
     */
    private void loadMainFrame() throws IOException {
        SQLiteJDBC database = new SQLiteJDBC();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/views/mainFrame.fxml"));
        Parent root = loader.load();
        GUIController guiController = loader.getController();
        guiController.setUser(user);

        database.saveUser(user, user.getUserID());

        guiController.setStage(stage);
        guiController.setHostServices(host);
        stage.setTitle("WINded");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        guiController.setToHome();
        stage.show();
    }

    /**
     *
     * @param event
     * cancelBtn event listener, goes back to the select user screen or quits the app if there are no users.
     */
    @FXML
    private void loadUserScreen(ActionEvent event) throws IOException {
        if (DB.retrieveAllUsers().isEmpty()) {
            stage.close();
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/views/switch_user.fxml"));
            Parent root = loader.load();
            SwitchUserController switchUserController = loader.getController();
            switchUserController.setHostServices(host);
            switchUserController.setStage(stage);
            switchUserController.setUser(user);
            stage.setTitle("WINded");
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }

    }

    /**
     *
     * @return the user property
     */
    public User getUser() {
        return user;
    }

    /**
     *
     * @return the stage property
     */
    public Stage getStage() {
        return stage;
    }

    /**
     *
     * @param stage a new Stage object for the stage property
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     *
     * @param host a HostServices object
     */
    public void setHostServices(HostServices host) {
        this.host = host;
    }
}
