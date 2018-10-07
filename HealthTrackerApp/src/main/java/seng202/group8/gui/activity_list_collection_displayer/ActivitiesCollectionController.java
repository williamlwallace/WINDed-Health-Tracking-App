package seng202.group8.gui.activity_list_collection_displayer;



import com.jfoenix.controls.*;
import java_sqlite_db.SQLiteJDBC;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.commons.text.WordUtils;
import seng202.group8.activity_collection.ActivityList;
import seng202.group8.activity_collection.ActivityListCollection;
import seng202.group8.data_entries.CoordinateData;
import seng202.group8.data_entries.Data;
import seng202.group8.data_entries.DataType;
import seng202.group8.data_entries.HeartRateData;
import seng202.group8.gui.GoogleMapsTools.GoogleMapsTools;
import seng202.group8.gui.activity_list_collection_displayer.activities_collection_dialogs.ModifyDataValueController;
import seng202.group8.gui.activity_list_collection_displayer.activities_collection_dialogs.NewDataDialogController;
import seng202.group8.parser.*;
import seng202.group8.user.User;

import java.io.*;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.*;


/**
 * @author lfa69, sgv15
 * Controller for activity_list_collection.fxml.
 * Deals with displaying the user Data values, display major insights and path followed, retrieve and upload CSV data from local directory
 */
public class ActivitiesCollectionController {

    /*Insights GUI elements*/
    @FXML
    private Text insightsTitle;

    @FXML
    private Text dateText;

    @FXML
    private Text fromText;

    @FXML
    private Text toText;

    @FXML
    private Text distanceCovered;

    @FXML
    private Text averageSpeed;

    @FXML
    private Text averageHeartRate;

    @FXML
    private Text maxHeartRate;

    @FXML
    private Text minHeartRate;

    @FXML
    private Text parserInfo;

    /* TreeView */
    @FXML
    private JFXTreeView activityListCollectionTreeView;

    /* WebView */
    @FXML
    private WebView googleMapsWebView;

    /* File Searching */
    @FXML
    private Button searchForFileButton;

    @FXML
    private TextField titleField;

    @FXML
    private JFXButton deleteButton;

    @FXML
    private StackPane dialogStackPane;

    @FXML
    private BorderPane welcomePane;

    @FXML
    private VBox insightsVBox;

    @FXML
    private Label inspirationalLabel;

    @FXML
    private Text dataTypeText;


    private static Stage primaryStage;
    private static User user;
    private String csvToParse;
    private String activityTitle;

    private final String[] aforisms = {"Success is where preparation and opportunity meet", "Winners never quit and quitters never win" };


    /**
     * TODO: make it available once the logic behind it can vbe implemented
     */
    @FXML
    private void initialize() {
        insightsVBox.setVisible(false);
        welcomePane.setVisible(true);
        Integer randInt = (new Random()).nextInt(2);
        String aforism = aforisms[randInt];
        inspirationalLabel.setText(aforism);

    }

    /**
     *
     * @param user
     * @param data
     * Deals with displaying the major insights of the Data value selected from the TreeView.
     */
    public void setInsights(User user, Data data) {
        HeartRateData heartRateData = data.getHeartRateData();

        //Display data value title
        if (data.getTitle().length() < 13) {
            insightsTitle.setText(WordUtils.capitalize(data.getTitle()));
        } else {
            insightsTitle.setText(WordUtils.capitalize(data.getTitle().substring(0, 10) + "..."));
        }
        //Display day and init/end dates of data value
        LocalDateTime fromTime = data.getAllDateTimes().get(0);
        LocalDateTime toTime = data.getAllDateTimes().get(data.getAllDateTimes().size() - 1);
        dateText.setText(fromTime.toLocalDate().toString());
        fromText.setText(fromTime.toLocalTime().toString());
        toText.setText(toTime.toLocalTime().toString());

        dataTypeText.setText(DataType.fromEnumToString(data.getDataType()));

        //Displaying the data distance covered in m or km depending on the integer size
        if (data.getDistanceCovered() < 1000) {
            distanceCovered.setText(String.format("%.2f", data.getDistanceCovered()) + " m");
        } else {
            distanceCovered.setText(String.format("%.2f", data.getDistanceCovered() / 1000) + " km");
        }

        //Display average data speed
        String averageSpeedString = String.format("%.2f", data.getDataSpeedKph()) + " km/h";
        averageSpeed.setText(averageSpeedString);

        //Display heart rate data insights
        if (heartRateData.getMeanAverageHeartRate() < 0) {
            averageHeartRate.setText("N/A");
            maxHeartRate.setText("N/A");
            minHeartRate.setText("N/A");
        } else {
            averageHeartRate.setText(String.valueOf(heartRateData.getMeanAverageHeartRate()) + " bpm");
            maxHeartRate.setText(String.valueOf(heartRateData.getHighestHeartRate()) + " bpm");
            minHeartRate.setText(String.valueOf(heartRateData.getLowestHeartRate()) + " bpm");
        }


    }

