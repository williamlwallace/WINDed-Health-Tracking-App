package seng202.group8.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressIndicator;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;

public class GUIController {

    @FXML
    private ProgressIndicator progressIndicator;

    @FXML
    private void handleButtonAction(ActionEvent event) {

        progressIndicator.setProgress(-1.0f);
    }

    public void changeScreenButtonPushed(ActionEvent event) throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getResource("home.fxml"));
        Scene homeScene = new Scene(root);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(homeScene);
        window.show();

    }
}
