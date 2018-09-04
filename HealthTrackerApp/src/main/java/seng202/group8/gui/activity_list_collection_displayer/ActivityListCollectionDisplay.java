package seng202.group8.gui.activity_list_collection_displayer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ActivityListCollectionDisplay extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("activity_list_collection.fxml"));

        Parent root= loader.load();
        Scene scene = new Scene(root, 1000, 800);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Ciao");
        primaryStage.show();

    }
}

//import javafx.application.Application;
//import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.control.Label;
//import javafx.scene.layout.StackPane;
//import javafx.scene.layout.VBox;
//import javafx.stage.Stage;
//
//public class ActivityListCollectionDisplay extends Application {
//
//    Scene scene1, scene2;
//
//    @Override
//    public void start(Stage primaryStage) {
//
//        primaryStage.setTitle("My First JavaFX GUI");
//
////Scene 1
//        Label label1= new Label("This is the first scene");
//        Button button1= new Button("Go to scene 2");
//        button1.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                primaryStage.setScene(scene2);
//            }
//        });
//        VBox layout1 = new VBox(20);
//        layout1.getChildren().addAll(label1, button1);
//        scene1= new Scene(layout1, 300, 250);
//
////Scene 2
//        Label label2= new Label("This is the second scene");
//        Button button2= new Button("Go to scene 1");
//        button2.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                primaryStage.setScene(scene1);
//            }
//        });
//        VBox layout2= new VBox(20);
//        layout2.getChildren().addAll(label2, button2);
//        scene2= new Scene(layout2,300,250);
//
//
//        primaryStage.setScene(scene1);
//        primaryStage.show();
//    }
//
//    public static void main(String[] args) {
//        launch(args);
//    }
//
//}
//
