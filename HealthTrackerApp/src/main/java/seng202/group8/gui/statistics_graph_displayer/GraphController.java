package seng202.group8.gui.statistics_graph_displayer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import seng202.group8.activity_collection.ActivityListCollection;
import seng202.group8.data_entries.Data;
import seng202.group8.services.statistics_service.GraphXY;
import seng202.group8.services.statistics_service.StatisticsService;
import seng202.group8.user.User;

import java.time.LocalDateTime;
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
    private Label dataname;

    @FXML
    private LineChart<Double,Double> graph;

    @FXML
    private NumberAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    public ArrayList<Data> getAllData() {
        return allData;
    }

    public void showDistance() {
        //CREATES GRAPH FOR DISTANCE OVER TIME
//        final NumberAxis xAxis = new NumberAxis();
//        final NumberAxis yAxis = new NumberAxis();
//        xAxis.setLabel("Time");
//        yAxis.setLabel("Distance");
//        graph = new LineChart<>(xAxis,yAxis);
//        graph.setTitle("Distance over Time");
//        XYChart.Series series = new XYChart.Series();
//        StatisticsService graphData = user.getStatsService();
//        //GraphXY xyData = graphData.getDistanceOverTimeGraph(currentdata);
//        GraphXY graphLists = new GraphXY();
//        //graphData.testGraph(graphLists);
//        graphLists.addYAxis(10.0);
//        graphLists.addXAxis(100.0);
//        displayOnGraph(graphLists, series);
        ObservableList<XYChart.Series<Double, Double>> lineChartData = FXCollections.observableArrayList();

        StatisticsService graphData = user.getStatsService();
        System.out.println(graphData.getAverageHeartRate());
        GraphXY xyData = graphData.getDistanceOverTimeGraph(currentdata);
        LineChart.Series<Double, Double> series = generateSeries(xyData);
        //series.getData().add(new XYChart.Data<Double, Double>(1.0, 15.0));
        //series.getData().add(new XYChart.Data<Double, Double>(4.0, 7.0));
        xAxis.setLabel("Time");
        yAxis.setLabel("Distance");
//        xAxis.setLowerBound(0.5);
//        xAxis.setUpperBound(10.0);
//        xAxis.setTickUnit(0.25);
        series.setName("Distance Over Time");
        lineChartData.add(series);

        graph.setData(lineChartData);
        graph.createSymbolsProperty();
    }

    public void showHeartRate() {
        ObservableList<XYChart.Series<Double, Double>> lineChartData = FXCollections.observableArrayList();
        StatisticsService graphData = user.getStatsService();
        GraphXY xyData = graphData.getHeartRateOverTimeGraph(currentdata);
        LineChart.Series<Double, Double> series = generateSeries(xyData);
        xAxis.setLabel("Time");
        yAxis.setLabel("Heart Rate");
        series.setName("Heart Rate Over Time");
        lineChartData.add(series);

        graph.setData(lineChartData);
        graph.createSymbolsProperty();
    }

    public LineChart.Series<Double, Double> generateSeries(GraphXY xyData) {
        LineChart.Series<Double, Double> series = new LineChart.Series<Double, Double>();
        for (int i = 0; i < xyData.getXAxis().size(); i++) {
            series.getData().add(new XYChart.Data<Double, Double>(xyData.getXAxis().get(i), xyData.getYAxis().get(i)));
        }
        return series;
    }

    public void nextData() {
        if (currentDataIndex == dataSize - 1) {
            //END OF LIST
            System.out.println("END OF LIST");
        } else {
            currentDataIndex++;
            setCurrentdata(allData.get(currentDataIndex));
            updateData();
        }
    }

    public void previousData() {
        if (currentDataIndex == 0) {
            //END OF LIST
            System.out.println("END OF LIST");
        } else {
            currentDataIndex--;
            setCurrentdata(allData.get(currentDataIndex));
            updateData();
        }
    }

    public Data getCurrentdata() {
        return currentdata;
    }

    public void setCurrentdata(Data currentdata) {
        this.currentdata = currentdata;
        dataname.setText(currentdata.getTitle());
    }

    public void updateData() {
        dataname.setText(currentdata.getTitle());
        showDistance();

    }

    public void setup() {
        ActivityListCollection activities = user.getUserActivities();
        this.allData = activities.getAllData();
        dataSize = allData.size();
        System.out.println(dataSize);
        setCurrentdata(allData.get(dataSize - 1));
        currentDataIndex = dataSize - 1;
        updateData();
    }



    private ArrayList<Data> allData;
    private Data currentdata;
    private Integer currentDataIndex;
    private Integer dataSize;
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
