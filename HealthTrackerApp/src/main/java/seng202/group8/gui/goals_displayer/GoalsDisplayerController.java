package seng202.group8.gui.goals_displayer;

import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import seng202.group8.activity_collection.ActivityList;
import seng202.group8.data_entries.Data;
import seng202.group8.data_entries.DataType;
import seng202.group8.parser.Parser;
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
    private VBox currentGoals;

    @FXML
    private ComboBox viewBox;

    @FXML
    private Label goalTypeLabel;


    public void createGoals() throws Exception {
        goalsService = new GoalsService(user);
        ActivityGoal activityGoal = new ActivityGoal(user, "activity1", GoalType.ActivityGoal, DataType.WALK, 200.0);
        ActivityGoal activityGoal2 = new ActivityGoal(user, "activity2", GoalType.ActivityGoal, DataType.RUN, 300.0);
        ActivityGoal activityGoal3 = new ActivityGoal(user, "activity3", GoalType.ActivityGoal, DataType.WALK, 200.0);
        ActivityGoal activityGoal4 = new ActivityGoal(user, "activity4", GoalType.ActivityGoal, DataType.RUN, 300.0);
        ActivityGoal activityGoal5 = new ActivityGoal(user, "activity5", GoalType.ActivityGoal, DataType.WALK, 200.0);
        ActivityGoal activityGoal6 = new ActivityGoal(user, "activity6", GoalType.ActivityGoal, DataType.RUN, 750.0);
        ActivityGoal activityGoal7 = new ActivityGoal(user, "activity7", GoalType.ActivityGoal, DataType.WALK, 1000.0);
        ActivityGoal activityGoal8 = new ActivityGoal(user, "activity8", GoalType.ActivityGoal, DataType.RUN, 2000.0);
        FrequencyGoal frequencyGoal = new FrequencyGoal(user, "Frequency1", GoalType.TimePerformedGoal, DataType.WALK, 3);
        WeightLossGoal weightLossGoal;
        try {
            weightLossGoal = new WeightLossGoal(user, "weight1", GoalType.WeightLossGoal, 60.0);
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
                selectedGoalType = GoalType.ActivityGoal;
                break;
        }
        setUpGoalsView();
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





//        ObservableList<Goal> goalObservableList = FXCollections.observableList(goalsToDisplay);
//        goalsListView.setItems(goalObservableList);
//        goalsListView.setCellFactory(new Callback<ListView<Goal>, ListCell<Goal>>() {
//            @Override
//            public ListCell<Goal> call(ListView<Goal> param) {
//                ListCell<Goal> cell = new ListCell<Goal>(){
//
//                    @Override
//                    protected void updateItem(Goal goal, boolean bln) {
//                        super.updateItem(goal, bln);
//
//                        HBox hBox = new HBox();
//                        Text title = new Text();
//                        if (goal != null) {
//                            title.setText(goal.getDescription());
//                            switch (selectedGoalType) {
//                                case WeightLossGoal:
//                                    hBox.getChildren().addAll(title);
//                                    hBox.setPadding(new Insets(5));
//                                    hBox.setStyle("-fx-background-color : green;");
//                                    setGraphic(hBox);
//                                    break;
//                                case ActivityGoal:
//                                    hBox.getChildren().addAll(title);
//                                    hBox.setPadding(new Insets(5));
//                                    hBox.setStyle("-fx-background-color : red;");
//                                    setGraphic(hBox);
//                                    break;
//                                case TimePerformedGoal:
//                                    hBox.getChildren().addAll(title);
//                                    hBox.setPadding(new Insets(5));
//                                    hBox.setStyle("-fx-background-color : blue;");
//                                    setGraphic(hBox);
//                                    break;
//                            }
//                        }
//                    }
//                };
//
//                return cell;
//            }
//        });
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

    public Stage getPrimaryStage() {
        return stage;
    }

    public void setPrimaryStage(Stage stage) {
        this.stage = stage;
    }
}
