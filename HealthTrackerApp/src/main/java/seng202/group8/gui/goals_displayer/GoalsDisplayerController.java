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
import seng202.group8.services.goals_service.goal_types.*;
import seng202.group8.user.User;
import utils.exceptions.NotCoherentWeightLossGoalException;

import java.util.ArrayList;

public class GoalsDisplayerController {

    private User user;
    private Stage stage;
    private ArrayList<Goal> goals = new ArrayList<>();//This one should not exist and allow the user to have a goalsService value in its start.


    @FXML
    private ListView<Goal> goalsListView;

    private ActivityGoal activityGoal;
    private WeightLossGoal weightLossGoal;
    private FrequencyGoal frequencyGoal;

    public void createGoals() {
        activityGoal = new ActivityGoal(user, "CiaoCiaoCiao", GoalType.ActivityGoal, DataType.WALK, 200.0);
        try {
            weightLossGoal = new WeightLossGoal(user, "CiaoCiaoCiao", GoalType.WeightLossGoal, 60.0);
        } catch (NotCoherentWeightLossGoalException e) {
            e.printStackTrace();
        }
        frequencyGoal = new FrequencyGoal(user, "Frequency", GoalType.TimePerformedGoal, DataType.WALK, 3);
        goals.add(activityGoal);
        goals.add(frequencyGoal);
        goals.add(weightLossGoal);
    }

    @FXML
    public void initialize() {
        setUpGoalsView();
    }


    private void setUpGoalsView() {
        ObservableList<Goal> goalObservableList = FXCollections.observableList(goals);
        goalsListView.setItems(goalObservableList);

        goalsListView.setCellFactory(new Callback<ListView<Goal>, ListCell<Goal>>() {
            @Override
            public ListCell<Goal> call(ListView<Goal> param) {
                ListCell<Goal> cell = new ListCell<Goal>(){

                    private HBox hBox = new HBox();
                    private Text title = new Text();
                    @Override
                    protected void updateItem(Goal goal, boolean bln) {
                        super.updateItem(goal, bln);
                        if (goal != null) {
                            switch (goal.getGoalType()) {
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
