package seng202.group8.gui.goals_displayer;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seng202.group8.data_entries.DataType;
import seng202.group8.services.goals_service.goal_types.Goal;
import seng202.group8.services.goals_service.goal_types.GoalType;
import seng202.group8.user.User;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class GoalsListSinglePreviousController {
    @FXML
    private Label title;

    @FXML
    private Label date;

    @FXML
    private Label end;

    @FXML
    private Button retry;

    @FXML
    private Button remove;

    @FXML
    private Label type;

    @FXML
    private Label passOrFail;

    @FXML
    private VBox fill;


    private Goal currentGoal;
    private User user;
    private GoalsDisplayerController mainController;

    /**
     * The function to set all of the fxml labels and button actions for a single goal in the list view
     * Changes the text based on the type of activity and calls other functions for when the retry / remove button is pushed
     */
    public void start() {
        if (currentGoal.getIsCompleted()) {
            passOrFail.setText("Success");
            retry.setText("Do Again");
            fill.setStyle("-fx-background-color: #b4ecbe;");
        } else {
            passOrFail.setText("Not Achieved");
            retry.setText("Retry");
            fill.setStyle("-fx-background-color: #e8b0b0;");
        }
        switch (GoalType.fromEnumToString(currentGoal.getGoalType())) {
            case "Activity":
                currentGoal.calculateTarget();
                end.setText(currentGoal.getTarget().toString() + " m");

                type.setText("Type:" + DataType.fromEnumToString(currentGoal.getDataType()));
                type.setOpacity(1);
                break;
            case "Frequency":
                currentGoal.calculateTarget();
                end.setText(currentGoal.getTarget().toString());

                type.setText("Type:" + DataType.fromEnumToString(currentGoal.getDataType()));
                type.setOpacity(1);
                break;
            case "Weight Loss":
                currentGoal.calculateTarget();
                end.setText(currentGoal.getTarget().toString() + " kg");

                type.setOpacity(0);
                break;
            default:
                System.out.println(currentGoal.getGoalType().toString());
                break;
        }
        title.setText(currentGoal.getDescription());

        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        date.setText(currentGoal.getTargetDate().format(format));

        retry.setOnAction(new EventHandler<ActionEvent>() {

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
                addGoalController.setMainController(mainController);
                addGoalController.retry(currentGoal);
                newStage.setTitle("Retry Goal");
                Scene scene = new Scene(root);
                newStage.setScene(scene);
                newStage.setResizable(false);
                newStage.show();

            }
        });

        remove.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent arg0) {
                user.getGoalsService().getAllCurrentGoals().remove(currentGoal);
                switch (GoalType.fromEnumToString(currentGoal.getGoalType())) {
                    case "Activity":
                        user.getGoalsService().getPreviousActivityGoals().remove(currentGoal);
                        break;
                    case "Frequency":
                        user.getGoalsService().getPreviousTimesPerformedGoals().remove(currentGoal);
                        break;
                    case "Weight Loss":
                        user.getGoalsService().getPreviousWeightLossGoals().remove(currentGoal);
                        break;
                    default:
                        break;
                }
                mainController.changeView();
            }
        });
    }

    /**
     * Sets the current goal that is used to get all the information for the fxml labels
     * @param currentGoal a goal type variable of the goal wanting to be created
     */
    public void setCurrentGoal(Goal currentGoal) {
        this.currentGoal = currentGoal;
    }

    /**
     * Gets the user for the goals
     * @return
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the use for this single list view
     * @param user the user currently using the application
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Sets the main controller for the goals display so that this class can call the update
     * function for the goals list view when something is edited or removed
     * @param mainController the controller of the goals display controller
     */
    public void setMainController(GoalsDisplayerController mainController) {
        this.mainController = mainController;
    }
}
