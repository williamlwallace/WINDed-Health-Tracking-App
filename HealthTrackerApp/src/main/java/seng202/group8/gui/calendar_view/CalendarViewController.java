package seng202.group8.gui.calendar_view;

import com.jfoenix.controls.JFXDatePicker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import seng202.group8.data_entries.Data;
import seng202.group8.user.User;

import java.time.*;
import java.util.ArrayList;
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


    /**
     * DatePicker listener, every time a date is selected a list of all the activities performed during
     * the selected day will appear in the ListView dedicated to activities.
     * TODO: add logic for the Goals part of it (Deliverable 3).
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
                            VBox vBox = new VBox();
                            Text title = new Text(data.getTitle());
                            Text distanceCovered = new Text("Distance covered: " + data.getDistanceCovered().toString());
//                            Text burntCalories = new Text("Burnt calories: " + String.valueOf(data.getConsumedCalories()));
                            vBox.getChildren().addAll(title, distanceCovered);
//                                    , burntCalories);
                            vBox.setPadding(new Insets(5));
                            setGraphic(vBox);
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

    public Stage getCurrentStage() {
        return currentStage;
    }

    public void setCurrentStage(Stage currentStage) {
        this.currentStage = currentStage;
    }
}
