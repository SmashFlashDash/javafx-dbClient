package org.dbclient.client.controllers;


import com.jfoenix.controls.JFXButton;
import common.dto.ItemDto;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import lombok.RequiredArgsConstructor;
import org.dbclient.client.services.WebClientService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MainController {
    private final WebClientService webClientService;

    // TODO:
    //  сервисы: data, setting
    //      settings - меняет порт webclient
    //      getData - статсут бар грузится, пока идут данные
    //      поидее нужно блокировать остальные запросы если последний не выполнен?
    //  config: webClient с настрйоками
    //  controllrs:
    //      основное меню с таблицей,
    //      второй - всплывающее окно редактировать
    //      всплывающее окно hbox с полями и кнопками сохранить отменить

    // TODO: сделать гуи с кнопками и статус баром
    //  ---
    //  вынести endpoint запроса к таблице в application properties
    //  default text вынести в отдельный файл yml
    //  icon на основное окно и текст database, there
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
    private JFXButton btnSaveUri;
    @FXML
    private TextField fieldUri;
    @FXML
    private Text fieldStatus;

    @FXML
    private JFXButton btnRefreshItems;
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
        // set default localhost text in
        setEvents();
        //connectFillTable();
    }

    private void setEvents() {
        btnSaveUri.setOnMouseClicked(event -> {
            webClientService.setBaseURI(fieldUri.getText());
            // test connect, show in status
        });
        btnRefreshItems.setOnMouseClicked(event -> {
            webClientService.getAllItems();
            // refresh table
        });
        btnAddItem.setOnMouseClicked(event -> {
            // show second window, block first widnow
            // второе окно с пустыми полями без id
        });
        btnEditItem.setOnMouseClicked(event -> {
            // show second window, block first widnow
            // передать во второе окно обьект выбранный в таблице
        });
        btnDeleteItem.setOnMouseClicked(event -> {
            // взять выбранный обект в таблице и отправить в deleteItem
            //webClientService.deleteItem();
        });
    }

    private void connectFillTable() {
    }

}
