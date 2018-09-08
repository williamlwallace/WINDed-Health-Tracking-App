package seng202.group8.gui.calendar_view;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import seng202.group8.data_entries.Data;
import seng202.group8.user.User;

import java.time.*;
import java.util.ArrayList;
import java.util.Date;

public class CalendarViewController {

    private User user;
    private Stage currentStage;

    @FXML
    private DatePicker datePicker;

    public void setDatePickerListener() {
        datePicker.valueProperty().addListener((ov, oldValue, newValue) -> {
            System.out.println(datePicker.getValue());
            LocalDate selectedDate = datePicker.getValue();
            LocalDateTime startDate = LocalDateTime.of(selectedDate.getYear(), selectedDate.getMonth(), selectedDate.getDayOfMonth(), 0, 0, 0);
            LocalDateTime endDate = LocalDateTime.of(selectedDate.getYear(), selectedDate.getMonth(), selectedDate.getDayOfMonth(), 23, 59, 59);
            ZonedDateTime zoneStartDateTime = startDate.atZone(ZoneId.systemDefault());
            ZonedDateTime zoneEndDateTime = endDate.atZone(ZoneId.systemDefault());
            Date dateStart = new Date(zoneStartDateTime.toInstant().toEpochMilli());
//            System.out.println("From " + zoneStartDateTime.getHour()+ " to " + zoneEndDateTime.toLocalDate().toString());
            Date dateEnd = new Date(zoneEndDateTime.toInstant().toEpochMilli());
            ArrayList<Data> userData = user.getUserActivities().retrieveActivititesBtwDates(dateStart, dateEnd);
            if (userData.size() == 0) {
                System.out.println("Nothing");
            }
            for (Data data : userData) {
                System.out.println(data.getTitle());
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
