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

/**
 * @author wwa52
 * Controller class for the edit user screen, deals with allowing the user to edit their details and updates this in
 * the database
 */
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


    /**
     * Sets up the text fields with the current user data
     */
    public void setUp() {
        nameField.setText(user.getName());
        ageField.setText(Integer.toString(user.getAge()));
        weightField.setText(Double.toString(user.getWeight()));
        heightField.setText(Double.toString(user.getHeight()));
    }


    /**
     * Event handler for when enter button is pressed, updates the user's details with the entered values, also error
     * checks for invalid input
     * @param event
     */
    public void submitPressed(ActionEvent event) {
        String name = null;
        int age = 0;
        double weight = 0.0;
        double height = 0.0;
        String errorMessage = "";
        errorText.setText("");
        if (nameField.getText() != null && !nameField.getText().isEmpty() && !nameField.getText().matches(".*\\d+.*")) {
            name = nameField.getText().trim();
        }
        if (ageField.getText() != null && !ageField.getText().isEmpty()) {
            try {
                Integer usernewAge = Integer.parseInt(ageField.getText());
                if (usernewAge > 0) {
                    age = usernewAge;
                } else {
                    throw new NumberFormatException();
                }
            } catch(NumberFormatException e) {
                age = 0;
            }
        }
        if (weightField.getText() != null && !weightField.getText().isEmpty()) {
            try {
                Double userWeight = Double.parseDouble(weightField.getText());
                if (userWeight > 0) {
                    weight = userWeight;
                } else {
                    throw new NumberFormatException();
                }
            } catch(NumberFormatException e) {
                weight = 0.0;
            }
        }
        if (heightField.getText() != null && !heightField.getText().isEmpty()) {
            try {
                Double userNewWeight = Double.parseDouble(heightField.getText());
                if (userNewWeight > 0) {
                    height = userNewWeight;
                } else {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                height = 0.0;
            }
        }
        if (name == null || (name.trim().length() == 0)) {
            errorMessage += "You must enter a name to proceed.";
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
                errorMessage += "Your weight must be a positive number.";
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
            user.setName(name);
            user.getUserActivities().setTitle(user.getName() + "'s activities collection");
            user.setAge(age);
            if (user.getWeight() != weight) {
                user.updateWeight(weight);
            }
            if (user.getHeight() != height) {
                user.setHeight(height);
            }
            try {
                SQLiteJDBC database = new SQLiteJDBC();
                database.saveUser(user, user.getUserID());
                guiController.setToHome();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * Event handler for cancel button being pressed, cancels the editing of the user and goes back to the home screen
     * @param event
     * @throws IOException
     */
    @FXML
    private void cancelPressed(ActionEvent event) throws IOException{
        guiController.setToHome();
    }


    /**
     *
     * @return the guiController
     */
    public GUIController getGuiController() {
        return guiController;
    }


    /**
     *
     * @param guiController the GUI controller
     */
    public void setGuiController(GUIController guiController) {
        this.guiController = guiController;
    }


    /**
     *
     * @return the user property
     */
    public User getUser() { return user; }


    /**
     *
     * @param user a new User object for the user property
     */
    public void setUser(User user) { this.user = user; }


    /**
     *
     * @return the stage property
     */
    public Stage getStage() { return stage; }


    /**
     *
     * @param stage a new Stage object for the stage property
     */
    public void setStage(Stage stage) { this.stage = stage; }

}





