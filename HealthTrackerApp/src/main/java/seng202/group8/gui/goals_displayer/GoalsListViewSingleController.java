package seng202.group8.gui.goals_displayer;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import seng202.group8.services.goals_service.goal_types.Goal;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class GoalsListViewSingleController {

    @FXML
    private Label title;

    @FXML
    private Label date;

    @FXML
    private Label start;

    @FXML
    private ProgressBar progress;

    @FXML
    private Label end;

    @FXML
    private Button edit;

    @FXML
    private Button remove;

    private Goal currentGoal;
    private ArrayList<Goal> goalsToDisplay;
    private Integer goalIndex;

    public void start() {
        title.setText(currentGoal.getDescription());
        currentGoal.calculateProgress();
        progress.setProgress(currentGoal.getProgress());

        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        date.setText(currentGoal.getTargetDate().format(format));

        currentGoal.calculateCurrent();
        start.setText(currentGoal.getCurrent().toString());

        currentGoal.calculateTarget();
        end.setText(currentGoal.getTarget().toString());

        edit.setStyle("-fx-font-color: #ffff");
        edit.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent arg0) {
                System.out.println("hello");

            }
        });

        remove.setStyle("-fx-font-color: #ffff");
        remove.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent arg0) {
                System.out.println("Test2");

            }
        });
    }

    public Goal getCurrentGoal() {
        return currentGoal;
    }

    public void setCurrentGoal(Goal currentGoal) {
        this.currentGoal = currentGoal;
    }

    public ArrayList<Goal> getGoalsToDisplay() {
        return goalsToDisplay;
    }

    public void setGoalsToDisplay(ArrayList<Goal> goalsToDisplay) {
        this.goalsToDisplay = goalsToDisplay;
    }

    public Integer getGoalIndex() {
        return goalIndex;
    }

    public void setGoalIndex(Integer goalIndex) {
        this.goalIndex = goalIndex;
    }
}
