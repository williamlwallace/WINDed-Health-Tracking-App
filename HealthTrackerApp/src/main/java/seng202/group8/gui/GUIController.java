package seng202.group8.gui;

import animatefx.animation.*;
import com.jfoenix.controls.JFXButton;
import javafx.application.HostServices;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuButton;
import javafx.scene.layout.BorderPane;

import javafx.event.ActionEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import seng202.group8.gui.activity_list_collection_displayer.ActivitiesCollectionController;
import seng202.group8.gui.calendar_view.CalendarViewController;
import seng202.group8.gui.edit_user.EditUserController;
import seng202.group8.gui.goals_displayer.GoalsDisplayerController;
import seng202.group8.gui.home_displayer.HomeController;
import seng202.group8.gui.statistics_graph_displayer.GraphController;
import seng202.group8.gui.switch_user.SwitchUserController;
import seng202.group8.user.User;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author wwa52
 * Controller for mainFrame.fxml, the main GUI screen.
 * Deals with the loading the fxml files for the corresponding sub menus
 */
public class GUIController implements Initializable {

    @FXML
    private BorderPane scene;

    @FXML
    private StackPane homeBtn;

    @FXML
    private StackPane activityBtn;

    @FXML
    private StackPane statsBtn;

    @FXML
    private StackPane goalsBtn;

    @FXML
    private StackPane calendarBtn;

    @FXML
    private MenuButton userBtn;


    private User user;
    private Stage stage;
    private HostServices host;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }


    /**
     * Function that refreshes the home screen, this is called whenever data is changed and the home screen data needs to be updated
     * @throws IOException
     */
    public void setToHome() throws IOException {
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


    /**
     * Event handler for pressing the home button on the side menu, loads the home screen fxml file and controller
     * @param event
     * @throws IOException
     */
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


    /**
     * Event handler for pressing the activity log button on the side menu, loads the activity log screen fxml file and controller
     * @param event
     * @throws IOException
     */
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


    /**
     * Event handler for pressing the statistics button on the side menu, loads the statistics screen fxml file and controller
     * @param event
     * @throws IOException
     */
    @FXML
    private void loadStatistics(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/views/graphs.fxml"));
        BorderPane statsScene = loader.load();
        GraphController graphController = loader.getController();
        graphController.setPrimaryStage(stage);
        //System.out.println(this.user);
        graphController.setUser(user);
        graphController.setup();
        scene.getChildren().setAll(statsScene);
        statsBtn.setStyle("-fx-background-color: #2874a6");
        homeBtn.setStyle("-fx-background-color: transparent");
        activityBtn.setStyle("-fx-background-color: transparent");
        goalsBtn.setStyle("-fx-background-color: transparent");
        calendarBtn.setStyle("-fx-background-color: transparent");
    }


    /**
     * Event handler for pressing the goals button on the side menu, loads the goals screen fxml file and controller
     * @param event
     * @throws IOException
     */
    @FXML
    private void loadGoals2(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/views/Goals.fxml"));
        BorderPane goalsScene = loader.load();
        GoalsDisplayerController goalsController = loader.getController();
        goalsController.setPrimaryStage(stage);
        goalsController.setUser(user);
        goalsController.setGoalsService(user.getGoalsService());
        goalsController.initialize(user);
        scene.getChildren().setAll(goalsScene);
        goalsBtn.setStyle("-fx-background-color: #2874a6");
        homeBtn.setStyle("-fx-background-color: transparent");
        activityBtn.setStyle("-fx-background-color: transparent");
        statsBtn.setStyle("-fx-background-color: transparent");
        calendarBtn.setStyle("-fx-background-color: transparent");
    }


    /**
     * Event handler for pressing the calendar button on the side menu, loads the calendar screen fxml file and controller
     * @param event
     * @throws IOException
     */
    @FXML
    private void loadCalendar(ActionEvent event) throws IOException {
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


    /**
     * Event handler for pressing the edit user button in the user menu, loads the edit user screen fxml file and controller
     * @param event
     * @throws IOException
     */
    @FXML
    private void loadEditUser(ActionEvent event) throws IOException {
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


    /**
     * Event handler for pressing the switch user button in the user menu, loads the switch user screen fxml file and controller
     * @param event
     * @throws IOException
     */
    @FXML
    private void loadUserScreen(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/views/switch_user.fxml"));
        Parent root = loader.load();
        SwitchUserController switchUserController = loader.getController();
        switchUserController.setHostServices(host);
        switchUserController.setStage(stage);
        switchUserController.setUser(user);
        stage.setTitle("WINded");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    /**
     * Event handler for pressing the quit app button in the user menu, closes the stage/window
     * @param event
     */
    @FXML
    private void quitApp(ActionEvent event) { stage.close(); }


    /**
     *
     * @return the user property
     */
    public User getUser() { return user; }


    /**
     *
     * @param user a new User object for the user property
     */
    public void setUser(User user) { this.user = user; }


    /**
     *
     * @return the stage property
     */
    public Stage getStage() { return stage; }


    /**
     *
     * @param stage a new Stage object for the stage property
     */
    public void setStage(Stage stage) { this.stage = stage; }


    /**
     *
     * @param host a HostServices object
     */
    public void setHostServices(HostServices host) { this.host = host; }

}
