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
    private Text noActivitiesText;



    @FXML
    public void initialize() {
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
                        for (ActivityList activityList : user.getUserActivities().getActivityListCollection()) {
                            for (Data data : activityList.getActivityList()) {
                                LocalDate dataLocalDate = data.getCreationDate()
                                        .toInstant()
                                        .atZone(ZoneId.systemDefault())
                                        .toLocalDate();
                                if (dataLocalDate.isEqual(item)) {
                                    setStyle("-fx-background-color:  #42d13d");
                                }
                            }
                        }
                        for (Goal goal : user.getGoalsService().getCurrentActivityGoals()) {
                            if (goal.getTargetDate().toLocalDate().isEqual(item)) {
                                setStyle("-fx-background-color:  #e59400");
                            }
                        }
                        for (Goal goal : user.getGoalsService().getCurrentTimesPerformedGoals()) {
                            if (goal.getTargetDate().toLocalDate().isEqual(item)) {
                                setStyle("-fx-background-color:  #e59400");
                            }
                        }
                        for (Goal goal : user.getGoalsService().getCurrentWeightLossGoals()) {
                            if (goal.getTargetDate().toLocalDate().isEqual(item)) {
                                setStyle("-fx-background-color:  #e59400");
                            }
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

            ArrayList<Data> userData = user.getUserActivities().retrieveActivititesBtwDates(start, end);
            if (userData.size() == 0) {
                System.out.println("Nothing");
            }
            for (Data data : userData) {
                System.out.println("START: " + start.getTime());
                System.out.println("TO CHECK: " + data.getCreationDate().getTime());
                System.out.println("END: " + end.getTime());
            }

            if (userData.size() != 0) {
                displayActivitiesForTheSelectedDay(userData);
                noActivitiesText.setVisible(false);
            } else {
                noActivitiesText.setVisible(true);
                noActivitiesText.setText("No activities on this day.");
                activitiesListView.getItems().clear();
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
