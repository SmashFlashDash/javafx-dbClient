package org.dbclient.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;

@SpringBootApplication
public class ClientAppV2 extends Application {
    //private static final String MAIN = "/main.fxml";
    //private static final String STYLE = "/style.css";

    private ConfigurableApplicationContext context;
    private Parent rootNode;

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void init() throws Exception {
        context = SpringApplication.run(ClientAppV2.class);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Main.fxml"));
        fxmlLoader.setControllerFactory(context::getBean);
        rootNode = fxmlLoader.load();
    }


    @Override
    public void start(Stage stage) {
        stage.setScene(new Scene(rootNode));
        //stage.getScene().getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        stage.show();
    }

    @Override
    public void stop() {
        context.close();
    }
}
