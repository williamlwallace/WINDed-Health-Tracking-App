package seng202.group8.gui.activity_list_collection_displayer;

import java.util.Arrays;
import java.util.List;
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;
//import java.awt.event.KeyEvent;

public class TextFieldTreeCellImpl extends TreeCell<String> {


    private TextField textField;
    private ContextMenu addMenu = new ContextMenu();

    public TextFieldTreeCellImpl() {
        MenuItem addMenuItem = new MenuItem("Add activity");
        addMenu.getItems().add(addMenuItem);
        addMenuItem.setOnAction(new EventHandler() {
            public void handle(Event t) {
            }
        });

    }
//        MenuItem addMenuItem = new MenuItem("Add Employee");
//        addMenu.getItems().add(addMenuItem);
//        addMenuItem.setOnAction(new EventHandler() {
//            public void handle(Event t) {
//                TreeItem newEmployee =
//                        new TreeItem<String>("New Employee");
//                getTreeItem().getChildren().add(newEmployee);
//            }
//        });
//    }


    @Override
    public void startEdit() {
        super.startEdit();

        if (textField == null) {
            createTextField();
        }
        setText(null);
        setGraphic(textField);
        textField.selectAll();
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
        setText((String) getItem());
        setGraphic(getTreeItem().getGraphic());
    }

    @Override
    public void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            if (isEditing()) {
                if (textField != null) {
                    textField.setText(getString());
                }
                setText(null);
                setGraphic(textField);
            } else {
                setText(getString());
                setGraphic(getTreeItem().getGraphic());
                if (!getTreeItem().isLeaf()){
                    setContextMenu(addMenu);
                }
            }
        }
    }

    private void createTextField() {
        textField = new TextField(getString());
        textField.setOnKeyReleased(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ENTER) {
                    commitEdit(textField.getText());

                } else if (t.getCode() == KeyCode.ESCAPE) {
                    cancelEdit();
                }
            }
        });
    }

    private String getString() {
        return getItem() == null ? "" : getItem().toString();
    }

}
