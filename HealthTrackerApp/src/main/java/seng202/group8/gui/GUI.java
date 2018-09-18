package seng202.group8.gui;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import seng202.group8.user.User;


import javax.swing.event.ChangeListener;
import java.io.File;
import java.net.URL;


public class GUI extends Application {

    public static Boolean isSplashLoaded = false;

    private User user;


    @Override
    public void start(Stage stage) throws Exception {


        FXMLLoader loader = new FXMLLoader(getClass().getResource("Splash.fxml"));
        Parent root = loader.load();
//        GUIController guiController = loader.getController();
//        stage.initStyle(StageStyle.UNDECORATED);
        SplashController splashController = loader.getController();
        splashController.setStage(stage);
        stage.setTitle("WINded");
        Scene scene = new Scene(root);
        stage.setScene(scene);
//        guiController.setToHome();
        stage.show();



//        FXMLLoader loader = new FXMLLoader(getClass().getResource("mainFrame.fxml"));
//        Parent root = loader.load();
//        GUIController guiController = loader.getController();
//        stage.setTitle("WINded");
//        Scene scene = new Scene(root);
//        stage.setScene(scene);
//        guiController.setToHome();
//        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}


