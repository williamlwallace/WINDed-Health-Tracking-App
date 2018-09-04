package seng202.group8.gui;

import javafx.fxml.FXML;
import javafx.scene.control.ProgressIndicator;

import java.awt.event.ActionEvent;

public class GUIController {

    @FXML
    private ProgressIndicator progressIndicator;

    @FXML
    private void handleButtonAction(ActionEvent event) {

        progressIndicator.setProgress(-1.0f);
    }
}
