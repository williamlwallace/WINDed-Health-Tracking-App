package seng202.group8.gui.activity_list_collection_displayer;


import javafx.beans.property.SimpleStringProperty;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.util.Callback;
import seng202.group8.activity_collection.ActivityList;
import seng202.group8.activity_collection.ActivityListCollection;
import seng202.group8.data_entries.Data;
import seng202.group8.data_entries.HeartRateData;
import seng202.group8.user.User;

import java.awt.event.KeyEvent;


public class ActivitiesCollectionController {

    /*Insights GUI elements*/
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




    public void setInsights(User user, int activityListIndex, int dataIndex) {
        Data data = user.getUserActivities().getActivityListCollection().get(activityListIndex).getActivity(dataIndex);
        HeartRateData heartRateData = data.getHeartRateData();

        distanceCovered.setText(data.getDistanceCovered().toString());
        averageHeartRate.setText(String.valueOf(heartRateData.getMeanAverageHeartRate()));
        maxHeartRate.setText(String.valueOf(heartRateData.getHighestHeartRate()));
        minHeartRate.setText(String.valueOf(heartRateData.getLowestHeartRate()));
    }

    public void setUpTreeView(User user) {
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

    public void showNewInsightsAndMap(MouseEvent event) {
        TreeItem<String> selectedItem = (TreeItem<String>) activityListCollectionTreeView.getSelectionModel().getSelectedItem();
        if (selectedItem != null && selectedItem.isLeaf()) {
            System.out.println("Ciao");
        }
    }

}




