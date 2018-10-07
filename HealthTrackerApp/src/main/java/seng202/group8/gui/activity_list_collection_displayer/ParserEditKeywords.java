package seng202.group8.gui.activity_list_collection_displayer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import seng202.group8.gui.activity_list_collection_displayer.ActivitiesCollectionController;
import seng202.group8.gui.activity_list_collection_displayer.ParserErrorTypeController;
import seng202.group8.parser.Parser;
import seng202.group8.user.User;

public class ParserEditKeywords extends Application {

    static Stage classStage = new Stage();

    private User user;

    private ActivitiesCollectionController parentControl;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //System.out.println("dis: "+errorMess);
        classStage = primaryStage;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/resources/views/ParserEditKeywords.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        ParserEditKeywordsController editUser = fxmlLoader.<ParserEditKeywordsController>getController();
        editUser.setUser(user);
        editUser.setParentControl(parentControl);
        System.out.println("Display: "+user.getUserID());
        classStage.setTitle("WINded");
        classStage.setScene(new Scene(root));
        classStage.show();
    }


    public void setUser(User user) {
        this.user = user;
    }


    public void setParentControl(ActivitiesCollectionController parentControl) {
        this.parentControl = parentControl;
    }
}
