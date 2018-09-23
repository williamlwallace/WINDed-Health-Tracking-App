package seng202.group8.gui.edit_user;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java_sqlite_db.SQLiteJDBC;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import seng202.group8.gui.GUIController;
import seng202.group8.user.User;
import java.io.IOException;

public class EditUserController {

    @FXML
    private JFXTextField nameField;

    @FXML
    private JFXTextField ageField;

    @FXML
    private JFXTextField weightField;

    @FXML
    private JFXTextField heightField;

    @FXML
    private JFXButton submitBtn;

    @FXML
    private JFXButton cancelBtn;

    @FXML
    private Label errorText;

    private User user;
    private Stage stage;

    private GUIController guiController;

    public User getUser() { return user; }

    public void setUser(User user) {
        this.user = user;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) { this.stage = stage; }


    public void setUp()
    {
        nameField.setText(user.getName());
        ageField.setText(Integer.toString(user.getAge()));
        weightField.setText(Double.toString(user.getWeight()));
        heightField.setText(Double.toString(user.getHeight()));
    }


    public void submitPressed(ActionEvent event)
    {
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
            user.setName(name);
            user.getUserActivities().setTitle(user.getName() + "'s activities collection");
            user.setAge(age);
            user.updateWeight(weight);
            user.setHeight(height);
            try {
                SQLiteJDBC database = new SQLiteJDBC();
                database.saveUser(user, 1);
                guiController.setToHome();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @FXML
    private void cancelPressed(ActionEvent event) throws IOException{
        guiController.setToHome();

    }

    public GUIController getGuiController() {
        return guiController;
    }

    public void setGuiController(GUIController guiController) {
        this.guiController = guiController;
    }
}





