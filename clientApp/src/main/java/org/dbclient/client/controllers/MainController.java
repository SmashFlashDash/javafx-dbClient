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
import javafx.scene.input.KeyCode;
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
        connetcToServer();
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
                connetcToServer();
            }
        });

        // клики кнопок
        btnSaveUri.setOnMouseClicked(event -> connetcToServer());
        btnRefreshItems.setOnMouseClicked(event -> refreshTable());
        btnAddItem.setOnMouseClicked(event -> popupController.show(parent));
        btnEditItem.setOnMouseClicked(event -> {
            ItemDto item = tableView.getSelectionModel().getSelectedItem();
            popupController.show(parent, item);
        });
        btnDeleteItem.setOnMouseClicked(event -> {
            ItemDto item = tableView.getSelectionModel().getSelectedItem();
            if (webClientService.deleteItem(item)) {
                refreshTable();
            } else {
                fieldStatus.setText("Запрос не выполнен");
            }
        });

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

    private boolean connetcToServer() {
        webClientService.setBaseURI(fieldUri.getText());
        if (webClientService.checkConnection()) {
            fieldStatus.setText("Подключено к серверу");
            refreshTable();
            setBtnDisable(false, new JFXButton[]{btnRefreshItems, btnAddItem});
            return true;
        }
        fieldStatus.setText("Сервер не отвечает");
        tableView.getItems().clear();
        setBtnDisable(true, new JFXButton[]{btnRefreshItems, btnAddItem, btnEditItem, btnDeleteItem});
        return false;
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

    public List<ItemDto> refreshTable() {
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
