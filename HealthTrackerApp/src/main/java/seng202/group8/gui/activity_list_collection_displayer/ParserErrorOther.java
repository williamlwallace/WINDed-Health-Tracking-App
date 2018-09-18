package seng202.group8.gui.activity_list_collection_displayer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import seng202.group8.parser.Parser;

public class ParserErrorOther extends Application {

    static Stage classStage = new Stage();

    private String errorMess;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        classStage = primaryStage;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ParserErrorOther.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        ParserErrorOtherController other = fxmlLoader.<ParserErrorOtherController>getController();
        other.setErrorMessage(errorMess);
        classStage.setTitle("WINded");
        classStage.setScene(new Scene(root));
        classStage.show();
    }

    public String getErrorMess() {
        return errorMess;
    }

    public void setErrorMess(String errorMess) {
        this.errorMess = errorMess;
    }
}
