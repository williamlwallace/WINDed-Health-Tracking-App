package seng202.group8.gui.statistics_graph_displayer;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import seng202.group8.data_entries.Data;
import seng202.group8.user.User;

public class GraphController {

    @FXML
    private Button distance;

    @FXML
    private Button stress;

    @FXML
    private Button calories;

    @FXML
    private Button heart;

    @FXML
    private Button weight;

    @FXML
    private Button bmi;

    @FXML
    private Button next;

    @FXML
    private Button previous;

    public void showDistance() {
        //CREATES GRAPH FOR DISTANCE OVER TIME
    }

    public void nextData() {

    }

    public void previousdata() {

    }

    public Data getCurrentdata() {
        return currentdata;
    }

    public void setCurrentdata(Data currentdata) {
        this.currentdata = currentdata;
    }

    private Data currentdata;
    private User user;
    private Stage primaryStage;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
}
