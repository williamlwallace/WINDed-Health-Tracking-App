package seng202.group8.gui.activity_list_collection_displayer;

import java_sqlite_db.SQLiteJDBC;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
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

/**
 * @author sgv15
 */
public class ParserErrorTypeController {
    @FXML
    private Label errorText;

    @FXML
    private TextField keyPhrase;

    @FXML
    private ComboBox actType;

    @FXML
    private Label errorMes;

    @FXML
    private CheckBox rememberTick;

    private String errorMessage;
    private List<String> errorList;
    private Parser parser;
    private Stage stage;

    private ActivitiesCollectionController parentControl;


    /**
     * Sets up the window with the correct data from the sent in information
     */
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
        this.stage.close();
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
            } else {
                errorMes.setText("Your phrase must be part of '"+errorList.get(1)+"'");
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
        if ((type != 0 && (phraseReturn != null && !(phraseReturn.trim().length() == 0))) || (!rememberTick.isSelected() && type != 0))  {
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
            List<String> csvArray = Arrays.asList(parser.getFilename().split("\\\\|/"));
            if (error == 0 && !parser.getDataList().isEmpty()) {
                User user = parser.getUser();
                SQLiteJDBC database = new SQLiteJDBC();
                int add = user.getUserActivities().checkDuplicate(parentControl.getActivityTitle());
                ArrayList<Data> newData = new ArrayList<Data>();
                if (add == -1) {
                    ActivityList newList = new ActivityList(parentControl.getActivityTitle());
                    add = user.getUserActivities().insertActivityList(newList);
                    database.insertActivityList(newList.getTitle(), newList.getCreationDate(), user.getUserID());
                    for (Data data : parser.getDataList()) {
                        user.getUserActivities().insertActivityInGivenList(add, data);
                        newData.add(data);
                    }
                    database.updateWithListOfData(newData, newList.getTitle(), newList.getCreationDate(), user.getUserID());
                } else {
                    for (Data data : parser.getDataList()) {
                        user.getUserActivities().insertActivityInGivenList(add, data);
                        newData.add(data);
                    }
                    ActivityList existingList = user.getUserActivities().getActivityListCollection().get(add);
                    database.updateWithListOfData(newData,existingList.getTitle(), existingList.getCreationDate(), user.getUserID());
                }
                parentControl.setUpTreeView();
                parentControl.setParserInfo("File '"+csvArray.get(csvArray.size() - 1)+"' has been uploaded.");

                //database.saveUser(user, 1);
                Stage stage = (Stage) errorText.getScene().getWindow();
                System.out.println(stage);
                stage.close();
            } else if (parser.getDataList().isEmpty()) {
                parentControl.setParserInfoText("File '"+csvArray.get(csvArray.size() - 1)+"' is either empty or only has activities you have already uploaded.");
                Stage stage = (Stage) errorText.getScene().getWindow();
                System.out.println(stage);
                stage.close();
            }
        } else {
            if (rememberTick.isSelected()) {
                errorMes.setText("You must enter both a phrase and a type");
            } else {
                errorMes.setText("You must enter a type");
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

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
