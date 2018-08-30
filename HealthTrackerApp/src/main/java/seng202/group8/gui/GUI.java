package seng202.group8.gui;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;

import java.awt.*;


public class GUI extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        BorderPane root = new BorderPane();
        root.setTop(topPane());
        root.setLeft(sidePane());
        primaryStage.setTitle("Winded");
        primaryStage.setScene(new Scene(root, 1024, 768));
        primaryStage.show();
    }


    private HBox topPane() {
        HBox hbox = new HBox();
        hbox.setStyle("-fx-background-color:#212121");
        hbox.setPadding(new Insets(25, 25, 25, 25));
        hbox.setSpacing(10);

        Text title = new Text("WINded");
        title.setFont(Font.font("Tahoma", FontWeight.BOLD, 38));
        title.setFill(Color.WHITE);
        hbox.getChildren().add(title);

        return hbox;

    }


    private VBox sidePane() {
        VBox vbox = new VBox();
        vbox.setPrefWidth(150);
        vbox.setStyle("-fx-background-color:#2d3041");

        for (int i = 1; i < 5; i++) {
            vbox.getChildren().add(Item(String.valueOf(i)));
        }

        return vbox;
    }


    private HBox Item(String icon) {
        Image image = new Image(GUI.class.getResource("/icons/" + icon + ".png").toExternalForm());
        ImageView imageView = new ImageView(image);
        Button btn = new Button();
        btn.setGraphic(imageView);
        btn.setPrefSize(150, 256);
        btn.setStyle("-fx-background-color:#212121");
        Pane paneIndicator = new Pane();
        paneIndicator.setPrefSize(5, 256);
        paneIndicator.setStyle("-fx-background-color:#212121");
        menuDecorator(btn, paneIndicator);
        HBox hbox = new HBox(paneIndicator, btn);

        return hbox;
    }


    private void menuDecorator(Button btn, Pane pane) {
        btn.setOnMouseEntered(value -> {
            btn.setStyle("-fx-background-color:black");
            pane.setStyle("-fx-background-color:white");
        });
        btn.setOnMouseExited(value -> {
            btn.setStyle("-fx-background-color:#212121");
            pane.setStyle("-fx-background-color:#212121");
        });
    }


    public static void main(String[] args) {
        launch(args);
    }
}

