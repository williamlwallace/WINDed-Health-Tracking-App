package seng202.group8.gui.activity_list_collection_displayer;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import seng202.group8.parser.*;
import javafx.event.ActionEvent;

import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.util.Arrays;
import java.util.List;

public class ParserErrorTypeController {
    @FXML
    private Text errorText;

    @FXML
    private TextField keyPhrase;

    @FXML
    private ComboBox actType;

    private String errorMessage;
    private List<String> errorList;
    private Parser parser;

    @FXML
    public void initialize() {
        Platform.runLater(() -> {
            errorText.setText(errorMessage);
            errorList = Arrays.asList(errorMessage.split("'"));
        });
    }

    public void quit(ActionEvent event) {
        Stage stage = (Stage) errorText.getScene().getWindow();
        stage.close();
    }

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
        if (type != 0 && phraseReturn != null) {
            parser.add(phraseReturn, type);
            try {
                parser.parseFile();
            } catch (FileNotFoundError e) {
                ParserErrorOther parseError = new ParserErrorOther();
                parseError.setErrorMess(e.getMessage());
                parseError.start(ParserErrorOther.classStage);
            } catch (NotCSVError e) {
                ParserErrorOther parseError = new ParserErrorOther();
                parseError.setErrorMess(e.getMessage());
                parseError.start(ParserErrorOther.classStage);
            } catch (DataMissingError e) {
                ParserErrorOther parseError = new ParserErrorOther();
                parseError.setErrorMess(e.getMessage());
                parseError.start(ParserErrorOther.classStage);
            } catch (noTypeError e) {
                ParserErrorType parseError = new ParserErrorType();
                parseError.setErrorMess(e.getMessage());
                parseError.setParser(parser);
                parseError.start(ParserErrorType.classStage);
            }
        }
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void setParser(Parser parser) {
        this.parser = parser;
    }
}
