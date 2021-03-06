package seng202.group8.gui.activity_list_collection_displayer;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import seng202.group8.parser.*;

import java.util.Arrays;
import java.util.List;

/**
 * @author sgv15
 */
public class ParserErrorOtherController {

    @FXML
    private Label errorText;

    private String errorMessage;


    /**
     * Initializes the screen by setting the title to display details about the error
     */
    @FXML
    public void initialize() {
        Platform.runLater(() -> {
            errorText.setText(errorMessage);
        });
    }


    /**
     * Quits out of the window.
     * @param event
     */
    public void quit(ActionEvent event) {
        Stage stage = (Stage) errorText.getScene().getWindow();
        stage.close();
    }


    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
