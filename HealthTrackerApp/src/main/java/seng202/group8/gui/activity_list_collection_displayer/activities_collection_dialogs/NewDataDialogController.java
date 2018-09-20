package seng202.group8.gui.activity_list_collection_displayer.activities_collection_dialogs;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import seng202.group8.user.User;

import java.util.ArrayList;

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
//        activitiesChoiceBox.se

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
