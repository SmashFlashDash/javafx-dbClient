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
    //  ---
    //  вынести endpoint запроса к таблице в application properties
    //  default text вынести в отдельный файл yml
    //  стили если будет добавить общих классов на обьекты
    //  и написать общий css
    //  ---
    //  по кнопке обновить выполняется подключение к сервеу item/all
    //  если данные получены генерируются колонки по полям в таблице
    //  и строки заполняются
    //  ---
    //  изначалаьно кнопки add edit save заблочены
    //  add разблокируется после первой загрузки
    //  edit delte если выбрана строка в таблице
    //  ---
    //  в таблице должен быть скролл бар
    //  колонки в таблице и поля не редактируемые
    //  ***
    //  можно сделать поиск кнопку и искать локально в таблице а не в sql

    @FXML
    private JFXButton btnSavePort;
    @FXML
    private TextField fieldPort;
    @FXML
    private Text fieldStatus;

    @FXML
    private JFXButton btnAddItem;
    @FXML
    private JFXButton btnDeleteItem;
    @FXML
    private JFXButton btnEditItem;


    @FXML
    private TableView<?> tableView;
    @FXML
    private TableColumn<?, ?> idCol;
    @FXML
    private TableColumn<?, ?> col2;

    @FXML
    void initialize() {
    }

}
