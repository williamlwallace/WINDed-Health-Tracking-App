package seng202.group8.gui;

import com.jfoenix.controls.JFXButton;
import javafx.animation.FadeTransition;
import javafx.application.HostServices;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import seng202.group8.gui.activity_list_collection_displayer.ActivitiesCollectionController;
import seng202.group8.gui.calendar_view.CalendarViewController;
import seng202.group8.gui.home_displayer.HomeController;
import seng202.group8.gui.statistics_graph_displayer.GraphController;
import seng202.group8.user.User;

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

    private User user;
    private Stage stage;
    private HostServices host;


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

            StackPane pane = FXMLLoader.load(getClass().getResource("../../../../resources/resources/views/Splash.fxml"));
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../../resources/views/home.fxml"));
        BorderPane homeScene = loader.load();
        //TO ADD THE CONTROLLER LINK AND FOLLOW SAME PROCEDURE IN METHODS BELOW
        HomeController homeController = loader.getController();
        homeController.setPrimaryStage(stage);
        homeController.setUser(user);
        homeController.setHostServices(host);
        homeController.setup();
        scene.getChildren().setAll(homeScene);
        homeBtn.setStyle("-fx-background-color: #2874a6");
        activityBtn.setStyle("-fx-background-color: transparent");
        statsBtn.setStyle("-fx-background-color: transparent");
        goalsBtn.setStyle("-fx-background-color: transparent");
    }

    @FXML
    private void loadHome(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../../resources/views/home.fxml"));
        BorderPane homeScene = loader.load();
        //TO ADD THE CONTROLLER LINK AND FOLLOW SAME PROCEDURE IN METHODS BELOW
        HomeController homeController = loader.getController();
        homeController.setPrimaryStage(stage);
        homeController.setUser(user);
        homeController.setup();
        homeController.setHostServices(host);
        scene.getChildren().setAll(homeScene);
        homeBtn.setStyle("-fx-background-color: #2874a6");
        activityBtn.setStyle("-fx-background-color: transparent");
        statsBtn.setStyle("-fx-background-color: transparent");
        goalsBtn.setStyle("-fx-background-color: transparent");
    }

    @FXML
    private void loadActivityLog(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../../resources/views/activity_list_collection.fxml"));
        BorderPane activityScene = loader.load();
        ActivitiesCollectionController activitiesCollectionController = loader.getController();
        activitiesCollectionController.setPrimaryStage(stage);
        activitiesCollectionController.setUser(user);
        activitiesCollectionController.setUpTreeView();
        scene.getChildren().setAll(activityScene);
        activityBtn.setStyle("-fx-background-color: #2874a6");
        homeBtn.setStyle("-fx-background-color: transparent");
        statsBtn.setStyle("-fx-background-color: transparent");
        goalsBtn.setStyle("-fx-background-color: transparent");
    }

    @FXML
    private void loadStatistics(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../../resources/views/graphs.fxml"));
        BorderPane statsScene = loader.load();
        GraphController graphController = loader.getController();
        graphController.setPrimaryStage(stage);
        graphController.setUser(user);
        graphController.setup();
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

    @FXML
    private void loadCalendar(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../../resources/views/calendar_view.fxml"));
        BorderPane calendarScene = loader.load();
        CalendarViewController calendarViewController = loader.getController();
        calendarViewController.setUser(user);
        calendarViewController.setCurrentStage(stage);
        scene.getChildren().setAll(calendarScene);
        statsBtn.setStyle("-fx-background-color: #2874a6");
        homeBtn.setStyle("-fx-background-color: transparent");
        activityBtn.setStyle("-fx-background-color: transparent");
        goalsBtn.setStyle("-fx-background-color: transparent");
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void quitApp(ActionEvent event) {
        stage.close();
    }

    public void setHostServices(HostServices host) {
        this.host = host;
    }
}
