package seng202.group8.gui.home_displayer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import seng202.group8.activity_collection.ActivityList;
import seng202.group8.data_entries.Data;
import seng202.group8.parser.Parser;
import seng202.group8.user.User;
import seng202.group8.user.user_stats.Sex;

import java.util.ArrayList;

public class Home extends Application {

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
    }

    /**
     * Starts the Home page
     * @param primaryStage the stage at which to put the home page onto
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {

        setUpMockUser();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../../../resources/views/home.fxml"));

        Parent root = loader.load();
        HomeController controller = (HomeController) loader.getController();

        controller.setUser(user);
        controller.setPrimaryStage(primaryStage);
        controller.setup();
        controller.setHostServices(getHostServices());

        Scene scene = new Scene(root, 1100, 640);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Home");
        primaryStage.show();
    }
}
