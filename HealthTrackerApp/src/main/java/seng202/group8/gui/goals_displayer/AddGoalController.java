package seng202.group8.gui.goals_displayer;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import seng202.group8.data_entries.Data;
import seng202.group8.data_entries.DataType;
import seng202.group8.services.goals_service.goal_types.*;
import seng202.group8.user.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

public class AddGoalController {

    private Stage stage;

    @FXML
    private Text goalDescriptionLabel;

    @FXML
    private Text goalTargetLabel;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField descriptionTextField;

    @FXML
    private TextField targetTextField;

    @FXML
    private ComboBox goalTypeCombo;

    @FXML
    private Text targetDateText;

    @FXML
    private Text activityTypeText;

    @FXML
    private ComboBox activityTypeBox;

    @FXML
    private JFXButton createGoal;

    @FXML
    private StackPane addStackPane;

    private User user;
    private Goal goal;
    private GoalsDisplayerController mainController;


    /**
     * The first function called when an add goal button is pressed, ONLY ADDING FUNCTION NOT EDIT FUNCTION
     */
    public void start() {
        targetTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d")) {
                    targetTextField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        goalTypeCombo.setValue(GoalType.fromEnumToString(mainController.getSelectedGoalType()));
        changeGoal();
    }

    /**
     * The function to start the add goal display when a user selects the edit button on a goal in the list view
     * This function will sets the fields correctly to what the user had previously put there
     * @param goal the goal that the user is wanting to edit
     */
    public void edit(Goal goal) {
        this.goal = goal;
        goalTypeCombo.setValue(GoalType.fromEnumToString(goal.getGoalType()));
        descriptionTextField.setText(goal.getDescription());
        targetTextField.setText(goal.getTarget().toString());
        datePicker.setValue(LocalDate.from(goal.getTargetDate()));
        createGoal.setText("Finish Changes");
        changeActivityType();
    }

    /**
     * Changes the activity type box to visible or not visible based on the goal selected and fills or not fills the box
     * only used when editing or retrying goals
     */
    private void changeActivityType() {
        switch (goalTypeCombo.getValue().toString()) {
            case "Activity":
                activityTypeText.setOpacity(1);
                activityTypeBox.setOpacity(1);
                activityTypeBox.setValue(DataType.fromEnumToString(goal.getDataType()));
                break;
            case "Frequency":
                activityTypeText.setOpacity(1);
                activityTypeBox.setOpacity(1);
                activityTypeBox.setValue(DataType.fromEnumToString(goal.getDataType()));
                break;
            case "Weight Loss":
                activityTypeText.setOpacity(0);
                activityTypeBox.setOpacity(0);
                break;
            default:
                break;
        }
    }

    /**
     * The function to start the add goal display when a user selects the retry button on a goal in the list view previous
     * This function will sets the fields correctly to what the user had previously put there
     * @param goal the goal that the user is wanting to retry
     */
    public void retry(Goal goal) {
        this.goal = goal;
        goalTypeCombo.setValue(GoalType.fromEnumToString(goal.getGoalType()));
        descriptionTextField.setText(goal.getDescription());
        targetTextField.setText(goal.getTarget().toString());
        createGoal.setText("Create Goal");
        changeActivityType();
    }

    /**
     * When the combo box at the top for changing the type of goal being created it will update the rest of the fields
     * e.g it will change the description background text so the user can understand what they have to type in each text field
     * Also toggles the activity type box field on or off based on the activity selected
     */
    public void changeGoal() {
        switch (goalTypeCombo.getValue().toString()) {
            case "Activity":
                descriptionTextField.setPromptText("Eg: Run for 1km");
                targetTextField.setPromptText("Eg: 1000 (m)");
                activityTypeText.setOpacity(1);
                activityTypeBox.setOpacity(1);
                break;
            case "Frequency":
                descriptionTextField.setPromptText("Eg: Run 10 times");
                targetTextField.setPromptText("Eg: 10");
                activityTypeText.setOpacity(1);
                activityTypeBox.setOpacity(1);
                break;
            case "Weight Loss":
                descriptionTextField.setPromptText("Eg: Aim to become 65 Kg");
                targetTextField.setPromptText("Eg: 65 (kg)");
                activityTypeText.setOpacity(0);
                activityTypeBox.setOpacity(0);
                break;
            default:
                break;
        }
    }

