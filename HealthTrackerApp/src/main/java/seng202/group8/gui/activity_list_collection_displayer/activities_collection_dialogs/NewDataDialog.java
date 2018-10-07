package seng202.group8.gui.activity_list_collection_displayer.activities_collection_dialogs;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import seng202.group8.activity_collection.ActivityList;
import seng202.group8.data_entries.CoordinateData;
import seng202.group8.data_entries.DataType;
import seng202.group8.data_entries.RunData;
import seng202.group8.user.User;
import seng202.group8.user.user_stats.Sex;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class NewDataDialog extends Application {

    public static void main(String[] args) {
        launch(args);
    }


    private User user;


    private void setUpMockUser() throws Exception {
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
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        setUpMockUser();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../../../../../resources/resources/views/new_data_dialog.fxml"));

        Parent root = loader.load();

        Scene scene = new Scene(root, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Ciao");
        primaryStage.show();
    }
}