    /**
     * TreeView items updater, called every time there is a need to display more data points (when data is added or deleted)
     */
    public void setUpTreeView() {
        ActivityListCollection activityListCollection = user.getUserActivities();
        for (ActivityList activityList : activityListCollection.getActivityListCollection()) {
            System.out.println("LIST: " + activityList.getTitle());
            for (Data data : activityList.getActivityList()) {
                System.out.println("    DATA: " + data.getTitle());
            }

        }
        TreeItem<String> rootNode = new TreeItem<>(activityListCollection.getTitle());
        for (ActivityList activityList : activityListCollection.getActivityListCollection()) {
            TreeItem<String> activityListNode = new TreeItem<>(activityList.getTitle());
            activityListNode.setExpanded(false);
            for (Data data : activityList.getActivityList()) {
                TreeItem<String> dataLeaf = new TreeItem<String> (data.getTitle());
                activityListNode.getChildren().add(dataLeaf);
            }
            rootNode.getChildren().add(activityListNode);
        }

        activityListCollectionTreeView.setRoot(rootNode);

    }


    /**
     *
     * @param htmlFile
     * GoogleMaps parsed file loader.
     */
    public void setUpWebView(String htmlFile) {
        WebEngine webEngine = googleMapsWebView.getEngine();
//        System.out.println(htmlFile);
        webEngine.loadContent(htmlFile);
    }

//    /**
//     *
//     * @param htmlFile
//     * @param data
//     * @return the parsed html file injected with the Data coordinates
//     * Parses the googleMapsView.html file and injects specific variables to allow the plotting of coordinates in the
//     * WebView that will render the html file.
//     */
//    private String jsInjection(String htmlFile, Data data) {
//        htmlFile = htmlFile.replace("&CENTERID", "lat: "
//                + data.getCoordinatesArrayList().get(0).getLatitude()
//                + ", lng: "
//                + data.getCoordinatesArrayList().get(0).getLongitude());
//        StringBuilder stringBuilder = new StringBuilder("var dataCoordVar = [ ");
//        for (int i = 0; i < data.getCoordinatesArrayList().size(); i++) {
//            CoordinateData coordinateData = data.getCoordinatesArrayList().get(i);
//            if (i != (data.getCoordinatesArrayList().size() -1)) {
//                stringBuilder.append("{lat: " + coordinateData.getLatitude()
//                        + ", lng: " + coordinateData.getLongitude() + "},");
//            } else {
//                stringBuilder.append("{lat: " + coordinateData.getLatitude()
//                        + ", lng: " + coordinateData.getLongitude() + "}];");
//            }
//        }
//        htmlFile = htmlFile.replace("&PREVFLIGHTCOORDID", stringBuilder.toString());
//
//        htmlFile = htmlFile.replace("&FLIGHTPATHVARID", "dataCoordPath");
//        htmlFile = htmlFile.replace("&PREVFLIGHTVAR", "dataCoordVar");
//        return htmlFile;
//    }

