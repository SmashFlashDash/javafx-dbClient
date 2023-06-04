package org.dbclient.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ClientApp extends Application {
    private ConfigurableApplicationContext context;
    private Parent rootNode;

    // TODO:
    //  сервисы: setting, getData, editData
    //      settings - меняет порт webclient
    //      getData - статсут бар грузится, пока идут данные
    //      поидее нужно блокировать остальные запросы если последний не выполнен?
    //  config: webClient с настрйоками
    //  controllrs:
    //      основное меню с таблицей,
    //      второй - всплывающее окно редактировать
    //      всплывающее окно hbox с полями и кнопками сохранить отменить

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void init() throws Exception {
        context = bootSpring();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Main.fxml"));
        fxmlLoader.setControllerFactory(context::getBean);
        rootNode = fxmlLoader.load();
    }


    @Override
    public void start(Stage stage) {
        stage.setScene(new Scene(rootNode));
        stage.show();
    }

    @Override
    public void stop() {
        context.close();
    }

    private ConfigurableApplicationContext bootSpring() {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(ClientApp.class);
        String[] args = getParameters().getRaw().stream().toArray(String[]::new);
        return builder.run(args);
    }
}
