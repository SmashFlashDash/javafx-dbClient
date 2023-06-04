package org.dbclient.client.exmp;

import javafx.scene.control.TextArea;
import org.dbclient.client.exmp.LoggerTabController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TabPaneManger {

    private final LoggerTabController loggerTabController;

    @Autowired
    public TabPaneManger( LoggerTabController loggerTabController) {
        this.loggerTabController = loggerTabController;
    }

    public TextArea getVisualLog() {
        return loggerTabController.getLoggerTxtArea();
    }

}