    /**
     * Checks for errors with the users entry fields and notifies the user if there is an incorrect fields and asks them to update them
     * this function also creates the goals once all fields are correct or updates the edited results if the user used the dit button rather than the add goal button
     */
    public void errorCheck() {
        Boolean error = false;
        if (descriptionTextField.getText().isEmpty()) {
            error = true;
            showError("No description");
        } else if (targetTextField.getText().isEmpty()) {
            showError("Target field is empty or not valid");
            error = true;
        } else if (Double.valueOf(targetTextField.getText()) < 1) {
            showError("Target field is empty or not valid");
            error = true;
        } else if ((datePicker.getValue() == null) || (datePicker.getValue().isBefore(LocalDate.now()))) {
            showError("Date is not entered or is set in the past");
            error = true;
        } else if (!goalTypeCombo.getValue().toString().equals(GoalType.fromEnumToString(GoalType.WeightLossGoal)) && activityTypeBox.getSelectionModel().isEmpty()) {
            showError("An activity type is not selected");
            error = true;
        } else if (!goalTypeCombo.getValue().toString().equals(GoalType.fromEnumToString(GoalType.WeightLossGoal))) {
            if (Double.valueOf(targetTextField.getText()) <= getCurrent()) {
                showError("Your target is not high enough, (You have already completed this goal you are trying to create)");
                error = true;
            }
        } else if (goalTypeCombo.getValue().toString().equals(GoalType.fromEnumToString(GoalType.WeightLossGoal))) {
            if (Double.valueOf(targetTextField.getText()) >= user.getWeight()) {
                showError("Weight target is not below your current weight, this is not a weight loss goal");
                error = true;
            }
        } if (!error) {
            DataType dataType;
            if (createGoal.getText() == "Finish Changes") {
                switch (goalTypeCombo.getValue().toString()) {
                    case "Activity":
                        dataType = DataType.fromStringToEnum(activityTypeBox.getValue().toString());
                        goal.setTarget(Double.valueOf(targetTextField.getText()));
                        goal.setDataType(dataType);
                        break;
                    case "Frequency":
                        dataType = DataType.fromStringToEnum(activityTypeBox.getValue().toString());
                        goal.setTarget(Double.valueOf(targetTextField.getText()));
                        goal.setDataType(dataType);
                        break;
                    case "Weight Loss":
                        goal.setTarget(Double.valueOf(targetTextField.getText()));
                        break;
                    default:
                        break;
                }
                goal.setDescription(descriptionTextField.getText());
                goal.setTargetDate(datePicker.getValue().atTime(0, 0));
            } else {
                switch (goalTypeCombo.getValue().toString()) {
                    case "Activity":
                        dataType = DataType.fromStringToEnum(activityTypeBox.getValue().toString());
                        ActivityGoal activityGoal = new ActivityGoal(user, descriptionTextField.getText(), GoalType.ActivityGoal, dataType, Double.valueOf(targetTextField.getText()), datePicker.getValue().atTime(0, 0));
                        user.getGoalsService().getCurrentActivityGoals().add(activityGoal);
                        break;
                    case "Frequency":
                        dataType = DataType.fromStringToEnum(activityTypeBox.getValue().toString());
                        FrequencyGoal freqGoal = new FrequencyGoal(user, descriptionTextField.getText(), GoalType.TimePerformedGoal, dataType, Integer.valueOf(targetTextField.getText()), datePicker.getValue().atTime(0, 0));
                        user.getGoalsService().getCurrentTimesPerformedGoals().add(freqGoal);
                        break;
                    case "Weight Loss":
                        WeightLossGoal weightGoal = new WeightLossGoal(user, descriptionTextField.getText(), GoalType.WeightLossGoal, Double.valueOf(targetTextField.getText()), datePicker.getValue().atTime(0, 0));
                        user.getGoalsService().getCurrentWeightLossGoals().add(weightGoal);
                        break;
                    default:
                        break;
                }
            }
            stage.close();
            mainController.changeView();
        }
    }

    /**
     * Gets the current progress of a goal that is about to be created to make sure it won't be completed instantly
     */
    private Double getCurrent() {
        DataType dataType = DataType.fromStringToEnum(activityTypeBox.getValue().toString());
        Double current = 0.0;
        ArrayList<Data> sameTypeData = user.getUserActivities().retrieveSameTypeActivities(dataType, new Date());
        switch (goalTypeCombo.getValue().toString()) {
            case "Activity":
                Double distanceCovered = 0.0;
                for (Data data : sameTypeData) {
                    distanceCovered += data.getDistanceCovered();
                }
                current = distanceCovered;
                break;
            case "Frequency":
                current = (double) sameTypeData.size();
                break;
        }
        return current;
    }

    /**
     * Shows an error to the user when they enter a field incorrectly
     * @param message the message to show to the user
     */
    private void showError(String message) {
        JFXDialogLayout content = new JFXDialogLayout();
        content.setHeading(new Text("Incorrect Entry"));
        content.setBody(new Label(message));
        JFXDialog dialog = new JFXDialog(addStackPane, content, JFXDialog.DialogTransition.CENTER);
        JFXButton gotItButton = new JFXButton("Got it!");

        gotItButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                dialog.close();
            }
        });

        content.setActions(gotItButton);
        dialog.show();
    }

    /**
     * Sets the stage for the add goal controller
     * @param stage a stage variable
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Sets the user that is currently using the application into the add goal controller
     * @param user a user variable that is using the application
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Sets the main controller variable to allow refreshing when a goal is edited or removed
     * @param controller the GoalsDisplayController class that holds the update methods needed
     */
    public void setMainController(GoalsDisplayerController controller) { this.mainController = controller;}
}