    /**
     *
     * @param mouseEvent
     * @throws IOException
     * TreeView item listener that allows to update the WebEngine rendering the GoogleMaps file
     * and the insights view every time a new Data object is clicked in the TreeView.
     */
    public void showNewInsightsAndMap(MouseEvent mouseEvent) throws IOException {
        TreeItem<String> selectedItem = (TreeItem<String>) activityListCollectionTreeView.getSelectionModel().getSelectedItem();
//        System.out.println("Selected Item:" + selectedItem);


        if (selectedItem != null ) {
            // Triggered only if the selected value is a Data value (discards activity lists and activity list collections)
            if (mouseEvent.getButton() == MouseButton.PRIMARY && selectedItem.isLeaf() && selectedItem != activityListCollectionTreeView.getRoot()) {

                //Makes transition from welcome view to the data insights
                insightsVBox.setVisible(true);
                welcomePane.setVisible(false);


                TreeItem<String> parent = selectedItem.getParent();
                int dataIndex = parent.getChildren().indexOf(selectedItem);
                int activityListIndex = parent.getParent().getChildren().indexOf(parent);

                //Loading the html template from GoogleMaps JavaScript API as URL
//                URL urlGoogleMaps = getClass().getResource("/resources/views/googleMapsView.html");
//
//                // Reading of the URL and create related String object
//                String strGoogleMaps = "";
//                BufferedReader in = new BufferedReader(
//                        new InputStreamReader(urlGoogleMaps.openStream()));
//
//                String inputLine;
//                while ((inputLine = in.readLine()) != null)
//                    strGoogleMaps += (inputLine + "\n");
//                in.close();
                String strGoogleMaps = GoogleMapsTools.returnHTMLFileToString("/resources/views/googleMapsView.html");
                // Retrieve selected Data value
                Data data = user.getUserActivities().getActivityListCollection().get(activityListIndex).getActivity(dataIndex);

                // Inject the GoogleMaps html file (read as string above) with the coordinates of the selected data
                String htmlFile = GoogleMapsTools.jsInjection(strGoogleMaps, data);

                // Now the string is complete and up-to-date (containing the
                // selected data coordinates in it), it can be passed to the WebView to load it.
                setUpWebView(htmlFile);
                setInsights(user, data);
            }
        }

    }


    /**
     *
     * @param activityListIndex
     * Opens a dialog for the user to input manual data in the app.
     */
    private void triggerNewActivityDialog(int activityListIndex, boolean isAddActList) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/views/new_data_dialog.fxml"));
        try {
            // Set up for the new stage opening on top of the application.
            Parent root = loader.load();
            Stage newStage = new Stage();
            newStage.initStyle(StageStyle.UNDECORATED);
            NewDataDialogController newDataDialogController = loader.getController();
            newDataDialogController.setActivityListIndexToAppendTo(activityListIndex);
            newDataDialogController.setAddingActivityList(isAddActList);
            newDataDialogController.toggleBtn(isAddActList);
            newDataDialogController.setStage(newStage);
            newDataDialogController.setUser(user);
            newDataDialogController.setActivitiesCollectionController(this);

            Scene scene = new Scene(root, 450, 400);
            newStage.setScene(scene);
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }




    /**
     *
     * @param event
     * Helper function that will make sure the csv file is loaded in the app before allowing the user to upload the data.
     */
    public void searchForFile(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV File", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);
        fileChooser.setTitle("File Chooser");

        File csvDirectory = fileChooser.showOpenDialog(getPrimaryStage());
        if (csvDirectory != null) {
            List<String> csvArray = Arrays.asList(csvDirectory.toPath().toString().split("/"));
            parserInfo.setText("File '" + csvArray.get(csvArray.size() - 1)+"' selected");
            csvToParse = csvDirectory.toPath().toString();
        } else {
            //Tell user the file was not found
        }

    }

    /**
     * Written by Sam, A function to load the edit key words screen and sends through the user.
     * @param event
     * @throws Exception
     */
    public void editKeyWords(ActionEvent event) throws Exception {
        ParserEditKeywords parseEdit = new ParserEditKeywords();
        System.out.println("Activities: "+user.getUserID());
        parseEdit.setUser(user);
        parseEdit.setParentControl(this);
        parseEdit.start(ParserEditKeywords.classStage);
    }

    /**
     * Written by Sam, takes the filename from searchForFile() and parses the data. Handles errors my opening new windows which give the user information of the errors.
     * @param event
     * @throws Exception
     */
    public void uploadFile(MouseEvent event) throws Exception {
        //System.out.println("Ciao");
        SQLiteJDBC database = new SQLiteJDBC();
        if (csvToParse != null && titleField.getText() != null && !titleField.getText().isEmpty()) {
            activityTitle = titleField.getText();
            int error = 0;
            Parser parser = new Parser(csvToParse, user);
            try {
                parser.parseFile();
                for (Data data : parser.getDataList()) {
//                    System.out.println(data.getTitle());
                }
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
                parseError.setParentControl(this);
                parseError.start(ParserErrorType.classStage);
                error = 1;
            }
            List<String> csvArray = Arrays.asList(csvToParse.split("\\\\|/"));
            if (error == 0 && !parser.getDataList().isEmpty()) {
                int add = user.getUserActivities().checkDuplicate(activityTitle);
                ArrayList<Data> newData = new ArrayList<Data>();
                if (add == -1) {
                    ActivityList newList = new ActivityList(activityTitle);
                    add = user.getUserActivities().insertActivityList(newList);
                    database.insertActivityList(activityTitle, newList.getCreationDate(), user.getUserID());
                    for (Data data : parser.getDataList()) {
                        newData.add(data);
                        user.getUserActivities().insertActivityInGivenList(add, data);
                    }
                    database.updateWithListOfData(newData,newList.getTitle(), newList.getCreationDate(), user.getUserID());
                } else {
                    for (Data data : parser.getDataList()) {
                        newData.add(data);
                        user.getUserActivities().insertActivityInGivenList(add, data);
                    }
                    ActivityList existingList = user.getUserActivities().getActivityListCollection().get(add);
                    database.updateWithListOfData(newData,existingList.getTitle(), existingList.getCreationDate(), user.getUserID());
                }
                setUpTreeView();
                parserInfo.setText("File '"+csvArray.get(csvArray.size() - 1)+"' has been uploaded.");


            } else if (parser.getDataList().isEmpty()) {
                parserInfo.setText("File '"+csvArray.get(csvArray.size() - 1)+"' is either empty or only has activities you have already uploaded.");
            }

        } else {
            System.out.println("csvToParse empty");
        }
    }

