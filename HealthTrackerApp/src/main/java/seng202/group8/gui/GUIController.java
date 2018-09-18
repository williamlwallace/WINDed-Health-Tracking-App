package seng202.group8.gui;

import com.jfoenix.controls.JFXButton;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

import javafx.event.ActionEvent;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GUIController implements Initializable {

    @FXML
    private BorderPane root;

    @FXML
    private BorderPane scene;

    @FXML
    private JFXButton homeBtn;

    @FXML
    private JFXButton activityBtn;

    @FXML
    private JFXButton statsBtn;

    @FXML
    private JFXButton goalsBtn;



    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
//        if (!GUI.isSplashLoaded) {
//            loadSplash();
//        }
    }


    private void loadSplash()
    {
        try {
            GUI.isSplashLoaded = true;

            StackPane pane = FXMLLoader.load(getClass().getResource("Splash.fxml"));
            root.getChildren().setAll(pane);

            FadeTransition fadeIn = new FadeTransition(Duration.seconds(3), pane);
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);
            fadeIn.setCycleCount(1);

            FadeTransition fadeOut = new FadeTransition(Duration.seconds(3), pane);
            fadeIn.setFromValue(1);
            fadeIn.setToValue(0);
            fadeIn.setCycleCount(1);

            fadeIn.play();

            fadeIn.setOnFinished((e) -> {
                fadeOut.play();
            });

            fadeOut.setOnFinished((e) -> {
                try {
                    BorderPane parentContent = FXMLLoader.load(getClass().getResource(("mainFrame.fxml")));
                    root.getChildren().setAll(parentContent);

                } catch (IOException ex) {
                    Logger.getLogger(GUIController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });

        } catch (IOException ex) {
            Logger.getLogger(GUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    public void setToHome() throws IOException {
        BorderPane homeScene = FXMLLoader.load(getClass().getResource("home.fxml"));
        scene.getChildren().setAll(homeScene);
        homeBtn.setStyle("-fx-background-color: #2874a6");
    }

    @FXML
    private void loadHome(ActionEvent event) throws IOException
    {
        BorderPane homeScene = FXMLLoader.load(getClass().getResource("home.fxml"));
        scene.getChildren().setAll(homeScene);
        homeBtn.setStyle("-fx-background-color: #2874a6");
        activityBtn.setStyle("-fx-background-color: transparent");
        statsBtn.setStyle("-fx-background-color: transparent");
        goalsBtn.setStyle("-fx-background-color: transparent");
    }

    @FXML
    private void loadActivityLog(ActionEvent event) throws IOException
    {
        BorderPane activityScene = FXMLLoader.load(getClass().getResource("../../../resources/views/activity_list_collection.fxml"));
        scene.getChildren().setAll(activityScene);
        activityBtn.setStyle("-fx-background-color: #2874a6");
        homeBtn.setStyle("-fx-background-color: transparent");
        statsBtn.setStyle("-fx-background-color: transparent");
        goalsBtn.setStyle("-fx-background-color: transparent");
    }

    @FXML
    private void loadStatistics(ActionEvent event) throws IOException
    {
        BorderPane statsScene = FXMLLoader.load(getClass().getResource("../../../resources/views/graphs.fxml"));
        scene.getChildren().setAll(statsScene);
        statsBtn.setStyle("-fx-background-color: #2874a6");
        homeBtn.setStyle("-fx-background-color: transparent");
        activityBtn.setStyle("-fx-background-color: transparent");
        goalsBtn.setStyle("-fx-background-color: transparent");
    }

    @FXML
    private void loadGoals(ActionEvent event) throws IOException
    {
        BorderPane goalsScene = FXMLLoader.load(getClass().getResource("Goals.fxml"));
        scene.getChildren().setAll(goalsScene);
        goalsBtn.setStyle("-fx-background-color: #2874a6");
        homeBtn.setStyle("-fx-background-color: transparent");
        activityBtn.setStyle("-fx-background-color: transparent");
        statsBtn.setStyle("-fx-background-color: transparent");
    }
}
