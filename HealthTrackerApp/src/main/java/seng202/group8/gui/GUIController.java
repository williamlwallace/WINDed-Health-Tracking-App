package seng202.group8.gui;

import animatefx.animation.*;
import com.jfoenix.controls.JFXButton;
import javafx.application.HostServices;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuButton;
import javafx.scene.layout.BorderPane;

import javafx.event.ActionEvent;
import javafx.stage.Stage;
import seng202.group8.gui.activity_list_collection_displayer.ActivitiesCollectionController;
import seng202.group8.gui.calendar_view.CalendarViewController;
import seng202.group8.gui.edit_user.EditUserController;
import seng202.group8.gui.goals_displayer.GoalsDisplayerController;
import seng202.group8.gui.home_displayer.HomeController;
import seng202.group8.gui.statistics_graph_displayer.GraphController;
import seng202.group8.user.User;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GUIController implements Initializable {

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

    @FXML
    private JFXButton calendarBtn;

    @FXML
    private MenuButton userBtn;


    private User user;
    private Stage stage;
    private HostServices host;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }


    public void setToHome() throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/views/home.fxml"));
        BorderPane homeScene = loader.load();
        userBtn.setText(user.getName());

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
        calendarBtn.setStyle("-fx-background-color: transparent");
    }

    @FXML
    public void loadHome(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/views/home.fxml"));
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
        calendarBtn.setStyle("-fx-background-color: transparent");
    }

    @FXML
    private void loadActivityLog(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/views/activity_list_collection.fxml"));
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
        calendarBtn.setStyle("-fx-background-color: transparent");

    }

    @FXML
    private void loadStatistics(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/views/graphs.fxml"));
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
        calendarBtn.setStyle("-fx-background-color: transparent");
    }

    @FXML
    private void loadGoals(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/views/Goals.fxml"));
        BorderPane goalsScene = loader.load();
        GoalsDisplayerController goalsController = loader.getController();
        goalsController.setPrimaryStage(stage);
        goalsController.setUser(user);
        goalsController.setGoalsService(user.getGoalsService());
        goalsController.initialize();
        scene.getChildren().setAll(goalsScene);
        goalsBtn.setStyle("-fx-background-color: #2874a6");
        homeBtn.setStyle("-fx-background-color: transparent");
        activityBtn.setStyle("-fx-background-color: transparent");
        statsBtn.setStyle("-fx-background-color: transparent");
        calendarBtn.setStyle("-fx-background-color: transparent");
    }

    @FXML
    private void loadCalendar(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/views/calendar_view.fxml"));
        BorderPane calendarScene = loader.load();
        CalendarViewController calendarViewController = loader.getController();
        calendarViewController.setUser(user);
        calendarViewController.setCurrentStage(stage);
        calendarViewController.setDatePickerCells();
        scene.getChildren().setAll(calendarScene);
        calendarBtn.setStyle("-fx-background-color: #2874a6");
        homeBtn.setStyle("-fx-background-color: transparent");
        activityBtn.setStyle("-fx-background-color: transparent");
        statsBtn.setStyle("-fx-background-color: transparent");
        goalsBtn.setStyle("-fx-background-color: transparent");
    }

    @FXML
    private void loadEditUser(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/views/editUser.fxml"));
        BorderPane editUserScene = loader.load();
        EditUserController editUserController = loader.getController();
        editUserController.setUser(user);
        editUserController.setStage(stage);
        editUserController.setGuiController(this);
        editUserController.setUp();
        scene.getChildren().setAll(editUserScene);
        calendarBtn.setStyle("-fx-background-color: transparent");
        homeBtn.setStyle("-fx-background-color: transparent");
        activityBtn.setStyle("-fx-background-color: transparent");
        statsBtn.setStyle("-fx-background-color: transparent");
        goalsBtn.setStyle("-fx-background-color: transparent");
    }

    @FXML
    private void quitApp(ActionEvent event) {
        stage.close();
    }

    public User getUser() { return user; }

    public void setUser(User user) {
        this.user = user;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setHostServices(HostServices host) {
        this.host = host;
    }
}