    /**
     * Written by Sam, sets the text of the parserInfo field
     * @param string
     */
    public void setParserInfoText(String string) {
        parserInfo.setText(string);
    }




    /**
     *OnActionListener for the delete button, it allows to delete an activity or a list of activities.
     * Problem: if deleting the last ActivityList, then you must upload using the parser and no manual entrance is possible.
     */
    //Need to link it to the database
    public void deleteSelectedActivityOrActivityList() {
        SQLiteJDBC database = new SQLiteJDBC();
        TreeItem selectedItem = (TreeItem) activityListCollectionTreeView.getSelectionModel().getSelectedItem();
        if (selectedItem != null && selectedItem != activityListCollectionTreeView.getRoot()) {
            TreeItem<String> parent = selectedItem.getParent();
            if (!selectedItem.isLeaf()) {

                //Dialog opening in the case the item to remove is an activity
                JFXDialogLayout content= new JFXDialogLayout();
                content.setHeading(new Text("Delete Activity List"));
                content.setBody(new Text("This process cannot be reversed, are you sure you want to delete the whole activity list?"));
                JFXDialog dialog =new JFXDialog(dialogStackPane, content, JFXDialog.DialogTransition.CENTER);
                JFXButton deleteButton=new JFXButton("Delete");
                deleteButton.setStyle("-fx-background-color: #ff0000;");

                // Button to cancel the removing action
                JFXButton cancelButton =new JFXButton("Cancel");
                cancelButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        dialog.close();
                    }
                });

                // Button to delete the selected activity
                deleteButton.setOnAction(new EventHandler<ActionEvent>(){
                    @Override
                    public void handle(ActionEvent event){
                        int activityListIndex = parent.getChildren().indexOf(selectedItem);

                        //Database code
                        ActivityList toDelete = user.getUserActivities().getActivityListCollection().get(activityListIndex);
                        System.out.println("toDelete: " + toDelete.getTitle());
                        database.deleteActivityList(toDelete.getTitle(), toDelete.getCreationDate());
                        //

                        user.getUserActivities().deleteActivityList(activityListIndex);
                        setUpTreeView();
                        dialog.close();
                    }
                });
                content.setActions(cancelButton, deleteButton);

