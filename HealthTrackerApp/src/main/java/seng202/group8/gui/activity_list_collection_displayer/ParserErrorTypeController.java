package seng202.group8.gui.activity_list_collection_displayer;

import java_sqlite_db.SQLiteJDBC;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import seng202.group8.activity_collection.ActivityList;
import seng202.group8.data_entries.Data;
import seng202.group8.parser.*;
import javafx.event.ActionEvent;

import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import seng202.group8.user.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ParserErrorTypeController {
    @FXML
    private Text errorText;

    @FXML
    private TextField keyPhrase;

    @FXML
    private ComboBox actType;

    @FXML
    private CheckBox rememberTick;

    private String errorMessage;
    private List<String> errorList;
    private Parser parser;

    private ActivitiesCollectionController parentControl;


    @FXML
    public void initialize() {
        Platform.runLater(() -> {
            errorText.setText(errorMessage);
            errorList = Arrays.asList(errorMessage.split("'"));
        });
    }

    /**
     * Quits the window
     * @param event
     */
    public void quit(ActionEvent event) {
        Stage stage = (Stage) errorText.getScene().getWindow();
        stage.close();
    }

    /**
     * Receives the information that the user gave and give it to the parser so it can continue to parse the file. Again handles errors.
     * @param event
     * @throws Exception
     */
    public void enter(ActionEvent event) throws Exception {
        String phraseReturn = null;
        int type = 0;
        if (keyPhrase.getText() != null && !keyPhrase.getText().isEmpty()) {
            if (errorList.get(1).toLowerCase().contains(keyPhrase.getText().toLowerCase())) {
                phraseReturn = keyPhrase.getText().toLowerCase();
            }
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
        if (!rememberTick.isSelected()) {
            phraseReturn = errorList.get(1);
        }
        if ((type != 0 && (phraseReturn != null && !(phraseReturn.trim().length() == 0))) || (rememberTick.isSelected() && type != 0))  {
            if (!rememberTick.isSelected()) {
                parser.add(phraseReturn, type, false);
            } else {
                parser.add(phraseReturn, type, true);
            }
            int error  = 0;
            try {
                parser.parseFile();
            } catch (FileNotFoundError e) {
                ParserErrorOther parseError = new ParserErrorOther();
                parseError.setErrorMess(e.getMessage());
                parseError.start(ParserErrorOther.classStage);
                error = 1;
            } catch (NotCSVError e) {
                ParserErrorOther parseError = new ParserErrorOther();
                parseError.setErrorMess(e.getMessage());
                parseError.start(ParserErrorOther.classStage);
                error = 1;
            } catch (DataMissingError e) {
                ParserErrorOther parseError = new ParserErrorOther();
                parseError.setErrorMess(e.getMessage());
                parseError.start(ParserErrorOther.classStage);
                error = 1;
            } catch (noTypeError e) {
                ParserErrorType parseError = new ParserErrorType();
                parseError.setErrorMess(e.getMessage());
                parseError.setParser(parser);
                parseError.setParentControl(parentControl);
                parseError.start(ParserErrorType.classStage);
                error = 1;
            }
            if (error == 0) {
                User user = parser.getUser();
                SQLiteJDBC database = new SQLiteJDBC();
                int add = user.getUserActivities().checkDuplicate(parentControl.getActivityTitle());
                ArrayList<Data> newData = new ArrayList<Data>();
                if (add == -1) {
                    ActivityList newList = new ActivityList(parentControl.getActivityTitle());
                    add = user.getUserActivities().insertActivityList(newList);
                    database.insertActivityList(newList.getTitle(), newList.getCreationDate(), 1);
                    for (Data data : parser.getDataList()) {
                        user.getUserActivities().insertActivityInGivenList(add, data);
                        newData.add(data);
                    }
                    database.updateWithListOfData(newData, newList.getTitle(), newList.getCreationDate(), 1);
                } else {
                    for (Data data : parser.getDataList()) {
                        user.getUserActivities().insertActivityInGivenList(add, data);
                        newData.add(data);
                    }
                    ActivityList existingList = user.getUserActivities().getActivityListCollection().get(add);
                    database.updateWithListOfData(newData,existingList.getTitle(), existingList.getCreationDate(), 1);
                }
                parentControl.setUpTreeView();
                List<String> csvArray = Arrays.asList(parser.getFilename().split("/"));
                parentControl.setParserInfo("File '"+csvArray.get(csvArray.size() - 1)+"' has been uploaded.");

                //database.saveUser(user, 1);
                Stage stage = (Stage) errorText.getScene().getWindow();
                stage.close();
            }
        }
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void setParser(Parser parserNew) {
        this.parser = parserNew;
    }
    public void setParentControl(ActivitiesCollectionController parentControlNew) {
        this.parentControl = parentControlNew;
    }
}
