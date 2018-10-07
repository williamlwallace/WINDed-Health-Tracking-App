package seng202.group8.gui.statistics_graph_displayer;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import seng202.group8.activity_collection.ActivityListCollection;
import seng202.group8.data_entries.Data;
import seng202.group8.services.statistics_service.GraphXY;
import seng202.group8.services.statistics_service.StatisticsService;
import seng202.group8.user.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class GraphController {

    @FXML
    private JFXButton bmi;

    @FXML
    private JFXButton weight;

    @FXML
    private Label time;

    @FXML
    private Label labelTitle1;

    @FXML
    private Label labelTitle2;

    @FXML
    private JFXButton previous;

    @FXML
    private JFXButton next;

    @FXML
    private Label dataName;

    @FXML
    private LineChart<Double,Double> graph;

    @FXML
    private LineChart<Double,Double> graph2;

    @FXML
    private NumberAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    @FXML
    private NumberAxis xAxis2;

    @FXML
    private NumberAxis yAxis2;

    @FXML
    private ComboBox comboBox;

    @FXML
    private StackPane statsStackPane;

    @FXML
    private VBox activityVBox;


    /**
     * When combo box has selected a new item it will run the specific function required for that graph
     */
    public void changeGraph() {
        switch (comboBox.getValue().toString()) {
            case "Distance Over Time":
                showDistance();
                break;
            case "Heart Rate Over Time":
                showHeartRate();
                break;
            case "Calories Over Time":
                showCalories();
                break;
            case "Stress Level Over Time":
                showStress();
                break;
            case "Speed Over Time":
                showSpeed();
                break;
            default:
                showDistance();
                break;
        }
    }


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

        graph.getXAxis().setTickLabelsVisible(true);
        //System.out.println(graph.getAxisSortingPolicy() + "This is the sorting policy.");
        Double maxDistance = xyData.getXAxis().get(xyData.getXAxis().size() - 1);
        yAxis.setUpperBound(maxDistance + (maxDistance * 0.1));
        yAxis.setLowerBound(xyData.getYAxis().get(0));

        xAxis.setLabel("Time " + (xyData.getXAxisScale()));
        yAxis.setLabel("Distance (m)");
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

        graph.getXAxis().setTickLabelsVisible(true);
        Double maxHeartRate = (double) currentData.getHeartRateData().getHighestHeartRate();
        yAxis.setUpperBound(maxHeartRate + (maxHeartRate * 0.1));
        yAxis.setLowerBound(xyData.getYAxis().get(0));

        xAxis.setLabel("Time " + (xyData.getXAxisScale()));
        yAxis.setLabel("Heart Rate (bpm)");
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

        graph.getXAxis().setTickLabelsVisible(true);
        Double maxCalories = xyData.getYAxis().get(xyData.getYAxis().size() - 1);
        yAxis.setUpperBound(maxCalories + (maxCalories * 0.1));
        yAxis.setLowerBound(xyData.getYAxis().get(0));

        xAxis.setLabel("Time " + (xyData.getXAxisScale()));
        yAxis.setLabel("Calories Burned (calories)");
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

        graph.getXAxis().setTickLabelsVisible(true);
        Double maxStress = (double) currentData.getStressLevelMax();
        this.yAxis.setUpperBound(maxStress);
        yAxis.setLowerBound((double) currentData.getStressLevelMin());

        xAxis.setLabel("Time " + (xyData.getXAxisScale()));
        yAxis.setLabel("Stress (%)");
        series.setName("Stress Over Time");
        graph.setTitle("Stress Visualization");

        graph.setCreateSymbols(false);
        lineChartData.add(series);
        graph.setData(lineChartData);
        graph.createSymbolsProperty();
    }


    /**
     * The function that runs when you hit the "Speed over time" button
     * Grabs the Speed data for the currently data activity selected
     * Displays it on the graph and sets x and y axis values / customizations
     */
    public void showSpeed() {
        ObservableList<XYChart.Series<Double, Double>> lineChartData = FXCollections.observableArrayList();
        StatisticsService graphData = user.getStatsService();
        GraphXY xyData = graphData.getSpeedGraphOverTime(currentData);
        LineChart.Series<Double, Double> series = generateSeries(xyData);

        graph.getXAxis().setTickLabelsVisible(true);
        Double maxStress = (double) currentData.getStressLevelMax();
        this.yAxis.setUpperBound(maxStress);
        yAxis.setLowerBound((double) currentData.getStressLevelMin());

        xAxis.setLabel("Time" + (xyData.getXAxisScale()));
        yAxis.setLabel("Speed (km/hr)");
        series.setName("Speed Over Time");
        graph.setTitle("Speed Visualization");

        graph.setCreateSymbols(false);
        lineChartData.add(series);
        graph.setData(lineChartData);
        graph.createSymbolsProperty();
    }


    /**
     * The function that runs when you hit the "BMI over time" button
     * Grabs the BMI data from the user entries
     * Displays it on the graph and sets x and y axis values / customizations
     */
    public void showBmi() {
        ObservableList<XYChart.Series<Double, Double>> lineChartData = FXCollections.observableArrayList();
        StatisticsService graphData = user.getStatsService();
        GraphXY xyData = graphData.getGraphDataBMIType();
        //for(int i = 0; i < xyData.getYAxis().size(); i++) {
        //    System.out.println(xyData.getYAxis().get(i));
        //}
        LineChart.Series<Double, Double> series = generateSeries(xyData);

        graph2.getXAxis().setTickLabelsVisible(true);

        xAxis2.setLabel("Time " + (xyData.getXAxisScale()));
        yAxis2.setLabel("BMI Value (kg/m²)");
        series.setName("BMI Over Time");
        graph2.setTitle("BMI Visualization");

        lineChartData.add(series);
        graph2.setData(lineChartData);
        graph2.createSymbolsProperty();
    }


    /**
     * The function that runs when you hit the "BMI over time" button
     * Grabs the BMI data from the user entries
     * Displays it on the graph and sets x and y axis values / customizations
     */
    public void showWeight() {
        ObservableList<XYChart.Series<Double, Double>> lineChartData = FXCollections.observableArrayList();
        StatisticsService graphData = user.getStatsService();
        GraphXY xyData = graphData.getGraphDataWeight();
        LineChart.Series<Double, Double> series = generateSeries(xyData);

        graph2.getXAxis().setTickLabelsVisible(true);

        xAxis2.setLabel("Time " + (xyData.getXAxisScale()));
        yAxis2.setLabel("Weight (kg)");
        series.setName("Weight Over Time");
        graph2.setTitle("Weight Change Visualization");

        lineChartData.add(series);
        graph2.setData(lineChartData);
        graph2.createSymbolsProperty();
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
            XYChart.Data dataPoint = new XYChart.Data<Double, Double>(xyData.getXAxis().get(i), xyData.getYAxis().get(i));
//            dataPoint.getNode().setStyle("-fx-background-color: #2874a6;");
//            lineSymbol.setStyle("-fx-background-color: #2874a6;");
            series.getData().add(dataPoint);
        }
        return series;
    }


    /**
     * The function that runs when the user clicks next activity and will travel through the allData arrayList
     * to a more recent data activity, unless at the end of the list then it won't go any further and checks tha
     * the data it is trying it select is graph able
     */
    public void nextData() {
        Boolean found = false;
        Integer currentDataIndex2 = currentDataIndex;
        while (currentDataIndex2 != dataSize - 1 && !found) {
            currentDataIndex2++;
            if (allData.get(currentDataIndex2).getIsGraphable()) {
                currentDataIndex = currentDataIndex2;
                setCurrentData(allData.get(currentDataIndex));
                found = true;
                changeGraph();
                updateData();
            }
        }
    }


    /**
     * The function that runs when the user clicks on the button previous activity which will travel through the allData
     * arrayList to a previous done activity to the current one selected, unless at the first activity that the user ever
     * inputted into the system, also when it runs through all data checks that the data value is graph abale before it is selected
     */
    public void previousData() {
        Boolean found = false;
        Integer currentDataIndex2 = currentDataIndex;
        while (currentDataIndex2 != 0 && !found) {
            currentDataIndex2--;
            if (allData.get(currentDataIndex2).getIsGraphable()) {
                currentDataIndex = currentDataIndex2;
                setCurrentData(allData.get(currentDataIndex));
                found = true;
                changeGraph();
                updateData();
            }
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
        dataName.setText(currentData.getTitle());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/LLLL/yyyy - HH:mm:ss");
        LocalDateTime after = currentData.getAllDateTimes().get(currentData.getAllDateTimes().size() - 1);
        LocalDateTime before = currentData.getAllDateTimes().get(0);
        String amOrPm1 = "AM";
        String amOrPm2 = "AM";
        if (before.getHour() > 11) {
            amOrPm1 = "PM";
            if (before.getHour() > 12) {
                before = before.minusHours(12);
            }
        }
        if (after.getHour() > 11) {
            amOrPm2 = "PM";
            if (after.getHour() > 12) {
                after = after.minusHours(12);
            }
        }
        String string = before.format(formatter) + " " + amOrPm1 + " | till | " + after.format(formatter) + " " + amOrPm2;
        time.setText(string);
    }


    /**
     * Updates the information on the page when the used changes the data activity selected and when first loaded
     */
    public void updateData() {
        dataName.setText(currentData.getTitle());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/LLLL/yyyy - HH:mm:ss");
        LocalDateTime after = currentData.getAllDateTimes().get(currentData.getAllDateTimes().size() - 1);
        LocalDateTime before = currentData.getAllDateTimes().get(0);
        String amOrPm1 = "AM";
        String amOrPm2 = "AM";
        if (before.getHour() > 11) {
            amOrPm1 = "PM";
            if (before.getHour() > 12) {
                before = before.minusHours(12);
            }
        }
        if (after.getHour() > 11) {
            amOrPm2 = "PM";
            if (after.getHour() > 12) {
                after = after.minusHours(12);
            }
        }
        String string = before.format(formatter) + " " + amOrPm1 + " | till | " + after.format(formatter) + " " + amOrPm2;
        time.setText(string);
        changeGraph();
        showBmi();
    }


    /**
     * Sets the page up when the user first goes into the Statistics page
     */
    public void setup() {
        ActivityListCollection activities = user.getUserActivities();
        this.allData = activities.getAllData();
        dataSize = allData.size();
        int graphableAmount = 0;
        for (int i = 0; i < allData.size(); i++) {
            if (allData.get(i).isGraphable) {
                graphableAmount++;
            }
        }
        if (dataSize == 0 || graphableAmount == 0) {
            activityVBox.setOpacity(0.0);
            JFXDialogLayout content = new JFXDialogLayout();
            content.setHeading(new Text("No Graph Data"));
            content.setBody(new Label("No activity data available is able to be graphed, if you want to view helpful information on this page " +
                    "Please go to the activity log section and add some csv file data"));
            JFXDialog dialog = new JFXDialog(statsStackPane, content, JFXDialog.DialogTransition.CENTER);
            JFXButton gotItButton = new JFXButton("Got it!");

            gotItButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    dialog.close();
                }
            });

            content.setActions(gotItButton);
            dialog.show();
            showBmi();
        } else {
            Boolean found = false;
            Integer currentDataIndex2 = allData.size();
            while (currentDataIndex2 != 0 && !found) {
                currentDataIndex2--;
                if (allData.get(currentDataIndex2).getIsGraphable()) {
                    currentDataIndex = currentDataIndex2;
                    setCurrentData(allData.get(currentDataIndex));
                    found = true;
                }
            }
            dataName.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
            time.setStyle("-fx-font-weight: bold;");
            comboBox.getSelectionModel().select("Distance Over Time");

            updateData();
        }
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

