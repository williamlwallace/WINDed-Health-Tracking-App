package seng202.group8.gui.goals_displayer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import seng202.group8.services.goals_service.GoalsService;
import seng202.group8.services.goals_service.goal_types.*;
import seng202.group8.user.User;
import java.io.IOException;
import java.util.ArrayList;

public class GoalsDisplayerController {

    private User user;
    private Stage stage;
    private GoalType selectedGoalType;
    private GoalsService goalsService;

    @FXML
    private ComboBox viewBox;

    @FXML
    private Label goalTypeLabel;

    @FXML
    private ListView goalList;

    @FXML
    private Label goalTypeLabelPrevious;

    @FXML
    private ListView goalListPrevious;

    private ArrayList<Goal> goalsToDisplay;
    private ArrayList<Goal> goalsToDisplayPrevious;
    private GoalsDisplayerController mainController;

    /**
     * An initial function called when you first go into the goals panel, this sets the main controller
     * variable and sets the combo boxes to default values, also calls the change view function
     */
    public void initialize(){
        selectedGoalType = GoalType.ActivityGoal;
        viewBox.getSelectionModel().select(0);
        System.out.println(user);
        user.getGoalsService().update();
        setMainController(this);
        changeView();
    }

    /**
     * Gets the value of the combo box for the viewing selection and sets the labels to either create a goal if there
     * is no goals for that section or to set up the goal list for that particular type of goal
     */
    public void changeView() {
        switch (viewBox.getValue().toString()) {
            case "Activity Goals":
                goalTypeLabel.setText("Activity Goals");
                goalTypeLabelPrevious.setText("Activity Goals");
                selectedGoalType = GoalType.ActivityGoal;
                break;
            case "Frequency Goals":
                goalTypeLabel.setText("Frequency Goals");
                goalTypeLabelPrevious.setText("Frequency Goals");
                selectedGoalType = GoalType.TimePerformedGoal;
                break;
            case "Weight Goals":
                goalTypeLabel.setText("Weight Loss Goals");
                goalTypeLabelPrevious.setText("Weight Loss Goals");
                selectedGoalType = GoalType.WeightLossGoal;
                break;
            default:
                break;
        }
        if (goalsService != null) {
            setupGoalsList();
            setupGoalsListPrevious();
        } else {
            goalTypeLabel.setText("Create a goal to get started");
            goalTypeLabelPrevious.setText("Finish a goal!");
        }
    }

    /**
     * Sets up the display list view for the goals that is currently selected by the variable goals to display
     * this function generates the goals single list view controllers into a list view fxml panel
     */
    private void setupGoalsList() {
        goalsToDisplay = goalsService.getCurrentActivityGoals();
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
        ObservableList<Goal> dataObservableList = FXCollections.observableList(goalsToDisplay);

        goalList.setItems(dataObservableList);

        goalList.setCellFactory(new Callback<ListView<Goal>, ListCell<Goal>>() {
            @Override
            public ListCell<Goal> call(ListView<Goal> param) {
                ListCell<Goal> cell = new ListCell<Goal>(){
                    @Override
                    protected void updateItem(Goal goal, boolean bln) {
                        super.updateItem(goal, bln);

                        if (goal != null) {
                            try {
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("../../../../resources/views/goalsListViewSingle.fxml"));
                                BorderPane goalSingle = loader.load();
                                GoalsListViewSingleController controller = loader.getController();
                                controller.setCurrentGoal(goal);
                                controller.setMainController(mainController);
                                controller.setUser(user);
                                controller.start();
                                setGraphic(goalSingle);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                };

                return cell;
            }
        });
    }

    /**
     * Sets up the display list view for the goals that have been previously completed and selected by the variable goals to display
     * this function generates the goals single list view controllers into a list view fxml panel
     */
    private void setupGoalsListPrevious() {
        goalsToDisplayPrevious = goalsService.getPreviousActivityGoals();
        switch(selectedGoalType) {
            case TimePerformedGoal:
                goalsToDisplayPrevious = goalsService.getPreviousTimesPerformedGoals();
                break;
            case WeightLossGoal:
                goalsToDisplayPrevious = goalsService.getPreviousWeightLossGoals();
                break;
            default:
                goalsToDisplayPrevious = goalsService.getPreviousActivityGoals();
                break;
        }
        ObservableList<Goal> dataObservableList = FXCollections.observableList(goalsToDisplayPrevious);

        goalListPrevious.setItems(dataObservableList);

        goalListPrevious.setCellFactory(new Callback<ListView<Goal>, ListCell<Goal>>() {
            @Override
            public ListCell<Goal> call(ListView<Goal> param) {
                ListCell<Goal> cell = new ListCell<Goal>(){
                    @Override
                    protected void updateItem(Goal goal, boolean bln) {
                        super.updateItem(goal, bln);

                        if (goal != null) {
                            try {
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("../../../../resources/views/goalsListViewSinglePrevious.fxml"));
                                BorderPane goalSingle = loader.load();
                                GoalsListViewSingleController controller = loader.getController();
                                controller.setCurrentGoal(goal);
                                controller.setMainController(mainController);
                                controller.setUser(user);
                                controller.start();
                                setGraphic(goalSingle);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                };

                return cell;
            }
        });
    }


    /**
     * The function called when the user clicks the add goal function
     * This loads the add goal controller for the user ot be able to add a goal
     */
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
        addGoalController.setMainController(this);
        addGoalController.start();
        newStage.setTitle("Add Goal");
        Scene scene = new Scene(root);
        newStage.setScene(scene);
        newStage.setResizable(false);
        newStage.show();
        changeView();
    }

    /**
     * Gets the current selected goal type from the combo Box
     * @return a goal type variable enum
     */
    public GoalType getSelectedGoalType() {
        return selectedGoalType;
    }

    /**
     * Changes the selected goal type variable to enum activity goals and calls the setup goals list function
     */
    public void showActivityGoals() {
        selectedGoalType = GoalType.ActivityGoal;
        setupGoalsList();
    }

    /**
     * Changes the selected goal type variable to enum times performed goals and calls the setup goals list function
     */
    public void showFrequencyGoals() {
        selectedGoalType = GoalType.TimePerformedGoal;
        setupGoalsList();
    }

    /**
     * Changes the selected goal type variable to enum weight loss goals and calls the setup goals list function
     */
    public void showWeightLossGoals() {
        selectedGoalType = GoalType.WeightLossGoal;
        setupGoalsList();
    }

    /**
     * Sets the goals service for the goals controller that it can use to retrieve the goals
     * @param goalsService a goals service variable that the user currently has
     */
    public void setGoalsService(GoalsService goalsService) {
        this.goalsService = goalsService;
    }

    /**
     * Gets the user that us currently using the application
     * @return the user of type User
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the user variable in this controller to the user using tha application
     * @param user the user using the application currently
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * GEts the stage of the current controller
     * @return a stage object
     */
    public Stage getPrimaryStage() {
        return stage;
    }

    /**
     * Sets the stage of the goals display controller
     * @param stage a stage object for the goals display to add to
     */
    public void setPrimaryStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Sets the main controller variable to be used by the other goal controllers
     * @param mainController the main controller to be passed around of type GoalsDisplayController
     */
    public void setMainController(GoalsDisplayerController mainController) { this.mainController = mainController; }
}
