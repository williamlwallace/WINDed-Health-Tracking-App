package seng202.group8.gui.activity_list_collection_displayer;



import java_sqlite_db.SQLiteJDBC;
import javafx.application.Application;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import seng202.group8.activity_collection.ActivityList;
import seng202.group8.activity_collection.ActivityListCollection;
import seng202.group8.data_entries.CoordinateData;
import seng202.group8.data_entries.Data;
import seng202.group8.data_entries.HeartRateData;
import seng202.group8.gui.activity_list_collection_displayer.activities_collection_dialogs.NewDataDialogController;
import seng202.group8.parser.*;
import seng202.group8.user.User;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * @author lfa69
 * Controller for activity_list_collection.fxml.
 * Deals with displaying the user Data values, display major insights and path followed, retrieve and upload CSV data from local directory
 */
public class ActivitiesCollectionController {

    /*Insights GUI elements*/
    @FXML
    private Text insightsTitle;

    @FXML
    private Text distanceCovered;

    @FXML
    private Text averageHeartRate;

    @FXML
    private Text maxHeartRate;

    @FXML
    private Text minHeartRate;

    @FXML
    private Text averageSpeed;

    @FXML
    private Text maxSpeed;

    @FXML
    private Text minSpeed;

    @FXML
    private Text parserInfo;

    @FXML
    private TextField titleField;

    /* TreeView */
    @FXML
    private TreeView activityListCollectionTreeView;

    /* WebView */
    @FXML
    private WebView googleMapsWebView;

    /* File Searching */
    @FXML
    private Button searchForFileButton;

    private static Stage primaryStage;
    private static User user;
    private String csvToParse;
    private String activityTitle;


    /**
     *
     * @param user
     * @param data
     * Deals with displaying the major insights of the Data value selected from the TreeView.
     */
    public void setInsights(User user, Data data) {
        HeartRateData heartRateData = data.getHeartRateData();
        insightsTitle.setText(data.getTitle() + " insights:");
        distanceCovered.setText(data.getDistanceCovered().toString());
        averageHeartRate.setText(String.valueOf(heartRateData.getMeanAverageHeartRate()));
        maxHeartRate.setText(String.valueOf(heartRateData.getHighestHeartRate()));
        minHeartRate.setText(String.valueOf(heartRateData.getLowestHeartRate()));
    }

    public void setUpTreeView() {
        ActivityListCollection activityListCollection = user.getUserActivities();
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
        activityListCollectionTreeView.setTooltip(new Tooltip("Double click on any of the activities containers to add a new activity to its list!"));
        activityListCollectionTreeView.setRoot(rootNode);

    }






    public void setUpWebView(String htmlFile) {
        WebEngine webEngine = googleMapsWebView.getEngine();
//        System.out.println(htmlFile);
        webEngine.loadContent(htmlFile);
    }


