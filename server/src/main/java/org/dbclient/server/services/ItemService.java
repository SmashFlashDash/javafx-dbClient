package org.dbclient.server.services;

import common.data.Item;
import common.dto.ItemAddDto;
import common.dto.ItemDto;
import lombok.RequiredArgsConstructor;
import org.dbclient.server.services.repositories.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;


    public Item add(ItemAddDto itemRequest) {
        Item item = Item.builder()
                .category(itemRequest.getCategory())
                .price(itemRequest.getPrice())
                .amount(itemRequest.getAmount()).build();
        return itemRepository.save(item);
    }

    public Item getById(Long id) {
        return itemRepository.findById(id).orElseThrow();
    }

    public List<Item> getAll() {
        return itemRepository.findAll();
    }

    public Item update(ItemDto itemDto) {
        return itemRepository.findById(itemDto.getId()).map(item -> {
            item.setAmount(itemDto.getAmount());
            item.setCategory(itemDto.getCategory());
            item.setPrice(itemDto.getPrice());
            item.setName(itemDto.getName());
            return itemRepository.save(item);
        }).orElseThrow();
    }

    public void delete(ItemDto itemDto) {
        itemRepository.deleteById(itemDto.getId());
    }


}
