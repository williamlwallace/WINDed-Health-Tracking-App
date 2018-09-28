package seng202.group8.gui.goals_displayer;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import seng202.group8.services.goals_service.goal_types.Goal;
import seng202.group8.user.User;

import java.io.IOException;
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
    private User user;

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

        edit.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent arg0) {
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
                addGoalController.edit(currentGoal);
                newStage.setTitle("Edit Goal");
                Scene scene = new Scene(root);
                newStage.setScene(scene);
                newStage.setResizable(false);
                newStage.show();

            }
        });

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
