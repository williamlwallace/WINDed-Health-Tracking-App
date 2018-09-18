package seng202.group8.gui.activity_list_collection_displayer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import seng202.group8.activity_collection.ActivityList;
import seng202.group8.data_entries.*;
import seng202.group8.user.User;
import seng202.group8.user.user_stats.Sex;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class ActivityListCollectionDisplay extends Application {

//    public static void main(String[] args) {
//        launch(args);
//    }

    private User user;

    private void setUpMockUser() {
        user = new User("Joel", 19, 72.0, 167.0, Sex.MALE);
        //Parser parserTest = new Parser("seng202_2018_example_data_clean.csv", user);
        //ArrayList<Data> dataList = parserTest.getDataList();
        ArrayList<Integer> heartRates = new ArrayList<Integer>();
        heartRates.add(100);
        heartRates.add(115);
        heartRates.add(120);
        ArrayList<LocalDateTime> localTimes = new ArrayList<LocalDateTime>();
        localTimes.add(LocalDateTime.of(2018, 9, 10, 6, 40, 10));
        localTimes.add(LocalDateTime.of(2018, 9, 10, 6, 40, 15));
        localTimes.add(LocalDateTime.of(2018, 9, 10, 6, 40, 25));
        //30.26881985,-97.83246599,204.4
        ArrayList<CoordinateData> coordinateList = new ArrayList<CoordinateData>();
        coordinateList.add(new CoordinateData(30.26881985,-97.83246599,204.4));
        coordinateList.add(new CoordinateData(30.26868423,-97.83252265,202));
        coordinateList.add(new CoordinateData(30.26863712,-97.83267747,201.5));
        RunData testData = new RunData("testDistance", DataType.RUN, localTimes, coordinateList, heartRates, user);
        user.getUserActivities().insertActivityList(new ActivityList("TESTS"));
        user.getUserActivities().insertActivityInGivenList(0, testData);
//        for (int i = 0; i < dataList.size(); i++) {
//            user.getUserActivities().insertActivityInGivenList(0, dataList.get(i));
//            System.out.println(dataList.get(i).getHeartRateList());
//        }
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        setUpMockUser();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../../../resources/views/activity_list_collection.fxml"));

        Parent root = loader.load();
        ActivitiesCollectionController controller = (ActivitiesCollectionController) loader.getController();
        controller.setUser(user);
        controller.setPrimaryStage(primaryStage);

//        controller.setInsights(user, 0, 0);
        controller.setUpTreeView();
//        controller.setUpWebView();
        Scene scene = new Scene(root, 1000, 800);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Ciao");
        primaryStage.show();

    }
}

