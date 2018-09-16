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

    /**
     * The function that runs when you hit the "Distance over time" button
     * Grabs the distance data for the currently data activity selected
     * Displays it on the graph and sets x and y axis values / customizations
     */
    public void showDistance() {
        ObservableList<XYChart.Series<Double, Double>> lineChartData = FXCollections.observableArrayList();
        StatisticsService graphData = user.getStatsService();
        GraphXY xyData = graphData.getDistanceOverTimeGraph(currentData);
        LineChart.Series<Double, Double> series = generateSeries(xyData);

        graph.getXAxis().setTickLabelsVisible(false);
        Double maxDistance = xyData.getXAxis().get(xyData.getXAxis().size() - 1);
        yAxis.setUpperBound(maxDistance + (maxDistance * 0.1));
        yAxis.setLowerBound(xyData.getYAxis().get(0));

        xAxis.setLabel("Time");
        yAxis.setLabel("Distance");
        series.setName("Distance Over Time");
        graph.setTitle("Distance Visualization");

        graph.setCreateSymbols(false);
        lineChartData.add(series);
        graph.setData(lineChartData);
        graph.createSymbolsProperty();
    }

    /**
     * The function that runs when you hit the "Heart Rate over time" button
     * Grabs the heart rate data for the currently data activity selected
     * Displays it on the graph and sets x and y axis values / customizations
     */
    public void showHeartRate() {
        ObservableList<XYChart.Series<Double, Double>> lineChartData = FXCollections.observableArrayList();
        StatisticsService graphData = user.getStatsService();
        GraphXY xyData = graphData.getHeartRateOverTimeGraph(currentData);
        LineChart.Series<Double, Double> series = generateSeries(xyData);

        graph.getXAxis().setTickLabelsVisible(false);
        Double maxHeartRate = (double) currentData.getHeartRateData().getHighestHeartRate();
        yAxis.setUpperBound(maxHeartRate + (maxHeartRate * 0.1));
        yAxis.setLowerBound(xyData.getYAxis().get(0));

        xAxis.setLabel("Time");
        yAxis.setLabel("Heart Rate");
        series.setName("Heart Rate Over Time");
        graph.setTitle("Heart Rate Visualization");

        graph.setCreateSymbols(false);
        lineChartData.add(series);
        graph.setData(lineChartData);
        graph.createSymbolsProperty();
    }

    /**
     * The function that runs when you hit the "Calories over time" button
     * Grabs the calories data for the currently data activity selected
     * Displays it on the graph and sets x and y axis values / customizations
     */
    public void showCalories() {
        ObservableList<XYChart.Series<Double, Double>> lineChartData = FXCollections.observableArrayList();
        StatisticsService graphData = user.getStatsService();
        GraphXY xyData = graphData.getCaloriesBurnedOverTimeGraph(currentData);
        LineChart.Series<Double, Double> series = generateSeries(xyData);

        graph.getXAxis().setTickLabelsVisible(false);
        Double maxCalories = xyData.getYAxis().get(xyData.getYAxis().size() - 1);
        yAxis.setUpperBound(maxCalories + (maxCalories * 0.1));
        yAxis.setLowerBound(xyData.getYAxis().get(0));

        xAxis.setLabel("Time");
        yAxis.setLabel("Calories Burned");
        series.setName("Calories Burned Over Time");
        graph.setTitle("Calories Burned Visualization");

        graph.setCreateSymbols(false);
        lineChartData.add(series);
        graph.setData(lineChartData);
        graph.createSymbolsProperty();
    }

    /**
     * The function that runs when you hit the "Stress Level over time" button
     * Grabs the Stress Level data for the currently data activity selected
     * Displays it on the graph and sets x and y axis values / customizations
     */
    public void showStress() {
        ObservableList<XYChart.Series<Double, Double>> lineChartData = FXCollections.observableArrayList();
        StatisticsService graphData = user.getStatsService();
        GraphXY xyData = graphData.getStressLevelOverTimeGraph(currentData);
        LineChart.Series<Double, Double> series = generateSeries(xyData);

        graph.getXAxis().setTickLabelsVisible(false);
        Double maxStress = (double) currentData.getStressLevelMax();
        this.yAxis.setUpperBound(maxStress);
        yAxis.setLowerBound((double) currentData.getStressLevelMin());

        xAxis.setLabel("Time");
        yAxis.setLabel("Stress %");
        series.setName("Stress Over Time");
        graph.setTitle("Stress Visualization");

        graph.setCreateSymbols(false);
        lineChartData.add(series);
        graph.setData(lineChartData);
        graph.createSymbolsProperty();
    }

    /**
     * Generates the series needed for the graphs to show, adds the graphXY axis arrayLists to
     * a series object which can be added to a graph to be shown to the user
     * @param xyData a GraphXY object type with the x and y arrayLists of doubles to be added to the graph
     * @return A series object to be added to a line chart
     */
    public LineChart.Series<Double, Double> generateSeries(GraphXY xyData) {
        LineChart.Series<Double, Double> series = new LineChart.Series<Double, Double>();
        for (int i = 0; i < xyData.getXAxis().size(); i++) {
            series.getData().add(new XYChart.Data<Double, Double>(xyData.getXAxis().get(i), xyData.getYAxis().get(i)));
        }
        return series;
    }

    /**
     * The function that runs when the user clicks next activity and will travel through the allData arrayList
     * to a more recent data activity, unless at the end of the list then it won't go any further
     */
    public void nextData() {
        if (currentDataIndex != dataSize - 1) {
            currentDataIndex++;
            setCurrentData(allData.get(currentDataIndex));
            updateData();
        }
    }

    /**
     * The function that runs when the user clicks on the button previous activity which will travel through the allData
     * arrayList to a previous done activity to the current one selected, unless at the first activity that the user ever
     * inputted into the system
     */
    public void previousData() {
        if (currentDataIndex != 0) {
            currentDataIndex--;
            setCurrentData(allData.get(currentDataIndex));
            updateData();
        }
    }

    /**
     * Gets the current data activity that is selected by the user
     * @return the data object that the user has currently selected
     */
    public Data getCurrentdata() {
        return currentData;
    }

    /**
     * sets the current data to a new data object that the user has selected
     * @param currentData the new data that current data will be set to
     */
    public void setCurrentData(Data currentData) {
        this.currentData = currentData;
        dataname.setText(currentData.getTitle());
    }

    /**
     * Updates the information on the page when the used changes the data activity selected and when first loaded
     */
    public void updateData() {
        dataname.setText(currentData.getTitle());
        showDistance();

    }

    /**
     * Sets the page up when the user first goes into the Statistics page
     */
    public void setup() {
        ActivityListCollection activities = user.getUserActivities();
        this.allData = activities.getAllData();
        dataSize = allData.size();
        setCurrentData(allData.get(dataSize - 1));
        currentDataIndex = dataSize - 1;
        updateData();
    }

    /**
     * Variables used for navigation of data and the stage object
     */
    private ArrayList<Data> allData;
    private Data currentData;
    private Integer currentDataIndex;
    private Integer dataSize;
    private User user;
    private Stage primaryStage;

    /**
     * Gets the current user using the page
     * @return
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the user for the page
     * @param user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Gets the primary stage
     * @return the primary stage object
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * Sets the primary Stage variable
     * @param primaryStage a stage variable to be passed in
     */
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
}
