package org.dbclient.client.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
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
import java.util.logging.Logger;

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
    void initialize() {
        ObservableList<ItemDto> items = FXCollections.observableArrayList(sendedItem);
        //leftView.set
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
            stage.setOnCloseRequest(event -> this.sendedItem = null);
            //stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException e) {
            //((ClientApp) context.getBean("Main")).stop();
            Platform.exit();
        }
    }
}
