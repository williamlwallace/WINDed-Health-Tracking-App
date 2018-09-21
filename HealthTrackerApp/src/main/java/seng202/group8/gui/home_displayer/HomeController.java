package seng202.group8.gui.home_displayer;

import animatefx.animation.Pulse;
import javafx.application.HostServices;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;
import seng202.group8.services.statistics_service.StatisticsService;
import seng202.group8.user.User;

import java.applet.AppletContext;
import java.io.IOException;
import java.net.*;

public class HomeController {

    @FXML
    private Label healthText;

    @FXML
    private Button cardio;

    @FXML
    private Button brad;

    @FXML
    private Button tach;

    @FXML
    private Label bmiText;

    @FXML
    private Label run;

    @FXML
    private Label walk;

    @FXML
    private Label bike;

    @FXML
    private Label all;

    @FXML
    private Label calories;

    @FXML
    private Label bmiLabel2;

    @FXML
    private Label healthLabel;

    @FXML
    private Label totalStatsLabel;

    @FXML
    private Label heartLabel;

    @FXML
    private Label heartValue;

    @FXML
    private Label weightValue;

    @FXML
    private ImageView heartrate;

    /**
     * Sets the page up when the user first goes into the Home page
     */
    public void setup() {
        statsService = user.getStatsService();
        statsService.updateHomeStats(user);
        statsService.setHealthStatus();
        healthText.setText(statsService.getHealthStatus());
        Pulse pulse = new Pulse(heartrate);
        pulse.setSpeed(3);
        pulse.setCycleCount(2);
        pulse.setDelay(Duration.seconds(0.5));
        pulse.play();
        pulse.playOnFinished(pulse);
        bmiText.setText(user.getBMIString());
        run.setText(String.format("%.1f", statsService.getKmRunTotal()) + " km");
        walk.setText(String.format("%.1f", statsService.getkmWalkTotal()) + " km");
        bike.setText(String.format("%.1f", statsService.getKmBikedTotal()) + " km");
        all.setText(String.format("%.1f", statsService.getKmTotal()) + " km");
        calories.setText(String.format("%.1f", statsService.getCaloriesBurnedTotal()) + " Cal");
        heartValue.setText(statsService.getAverageHeartRate() + " BPM");
        statsService.setWeightLossTotal();
        weightValue.setText(String.format("%.1f", statsService.getWeightLossTotal()) + " kg");
        totalStatsLabel.setStyle("-fx-font-size: 26px; -fx-font-weight: bold; -fx-background-color: #2e86c1");
        bmiLabel2.setStyle("-fx-font-size: 26px; -fx-font-weight: bold; -fx-background-color:  #2e86c1");
        healthLabel.setStyle("-fx-font-size: 26px; -fx-font-weight: bold; -fx-background-color:  #2e86c1");
        heartLabel.setStyle("-fx-font-size: 26px; -fx-font-weight: bold; -fx-background-color:  #2e86c1");
        brad.setStyle("-fx-background-color:  #2e86c1");
        cardio.setStyle("-fx-background-color:  #2e86c1");
        tach.setStyle("-fx-background-color:  #2e86c1");
    }

    public void searchCardio() {
        this.host.showDocument("https://www.google.com/search?q=Cardiovascular+Mortality");
    }

    public void searchBrad() {
        this.host.showDocument("https://www.google.com/search?q=bradycardia");
    }

    public void searchTach() {
        this.host.showDocument("https://www.google.com/search?q=Tachycardia");
    }

    public void setHostServices(HostServices host) {
        this.host = host;
    }

    /**
     * Variables used for navigation of data and the stage object
     */
    private User user;
    private Stage primaryStage;
    private StatisticsService statsService;
    private HostServices host;

    /**
     * Gets the current user using the page
     * @return
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the user for the page
     * @param user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Gets the primary stage
     * @return the primary stage object
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * Sets the primary Stage variable
     * @param primaryStage a stage variable to be passed in
     */
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
}
