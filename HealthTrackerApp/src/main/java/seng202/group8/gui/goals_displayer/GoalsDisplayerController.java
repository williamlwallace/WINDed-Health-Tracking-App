package seng202.group8.gui.goals_displayer;

import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.apache.commons.lang3.ObjectUtils;
import seng202.group8.activity_collection.ActivityList;
import seng202.group8.data_entries.Data;
import seng202.group8.data_entries.DataType;
import seng202.group8.parser.Parser;
import seng202.group8.services.goals_service.GoalsService;
import seng202.group8.services.goals_service.goal_types.*;
import seng202.group8.user.User;
import utils.exceptions.NotCoherentWeightLossGoalException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class GoalsDisplayerController {

    private User user;
    private Stage stage;
    private GoalType selectedGoalType;
    private GoalsService goalsService;
    //

    @FXML
    private VBox currentGoals;

    @FXML
    private ComboBox viewBox;

    @FXML
    private Label goalTypeLabel;


    public void createGoals() throws Exception {
        ActivityGoal activityGoal = new ActivityGoal(user, "activity1", GoalType.ActivityGoal, DataType.WALK, 200.0, LocalDateTime.now());
        ActivityGoal activityGoal2 = new ActivityGoal(user, "activity2", GoalType.ActivityGoal, DataType.RUN, 300.0, LocalDateTime.now());
        ActivityGoal activityGoal3 = new ActivityGoal(user, "activity3", GoalType.ActivityGoal, DataType.WALK, 200.0, LocalDateTime.now());
        ActivityGoal activityGoal4 = new ActivityGoal(user, "activity4", GoalType.ActivityGoal, DataType.RUN, 300.0, LocalDateTime.now());
        ActivityGoal activityGoal5 = new ActivityGoal(user, "activity5", GoalType.ActivityGoal, DataType.WALK, 200.0, LocalDateTime.now());
        ActivityGoal activityGoal6 = new ActivityGoal(user, "activity6", GoalType.ActivityGoal, DataType.RUN, 750.0, LocalDateTime.now());
        ActivityGoal activityGoal7 = new ActivityGoal(user, "activity7", GoalType.ActivityGoal, DataType.WALK, 1000.0, LocalDateTime.now());
        ActivityGoal activityGoal8 = new ActivityGoal(user, "activity8", GoalType.ActivityGoal, DataType.RUN, 2000.0, LocalDateTime.now());
        FrequencyGoal frequencyGoal = new FrequencyGoal(user, "Frequency1", GoalType.TimePerformedGoal, DataType.WALK, 3, LocalDateTime.now());
        WeightLossGoal weightLossGoal;
        try {
            weightLossGoal = new WeightLossGoal(user, "weight1", GoalType.WeightLossGoal, 60.0, LocalDateTime.now());
            goalsService.getCurrentActivityGoals().add(activityGoal);
            goalsService.getCurrentActivityGoals().add(activityGoal2);
            goalsService.getCurrentActivityGoals().add(activityGoal3);
            goalsService.getCurrentActivityGoals().add(activityGoal4);
            goalsService.getCurrentActivityGoals().add(activityGoal5);
            goalsService.getCurrentActivityGoals().add(activityGoal6);
            goalsService.getCurrentActivityGoals().add(activityGoal7);
            goalsService.getCurrentActivityGoals().add(activityGoal8);
            goalsService.getCurrentTimesPerformedGoals().add(frequencyGoal);
            goalsService.getCurrentWeightLossGoals().add(weightLossGoal);
            Parser parserTest = new Parser("seng202_2018_example_data_clean.csv", user);
            parserTest.parseFile();
            ArrayList<Data> dataList = parserTest.getDataList();
            user.getUserActivities().insertActivityList(new ActivityList("TESTS"));
            for (int i = 0; i < dataList.size(); i++) {
                user.getUserActivities().insertActivityInGivenList(0, dataList.get(i));
            }
        } catch (NotCoherentWeightLossGoalException e) {
            e.printStackTrace();
        }

    }

    public void initialize(){
        if (goalsService != null) {
            selectedGoalType = GoalType.ActivityGoal;
            goalTypeLabel.setText("Activity Goals");
            viewBox.setValue("Activity Goals");
            setUpGoalsView();
        } else {
            goalTypeLabel.setText("Create a goal to get started");
        }
    }

    public void changeView() {
        switch (viewBox.getValue().toString()) {
            case "Activity Goals":
                goalTypeLabel.setText("Activity Goals");
                selectedGoalType = GoalType.ActivityGoal;
                break;
            case "Frequency Goals":
                goalTypeLabel.setText("Frequency Goals");
                selectedGoalType = GoalType.TimePerformedGoal;
                break;
            case "Weight Goals":
                goalTypeLabel.setText("Weight Goals");
                selectedGoalType = GoalType.WeightLossGoal;
                break;
            default:
                break;
        }
        if (goalsService != null) {
            setUpGoalsView();
        } else {
            goalTypeLabel.setText("Create a goal to get started");
        }
    }


    private void setUpGoalsView() {

        ArrayList<Goal> goalsToDisplay;
        switch(selectedGoalType) {
            case TimePerformedGoal:
                goalsToDisplay = goalsService.getCurrentTimesPerformedGoals();
                break;
            case WeightLossGoal:
                goalsToDisplay = goalsService.getCurrentWeightLossGoals();
                break;
            default:
                goalsToDisplay = goalsService.getCurrentActivityGoals();
                break;
        }
        System.out.println(goalsToDisplay);
        Label [] titles = new Label[goalsToDisplay.size()];
        Label [] currents = new Label[goalsToDisplay.size()];
        Label [] targets = new Label[goalsToDisplay.size()];
        Label [] percentages = new Label[goalsToDisplay.size()];
        ProgressBar[] pbs = new ProgressBar[goalsToDisplay.size()];
        VBox vbs [] = new VBox [goalsToDisplay.size()];
        HBox hbs [] = new HBox [goalsToDisplay.size()];
        currentGoals.getChildren().clear();
        for (int i = 0; i < goalsToDisplay.size(); i++) {

            Label title = titles[i] = new Label();
            title.setText(goalsToDisplay.get(i).getDescription());

            ProgressBar pb = pbs[i] = new ProgressBar();
            goalsToDisplay.get(i).calculateProgress();
            pb.setProgress(goalsToDisplay.get(i).getProgress());
            pb.setMinWidth(300.0);

            Label current = currents[i] = new Label();
            goalsToDisplay.get(i).calculateCurrent();
            current.setText(goalsToDisplay.get(i).getCurrent().toString());

            Label target = targets[i] = new Label();
            goalsToDisplay.get(i).calculateTarget();
            target.setText(goalsToDisplay.get(i).getTarget().toString());

            Label percentage = percentages[i] = new Label();
            percentage.setText(Double.toString(goalsToDisplay.get(i).getProgress() * 100.0) + " %");

            HBox hb = hbs[i] = new HBox();
            hb.setSpacing(15);
            hb.setAlignment(Pos.CENTER);
            hb.getChildren().addAll(current, pb, target);

            VBox vb = vbs[i] = new VBox();
            vb.setSpacing(7);
            vb.setAlignment(Pos.CENTER);
            vb.getChildren().addAll(title, hb, percentage);
        }
        currentGoals.setSpacing(10);
        currentGoals.getChildren().addAll(vbs);
    }


    public void createGoal() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../../../resources/views/add_goal.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        AddGoalController addGoalController = loader.getController();
        Stage newStage = new Stage();
        addGoalController.setStage(newStage);
        addGoalController.setUser(user);
        addGoalController.start();
        newStage.setTitle("Add Goal");
        Scene scene = new Scene(root);
        newStage.setScene(scene);
        newStage.setResizable(false);
        newStage.show();
        changeView();
    }

    public void showActivityGoals() {
        selectedGoalType = GoalType.ActivityGoal;
        setUpGoalsView();
    }

    public void showFrequencyGoals() {
        selectedGoalType = GoalType.TimePerformedGoal;
        setUpGoalsView();
    }

    public void showWeightLossGoals() {
        selectedGoalType = GoalType.WeightLossGoal;
        setUpGoalsView();
    }

    public void setGoalsService(GoalsService goalsService) {
        this.goalsService = goalsService;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Stage getPrimaryStage() {
        return stage;
    }

    public void setPrimaryStage(Stage stage) {
        this.stage = stage;
    }
}
