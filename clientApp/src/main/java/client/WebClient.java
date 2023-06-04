package client;

import common.data.Item;
import common.dto.ItemAddDto;
import reactor.core.publisher.Flux;

public class WebClient {
    public Flux<Item> items(String message) {
        ItemAddDto s = new ItemAddDto();
        return null;
    }
}