    public void showNewInsightsAndMap(MouseEvent mouseEvent) throws IOException {
        TreeItem<String> selectedItem = (TreeItem<String>) activityListCollectionTreeView.getSelectionModel().getSelectedItem();
        System.out.println("Selected Item:" + selectedItem);
        if (selectedItem != null ) {
            if (mouseEvent.getButton() == MouseButton.PRIMARY && selectedItem.isLeaf() && selectedItem != activityListCollectionTreeView.getRoot()) {
                System.out.println("Ciao " + selectedItem.getValue());
                TreeItem<String> parent = selectedItem.getParent();
                int dataIndex = parent.getChildren().indexOf(selectedItem);
                int activityListIndex = parent.getParent().getChildren().indexOf(parent);

                StringBuilder bldr = new StringBuilder();
                String str;

                URL urlGoogleMaps = getClass().getResource("../../../../resources/views/googleMapsView.html");

                String strGoogleMaps = "";
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(urlGoogleMaps.openStream()));

                String inputLine;
                while ((inputLine = in.readLine()) != null)
                    strGoogleMaps += (inputLine + "\n");
                in.close();


                Data data = user.getUserActivities().getActivityListCollection().get(activityListIndex).getActivity(dataIndex);
                String htmlFile = jsInjection(strGoogleMaps, data);
                setUpWebView(htmlFile);
                setInsights(user, data);
            } else if (!selectedItem.isLeaf() && selectedItem != activityListCollectionTreeView.getRoot() && mouseEvent.getClickCount() == 2) {
                System.out.println("This should happen only if I click a non root non leaf element and right click on it");
                TreeItem<String> parent = selectedItem.getParent();
                int activityListIndex = parent.getChildren().indexOf(selectedItem);
                System.out.println(activityListIndex);
                triggerNewActivityDialog(activityListIndex);

            }
        }

    }


    private void triggerNewActivityDialog(int activityListIndex) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../../../resources/views/new_data_dialog.fxml"));
        try {
            Parent root = loader.load();
            Stage newStage = new Stage();
            NewDataDialogController newDataDialogController = loader.getController();
            newDataDialogController.setActivityListIndexToAppendTo(activityListIndex);
            newDataDialogController.setStage(newStage);
            newDataDialogController.setUser(user);
            newDataDialogController.setActivitiesCollectionController(this);

            Scene scene = new Scene(root, 450, 400);
//            primaryStage.initModality(Modality.APPLICATION_MODAL);
            newStage.setScene(scene);
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    private String jsInjection(String htmlFile, Data data) {
        htmlFile = htmlFile.replace("&CENTERID", "lat: "
                + data.getCoordinatesArrayList().get(0).getLatitude()
                + ", lng: "
                + data.getCoordinatesArrayList().get(0).getLongitude());
        StringBuilder stringBuilder = new StringBuilder("var dataCoordVar = [ ");
        for (int i = 0; i < data.getCoordinatesArrayList().size(); i++) {
            CoordinateData coordinateData = data.getCoordinatesArrayList().get(i);
            if (i != (data.getCoordinatesArrayList().size() -1)) {
                stringBuilder.append("{lat: " + coordinateData.getLatitude()
                        + ", lng: " + coordinateData.getLongitude() + "},");
            } else {
                stringBuilder.append("{lat: " + coordinateData.getLatitude()
                        + ", lng: " + coordinateData.getLongitude() + "}];");
            }
        }
        htmlFile = htmlFile.replace("&PREVFLIGHTCOORDID", stringBuilder.toString());

        htmlFile = htmlFile.replace("&FLIGHTPATHVARID", "dataCoordPath");
        htmlFile = htmlFile.replace("&PREVFLIGHTVAR", "dataCoordVar");
//        System.out.println(htmlFile);
        return htmlFile;

    }

    public void searchForFile(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV File", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);
        fileChooser.setTitle("File Chooser");

        File csvDirectory = fileChooser.showOpenDialog(getPrimaryStage());
        if (csvDirectory != null) {
            List<String> csvArray = Arrays.asList(csvDirectory.toPath().toString().split("/"));
            parserInfo.setText("File '"+csvArray.get(csvArray.size() - 1)+"' selected");
            csvToParse = csvDirectory.toPath().toString();
        } else {
            //Tell user the file was not found
        }

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
            if (error == 0) {
                int add = user.getUserActivities().checkDuplicate(activityTitle);
                ArrayList<Data> newData = new ArrayList<Data>();
                if (add == -1) {
                    ActivityList newList = new ActivityList(activityTitle);
                    add = user.getUserActivities().insertActivityList(newList);
                    database.insertActivityList(activityTitle, database.getStringFromLocalDateTime(database.convertToLocalDateTimeViaInstant(newList.getCreationDate())), 1);
                    for (Data data : parser.getDataList()) {
                        newData.add(data);
                        user.getUserActivities().insertActivityInGivenList(add, data);
                    }
                    database.updateWithListOfData(newData,newList.getTitle(), newList.getCreationDate(), 1);
                } else {
                    for (Data data : parser.getDataList()) {
                        newData.add(data);
                        user.getUserActivities().insertActivityInGivenList(add, data);
                    }
                    ActivityList existingList = user.getUserActivities().getActivityListCollection().get(add);
                    database.updateWithListOfData(newData,existingList.getTitle(), existingList.getCreationDate(), 1);
                }
                setUpTreeView();
                List<String> csvArray = Arrays.asList(csvToParse.split("/"));
                parserInfo.setText("File '"+csvArray.get(csvArray.size() - 1)+"' has been uploaded.");
                //database.saveUser(user, 1);


            }

        } else {
            System.out.println("csvToParse empty");
        }
    }

    public void setParserInfo(String s) {
        parserInfo.setText(s);
    }


    private static void launchTypeError() {
        Application.launch(ParserErrorType.class);
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void setPrimaryStage(Stage primaryStage) {
        ActivitiesCollectionController.primaryStage = primaryStage;
    }

    public String getCSVToParse() {
        return csvToParse;
    }

    public String getActivityTitle() {
        return activityTitle;
    }
}




