package seng202.group8.gui.user_info_gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;

public class GetUserInfo extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("GetUserInfo.fxml"));
        primaryStage.setTitle("WINded");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public void enterDetails(ActionEvent event) {
        System.out.printf("hello");
    }

    public static void main(String[] args) {
        launch(args);
    }


}
