package org.dbclient.client.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;

@Component
public class StageInit implements ApplicationListener<ClientUi.StageReadyEvent> {
    @Value("/fxml/chart.fxml")
    private URL resource;

    @Override
    public void onApplicationEvent(ClientUi.StageReadyEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(resource);
        try {
            //Parent parent = FXMLLoader.load(resource);
            Parent parent = fxmlLoader.load();
            Stage stage = event.getStage();
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
