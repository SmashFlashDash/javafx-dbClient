package org.dbclient.client.services;

import common.dto.ItemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class DataService {
    private final WebClient webClient;
    private final Logger log = Logger.getLogger(this.getClass().getName());

    public List<ItemDto> getAllItems() {
        Mono<List<ItemDto>> response = webClient
                .get().uri("/api/v1/item/all")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<ItemDto>>() {
                })
                .doOnSuccess(this::sucess)
                .doOnError(this::error);
        return response.block();
    }

    public ItemDto getItem(Long id) {
        Mono<ItemDto> response = webClient
                .get().uri("/api/v1/item/all")
                .retrieve()
                .bodyToMono(ItemDto.class)
                .doOnSuccess(this::sucess)
                .doOnError(this::error);
        return response.block();
    }

    public ItemDto addItem(ItemDto itemDto) {
        Mono<ItemDto> response = webClient
                .post().uri("/api/v1/item/all")
                .body(Mono.just(itemDto), ItemDto.class)
                .retrieve()
                .bodyToMono(ItemDto.class)
                //.onErrorMap(e -> new MyException("messahe",e))
                .doOnSuccess(this::sucess)
                .doOnError(this::error);
        return response.block();
    }

    public ItemDto editItem(ItemDto itemDto) {
        Mono<ItemDto> response = webClient
                .put().uri("/api/v1/item/all")
                .body(Mono.just(itemDto), ItemDto.class)
                .retrieve()
                .bodyToMono(ItemDto.class)
                //.onErrorMap(e -> new MyException("messahe",e))
                .doOnSuccess(this::sucess)
                .doOnError(this::error);
        return response.block();
    }

    public ItemDto deleteItem(ItemDto itemDto) {
        // TODO: проверить выполнение
        // можо поменять на get
        Mono<ItemDto> response = webClient
                .method(HttpMethod.DELETE).uri("/api/v1/item/all")
                .body(Mono.just(itemDto), ItemDto.class)
                .retrieve()
                .bodyToMono(ItemDto.class)
                //.onErrorMap(e -> new MyException("messahe",e))
                .doOnSuccess(this::sucess)
                .doOnError(this::error);
        return response.block();
    }

    private void onStart() {
        // TODO: блочить кнопки и показать статус что выполняется запрос
        //  блочить можно перед response block
    }

    private <T> void sucess(T responseObj) {
        // TODO: разблочить кнопки, и показать стасту успешно
        log.info("sucess ".concat(responseObj.toString()));
    }

    private void error(Throwable ex) {
        // TODO: разблочить кнопки, показать в статусе ошибку
        ex.printStackTrace();
        log.info("error ".concat(ex.getMessage()));
    }
}
