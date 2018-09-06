package seng202.group8.gui.activity_list_collection_displayer;



import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import seng202.group8.activity_collection.ActivityList;
import seng202.group8.activity_collection.ActivityListCollection;
import seng202.group8.data_entries.CoordinateData;
import seng202.group8.data_entries.Data;
import seng202.group8.data_entries.HeartRateData;
import seng202.group8.user.User;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;


public class ActivitiesCollectionController {

    /*Insights GUI elements*/
    @FXML
    private Text insightsTitle;

    @FXML
    private Text distanceCovered;

    @FXML
    private Text averageHeartRate;

    @FXML
    private Text maxHeartRate;

    @FXML
    private Text minHeartRate;

    @FXML
    private Text averageSpeed;

    @FXML
    private Text maxSpeed;

    @FXML
    private Text minSpeed;

    /* TreeView */
    @FXML
    private TreeView activityListCollectionTreeView;

    /* WebView */
    @FXML
    private WebView googleMapsWebView;

    /* File Searching */
    @FXML
    private Button searchForFileButton;

    private static Stage primaryStage;
    private static User user;

    public void setInsights(User user, Data data) {
        HeartRateData heartRateData = data.getHeartRateData();
        insightsTitle.setText(data.getTitle() + " insights:");
        distanceCovered.setText(data.getDistanceCovered().toString());
        averageHeartRate.setText(String.valueOf(heartRateData.getMeanAverageHeartRate()));
        maxHeartRate.setText(String.valueOf(heartRateData.getHighestHeartRate()));
        minHeartRate.setText(String.valueOf(heartRateData.getLowestHeartRate()));
    }

    public void setUpTreeView() {
        ActivityListCollection activityListCollection = user.getUserActivities();
        TreeItem<String> rootNode = new TreeItem<>(activityListCollection.getTitle());
        for (ActivityList activityList : activityListCollection.getActivityListCollection()) {
            TreeItem<String> activityListNode = new TreeItem<>(activityList.getTitle());
            activityListNode.setExpanded(false);
            for (Data data : activityList.getActivityList()) {
                TreeItem<String> dataLeaf = new TreeItem<String> (data.getTitle());
                activityListNode.getChildren().add(dataLeaf);
            }
            rootNode.getChildren().add(activityListNode);
        }
        activityListCollectionTreeView.setRoot(rootNode);
    }

    public void setUpWebView(String htmlFile) {
        WebEngine webEngine = googleMapsWebView.getEngine();
        System.out.println(htmlFile);
        webEngine.loadContent(htmlFile);
    }


    public void showNewInsightsAndMap(MouseEvent mouseEvent) throws IOException {
        TreeItem<String> selectedItem = (TreeItem<String>) activityListCollectionTreeView.getSelectionModel().getSelectedItem();
        if (selectedItem != null && selectedItem.isLeaf()) {
            System.out.println(selectedItem.getValue());
            TreeItem<String> parent = selectedItem.getParent();
            int dataIndex = parent.getChildren().indexOf(selectedItem);
            int activityListIndex = parent.getParent().getChildren().indexOf(parent);

            StringBuilder bldr = new StringBuilder();
            String str;

            URL urlGoogleMaps = getClass().getResource("googleMapsView.html");

            String strGoogleMaps = "";
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(urlGoogleMaps.openStream()));

            String inputLine;
            while ((inputLine = in.readLine()) != null)
                strGoogleMaps += (inputLine + "\n");
            in.close();


            Data data = user.getUserActivities().getActivityListCollection().get(activityListIndex).getActivity(dataIndex);
            String htmlFile = jsInjection(strGoogleMaps, data);
            setUpWebView(htmlFile);
            setInsights(user, data);
        }
    }

    private String jsInjection(String htmlFile, Data data) {
        htmlFile = htmlFile.replace("&CENTERID", "lat: "
                + data.getCoordinatesArrayList().get(0).getLatitude()
                + ", lng: "
                + data.getCoordinatesArrayList().get(0).getLongitude());
        StringBuilder stringBuilder = new StringBuilder("var dataCoordVar = [ ");
        for (int i = 0; i < data.getCoordinatesArrayList().size(); i++) {
            CoordinateData coordinateData = data.getCoordinatesArrayList().get(i);
            if (i != (data.getCoordinatesArrayList().size() -1)) {
                stringBuilder.append("{lat: " + coordinateData.getLatitude()
                        + ", lng: " + coordinateData.getLongitude() + "},");
            } else {
                stringBuilder.append("{lat: " + coordinateData.getLatitude()
                        + ", lng: " + coordinateData.getLongitude() + "}];");
            }
        }
        htmlFile = htmlFile.replace("&PREVFLIGHTCOORDID", stringBuilder.toString());

        htmlFile = htmlFile.replace("&FLIGHTPATHVARID", "dataCoordPath");
        htmlFile = htmlFile.replace("&PREVFLIGHTVAR", "dataCoordVar");
        System.out.println(htmlFile);
        return htmlFile;

    }

    public void searchForFile(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("File Chooser");
        fileChooser.showOpenDialog(primaryStage);
    }

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
        ActivitiesCollectionController.primaryStage = primaryStage;
    }
}




