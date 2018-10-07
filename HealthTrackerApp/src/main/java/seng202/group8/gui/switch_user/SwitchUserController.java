package seng202.group8.gui.switch_user;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXListView;
import java_sqlite_db.SQLiteJDBC;
import javafx.application.HostServices;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.sqlite.core.DB;
import seng202.group8.activity_collection.ActivityList;
import seng202.group8.data_entries.Data;
import seng202.group8.gui.GUIController;
import seng202.group8.gui.user_info_gui.GetUserInfoController;
import seng202.group8.services.goals_service.goal_types.Goal;
import seng202.group8.services.goals_service.goal_types.GoalType;
import seng202.group8.user.User;
import seng202.group8.user.user_stats.Sex;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;

/**
 * @author wwa52, lfa69
 * Controller class for the switch user screen, deals with selecting which user to load and also loading the new user
 * screen if requested
 */
public class SwitchUserController {

    private User user;
    private Stage stage;
    private HostServices host;

    private SQLiteJDBC DB = new SQLiteJDBC();
    private ArrayList<User> users;
    private Image userIcon = new Image("/resources/views/images/user_32px.png");

    @FXML
    private JFXListView<User> switchUserListView;

    @FXML
    private JFXButton enterBtn;

    @FXML
    private JFXButton cancelBtn;

    @FXML
    private JFXButton newUserBtn;

    @FXML
    private StackPane dialogStackPane;


    /**
     * Initialises the switch user screen, retrieves all the users from the database and calls populateUsersListView
     * to populate the list view with the retrieved users
     */
    @FXML
    public void initialize() {

        users = DB.retrieveAllUsers();
        populateUsersListView(users);
        switchUserListView.getSelectionModel().selectFirst();
    }


    /**
     * Populates the list view with users retrieved from the database
     * @param users
     */
    private void populateUsersListView(ArrayList<User> users) {

        ObservableList<User> observableList = FXCollections.observableList(users);
        switchUserListView.setItems(observableList);
        switchUserListView.setCellFactory(new Callback<ListView<User>, ListCell<User>>() {
            @Override
            public ListCell<User> call(ListView<User> param) {
                ListCell<User> cell = new ListCell<User>(){

                    @Override
                    protected void updateItem(User user, boolean bln) {
                        super.updateItem(user, bln);

                        if (user != null) {
                            HBox hBox = new HBox();
                            hBox.setAlignment(Pos.CENTER_LEFT);
                            hBox.setSpacing(10);
                            Label userName = new Label(user.getName());
                            userName.setStyle("-fx-text-fill: white");
                            userName.setFont(Font.font("Arial", 14));
                            userName.setPrefWidth(200);
                            ImageView userIconView = new ImageView();
                            userIconView.setImage(userIcon);
                            ImageView deleteImageView = new ImageView();
                            JFXButton deleteBtn = new JFXButton();
                            deleteBtn.setButtonType(JFXButton.ButtonType.RAISED);
                            deleteBtn.setTooltip(new Tooltip("Delete this user"));
                            deleteBtn.setOnAction(new EventHandler<ActionEvent>() {

                                @Override
                                public void handle(ActionEvent event) {

                                    JFXDialogLayout content= new JFXDialogLayout();
                                    content.setHeading(new Text("Delete this user"));
                                    content.setBody(new Text("This process cannot be reversed, are you sure you want to delete this user?"));
                                    JFXDialog dialog = new JFXDialog(dialogStackPane, content, JFXDialog.DialogTransition.CENTER);
                                    JFXButton deleteButton=new JFXButton("Delete");
                                    deleteButton.setStyle("-fx-background-color: #c1c1c1;");

                                    // Button to cancel deletion of user
                                    JFXButton cancelButton = new JFXButton("Cancel");
                                    cancelButton.setStyle("-fx-background-color: #c1c1c1;");
                                    cancelButton.setOnAction(new EventHandler<ActionEvent>() {
                                        @Override
                                        public void handle(ActionEvent event) {
                                            dialog.close();
                                        }
                                    });

                                    // Button to delete the selected user
                                    deleteButton.setOnAction(new EventHandler<ActionEvent>(){
                                        @Override
                                        public void handle(ActionEvent event) {
                                            if (users.size() == 1) {
                                                DB.deleteUser(switchUserListView.getSelectionModel().getSelectedItem().getUserID());
                                                dialog.close();
                                                try {
                                                    loadNewUserScreen(event);
                                                } catch (IOException e) {
                                                    System.out.println("Something went wrong when trying to delete user");
                                                }

                                            } else {
                                                DB.deleteUser(switchUserListView.getSelectionModel().getSelectedItem().getUserID());
                                                dialog.close();
                                                initialize();
                                            }
                                        }
                                    });
                                    content.setActions(cancelButton, deleteButton);
                                    dialog.show();
                                }
                            });

                            deleteBtn.setGraphic(deleteImageView);
                            deleteBtn.setId("deleteBtn");
                            hBox.getChildren().addAll(userIconView, userName, deleteBtn);
                            setGraphic(hBox);
                        }
                    }
                };
                return cell;
            }
        });
    }


    /**
     * Event handler for the enter button, loads the mainFrame and home screen, plus the GUI controller
     * @param event
     * @throws IOException
     */
    @FXML
    private void enterApp(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/views/mainFrame.fxml"));
        Parent root = loader.load();
        GUIController guiController = loader.getController();
        guiController.setHostServices(host);
        guiController.setStage(stage);
        guiController.setUser(switchUserListView.getSelectionModel().getSelectedItem());
        stage.setTitle("WINded");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        guiController.setToHome();
        stage.show();
    }


    /**
     * Event handler for the new user button, loads the get user info screen and controller
     * @param event
     * @throws IOException
     */
    @FXML
    private void loadNewUserScreen(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/views/GetUserInfo.fxml"));
        Parent root = loader.load();
        GetUserInfoController getUserInfoController = loader.getController();
        getUserInfoController.setHostServices(host);
        getUserInfoController.setStage(stage);
        stage.setTitle("WINded");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    /**
     * Event handler for pressing the cancel button, closes the stage/window
     * @param event
     */
    @FXML
    private void quitApp(ActionEvent event) { stage.close(); }


    /**
     *
     * @return the user property
     */
    public User getUser() { return user; }


    /**
     *
     * @param user a new User object for the user property
     */
    public void setUser(User user) { this.user = user; }


    /**
     *
     * @return the stage property
     */
    public Stage getStage() { return stage; }


    /**
     *
     * @param stage a new Stage object for the stage property
     */
    public void setStage(Stage stage) { this.stage = stage; }


    /**
     *
     * @param host a HostServices object
     */
    public void setHostServices(HostServices host) { this.host = host; }

}
