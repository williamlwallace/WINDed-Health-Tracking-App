package seng202.group8.gui.goals_displayer;

import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import seng202.group8.data_entries.Data;
import seng202.group8.data_entries.DataType;
import seng202.group8.services.goals_service.GoalsService;
import seng202.group8.services.goals_service.goal_types.*;
import seng202.group8.user.User;
import utils.exceptions.NotCoherentWeightLossGoalException;

import java.util.ArrayList;

public class GoalsDisplayerController {

    private User user;
    private Stage stage;
    private GoalType selectedGoalType;
    //TO DELETE ONCE CONNECTED TO USERS
    private GoalsService goalsService;
    //

    @FXML
    private ListView<Goal> goalsListView;


    public void createGoals() {
        goalsService = new GoalsService(user);
        ActivityGoal activityGoal = new ActivityGoal(user, "CiaoCiaoCiao", GoalType.ActivityGoal, DataType.WALK, 200.0);
        FrequencyGoal frequencyGoal = new FrequencyGoal(user, "Frequency", GoalType.TimePerformedGoal, DataType.WALK, 3);
        WeightLossGoal weightLossGoal;
        try {
            weightLossGoal = new WeightLossGoal(user, "CiaoCiaoCiao", GoalType.WeightLossGoal, 60.0);
            goalsService.getCurrentActivityGoals().add(activityGoal);
            goalsService.getCurrentTimesPerformedGoals().add(frequencyGoal);
            goalsService.getCurrentWeightLossGoals().add(weightLossGoal);
        } catch (NotCoherentWeightLossGoalException e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void initialize() {
        if (goalsService != null) {
            setUpGoalsView();
        }
    }


    private void setUpGoalsView() {

        ArrayList<Goal> goalsToDisplay = goalsService.getCurrentActivityGoals();
        switch(selectedGoalType) {
            case TimePerformedGoal:
                goalsToDisplay = goalsService.getCurrentTimesPerformedGoals();
                break;
            case WeightLossGoal:
                goalsToDisplay = goalsService.getCurrentWeightLossGoals();
                break;
            default:
                break;
        }
        for (Goal goal : goalsToDisplay) {
            System.out.println(goal.getDescription());
        }

        ObservableList<Goal> goalObservableList = FXCollections.observableList(goalsToDisplay);
        goalsListView.setItems(goalObservableList);
        goalsListView.setCellFactory(new Callback<ListView<Goal>, ListCell<Goal>>() {
            @Override
            public ListCell<Goal> call(ListView<Goal> param) {
                ListCell<Goal> cell = new ListCell<Goal>(){

                    @Override
                    protected void updateItem(Goal goal, boolean bln) {
                        super.updateItem(goal, bln);

                        HBox hBox = new HBox();
                        Text title = new Text();
                        if (goal != null) {
                            title.setText(goal.getDescription());
                            switch (selectedGoalType) {
                                case WeightLossGoal:
                                    hBox.getChildren().addAll(title);
                                    hBox.setPadding(new Insets(5));
                                    hBox.setStyle("-fx-background-color : green;");
                                    setGraphic(hBox);
                                    break;
                                case ActivityGoal:
                                    hBox.getChildren().addAll(title);
                                    hBox.setPadding(new Insets(5));
                                    hBox.setStyle("-fx-background-color : red;");
                                    setGraphic(hBox);
                                    break;
                                case TimePerformedGoal:
                                    hBox.getChildren().addAll(title);
                                    hBox.setPadding(new Insets(5));
                                    hBox.setStyle("-fx-background-color : blue;");
                                    setGraphic(hBox);
                                    break;
                            }
                        }
                    }
                };

                return cell;
            }
        });
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


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
