package seng202.group8.gui.statistics_graph_displayer;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import seng202.group8.activity_collection.ActivityListCollection;
import seng202.group8.data_entries.Data;
import seng202.group8.services.statistics_service.GraphXY;
import seng202.group8.services.statistics_service.StatisticsService;
import seng202.group8.user.User;

import java.util.ArrayList;

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

    @FXML
    private Text dataName;

    @FXML
    private LineChart graph;

    public void showDistance() {
        //CREATES GRAPH FOR DISTANCE OVER TIME
        graph.setTitle("Distance over Time");
        XYChart.Series series = new XYChart.Series();
        StatisticsService graphData = user.getStatsService();
        GraphXY xyData = graphData.getDistanceOverTimeGraph(currentdata);
        displayOnGraph(xyData, series);

    }

    public void displayOnGraph(GraphXY xyData, XYChart.Series series) {
        for(int i = 0; i < xyData.getXAxis().size(); i++) {
            series.getData().add(new XYChart.Data(xyData.getXAxis(), xyData.getYAxis()));
        }
    }

    public void nextData() {
        if (currentDataIndex == dataSize) {
            //END OF LIST
            System.out.println("END OF LIST");
        } else {
            setCurrentdata(allData.get(currentDataIndex + 1));
            updateData();
        }
    }

    public void previousData() {
        if (currentDataIndex == 0) {
            //END OF LIST
            System.out.println("END OF LIST");
        } else {
            setCurrentdata(allData.get(currentDataIndex - 1));
            updateData();
        }
    }

    public Data getCurrentdata() {
        return currentdata;
    }

    public void setCurrentdata(Data currentdata) {
        this.currentdata = currentdata;
        dataName.setText(currentdata.getTitle());
    }

    public void updateData() {
        dataName.setText(currentdata.getTitle());
        selectedButton.fire();
    }

    public void setup() {
        ActivityListCollection activities = user.getUserActivities();
        ArrayList<Data> allData = activities.getAllData();
        dataSize = allData.size();
        setCurrentdata(allData.get(dataSize - 1));
        currentDataIndex = dataSize - 1;
        selectedButton = distance;
        updateData();
    }

    private ArrayList<Data> allData;
    private Data currentdata;
    private Integer currentDataIndex;
    private Integer dataSize;
    private User user;
    private Stage primaryStage;
    private Button selectedButton;

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
