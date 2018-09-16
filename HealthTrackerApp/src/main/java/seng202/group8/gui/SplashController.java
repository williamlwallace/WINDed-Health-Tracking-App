package seng202.group8.gui;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SplashController implements Initializable {

    @FXML
    private AnchorPane parent;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        new splashScreen().start();
//        FadeTransition fadeIn = new FadeTransition(javafx.util.Duration.seconds(3), parent);
//        fadeIn.setFromValue(0);
//        fadeIn.setToValue(1);
//        fadeIn.setCycleCount(1);
//
//        FadeTransition fadeOut = new FadeTransition(Duration.seconds(3), parent);
//        fadeIn.setFromValue(1);
//        fadeIn.setToValue(0);
//        fadeIn.setCycleCount(1);
//
//        fadeIn.play();
//
//        fadeIn.setOnFinished((e) -> {
//            fadeOut.play();
//        });
//
//        fadeOut.setOnFinished((e) -> {
//            try {
//                Parent fxml = FXMLLoader.load(getClass().getResource("home.fxml"));
//                parent.getChildren().removeAll();
//                parent.getChildren().setAll(fxml);
//            } catch (IOException ex) {
//                Logger.getLogger(SplashController.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        });

    }

    class splashScreen extends Thread {

        @Override
        public void run() {
            try {
                Thread.sleep(3000);

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        Parent root = null;
                        try {
                            root = FXMLLoader.load(getClass().getResource("home.fxml"));
                        } catch (IOException ex) {
                            Logger.getLogger(SplashController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        Scene scene = new Scene(root);
                        Stage stage = new Stage();
                        stage.setScene(scene);
                        stage.setTitle("WINded");
                        stage.show();
                        parent.getScene().getWindow().hide();
                    }
                });
            } catch (InterruptedException ex) {
                Logger.getLogger(SplashController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }


}
