package seng202.group8.gui;

import animatefx.animation.*;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
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
import seng202.group8.gui.user_info_gui.GetUserInfoController;
import seng202.group8.user.User;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SplashController implements Initializable {

    @FXML
    private StackPane parent;

    @FXML
    private ImageView logo;

    private User user;
    private Stage stage;
    private HostServices host;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        new splashScreen().start();
        FadeTransition fadeIn = new FadeTransition(javafx.util.Duration.seconds(1), parent);
        fadeIn.setFromValue(1);
        fadeIn.setToValue(1);
        fadeIn.setCycleCount(1);

        FadeTransition fadeOut = new FadeTransition(Duration.seconds(1), parent);
        fadeIn.setFromValue(1);
        fadeIn.setToValue(1);
        fadeIn.setCycleCount(1);

        fadeIn.play();

//        String musicFile = "../../../resources/views/Winded Chime.wav";
//        Media sound = new Media(new File(musicFile).toURI().toString());
//        MediaPlayer mediaPlayer = new MediaPlayer(sound);

        SlideInLeft anim1 = new SlideInLeft(logo);
        anim1.setSpeed(1.2);
        anim1.play();
//        mediaPlayer.play();
        LightSpeedOut anim2 = new LightSpeedOut(logo);
        anim2.setDelay(Duration.seconds(0.5));
        anim2.setSpeed(2);
        anim1.playOnFinished(anim2);





//        Bounce tada = new Bounce(logo);
//        tada.setCycleCount(6);
//        tada.play();





        fadeIn.setOnFinished((e) -> {
            fadeOut.play();
        });
        //1280
        //720

        fadeOut.setOnFinished((e) -> {
            try {
                // This is where we call the database and try to retrieve the user
                if (user != null) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../../../resources/views/mainFrame.fxml"));
                    Parent root = loader.load();
                    GUIController guiController = loader.getController();
                    guiController.setHostServices(host);
                    guiController.setStage(stage);
                    guiController.setUser(user);
                    stage.setTitle("WINded");
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    guiController.setToHome();
                    stage.show();
                } else {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../../../resources/views/GetUserInfo.fxml"));
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

//    class splashScreen extends Thread {
//
//        @Override
//        public void run() {
//            try {
//                Thread.sleep(3000);
//
//                Platform.runLater(new Runnable() {
//                    @Override
//                    public void run() {
//                        Parent root = null;
//                        try {
//                            root = FXMLLoader.load(getClass().getResource("mainFrame.fxml"));
//                         mainFrame   System.out.println("Done");
//                        } catch (IOException ex) {
//                            Logger.getLogger(SplashController.class.getName()).log(Level.SEVERE, null, ex);
//                        }
//                        Scene scene = new Scene(root);
//                        Stage stage = new Stage();
//                        stage.setScene(scene);
//                        stage.setTitle("WINded");
//                        stage.show();
//                        parent.getScene().getWindow().hide();
//
//                    }
//                });
//            } catch (InterruptedException ex) {
//                Logger.getLogger(SplashController.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//    }


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

    public void setHostServices(HostServices host) {
        this.host = host;
    }
}
