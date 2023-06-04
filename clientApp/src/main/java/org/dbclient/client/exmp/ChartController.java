package org.dbclient.client.exmp;

import common.dto.ItemDto;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lombok.RequiredArgsConstructor;
import org.dbclient.client.webclient.WebClientReactive;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class ChartController {
    // TODO: сделать гуи с кнопками и статус баром
    //

    @FXML
    private AnchorPane MainPane;

    @FXML
    private Button btn;
    @FXML
    void doStuff(MouseEvent event) {
        // TODO: можно делать асинхронный запрос подписавшись используя subscribe().
        // TODO: синхронный запрос
        //  webclient правильно определить в конфиге
        WebClient webClient = WebClient.create();
        Mono<List<ItemDto>> response = webClient
                .get()
                // TODO: брать порт из настроек
                .uri("http://localhost:8080/api/v1/item/all")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<ItemDto>>(){})
                .doOnError(this::error);
        // TODO: получить список и обновить таблицу
        response.block();

        //List<ItemDto> s = webClient.items();
    }

    private void error(Throwable ex) {
        // TODO: ошибку правильно выводит в статус
        ex.printStackTrace();
    }
}
