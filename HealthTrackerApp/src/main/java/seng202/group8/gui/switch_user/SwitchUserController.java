package seng202.group8.gui.switch_user;

import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Callback;
import seng202.group8.services.goals_service.goal_types.Goal;
import seng202.group8.services.goals_service.goal_types.GoalType;
import seng202.group8.user.User;
import seng202.group8.user.user_stats.Sex;

import java.util.ArrayList;
import java.util.Observable;

public class SwitchUserController {



    @FXML
    private JFXListView<User> switchUserListView;


    private ArrayList<User> users;
    private Image userIcon = new Image("/resources/views/images/user.png");


    @FXML
    public void initialize() {
        // TODO to delete
        User user = new User("Joel", 19, 72.0, 167.0, Sex.MALE);
        User user1 = new User("Joel", 19, 72.0, 167.0, Sex.MALE);
        //
        users = new ArrayList<>();
        users.add(user);
        users.add(user1);
        users.add(user1);
        users.add(user1);
        users.add(user1);
        users.add(user1);

//        users = null; // call to DB
        populateUsersListView(users);
    }


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
                            Text userName = new Text(user.getName());
                            ImageView userIconView = new ImageView();
                            userIconView.setImage(userIcon);
                            hBox.getChildren().addAll(userIconView, userName);
                            setGraphic(hBox);
                        }
                    }
                };

                return cell;
            }
        });


    }


    public static void main(String[] args) {

    }





}
