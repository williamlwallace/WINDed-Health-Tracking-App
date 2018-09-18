package seng202.group8.gui.activity_list_collection_displayer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import seng202.group8.user.User;

public class ParserErrors extends Application {

    static Stage classStage = new Stage();

    private String errorMess;
    private String filename;
    private User user;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println("dis: "+errorMess);
        classStage = primaryStage;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ParserErrors.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        ParserErrorsController noType = fxmlLoader.<ParserErrorsController>getController();
        noType.setErrorMessage(errorMess);
        noType.setFilename(filename);
        noType.setUser(user);
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

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
