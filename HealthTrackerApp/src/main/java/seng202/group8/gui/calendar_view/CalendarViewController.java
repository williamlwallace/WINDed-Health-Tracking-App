package seng202.group8.gui.calendar_view;

import com.jfoenix.controls.JFXDatePicker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import seng202.group8.activity_collection.ActivityList;
import seng202.group8.data_entries.Data;
import seng202.group8.data_entries.DataType;
import seng202.group8.services.goals_service.goal_types.Goal;
import seng202.group8.services.goals_service.goal_types.GoalType;
import seng202.group8.user.User;

import java.time.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 * @author lfa69
 * calendar_view.fxml controller. It allows the user to check the performed activities during specific days.
 */
public class CalendarViewController {

    private User user;
    private Stage currentStage;

    @FXML
    private JFXDatePicker datePicker;

    @FXML
    private ListView<Data> activitiesListView;

    @FXML
    private ListView<Goal> goalsListView;

    @FXML
    private Text noActivitiesText;

    @FXML
    private Text noGoalsText;



    @FXML
    public void initialize() {
        noGoalsText.setVisible(false);
        setDatePickerListener();
    }

    public void setDatePickerCells() {//TODO: link to GUI class when this is opened. AFTER setting user!!!
        final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(DatePicker param) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        boolean hasGoals = false;
                        boolean hasActivities = false;
                        for (ActivityList activityList : user.getUserActivities().getActivityListCollection()) {
                            for (Data data : activityList.getActivityList()) {
                                LocalDate dataLocalDate = data.getCreationDate()
                                        .toInstant()
                                        .atZone(ZoneId.systemDefault())
                                        .toLocalDate();
                                if (dataLocalDate.isEqual(item)) {
                                    hasActivities = true;

                                }
                            }
                        }
                        for (Goal goal : user.getGoalsService().getAllCurrentGoals()) {
                            if (goal.getTargetDate().toLocalDate().isEqual(item)) {
                                hasGoals = true;
                            }
                        }

                        if (hasGoals && hasActivities) {
                            setStyle("-fx-background-color: linear-gradient(#e59400 50%, #42d13d 50%)");
                        } else if (hasGoals) {
                            setStyle("-fx-background-color:  #e59400");
                        } else if (hasActivities) {
                            setStyle("-fx-background-color:  #42d13d");
                        }

                    }
                };
            }
        };
        datePicker.setDayCellFactory(dayCellFactory);
    }

    /**
     * DatePicker listener, every time a date is selected a list of all the activities performed during
     * the selected day will appear in the ListView dedicated to activities.
     */
    public void setDatePickerListener() {

        noActivitiesText.setText("Click on a day to find the activities performed or the goals due");


        datePicker.valueProperty().addListener((ov, oldValue, newValue) -> {
            System.out.println(datePicker.getValue());
            LocalDate selectedDate = datePicker.getValue();
            LocalDateTime startDate = LocalDateTime.of(selectedDate.getYear(), selectedDate.getMonth(), selectedDate.getDayOfMonth(), 0, 0, 0);
            LocalDateTime endDate = LocalDateTime.of(selectedDate.getYear(), selectedDate.getMonth(), selectedDate.getDayOfMonth(), 23, 59, 59);
            ZonedDateTime zoneStartDateTime = startDate.atZone(ZoneId.systemDefault());
            ZonedDateTime zoneEndDateTime = endDate.atZone(ZoneId.systemDefault());
            Date start = new Date(zoneStartDateTime.toInstant().toEpochMilli());
//            System.out.println("From " + zoneStartDateTime.getHour()+ " to " + zoneEndDateTime.toLocalDate().toString());
            Date end = new Date(zoneEndDateTime.toInstant().toEpochMilli());

//            set up for the actvitities
            ArrayList<Data> userData = user.getUserActivities().retrieveActivititesBtwDates(start, end);
            if (userData.size() != 0) {
                displayActivitiesForTheSelectedDay(userData);
                noActivitiesText.setVisible(false);
            } else {
                noActivitiesText.setVisible(true);
                noActivitiesText.setText("No activities on this day.");
                activitiesListView.getItems().clear();
            }

            //set up for goals
            ArrayList<Goal> goalsOnSelectedDate = user.getGoalsService().getAllCurrentGoalsExpiringOnGivenDate(selectedDate);
            if (goalsOnSelectedDate.size() != 0) {
                noGoalsText.setVisible(false);
                displayGoalsForTheSelectedDay(goalsOnSelectedDate);
            } else {
                noGoalsText.setVisible(true);
                goalsListView.getItems().clear();
            }

        });
    }


    private void displayGoalsForTheSelectedDay(ArrayList<Goal> goals) {
        ObservableList<Goal> goalsObservableList = FXCollections.observableList(goals);
        goalsListView.setItems(goalsObservableList);

        goalsListView.setCellFactory(new Callback<ListView<Goal>, ListCell<Goal>>() {
            @Override
            public ListCell<Goal> call(ListView<Goal> param) {
                ListCell<Goal> cell = new ListCell<Goal>(){

                    @Override
                    protected void updateItem(Goal goal, boolean bln) {
                        super.updateItem(goal, bln);

                        if (goal != null) {
                            HBox hBox = new HBox();
                            Image activityImage = selectRightImageForGoal(goal.getGoalType());
                            ImageView imageView = new ImageView(activityImage);
                            VBox vBox = new VBox();

                            hBox.getChildren().addAll(imageView, vBox);

                            Text title = new Text(goal.getDescription());
                            Text distanceCovered = new Text("Goal type: " + GoalType.fromEnumToString(goal.getGoalType()));

                            vBox.getChildren().addAll(title, distanceCovered);

                            vBox.setPadding(new Insets(5));
                            vBox.setAlignment(Pos.CENTER_LEFT);
                            setGraphic(hBox);
                        }
                    }
                };

                return cell;
            }
        });
    }


    /**
     *
     * @param userData
     * ListView for Data values helper, it populates the ListView with all the activities
     * performed during the selected (from DatePicker) date.
     */
    private void displayActivitiesForTheSelectedDay(ArrayList<Data> userData) {
        ObservableList<Data> dataObservableList = FXCollections.observableList(userData);
        activitiesListView.setItems(dataObservableList);

        activitiesListView.setCellFactory(new Callback<ListView<Data>, ListCell<Data>>() {
            @Override
            public ListCell<Data> call(ListView<Data> param) {
                ListCell<Data> cell = new ListCell<Data>(){

                    @Override
                    protected void updateItem(Data data, boolean bln) {
                        super.updateItem(data, bln);

                        if (data != null) {
                            HBox hBox = new HBox();
                            Image activityImage = selectRightImageForActivity(data.getDataType());
                            ImageView imageView = new ImageView(activityImage);
                            VBox vBox = new VBox();

                            hBox.getChildren().addAll(imageView, vBox);

                            Text title = new Text(data.getTitle());
                            Text distanceCovered = new Text("Distance covered: " + String.format("%.2f", data.getDistanceCovered()) + " m");
                            if (data.getIsGraphable()) {
                                //Text burntCalories = new Text("Burnt calories: " + String.format("%.2f", data.getConsumedCalories()) + " cal");
                                vBox.getChildren().addAll(title, distanceCovered);//, burntCalories);

                                // TODO
                            } else {
                                vBox.getChildren().addAll(title, distanceCovered);
                            }
//                                    , burntCalories);
                            vBox.setPadding(new Insets(5));
                            vBox.setAlignment(Pos.CENTER_LEFT);
                            setGraphic(hBox);
                        }
                    }
                };

                return cell;
            }
        });

    }

    private Image selectRightImageForGoal(GoalType goalType) {
        switch (goalType) {
            case ActivityGoal:
                return new Image("resources/views/images/icons8-geocaching-80.png");
            case WeightLossGoal:
                return new Image("resources/views/images/icons8-bilancia-64.png");
            default:
                return new Image("resources/views/images/icons8-abaco-80.png");
        }
    }

    private Image selectRightImageForActivity(DataType dataType) {
        switch (dataType) {
            case WALK:
                return new Image("resources/views/images/activities_images/icons8-walking-80.png");
            case RUN:
                return new Image("resources/views/images/activities_images/icons8-exercise-80(1).png");
            case HIKE:
                return new Image("resources/views/images/activities_images/icons8-trekking-80(1).png");
            case CLIMB:
                return new Image("resources/views/images/activities_images/icons8-climbing-80.png");
            case SWIM:
                return new Image("resources/views/images/activities_images/icons8-swimmer-80.png");
            case WATER_SPORTS:
                return new Image("resources/views/images/activities_images/icons8-water-sport-80.png");
            default:
                return new Image("resources/views/images/activities_images/icons8-bicycle-80.png");
        }
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Stage getCurrentStage() {
        return currentStage;
    }

    public void setCurrentStage(Stage currentStage) {
        this.currentStage = currentStage;
    }
}
