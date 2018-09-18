package seng202.group8.gui;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


import javax.swing.event.ChangeListener;
import java.io.File;
import java.net.URL;


public class GUI extends Application {

    public static Boolean isSplashLoaded = false;

    @Override
    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("mainFrame.fxml"));
        stage.setTitle("WINded");
        stage.setScene(new Scene(root));
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}


