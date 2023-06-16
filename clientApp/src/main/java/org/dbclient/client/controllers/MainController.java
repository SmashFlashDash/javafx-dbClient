package org.dbclient.client.controllers;


import com.jfoenix.controls.JFXButton;
import common.dto.ItemDto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;
import lombok.RequiredArgsConstructor;
import org.dbclient.client.services.WebClientService;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

@Component
@RequiredArgsConstructor
public class MainController {
    private final WebClientService webClientService;
    private final PopupController popupController;
    private final ApplicationContext context;
    private final Logger log = Logger.getLogger("MainController");
    private final HashMap<String, Task<?>> tasks = new HashMap<>();
    private Parent parent;
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
        setBtnDisable(true, new JFXButton[]{btnRefreshItems, btnAddItem, btnEditItem, btnDeleteItem});
        initEvents();
        initTableColumns(ItemDto.class);
        btnConnetcToServer();
    }

    public Object show() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Main.fxml"));
        fxmlLoader.setControllerFactory(context::getBean);
        parent = fxmlLoader.load();
        return parent;
    }

    private void initEvents() {
        fieldUri.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                btnConnetcToServer();
            }
        });
        // клики кнопок
        btnSaveUri.setOnMouseClicked(event -> btnConnetcToServer());
        btnRefreshItems.setOnMouseClicked(event -> btnRefreshTable());
        btnAddItem.setOnMouseClicked(event -> btnAddItem());
        btnEditItem.setOnMouseClicked(event -> btnEditItem());
        btnDeleteItem.setOnMouseClicked(event -> btnDeleteItem());
        // блокировка кнопок при выборе строки в таблице
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

    /**
     * Инит полей в таблице по поялм ожидаемого класса
     */
    private <T> void initTableColumns(T type) {
        tableView.getColumns().clear();
        for (Field field : ((Class) type).getDeclaredFields()) {
            TableColumn column = new TableColumn<>(field.getName());
            column.setCellValueFactory(new PropertyValueFactory<>(field.getName()));
            tableView.getColumns().add(column);
        }
    }

    private void btnConnetcToServer() {
        if (tasks.containsKey("connect")) {
            tasks.get("connect").cancel();
        }
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() {
                fieldStatus.setText("Подключение...");
                webClientService.setBaseURI(fieldUri.getText());
                if (webClientService.checkConnection()) {
                    fieldStatus.setText("Подключено к серверу");
                    btnRefreshTable();
                    setBtnDisable(false, new JFXButton[]{btnRefreshItems, btnAddItem});
                } else {
                    fieldStatus.setText("Сервер не отвечает");
                    tableView.getItems().clear();
                    setBtnDisable(true, new JFXButton[]{btnRefreshItems, btnAddItem, btnEditItem, btnDeleteItem});
                }
                return null;
            }
        };
        startDaemonThread(task);
        tasks.put("connect", task);
    }

    private <T> void startDaemonThread(Task<T> task) {
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    public void btnRefreshTable() {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                List<ItemDto> items = webClientService.getAllItems();
                ObservableList<ItemDto> list = FXCollections.observableArrayList(items);
                tableView.setItems(list);
                return null;
            }
        };
        startDaemonThread(task);
    }

    private void btnEditItem() {
        ItemDto item = tableView.getSelectionModel().getSelectedItem();
        popupController.show(parent, item);
        parent.requestFocus();
    }

    private void btnAddItem() {
        popupController.show(parent);
        parent.requestFocus();
    }

    private void btnDeleteItem() {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() {
                ItemDto item = tableView.getSelectionModel().getSelectedItem();
                if (webClientService.deleteItem(item)) {
                    btnRefreshTable();
                } else {
                    fieldStatus.setText("Запрос не выполнен");
                }
                return null;
            }
        };
        startDaemonThread(task);
    }

    private void setBtnDisable(boolean bool, JFXButton[] btns) {
        for (JFXButton btn : btns) {
            btn.setDisable(bool);
        }
    }

}
