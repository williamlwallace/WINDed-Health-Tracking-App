package seng202.group8.gui.activity_list_collection_displayer;

import java_sqlite_db.SQLiteJDBC;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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

    @FXML
    private Label errorMes;

    private User user;
    private Parser parser;

    private ActivitiesCollectionController parentControl;


    @FXML
    public void initialize() throws Exception {
        parser = new Parser("", user);
        ObservableList<String> choiceAdd = FXCollections.observableArrayList(parser.getRemoveableWords());
        actRemove.setItems(choiceAdd);
    }

    /**
     * Quits the window
     * @param event
     */
    public void quit(ActionEvent event) {
        Stage stage = (Stage) keyPhrase.getScene().getWindow();
        stage.close();
    }

    public void remove(ActionEvent event) throws Exception {
        if (actRemove.getValue() != null) {
            SQLiteJDBC database = new SQLiteJDBC();
            database.deleteParserKeyword(1, actRemove.getValue().toString());
            parser = new Parser("", user);
            ObservableList<String> choiceAdd = FXCollections.observableArrayList(parser.getRemoveableWords());
            errorMes.setText("Phrase '"+actRemove.getValue().toString()+"' has been removed");
            actRemove.setItems(choiceAdd);
        }
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
        } else {
            errorMes.setText("Please enter a phrase and select a type");
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
            SQLiteJDBC database = new SQLiteJDBC();
            ArrayList<ArrayList<String>> acceptedValues = parser.getAcceptedValues();
            boolean continueBool = true;
            for (int place = 0; place < acceptedValues.size(); place++) {
                for (int i = 0; i < acceptedValues.get(place).size(); i++) {
                    if (phraseReturn.equals(acceptedValues.get(place).get(i))) {
                        continueBool = false;
                    }
                }
            }
            if (continueBool) {
                parser.add(phraseReturn, type, true);
                parser = new Parser("", user);
                ObservableList<String> choiceAdd = FXCollections.observableArrayList(parser.getRemoveableWords());
                actRemove.setItems(choiceAdd);
                errorMes.setText("Phrase '"+phraseReturn+"' has been added");
            } else {
                errorMes.setText("Phrase '"+phraseReturn+"' is already in the application");
            }
        }
    }

    public void setUser(User user) {
        this.user = user;
    }
    public void setParentControl(ActivitiesCollectionController parentControlNew) {
        this.parentControl = parentControlNew;
    }
}
