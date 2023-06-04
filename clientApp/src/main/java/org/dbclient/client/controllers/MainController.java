package org.dbclient.client.controllers;


import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import org.kordamp.ikonli.javafx.FontIcon;
import org.springframework.stereotype.Component;

@Component
public class MainController {
    // TODO: сделать гуи с кнопками и статус баром
    //
    @FXML
    private JFXButton btnAddItem;

    @FXML
    private JFXButton btnDeleteItem;

    @FXML
    private JFXButton btnEditItem;

    @FXML
    private JFXButton btnSavePort;

    @FXML
    private TableColumn<?, ?> col2;

    @FXML
    private TextField fieldPort;

    @FXML
    private Text fieldStatus;

    @FXML
    private TableColumn<?, ?> idCol;

    @FXML
    private TableView<?> tableView;

}
