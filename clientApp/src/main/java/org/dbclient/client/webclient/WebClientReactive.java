package org.dbclient.client.webclient;

import common.dto.ItemDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.logging.Logger;

//@RequestMapping("api/v1/item")
public class WebClientReactive {
    private final WebClient webClient;
    private final Logger log;

    public WebClientReactive(WebClient webClient) {
        this.webClient = WebClient.create();
        this.log = Logger.getLogger(this.getClass().getName());
    }

    @GetMapping(value = "/all")
    public List<ItemDto> items() {
        log.info("Starting NON-BLOCKING Controller!");
        Mono<List<ItemDto>> response = webClient
                .get()
                // TODO: брать порт из настроек
                .uri("http://localhost:8888/api/v1/item/all")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<ItemDto>>() {
                });
        return response.block();
    }
}
