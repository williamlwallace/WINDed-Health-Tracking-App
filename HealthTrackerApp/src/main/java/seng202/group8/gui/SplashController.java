package seng202.group8.gui;

import animatefx.animation.*;
//import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import java_sqlite_db.SQLiteJDBC;
import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import seng202.group8.gui.switch_user.SwitchUserController;
import seng202.group8.gui.user_info_gui.GetUserInfoController;
import seng202.group8.user.User;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author wwa52, lfa69
 * Controller class for the splash screen, this is the first fxml file called from the main GUI class
 */
public class SplashController implements Initializable {

    @FXML
    private StackPane parent;

    @FXML
    private ImageView logo;

    private User user;
    private Stage stage;
    private HostServices host;


    /**
     *
     * Initialises the splash screen including animations. Also determines whether to load the create new user screen or
     * switch user screen based whether or not there are user(s) in the database.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        FadeTransition fadeIn = new FadeTransition(javafx.util.Duration.seconds(1), parent);
        fadeIn.setFromValue(1);
        fadeIn.setToValue(1);
        fadeIn.setCycleCount(1);

        FadeTransition fadeOut = new FadeTransition(Duration.seconds(1), parent);
        fadeIn.setFromValue(1);
        fadeIn.setToValue(1);
        fadeIn.setCycleCount(1);

        fadeIn.play();

//        String musicFile = "/resources/views/audio/Winded Chime.wav";
//        Media sound = new Media((new File(musicFile)).toURI().toString());
//        MediaPlayer mediaPlayer = new MediaPlayer(sound);

        SlideInLeft anim1 = new SlideInLeft(logo);
        anim1.setSpeed(1.2);
        anim1.play();
//        mediaPlayer.play();
        LightSpeedOut anim2 = new LightSpeedOut(logo);
        anim2.setDelay(Duration.seconds(0.5));
        anim2.setSpeed(2);
        anim1.playOnFinished(anim2);

        fadeIn.setOnFinished((e) -> {
            fadeOut.play();
        });

        fadeOut.setOnFinished((e) -> {
            try {
                SQLiteJDBC database = new SQLiteJDBC();
                ArrayList<User> users = database.retrieveAllUsers();

                if (users.size() != 0) { // Check for if there are users in the database
                    user = users.get(0);
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

                } else {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/views/GetUserInfo.fxml"));
                    Parent root = loader.load();
                    GetUserInfoController getUserInfoController = loader.getController();
                    getUserInfoController.setHostServices(host);
                    getUserInfoController.setStage(stage);
                    stage.setTitle("WINded");
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                }
            } catch (IOException ex) {
                Logger.getLogger(SplashController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }


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