                dialog.show();


            } else {

                // Dialog opening in the case the item to remove is an activity list
                JFXDialogLayout content= new JFXDialogLayout();
                content.setHeading(new Text("Delete " + selectedItem.getValue().toString()));
                content.setBody(new Text("This process cannot be reversed, are you sure you want to delete the selected activity?"));
                JFXDialog dialog = new JFXDialog(dialogStackPane, content, JFXDialog.DialogTransition.CENTER);
                JFXButton deleteButton=new JFXButton("Delete");
                deleteButton.setStyle("-fx-background-color: #ff0000;");

                // Button to cancel the removing action
                JFXButton cancelButton =new JFXButton("Cancel");
                cancelButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        dialog.close();
                    }
                });

                // Button to delete the selected activity list
                deleteButton.setOnAction(new EventHandler<ActionEvent>(){
                    @Override
                    public void handle(ActionEvent event){
                        //
                        int dataListIndex = parent.getChildren().indexOf(selectedItem);
                        int activityListIndex = parent.getParent().getChildren().indexOf(parent);
                        //
                        System.out.println("activity list index: " + activityListIndex);
//                        System.out.println("data index: " + dataListIndex);
                        ActivityList activityList = user.getUserActivities().getActivityListCollection().get(activityListIndex);
                        if (activityList.getActivityList().size() > 1) {
                            Data dataToDelete = activityList.getActivity(dataListIndex);
                            activityList.getActivityList().remove(dataToDelete);
                            //Database code
                            database.deleteActivity(dataToDelete.getDataId());
                        } else {
                            //Database code
                            ActivityList toDelete = user.getUserActivities().getActivityListCollection().get(activityListIndex);
                            database.deleteActivityList(toDelete.getTitle(), toDelete.getCreationDate());

                            user.getUserActivities().deleteActivityList(activityListIndex);

                        }

                        setUpTreeView();
                        dialog.close();
                    }
                });
                content.setActions(cancelButton, deleteButton);

                dialog.show();

            }
        }
        setUpTreeView();
    }


    /**
     * Listener for the 'Add' button from activity_list_collection.fxml
     * Triggers the opening of a new new_data_dialog.fxml view and the related controller.
     */
    public void addToActivityList() {

        TreeItem selectedItem = (TreeItem) activityListCollectionTreeView.getSelectionModel().getSelectedItem();
        if (selectedItem == null || selectedItem.equals(activityListCollectionTreeView.getRoot())) {
//            TreeItem<String> parent = selectedItem.getParent();
            int activityListIndex = -1;
            triggerNewActivityDialog(activityListIndex, true);
        } else if (!selectedItem.isLeaf()) {
            TreeItem<String> parent = selectedItem.getParent();
            int activityListIndex = parent.getChildren().indexOf(selectedItem);
            triggerNewActivityDialog(activityListIndex, false);
        }
    }

    /**
     * Listener for the 'Edit' button from activity_list_collection.fxml
     * Triggers the opening of a new modify_data_dialog.fxml view and the related controller.
     */
    public void modifyButtonListener() {
        TreeItem selectedItem = (TreeItem) activityListCollectionTreeView.getSelectionModel().getSelectedItem();
        if (selectedItem != null && selectedItem.isLeaf() && !selectedItem.equals(activityListCollectionTreeView.getRoot())) {
            TreeItem parent = selectedItem.getParent();

            int dataIndex = parent.getChildren().indexOf(selectedItem);
            int activityListIndex = parent.getParent().getChildren().indexOf(parent);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/views/modify_data_dialog.fxml"));
            try {
                Stage newStage = new Stage();
                newStage.initStyle(StageStyle.UNDECORATED);
                Parent root = loader.load();
                ModifyDataValueController modifyDataValueController = loader.getController();
                modifyDataValueController.setActivityListIndex(activityListIndex);
                modifyDataValueController.setDataIndex(dataIndex);
                modifyDataValueController.setStage(newStage);
                modifyDataValueController.setUser(user);
                modifyDataValueController.setActivitiesCollectionController(this);
                modifyDataValueController.initialSetUp();

                Scene scene = new Scene(root, 450, 400);
                newStage.setScene(scene);
                newStage.show();
            } catch (IOException e) {
                    e.printStackTrace();
            }

        }

    }


    /**
     *  Listener for the info ImageView (related to the resources/views/images/info.png file). Aims to help the user
     *  understanding how to interact with the whole view.
     */
    public void showHelp() {
        JFXDialogLayout content = new JFXDialogLayout();
        content.setHeading(new Text("Help"));
        content.setBody(new Label("Select any of the elements in the activities collection and press one of the three buttons." +
                "Depending on the element selected and the button pressed you will be able to modify/delete/" +
                "add different elements in the activities collection"));
        JFXDialog dialog = new JFXDialog(dialogStackPane, content, JFXDialog.DialogTransition.CENTER);
        JFXButton gotItButton=new JFXButton("Got it!");
//        gotItButton.setStyle("-fx-background-color: #ff0000;");

        gotItButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                dialog.close();
            }
        });

        content.setActions(gotItButton);
        dialog.show();
    }


    /**
     *
     * @param s a new String value for the text displayed by the parserInfo FXML object
     */
    public void setParserInfo(String s) {
        parserInfo.setText(s);
    }


    private static void launchTypeError() {
        Application.launch(ParserErrorType.class);
    }


    /**
     *
     * @return the user property
     */
    public User getUser() {
        return user;
    }

    /**
     *
     * @param user a new User object for the user property
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     *
     * @return the primaryStage property
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     *
     * @param primaryStage a new Stage object for the primaryStage property
     */
    public void setPrimaryStage(Stage primaryStage) {
        ActivitiesCollectionController.primaryStage = primaryStage;
    }


    public String getActivityTitle() {
        return activityTitle;
    }
}




