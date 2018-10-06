package seng202.group8.gui.home_displayer;

import animatefx.animation.Pulse;
import com.jfoenix.controls.JFXButton;
import javafx.application.HostServices;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.Duration;
import seng202.group8.data_entries.Data;
import seng202.group8.gui.GoogleMapsTools.GoogleMapsTools;
import seng202.group8.services.health_service.HealthService;
import seng202.group8.services.statistics_service.StatisticsService;
import seng202.group8.user.BMIType;
import seng202.group8.user.User;

import java.applet.AppletContext;
import java.io.IOException;
import java.net.*;

public class HomeController {

    @FXML
    private Label healthText;

    @FXML
    private JFXButton cardio;

    @FXML
    private JFXButton brad;

    @FXML
    private JFXButton tach;

    @FXML
    private Label bmiText;

    @FXML
    private Label mostRecentActivity;

    @FXML
    private ImageView bmiImage;

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

    @FXML
    private WebView homeWebView;

    private HealthService healthService;

    private Image anorexicBMI = new Image("/resources/views/images/anorexic_bmi.png");
    private Image lightBMI = new Image("/resources/views/images/light_bmi.png");
    private Image normalBMI = new Image("/resources/views/images/normal_bmi.png");
    private Image overweightBMI = new Image("/resources/views/images/overweight_bmi.png");
    private Image obeseBMI = new Image("/resources/views/images/obese_bmi.png");
    private Image noRisk = new Image("/resources/views/images/ok.png");
    private Image healthRisk = new Image("/resources/views/images/healthrisk.png");

    /**
     * Sets the page up when the user first goes into the Home page
     */
    public void setup() {
        statsService = user.getStatsService();
        statsService.updateHomeStats(user);
        statsService.setHealthStatus();
        healthText.setText(statsService.getHealthStatus());
        Pulse pulse = new Pulse(heartrate);
        pulse.setSpeed(3.5);
        pulse.setCycleCount(2);
        pulse.setDelay(Duration.seconds(0.5));
        pulse.play();
        pulse.playOnFinished(pulse);

        if (user.getBMI().getBMICategory() == BMIType.ANOREXIC) {
            bmiImage.setImage(anorexicBMI);
        }
        if (user.getBMI().getBMICategory() == BMIType.LIGHT) {
            bmiImage.setImage(lightBMI);
        }
        if (user.getBMI().getBMICategory() == BMIType.NORMAL) {
            bmiImage.setImage(normalBMI);
        }
        if (user.getBMI().getBMICategory() == BMIType.OVERWEIGHT) {
            bmiImage.setImage(overweightBMI);
        }
        if (user.getBMI().getBMICategory() == BMIType.OBESE) {
            bmiImage.setImage(obeseBMI);
        }

        bmiText.setText(user.getBMIString());

        //Edge cases to cover
        if (user.getBMI().getBMIValue() < 2) {
            bmiImage.setImage(anorexicBMI);
        }

        if (user.getBMI().getBMIValue() > 299.0) {
            bmiText.setText(">300 OBESE");
        }

//        System.out.println(user.getStatsService().getHealthStatus());
//        if (healthService.isTachicardic() || healthService.isAtCardiovasMortalityRisk() || healthService.isAtCardiovasMortalityRisk()) {
//            healthRiskImage.setImage(healthRisk);
//        } else {
//            healthRiskImage.setImage(noRisk);
//        }

        run.setText(String.format("%.1f", statsService.getKmRunTotal()) + " km");
        walk.setText(String.format("%.1f", statsService.getkmWalkTotal()) + " km");
        bike.setText(String.format("%.1f", statsService.getKmBikedTotal()) + " km");
        all.setText(String.format("%.1f", statsService.getKmTotal()) + " km");
        calories.setText(String.format("%.1f", statsService.getCaloriesBurnedTotal()) + " Cal");
        heartValue.setText(statsService.getAverageHeartRate() + " BPM");
        statsService.setWeightLossTotal();
        weightValue.setText(String.format("%.1f", statsService.getWeightLossTotal()) + " kg");
//        totalStatsLabel.setStyle("-fx-font-size: 26px; -fx-font-weight: bold; -fx-background-color: #2e86c1");
//        bmiLabel2.setStyle("-fx-font-size: 26px; -fx-font-weight: bold; -fx-background-color:  #2e86c1");
//        healthLabel.setStyle("-fx-font-size: 26px; -fx-font-weight: bold; -fx-background-color:  #2e86c1");
//        heartLabel.setStyle("-fx-font-size: 26px; -fx-font-weight: bold; -fx-background-color:  #2e86c1");
//        brad.setStyle("-fx-background-color:  #2e86c1");
//        cardio.setStyle("-fx-background-color:  #2e86c1");
//        tach.setStyle("-fx-background-color:  #2e86c1");

        //SWebView Setup
        try {
            String strGoogleMaps = GoogleMapsTools.returnHTMLFileToString("/resources/views/googleMapsView.html");
            // Retrieve selected Data value
            Data data = user.getUserActivities().getMostCurrentActivity();

            if (data != null) {
                mostRecentActivity.setText("Most Recent Activity - " + data.getTitle());
                String htmlFile = GoogleMapsTools.jsInjection(strGoogleMaps, data);
                WebEngine webEngine = homeWebView.getEngine();
//              System.out.println(htmlFile);
                webEngine.loadContent(htmlFile);
            } else {
                //raise dialog saying no values in or show chch
            }

            // Inject the GoogleMaps html file (read as string above) with the coordinates of the selected data

        } catch (IOException e) {

        }
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
