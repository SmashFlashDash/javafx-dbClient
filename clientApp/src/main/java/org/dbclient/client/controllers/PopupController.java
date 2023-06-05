package org.dbclient.client.controllers;

import com.jfoenix.controls.JFXButton;
import common.dto.ItemAddDto;
import common.dto.ItemDto;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import org.dbclient.client.config.DtoMapper;
import org.dbclient.client.services.WebClientService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.Field;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class PopupController {
    private final WebClientService webClientService;
    private final MainController mainController;
    private final ApplicationContext context;
    private final Logger log = Logger.getLogger("PopupController");
    private ItemDto editingItem = null;

    @FXML
    private Label labelStatus;

    @FXML
    private ListView<String> leftViewType;
    @FXML
    private ListView<String> leftViewTitle;
    @FXML
    private ListView<String> rightView;

    @FXML
    private JFXButton btnAccept;
    @FXML
    private JFXButton btnDeny;

    @FXML
    void initialize() throws IllegalAccessException {
        listViewsInit();
        leftViewType.setMouseTransparent(true);
        leftViewType.setFocusTraversable(false);
        leftViewTitle.setMouseTransparent(true);
        leftViewTitle.setFocusTraversable(false);
        rightView.setOnEditCommit(event -> rightView.getItems().set(event.getIndex(), event.getNewValue()));
        rightView.setEditable(true);
        rightView.setCellFactory(TextFieldListCell.forListView());
        btnEventsInit();
    }

    private void btnEventsInit() {
        btnDeny.setOnMouseClicked(event -> {
            Stage stage = (Stage) btnDeny.getScene().getWindow();
            stage.close();
        });
        // парсинг строковых значений полей в новый itemDto
        btnAccept.setOnMouseClicked(event -> {
            //TODO: хорошо сделать через рефлексию чтоббы не зависить от обьекта таблицы
            // на получилась идуя делать в rightView.setOnEditCommit, но это не то
            // Field field = fields.get(event.getIndex());
            // Class<T> c = (Class<T>) Class.forName(field.getName());
            // field.get(sendedItem);
            // field.set(sendedItem, event.getNewValue())

            List<String> items = rightView.getItems();
            try {
                if (editingItem == null) {
                    webClientService.addItem(DtoMapper.parseToItemAddDto(items.toArray(new String[items.size()])));
                } else {
                    webClientService.editItem(DtoMapper.parseToItemDto(items.toArray(new String[items.size()])));
                }
                Stage stage = (Stage) btnDeny.getScene().getWindow();
                stage.close();
            } catch (IllegalArgumentException | DateTimeParseException ex) {
                labelStatus.setText(ex.getMessage());
            }
        });
    }

    private void listViewsInit() throws IllegalAccessException {
        for (Field field : ItemDto.class.getDeclaredFields()) {
            leftViewType.getItems().add(field.getType().getSimpleName());
            leftViewTitle.getItems().add(field.getName());
            if (editingItem != null) {
                Object value = field.get(editingItem);
                rightView.getItems().add(value==null? "" : value.toString());
            } else {
                rightView.getItems().add("");
            }
        }
    }

    public void show(Parent parentStage) {
        showFxml(parentStage);
    }

    public void show(Parent parentStage, ItemDto sendedItem) {
        this.editingItem = sendedItem;
        showFxml(parentStage);
    }

    private void showFxml(Parent parentStage) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Popup.fxml"));
        fxmlLoader.setControllerFactory(context::getBean);
        try {
            Parent parent = fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(parentStage.getScene().getWindow());
            stage.setOnHidden(event -> {
                this.editingItem = null;
            });
            //stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException e) {
            //((ClientApp) context.getBean("Main")).stop();
            Platform.exit();
        }
    }
}
