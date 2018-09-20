package seng202.group8.gui.activity_list_collection_displayer.activities_collection_dialogs;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXToggleButton;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import seng202.group8.user.User;

import java.util.ArrayList;
import java.util.Arrays;

public class NewDataDialogController {

    private User user;
    private int activityListIndexToAppendTo;
    private Stage stage;

    @FXML
    private ChoiceBox<String> activitiesChoiceBox;

    @FXML
    private TextField descriptionTextField;

    @FXML
    private DatePicker datePickerNewActivity;

    @FXML
    private ChoiceBox<Integer> fromTime;

    @FXML
    private ChoiceBox<Integer> toTime;

    @FXML
    private TextField distanceCoveredTextField;

    @FXML
    private JFXButton createDataButton;

    @FXML
    private JFXToggleButton newActivityListToggle;

    @FXML
    private TextField newActivityListName;

    @FXML
    public void initialize() {
        ArrayList<String> activitiesChoice = new ArrayList<>();
        activitiesChoice.add("Walk");
        activitiesChoice.add("Run");
        activitiesChoice.add("Hike");
        activitiesChoice.add("Climb");
        activitiesChoice.add("Bike");
        activitiesChoice.add("Swim");
        activitiesChoice.add("Water Sport");
        ObservableList<String> observableList = FXCollections.observableList(activitiesChoice);
        activitiesChoiceBox.setItems(observableList);
        activitiesChoiceBox.setValue("Walk");
//        activitiesChoiceBox.se

        distanceCoveredTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d")) {
                    distanceCoveredTextField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        ArrayList<Integer> hours = new ArrayList<>();
        for (int i = 0; i < 25; i++) {
            hours.add(i);
        }
        ObservableList<Integer> integerObservableList = FXCollections.observableList(hours);
        fromTime.setItems(integerObservableList);
        toTime.setItems(integerObservableList);
        fromTime.setValue(hours.get(0));
        toTime.setValue(hours.get(0));

        newActivityListName.setVisible(false);

    }


    public void togglListener() {
        if (newActivityListToggle.isSelected()) {
            newActivityListName.setVisible(true);
        } else {
            newActivityListName.setVisible(false);
        }
    }

    public void createActivity() {

        if (allFieldsAreCorrect()) {
            if (newActivityListToggle.isSelected()) {

            } else {

            }
        } else {

        }
    }

    private boolean allFieldsAreCorrect() {
        boolean descrNoNull = !descriptionTextField.getText().equals("");
        return true;
    }






    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getActivityListIndexToAppendTo() {
        return activityListIndexToAppendTo;
    }

    public void setActivityListIndexToAppendTo(int activityListIndexToAppendTo) {
        this.activityListIndexToAppendTo = activityListIndexToAppendTo;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
