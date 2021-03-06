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
import java.time.chrono.ChronoLocalDate;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author lfa69
 * Controller for new_data_dialog.fxml deals with asking the user for a set of minimum values required to create a Data value.
 * This value if valid data is returned will be added to an already existing ActivityList or it will ad an ActivityList
 * and then populate it with the Data value created (user choice)
 */
public class NewDataDialogController {


    private boolean isAddingActivityList;
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

        if (isAddingActivityList) {
            newActivityListToggle.setSelected(true);
        }

    }


    /**
     * Toggle button listener, make the newActivityListName parameter visible
     * only if required to create a new ActivityList.
     */
    public void togglListener() {
        //System.out.println(isAddingActivityList);
        if (isAddingActivityList) {
            newActivityListToggle.setSelected(true);
        }

        if (newActivityListToggle.isSelected()) {
            newActivityListName.setVisible(true);
        } else {
            newActivityListName.setVisible(false);
        }

    }


    /**
     * Backend process that once createDataButton is clicked and the given info are correct it creates an Data object
     * and adds it to the user data values.
     */
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
            CoordinateData coordinate1 = new CoordinateData(-1, -1, -1);
            CoordinateData coordinate2 = new CoordinateData(-1, -1, -(Double.valueOf(distanceCoveredTextField.getText()) + 1));

            coordinateData.add(coordinate1);
            coordinateData.add(coordinate2);

            ArrayList<Integer> heartRates = new ArrayList<>();
            heartRates.add(-1);
            heartRates.add(-1);

            SQLiteJDBC database = new SQLiteJDBC();

            Data dataVal = createDataObject(dataDescription, dataType, dataTimes, coordinateData, heartRates);
            dataVal.setIsGraphable(false);
            dataVal.setDataId(database.getNextDataID());
            dataVal.setDistanceCovered(new Double(distanceCoveredTextField.getText()));
            dataVal.setDataSpeedKph(dataVal.getDataSpeedKph());

            //Controlling if there is a need to create a new activity list
            if (newActivityListToggle.isSelected()) {
                ActivityList activityList = new ActivityList(newActivityListName.getText());
                activityList.insertActivity(dataVal);
                user.getUserActivities().insertActivityList(activityList);
                database.insertActivityList(activityList.getTitle(), activityList.getCreationDate(), user.getUserID());
                database.updateWithListOfData(activityList.getActivityList(), activityList.getTitle(), activityList.getCreationDate(), user.getUserID());
            } else {
                ActivityList existingList = user.getUserActivities().getActivityListCollection().get(activityListIndexToAppendTo);
                existingList.insertActivity(dataVal);
                ArrayList<Data> newDataList = new ArrayList<Data>();
                newDataList.add(dataVal);
                database.updateWithListOfData(newDataList, existingList.getTitle(), existingList.getCreationDate(), user.getUserID());
            }
            stage.hide();
            activitiesCollectionController.setUpTreeView();
        } else {
            errorText.setVisible(true);
        }
    }

    /**
     *
     * @param dataDescription
     * @param dataType
     * @param dataTimes
     * @param coordinateData
     * @param heartRates
     * @return  Data object depending on the dataType parameter
     */
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


    /**
     *
     * @param isToggleSelected
     * @return a boolean value that represents if there is any problem with the filled fields, it checks the start
     * and end time are plausible (start < end) and not null and if text fields are empty
     */
    private boolean allFieldsAreCorrect(boolean isToggleSelected) {
        boolean descrNoNull = !descriptionTextField.getText().trim().equals("");
        boolean datePickerNoNull = !(datePickerNewActivity.getValue() == null) && !(datePickerNewActivity.getValue().isAfter(LocalDate.now()));
        boolean timesNoNull = (fromTime.getValue() != null) && (toTime.getValue() != null);
        boolean fromTimeSmallerThanToTime = true;

        if (timesNoNull) {
            fromTimeSmallerThanToTime = fromTime.getValue().isBefore(toTime.getValue());
        }

        boolean distanceCoveredTextFieldNoNull = !distanceCoveredTextField.getText().equals("");
        boolean activityListNotNull = true;
        if (isToggleSelected) {
            if (newActivityListName.getText() != null) {
                activityListNotNull = !(newActivityListName.getText().trim().length() == 0) ;
            } else {
                activityListNotNull = false;
            }
        }
        return descrNoNull && datePickerNoNull && timesNoNull
                && fromTimeSmallerThanToTime && distanceCoveredTextFieldNoNull
                && activityListNotNull;
    }


    /**
     *
     * @param toToggle a boolean value to represent if the toggle button must be forced to toggle.
     */
    public void toggleBtn(boolean toToggle) {
        if (toToggle) {
            newActivityListToggle.setSelected(true);
            newActivityListName.setVisible(true);
        }
    }


    /**
     * Closes the Stage object this controller manages
     */
    public void exitButtonListener() {
        stage.close();
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
     * @param user a new User object for the user property
     */
    public void setUser(User user) {
        this.user = user;
    }


    /**
     *
     * @return the index of the activity list where the selected Data object needs to be moved to
     */
    public int getActivityListIndexToAppendTo() {
        return activityListIndexToAppendTo;
    }


    /**
     *
     * @param activityListIndexToAppendTo an integer for the activityListIndexToAppendTo property
     */
    public void setActivityListIndexToAppendTo(int activityListIndexToAppendTo) {
        this.activityListIndexToAppendTo = activityListIndexToAppendTo;
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
     * @return the activitiesCollectionController property
     */
    public ActivitiesCollectionController getActivitiesCollectionController() {
        return activitiesCollectionController;
    }


    /**
     *
     * @param activitiesCollectionController a new ActivitiesCollectionController for the activitiesCollectionController
     */
    public void setActivitiesCollectionController(ActivitiesCollectionController activitiesCollectionController) {
        this.activitiesCollectionController = activitiesCollectionController;
    }


    /**
     *
     * @return shows if there is a need to move the Data value (toggled button)
     */
    public boolean isAddingActivityList() {
        return isAddingActivityList;
    }


    /**
     *
     * @param addingActivityList a new boolean value for the isAddingActivityList property
     */
    public void setAddingActivityList(boolean addingActivityList) {
        this.isAddingActivityList = addingActivityList;
    }

}
