package seng202.group8.gui.activity_list_collection_displayer.activities_collection_dialogs;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXToggleButton;
import java_sqlite_db.SQLiteJDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import seng202.group8.activity_collection.ActivityList;
import seng202.group8.data_entries.Data;
import seng202.group8.data_entries.DataType;
import seng202.group8.gui.activity_list_collection_displayer.ActivitiesCollectionController;
import seng202.group8.user.User;

import java.util.ArrayList;

public class ModifyDataValueController {

    private int activityListIndex;
    private int dataIndex;
    private User user;
    private Stage stage;
    private ActivitiesCollectionController activitiesCollectionController;
    private Data dataValue;
    private ActivityList currentActivityList;
    private ActivityList newActivityList;
    private int newActivityListIndex;



    @FXML
    private ChoiceBox activitiesChoiceBox;

    @FXML
    private TextField descriptionTextField;

    @FXML
    private JFXToggleButton newActivityListToggle;

    @FXML
    private ChoiceBox<String> newActivityListCombobox;

    @FXML
    private Text errorText;


    /**
     * Function called as soon as the view is loaded, forced to make a manual call as giving this function a @FXML tag would produce a NullPointerException.
     * Pre-fills all the nodes in the view with the current values of the data object selected.
     */
    public void initialSetUp() {
        errorText.setVisible(false);
        setDataValue();
        setChoiceBox();
        descriptionTextField.setText(dataValue.getTitle());
        ArrayList<String> activitiesTitle = new ArrayList<>();
        for (ActivityList activityList : user.getUserActivities().getActivityListCollection()) {
            activitiesTitle.add(activityList.getTitle());
        }
        ObservableList<String> observableList = FXCollections.observableList(activitiesTitle);
        newActivityListCombobox.setItems(observableList);
        newActivityListCombobox.setValue(currentActivityList.getTitle());
        newActivityListCombobox.setVisible(false);
    }

    /**
     * Helper function for initialSetUp function.
     */
    public void setDataValue() {
        this.currentActivityList = user.getUserActivities().getActivityListCollection().get(activityListIndex);
        this.dataValue = this.currentActivityList.getActivity(dataIndex);
    }

    /**
     * Ensures the textField needed for moving the data value to another activity is made visible only if the toggle button is selected.
     */
    public void toggleButtonListener() {
        if (newActivityListToggle.isSelected()) {
            newActivityListCombobox.setVisible(true);
        } else {
            newActivityListCombobox.setVisible(false);
        }
    }

    /**
     * Adds all the possible activities values and prefills the
     */
    public void setChoiceBox() {
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
        activitiesChoiceBox.setValue(DataType.fromEnumToString(dataValue.getDataType()));
    }

    /**
     * Ensures the data inserted is valid and that in the case
     * the user wants to move the data value, the activity list exists.
     */
    public void modifyDataButtonListener() {
        SQLiteJDBC database = new SQLiteJDBC();
        if (checkAllDataValid()) {
            dataValue.setTitle(descriptionTextField.getText().trim());
            DataType selectedType = DataType.fromStringToEnum((String) activitiesChoiceBox.getSelectionModel().getSelectedItem());
            dataValue.setDataType(selectedType);
            if (newActivityListToggle.isSelected()) {
                setNewActivityList();
                //In case I move the activity to a different activity list
                database.updateActivity(dataValue,
                        user.getUserActivities().getActivityListCollection().get(newActivityListIndex));

                user.getUserActivities().insertActivityInGivenList(newActivityListIndex, dataValue);
                user.getUserActivities()
                        .getActivityListCollection()
                        .get(activityListIndex)
                        .getActivityList().remove(dataIndex);
                if (currentActivityList.getActivityList().size() == 0) {
                    user.getUserActivities().deleteActivityList(activityListIndex);
                    database.deleteActivityList(currentActivityList.getTitle(), currentActivityList.getCreationDate());
                }
            } else {
                database.updateActivity(dataValue, currentActivityList);
            }



            activitiesCollectionController.setUpTreeView();
            stage.close();

        //Error handling
        } else if (!checkAllDataValid()) { //Some data is not valid
            errorText.setText("Some values are invalid.");
            errorText.setStyle("-fx-fill: #c90909");
            errorText.setVisible(true);
        } else {//The activity list is not found
            errorText.setText("Activities list not found!");
            errorText.setStyle("-fx-fill: #e59400");
            errorText.setVisible(true);
        }
    }

    /**
     * Helper function for modifyDataButtonListener function.
     * @return a boolean value indicating if the inputted data is valid.
     */
    private boolean checkAllDataValid() {
        boolean okayDescriptionTextField = descriptionTextField.getText() != null && !descriptionTextField.getText().equals("");

        return okayDescriptionTextField;
    }

    /**
     * Helper function for modifyDataButtonListener function.
     * @return a boolean value indicating if the activity list mentioned exists.
     */
    private boolean setNewActivityList() {
        boolean exists = true;

        if (newActivityListToggle.isSelected()) {
            this.newActivityListIndex = newActivityListCombobox.getSelectionModel().getSelectedIndex();
            this.newActivityList = user.getUserActivities().getActivityListCollection().get(newActivityListIndex);
// for (ActivityList activityList : user.getUserActivities().getActivityListCollection()) {
//                if (activityList.getTitle().toLowerCase().equals(newActivityListName.getText().toLowerCase().trim())) {
//                    this.newActivityList = activityList;
//                    this.newActivityListIndex = user.getUserActivities().getActivityListCollection().indexOf(activityList);
//                }
//            }
            if (this.newActivityList == null) {
                exists = false;
            }
        }

        return exists;
    }


    /**
     * Closes the Stage object this controller manages
     */
    public void exitButtonListener() {
        stage.close();
    }

    /**
     * @return an int value representing the index of the activity list where the Data object selected is stored
     * when clicking on the modify button in activity_list_collection.fxml.
     */
    public int getActivityListIndex() {
        return activityListIndex;
    }

    /**
     *
     * @param activityListIndex a new int value for the activityListIndex method
     */
    public void setActivityListIndex(int activityListIndex) {
        this.activityListIndex = activityListIndex;
    }

    /**
     *
     * @return the index of the selected Data value within the activity list where it is stored.
     */
    public int getDataIndex() {
        return dataIndex;
    }

    /**
     *
     * @param dataIndex a new int value for the dataIndex property
     */
    public void setDataIndex(int dataIndex) {
        this.dataIndex = dataIndex;
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
     * @return the parent controller (from ActivityListCollectionController) activitiesCollectionController property
     */
    public ActivitiesCollectionController getActivitiesCollectionController() {
        return activitiesCollectionController;
    }

    /**
     *
     * @param activitiesCollectionController a new ActivitiesCollectionController object
     * for the activitiesCollectionController property
     */
    public void setActivitiesCollectionController(ActivitiesCollectionController activitiesCollectionController) {
        this.activitiesCollectionController = activitiesCollectionController;
    }
}
