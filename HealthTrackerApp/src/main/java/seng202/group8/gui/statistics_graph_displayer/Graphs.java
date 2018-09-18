package seng202.group8.gui.statistics_graph_displayer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import seng202.group8.activity_collection.ActivityList;
import seng202.group8.data_entries.*;
import seng202.group8.parser.Parser;
import seng202.group8.user.User;
import seng202.group8.user.user_stats.Sex;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Graphs extends Application {

    private User user;

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Temporary mock user until combination with other gui classes
     * @throws Exception for when the parser could get errors
     */
    private void setUpMockUser() throws Exception {
        user = new User("Joel", 19, 72.0, 167.0, Sex.MALE);
        Parser parserTest = new Parser("seng202_2018_example_data_clean.csv", user);
        parserTest.parseFile();
        ArrayList<Data> dataList = parserTest.getDataList();
        user.getUserActivities().insertActivityList(new ActivityList("TESTS"));
        for (int i = 0; i < dataList.size(); i++) {
            user.getUserActivities().insertActivityInGivenList(0, dataList.get(i));
        }
        user.updateBMI(10.0);
        user.updateBMI(20.0);
        user.updateBMI(30.0);
        //user.getUserStats().getUserBMITypeRecords().get(0).setDate(LocalDateTime.now());
    }

    /**
     * Starts the Statistics page
     * @param primaryStage the stage at which to put the statistics page onto
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {

        setUpMockUser();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../../../resources/views/graphs.fxml"));

        Parent root = loader.load();
        GraphController controller = (GraphController) loader.getController();

        controller.setUser(user);
        controller.setPrimaryStage(primaryStage);
        controller.setup();

        Scene scene = new Scene(root, 1280, 720);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
