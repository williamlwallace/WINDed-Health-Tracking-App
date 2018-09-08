package seng202.group8.gui.calendar_view;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import seng202.group8.user.User;

public class CalendarViewController {

    private User user;
    private Stage currentStage;

    @FXML
    private DatePicker datePicker;

    public void setDatePickerListener() {
        datePicker.valueProperty().addListener((ov, oldValue, newValue) -> {
            System.out.println(datePicker.getValue());
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
