package seng202.group8.gui.activity_list_collection_displayer.activities_collection_dialogs;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTimePicker;
import com.jfoenix.controls.JFXToggleButton;
import java_sqlite_db.SQLiteJDBC;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import seng202.group8.activity_collection.ActivityList;
import seng202.group8.data_entries.*;
import seng202.group8.gui.activity_list_collection_displayer.ActivitiesCollectionController;
import seng202.group8.user.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author lfa69
 * Controller for new_data_dialog.fxml deals with asking the user for a set of minimum values required to create a Data value.
 * This value if valid data is returned will be added to an already existing ActivityList or it will ad an ActivityList
 * and then populate it with the Data value created (user choice)
 */
public class NewDataDialogController {

    private User user;
    private int activityListIndexToAppendTo;
    private Stage stage;
    private ActivitiesCollectionController activitiesCollectionController;


    @FXML
    private ChoiceBox<String> activitiesChoiceBox;

    @FXML
    private TextField descriptionTextField;

    @FXML
    private JFXDatePicker datePickerNewActivity;

    @FXML
    private JFXTimePicker toTime;

    @FXML
    private JFXTimePicker fromTime;


    @FXML
    private TextField distanceCoveredTextField;

    @FXML
    private JFXButton createDataButton;

    @FXML
    private JFXToggleButton newActivityListToggle;

    @FXML
    private TextField newActivityListName;

    @FXML
    private Text errorText;

    /**
     * Method called by fxml view as soon as the fxml file is loaded, populates choice boxes.
     * Checks for user to add only integers to distance covered values.
     */
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

        errorText.setVisible(false);
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

        if (allFieldsAreCorrect(newActivityListToggle.isSelected())) {

            String dataDescription = descriptionTextField.getText();
            DataType dataType = DataType.fromStringToEnum(activitiesChoiceBox.getValue());

            //Dates creation
            LocalDate dataDate = datePickerNewActivity.getValue();
            LocalTime toTimeData = toTime.getValue();
            LocalTime fromTimeData = fromTime.getValue();

            LocalDateTime startTime = dataDate.atTime(fromTimeData);
            LocalDateTime endTime = dataDate.atTime(toTimeData);

            ArrayList<LocalDateTime> dataTimes = new ArrayList<>();
            dataTimes.add(startTime);
            dataTimes.add(endTime);
            //

            ArrayList<CoordinateData> coordinateData = new ArrayList<>();
            CoordinateData coordinate1 = new CoordinateData(34, 45, 54);
            CoordinateData coordinate2 = new CoordinateData(34, 35, 54);
            coordinateData.add(coordinate1);
            coordinateData.add(coordinate2);


            ArrayList<Integer> heartRates = new ArrayList<>();
            heartRates.add(60);
            heartRates.add(60);

            Data dataVal = createDataObject(dataDescription, dataType, dataTimes, coordinateData, heartRates);

            SQLiteJDBC database = new SQLiteJDBC();

            if (newActivityListToggle.isSelected()) {
                ActivityList activityList = new ActivityList(newActivityListName.getText());
                activityList.insertActivity(dataVal);
                user.getUserActivities().insertActivityList(activityList);
                database.insertActivityList(activityList.getTitle(),
                        database.getStringFromLocalDateTime(database.convertToLocalDateTimeViaInstant(activityList.getCreationDate())), 1);
                database.updateWithListOfData(activityList.getActivityList(), activityList.getTitle(), activityList.getCreationDate(), 1);
            } else {
                System.out.println(activityListIndexToAppendTo);
                ActivityList existingList = user.getUserActivities().getActivityListCollection().get(activityListIndexToAppendTo);
                existingList.insertActivity(dataVal);
                ArrayList<Data> newDataList = new ArrayList<Data>();
                newDataList.add(dataVal);
                database.updateWithListOfData(newDataList, existingList.getTitle(), existingList.getCreationDate(), 1);
            }
            stage.hide();
            activitiesCollectionController.setUpTreeView();
        } else {
            errorText.setVisible(true);
        }
        System.out.println("All g");
    }

    private Data createDataObject(String dataDescription,DataType dataType,ArrayList<LocalDateTime> dataTimes, ArrayList<CoordinateData> coordinateData, ArrayList<Integer> heartRates) {

        switch (dataType) {
            case WALK:
                return new WalkData(dataDescription, dataType, dataTimes, coordinateData, heartRates, user);
            case RUN:
                return new RunData(dataDescription, dataType, dataTimes, coordinateData, heartRates, user);
            case HIKE:
                return new HikeData(dataDescription, dataType, dataTimes, coordinateData, heartRates, user);
            case BIKE:
                return new BikeData(dataDescription, dataType, dataTimes, coordinateData, heartRates, user);
            case CLIMB:
                return new ClimbData(dataDescription, dataType, dataTimes, coordinateData, heartRates, user);
            case SWIM:
                return new SwimData(dataDescription, dataType, dataTimes, coordinateData, heartRates, user);
            default:
                return new WaterSportsData(dataDescription, dataType, dataTimes, coordinateData, heartRates, user);
        }

    }

    private boolean allFieldsAreCorrect(boolean isToggleSelected) {
        boolean descrNoNull = !descriptionTextField.getText().equals("");
        boolean datePickerNoNull = !(datePickerNewActivity.getValue() == null);
        boolean timesNoNull = (fromTime.getValue() != null) && (toTime.getValue() != null);
        boolean fromTimeSmallerThanToTime = true;

        if (timesNoNull) {
            fromTimeSmallerThanToTime = fromTime.getValue().isBefore(toTime.getValue());
        }
        boolean distanceCoveredTextFieldNoNull = !distanceCoveredTextField.getText().equals("");
        boolean activityListNotNull = true;
        if (isToggleSelected) {
            activityListNotNull = !newActivityListToggle.getText().equals("");
        }
        return descrNoNull && datePickerNoNull && timesNoNull
                && fromTimeSmallerThanToTime && distanceCoveredTextFieldNoNull
                && activityListNotNull;
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

    public ActivitiesCollectionController getActivitiesCollectionController() {
        return activitiesCollectionController;
    }

    public void setActivitiesCollectionController(ActivitiesCollectionController activitiesCollectionController) {
        this.activitiesCollectionController = activitiesCollectionController;
    }
}
