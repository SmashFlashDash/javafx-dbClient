package org.dbclient.client;

import javafx.application.Application;
import org.dbclient.client.ui.ClientUi;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ClientApp {
    public static void main(String[] args) {
        Application.launch(ClientUi.class, args);
    }
}
