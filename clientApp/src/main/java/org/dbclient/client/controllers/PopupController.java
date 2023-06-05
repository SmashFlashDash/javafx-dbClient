package org.dbclient.client.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import common.data.Item;
import common.dto.ItemDto;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import org.dbclient.client.ClientApp;
import org.dbclient.client.services.WebClientService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class PopupController {
    private final WebClientService webClientService;
    private final MainController mainController;
    private final ApplicationContext context;
    private final Logger log = Logger.getLogger("PopupController");
    private ItemDto sendedItem = null;

    @FXML
    private JFXListView<String> leftView;
    @FXML
    private JFXListView<String> rightView;

    @FXML
    private JFXButton btnAccept;
    @FXML
    private JFXButton btnDeny;

    @FXML
    void initialize() throws IllegalAccessException {
        // List<String> titles = Arrays.stream(ItemDto.class.getDeclaredFields()).map(Field::getName).collect(Collectors.toList());
        // leftView.getItems().addAll(titles);

        for (Field field : ItemDto.class.getDeclaredFields()){
            leftView.getItems().add(field.getName());
            if (sendedItem != null) {
                rightView.getItems().add(field.get(sendedItem).toString());
            }
        }

        leftView.setMouseTransparent(true);
        leftView.setFocusTraversable(false);
//        ObservableList<ItemDto> items = FXCollections.observableArrayList(sendedItem);
//        sendedItem.g
//        rightView.setCellFactory(param -> new ListCell<Word>() {
//            @Override
//            protected void updateItem(Word item, boolean empty) {
//                super.updateItem(item, empty);
//
//                if (empty || item == null || item.getWord() == null) {
//                    setText(null);
//                } else {
//                    setText(item.getWord());
//                }
//            }
//        });

        btnEventsInit();
    }

    private void btnEventsInit() {
        btnDeny.setOnMouseClicked(event -> {
            Stage stage = (Stage) btnDeny.getScene().getWindow();
            stage.close();
        });
        btnAccept.setOnMouseClicked(event -> {
            Stage stage = (Stage) btnDeny.getScene().getWindow();
            stage.close();
        });
    }

    public void show(Parent parentStage) {
        showFxml(parentStage);
    }
    public void show(Parent parentStage, ItemDto sendedItem) {
        this.sendedItem = sendedItem;
        showFxml(parentStage);
    }

    private void showFxml(Parent parentStage){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Popup.fxml"));
        fxmlLoader.setControllerFactory(context::getBean);
        try {
            Parent parent = fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(parentStage.getScene().getWindow());
            stage.setOnHidden(event -> {
                this.sendedItem = null;
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
