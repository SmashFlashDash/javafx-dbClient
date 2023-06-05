package org.dbclient.client.services;

import common.dto.ItemAddDto;
import common.dto.ItemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class WebClientService {
    private final WebClient webClient;
    private final Logger log = Logger.getLogger("WebClientService");
    private String baseURI = "http://localhost:8080";

    public void setBaseURI(String baseURI) {
        this.baseURI = baseURI;
    }

    public boolean checkConnection() {
        try {
            Mono<Object> response = webClient.get().uri(baseURI)
                    .retrieve()
                    .onRawStatus(status -> true, respons -> Mono.empty())
                    .bodyToMono(Object.class);
            response.block();
            return true;
        } catch (WebClientRequestException ex) {
            return false;
        }


    }

    public List<ItemDto> getAllItems() {
        Mono<List<ItemDto>> response = webClient
                .get().uri(baseURI.concat("/api/v1/item/all"))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<ItemDto>>() {
                })
                .doOnSuccess(this::sucess)
                .doOnError(this::error);
        return response.block();
    }

    public ItemDto getItem(Long id) {
        Mono<ItemDto> response = webClient
                .get()
                .uri(uriBuilder ->
                        uriBuilder.path(baseURI + "/api/v1/item{id}").build(id))
                .retrieve()
                .bodyToMono(ItemDto.class)
                .doOnSuccess(this::sucess)
                .doOnError(this::error);
        return response.block();
    }

    public ItemDto addItem(ItemAddDto itemDto) {
        Mono<ItemDto> response = webClient
                .post().uri("/api/v1/item")
                .body(Mono.just(itemDto), ItemDto.class)
                .retrieve()
                .bodyToMono(ItemDto.class)
                .doOnSuccess(this::sucess)
                .doOnError(this::error);
        return response.block();
    }

    public ItemDto editItem(ItemDto itemDto) {
        Mono<ItemDto> response = webClient
                .put().uri(baseURI + "/api/v1/item")
                .body(Mono.just(itemDto), ItemDto.class)
                .retrieve()
                .bodyToMono(ItemDto.class)
                .doOnSuccess(this::sucess)
                .doOnError(this::error);
        return response.block();
    }

    public boolean deleteItem(ItemDto itemDto) {
        try {
            Mono<String> response = webClient
                    .method(HttpMethod.DELETE).uri(baseURI + "/api/v1/item")
                    .body(Mono.just(itemDto), ItemDto.class)
                    .retrieve()
                    .bodyToMono(String.class)
                    .doOnSuccess(this::sucess)
                    .doOnError(this::error);
            response.block();
            return true;
        } catch (RuntimeException ex) {
            return false;
        }
    }

    private <T> void sucess(T responseObj) {
        if (responseObj == null) {
            log.info("sucess return null");
        } else {
            log.info("sucess ".concat(responseObj.toString()));
        }
    }

    private void error(Throwable ex) {
        ex.printStackTrace();
        log.info("error ".concat(ex.getMessage()));
    }
}
