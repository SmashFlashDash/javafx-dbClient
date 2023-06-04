package org.dbclient.client.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;


@Component
@RequiredArgsConstructor
public class StageInit implements ApplicationListener<ClientUi.StageReadyEvent> {
    @Value("/fxml/tabs/chart.fxml")
    private Resource resource;
    @Value("Hello, there")
    private String title;
    private final ConfigurableApplicationContext applicationContext;

    @Override
    public void onApplicationEvent(ClientUi.StageReadyEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(resource.getURL());
            fxmlLoader.setControllerFactory(applicationContext::getBean);
            //Parent parent = FXMLLoader.load(resource);
            Parent parent = fxmlLoader.load();
            Stage stage = event.getStage();
            stage.setScene(new Scene(parent));
            stage.setTitle(title);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
