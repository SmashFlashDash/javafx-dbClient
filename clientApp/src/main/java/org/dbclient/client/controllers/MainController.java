package org.dbclient.client.controllers;


import com.jfoenix.controls.JFXButton;
import common.dto.ItemDto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import lombok.RequiredArgsConstructor;
import org.dbclient.client.services.WebClientService;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.List;
import java.util.logging.Logger;

@Component
@RequiredArgsConstructor
public class MainController {
    private final WebClientService webClientService;
    private final PopupController popupController;
    private final ApplicationContext context;
    private final Logger log = Logger.getLogger("MainController");
    private Parent parent;

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
    private TableView<ItemDto> tableView;


    @FXML
    void initialize() {
        initEvents();
        initTableColumns(ItemDto.class);
        connetcToServer();
    }

    public Object show() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Main.fxml"));
        fxmlLoader.setControllerFactory(context::getBean);
        parent = fxmlLoader.load();
        return parent;
    }

    // TODO: чтобы не делать пооток и не блочить webClient
    //  пердавать в методы ламбдва функции onSucess OnError
    //  с блоком других кнопока и остальными действиями
    //  там принимапть функциональный интерфейс

    private void initEvents() {
        setBtnDisable(true, new JFXButton[]{btnRefreshItems, btnAddItem, btnEditItem, btnDeleteItem});
        btnSaveUri.setOnMouseClicked(event -> connetcToServer());
        btnRefreshItems.setOnMouseClicked(event -> refreshTable());
        btnAddItem.setOnMouseClicked(event -> popupController.show(parent));
        btnEditItem.setOnMouseClicked(event -> {
            ItemDto item = tableView.getSelectionModel().getSelectedItem();
            popupController.show(parent, item);
            System.out.println("showed");
        });
        btnDeleteItem.setOnMouseClicked(event -> {
            ItemDto item = tableView.getSelectionModel().getSelectedItem();
            if (webClientService.deleteItem(item)) {
                refreshTable();
            }
        });

        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection == null) {
                btnEditItem.setDisable(true);
                btnDeleteItem.setDisable(true);
                return;
            }
            btnEditItem.setDisable(false);
            btnDeleteItem.setDisable(false);
        });
    }

    private boolean connetcToServer() {
        webClientService.setBaseURI(fieldUri.getText());
        if (webClientService.checkConnection()) {
            fieldStatus.setText("Подключено к серверу");
            refreshTable();
            setBtnDisable(false, new JFXButton[]{btnRefreshItems, btnAddItem});
            return true;
        }
        fieldStatus.setText("Сервер не отвечает");
        setBtnDisable(true, new JFXButton[]{btnRefreshItems, btnAddItem, btnEditItem, btnDeleteItem});
        return false;
    }

    private <T> void initTableColumns(T type) {
        tableView.getColumns().clear();
        for (Field field : ((Class) type).getDeclaredFields()) {
            TableColumn column = new TableColumn<>(field.getName());
            column.setCellValueFactory(new PropertyValueFactory<>(field.getName()));
            tableView.getColumns().add(column);
        }
    }

    private List<ItemDto> refreshTable() {
        List<ItemDto> items = webClientService.getAllItems();
        ObservableList<ItemDto> list = FXCollections.observableArrayList(items);
        tableView.setItems(list);
        return items;
    }

    private void setBtnDisable(boolean bool, JFXButton[] btns) {
        for (JFXButton btn : btns) {
            btn.setDisable(bool);
        }
    }

}
