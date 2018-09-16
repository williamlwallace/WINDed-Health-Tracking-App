package seng202.group8.gui;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import javax.swing.*;
import javafx.event.ActionEvent;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GUIController {



    @FXML
    private void loadSplash() throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getResource("Splash.fxml"));


        FadeTransition fadeIn = new FadeTransition(Duration.seconds(3), root);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.setCycleCount(1);

        FadeTransition fadeOut = new FadeTransition(Duration.seconds(3), root);
        fadeIn.setFromValue(1);
        fadeIn.setToValue(0);
        fadeIn.setCycleCount(1);

        fadeIn.play();

        fadeIn.setOnFinished((e) -> {
            fadeOut.play();
        });

        fadeOut.setOnFinished((e) -> {

        });


    }


    @FXML
    private void loadHome(ActionEvent event) throws  IOException
    {
        Parent root = FXMLLoader.load(getClass().getResource("home.fxml"));
        Scene homeScene = new Scene(root);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(homeScene);
        window.show();
    }

    @FXML
    private void loadActivityLog(ActionEvent event) throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getResource("ActivityLog.fxml"));
        Scene activityScene = new Scene(root);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(activityScene);
        window.show();
    }

    @FXML
    private void loadStatistics(ActionEvent event) throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getResource("Statistics.fxml"));
        Scene activityScene = new Scene(root);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(activityScene);
        window.show();
    }

    @FXML
    private void loadGoals(ActionEvent event) throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getResource("Goals.fxml"));
        Scene activityScene = new Scene(root);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(activityScene);
        window.show();
    }
}
