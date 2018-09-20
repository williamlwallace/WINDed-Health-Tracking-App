package seng202.group8.gui.goals_displayer;

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

public class GoalsDisplayer extends Application {

    private User user;

    public static void main(String[] args) {
        launch(args);
    }


    /**
     * Temporary mock user until combination with other gui classes
     * @throws Exception for when the parser could get errors
     */
    private void setUpMockUser() throws Exception {
        user = new User("Joel", 19, 222.0, 167.0, Sex.MALE);
        Parser parserTest = new Parser("seng202_2018_example_data_clean.csv", user);
        parserTest.parseFile();
        ArrayList<Data> dataList = parserTest.getDataList();
        user.getUserActivities().insertActivityList(new ActivityList("TESTS"));
        for (int i = 0; i < dataList.size(); i++) {
            user.getUserActivities().insertActivityInGivenList(0, dataList.get(i));
        }
        String string = "created only to avoid having a lot of yellow lines.";
        user.updateBMI(10.0);
        user.updateBMI(20.0);
        user.updateBMI(30.0);
        System.out.println(user.getWeight());
        //user.getUserStats().getUserBMITypeRecords().get(0).setDate(LocalDateTime.now());
    }

    @Override
    public void start(Stage stage) throws Exception {

        setUpMockUser();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../../../../resources/resources/views/goals_displayer.fxml"));
        Parent root = loader.load();
        GoalsDisplayerController goalsDisplayerController = loader.getController();
        goalsDisplayerController.setUser(user);
        goalsDisplayerController.setStage(stage);
        goalsDisplayerController.createGoals();
        System.out.println("Ciao I am in GoalsDisplayer: " + user.getWeight());
        stage.setTitle("WINded");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

    }
}
