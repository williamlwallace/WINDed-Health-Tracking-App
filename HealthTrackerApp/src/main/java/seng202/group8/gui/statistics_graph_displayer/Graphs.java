package seng202.group8.gui.statistics_graph_displayer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import seng202.group8.activity_collection.ActivityList;
import seng202.group8.data_entries.CoordinateData;
import seng202.group8.data_entries.DataType;
import seng202.group8.data_entries.WalkData;
import seng202.group8.user.User;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Graphs extends Application {

    private User user;

    public static void main(String[] args) {
        launch(args);
    }

    private void setUpMockUser() {
        user = new User("Lorenzo", 22, 83.0, 184.0);
        ArrayList<LocalDateTime> localDateTimes = new ArrayList<>();
        localDateTimes.add(LocalDateTime.now());
        ArrayList<Integer> heartRateList = new ArrayList<>();
        heartRateList.add(45);
        heartRateList.add(48);
        heartRateList.add(55);

        ArrayList<CoordinateData> coordinatesList = new ArrayList<>();
        coordinatesList.add(new CoordinateData(40.7316, -73.9352, 56.0));
        coordinatesList.add(new CoordinateData(40.7326, -73.9362, 56.0));
        coordinatesList.add(new CoordinateData(40.7336, -73.9372, 56.0));

        ArrayList<CoordinateData> coordinatesList1 = new ArrayList<>();
        coordinatesList1.add(new CoordinateData(44.7994, 10.3262, 1.0));
        coordinatesList1.add(new CoordinateData(44.8005, 10.3273, 1.0));
        coordinatesList1.add(new CoordinateData(44.8016, 10.3284, 1.0));
        WalkData data =
                new WalkData("test1", DataType.WALK, localDateTimes, coordinatesList, heartRateList);
        WalkData data1 =
                new WalkData("test2", DataType.WALK, localDateTimes, coordinatesList1, heartRateList);
        WalkData data2 =
                new WalkData("test3", DataType.WALK, localDateTimes, coordinatesList, heartRateList);
        user.getUserActivities().insertActivityList(new ActivityList("TESTS"));
        user.getUserActivities().insertActivityInGivenList(0, data);
        user.getUserActivities().insertActivityInGivenList(0, data1);
        user.getUserActivities().insertActivityInGivenList(0, data2);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        setUpMockUser();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("graphs.fxml"));

        Parent root = loader.load();
        GraphController controller = loader.getController();

        controller.setUser(user);
        controller.setPrimaryStage(primaryStage);

        Scene scene = new Scene(root, 1000, 800);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Statistics");
        primaryStage.show();
    }
}
