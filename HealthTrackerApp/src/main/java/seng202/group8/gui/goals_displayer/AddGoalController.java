package seng202.group8.gui.goals_displayer;

import com.jfoenix.controls.JFXButton;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import seng202.group8.data_entries.DataType;
import seng202.group8.services.goals_service.goal_types.*;
import seng202.group8.user.User;

import java.time.LocalDate;
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

    private User user;
    private Goal goal;

    public void start() {
        targetTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d")) {
                    targetTextField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        goalTypeCombo.getSelectionModel().select("Activity Goal");
    }

    public void edit(Goal goal) {
        this.goal = goal;
        goalTypeCombo.setValue(goal.getGoalType());
        descriptionTextField.setText(goal.getDescription());
        targetTextField.setText(goal.getTarget().toString());
        datePicker.setValue(LocalDate.from(goal.getTargetDate()));
        createGoal.setText("Finish Changes");
        switch (goalTypeCombo.getValue().toString()) {
            case "Activity Goal":
                activityTypeText.setOpacity(1);
                activityTypeBox.setOpacity(1);
                activityTypeBox.setValue(goal.getDataType());
                break;
            case "Frequency Goal":
                activityTypeText.setOpacity(1);
                activityTypeBox.setOpacity(1);
                activityTypeBox.setValue(goal.getDataType());
                break;
            case "Weight Goal":
                activityTypeText.setOpacity(0);
                activityTypeBox.setOpacity(0);
                break;
            default:
                break;
        }
    }

    public void changeGoal() {
        switch (goalTypeCombo.getValue().toString()) {
            case "Activity Goal":
                descriptionTextField.setPromptText("Eg: Run for 1km");
                targetTextField.setPromptText("Eg: 1000 (m)");
                activityTypeText.setOpacity(1);
                activityTypeBox.setOpacity(1);
                break;
            case "Frequency Goal":
                descriptionTextField.setPromptText("Eg: Run 10 times");
                targetTextField.setPromptText("Eg: 10");
                activityTypeText.setOpacity(1);
                activityTypeBox.setOpacity(1);
                break;
            case "Weight Goal":
                descriptionTextField.setPromptText("Eg: Aim to become 65 Kg");
                targetTextField.setPromptText("Eg: 65 (kg)");
                activityTypeText.setOpacity(0);
                activityTypeBox.setOpacity(0);
                break;
            default:
                break;
        }
    }

    public void errorCheck() {
        if (descriptionTextField.getText().isEmpty()) {
            System.out.println("No description");
        } else if (targetTextField.getText().isEmpty()) {
            System.out.println("Target field is empty or not valid");
        } else if (Double.valueOf(targetTextField.getText()) < 1) {
            System.out.println("Target field is empty or not valid");
        } else if ((datePicker.getValue() == null) || (datePicker.getValue().isBefore(LocalDate.now()))) {
            System.out.println("Date is not entered or is set in the past");
//        } else if (goalTypeCombo.getValue().toString() != "Weight Goal") {// && activityTypeBox.getSelectionModel().isEmpty()) {
//            System.out.println("An activity type is not selected");
//        } else {
        } else {
            DataType dataType = createDataType(activityTypeBox.getValue().toString());
            if (createGoal.getText() == "Finish Changes") {
                goal.setDescription(descriptionTextField.getText());
                goal.setTargetDate(datePicker.getValue().atTime(0, 0));
                goal.setTarget(Double.valueOf(targetTextField.getText()));
            } else {
                switch (goalTypeCombo.getValue().toString()) {
                    case "Activity Goal":
                        ActivityGoal activityGoal = new ActivityGoal(user, descriptionTextField.getText(), GoalType.ActivityGoal, dataType, Double.valueOf(targetTextField.getText()), datePicker.getValue().atTime(0, 0));
                        user.getGoalsService().getCurrentActivityGoals().add(activityGoal);
                        break;
                    case "Frequency Goal":
                        FrequencyGoal freqGoal = new FrequencyGoal(user, descriptionTextField.getText(), GoalType.TimePerformedGoal, dataType, Integer.valueOf(targetTextField.getText()), datePicker.getValue().atTime(0, 0));
                        user.getGoalsService().getCurrentTimesPerformedGoals().add(freqGoal);
                        break;
                    case "Weight Goal":
                        WeightLossGoal weightGoal = new WeightLossGoal(user, descriptionTextField.getText(), GoalType.WeightLossGoal, Double.valueOf(targetTextField.getText()), datePicker.getValue().atTime(0, 0));
                        user.getGoalsService().getCurrentWeightLossGoals().add(weightGoal);
                        break;
                    default:
                        break;
                }
            }
            stage.close();
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public DataType createDataType(String data) {
        switch (data) {
            case "WALK":
                return DataType.WALK;
            case "RUN":
                return DataType.RUN;
            case "BIKE":
                return DataType.BIKE;
            case "HIKE":
                return DataType.HIKE;
            case "SWIM":
                return DataType.SWIM;
            case "CLIMB":
                return DataType.CLIMB;
            case "WATER_SPORTS":
                return DataType.WATER_SPORTS;
            default:
                return DataType.WALK;
        }

    }
}
