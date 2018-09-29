package seng202.group8.gui.activity_list_collection_displayer;

import java_sqlite_db.SQLiteJDBC;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import seng202.group8.activity_collection.ActivityList;
import seng202.group8.data_entries.Data;
import seng202.group8.parser.*;
import seng202.group8.user.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ParserEditKeywordsController {

    @FXML
    private TextField keyPhrase;

    @FXML
    private ComboBox actType;

    @FXML
    private ChoiceBox actRemove;

    private User user;
    private Parser parser;

    private ActivitiesCollectionController parentControl;


    @FXML
    public void initialize() throws Exception {
        parser  = new Parser("", user);

    }

    /**
     * Quits the window
     * @param event
     */
    public void quit(ActionEvent event) {
        Stage stage = (Stage) keyPhrase.getScene().getWindow();
        stage.close();
    }

    public void remove(ActionEvent event) {

    }

    /**
     * Receives the information that the user gave and uses it to add a new key word for the parser.
     * @param event
     * @throws Exception
     */
    public void enter(ActionEvent event) throws Exception {
        String phraseReturn = null;
        int type = 0;
        if (keyPhrase.getText() != null && !keyPhrase.getText().isEmpty()) {
            phraseReturn = keyPhrase.getText().toLowerCase();
        }
        if (actType.getValue() != null) {
            switch (actType.getValue().toString()) {
                case "Walk":
                    type = 1;
                    break;
                case "Hike":
                    type = 2;
                    break;
                case "Run":
                    type = 3;
                    break;
                case "Climb":
                    type = 4;
                    break;
                case "Bike":
                    type = 5;
                    break;
                case "Swim":
                    type = 6;
                    break;
                case "Water Sports":
                    type = 7;
                    break;
                default:
                    break;
            }
        }
        if (type != 0 && (phraseReturn != null && !(phraseReturn.trim().length() == 0)))  {
            parser.add(phraseReturn, type, true);
        }
    }

    public void setUser(User user) {
        this.user = user;
    }
    public void setParentControl(ActivitiesCollectionController parentControlNew) {
        this.parentControl = parentControlNew;
    }
}
