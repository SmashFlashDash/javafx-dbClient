package org.dbclient.server.services;

import common.ItemDto;
import lombok.RequiredArgsConstructor;
import org.dbclient.server.services.repositories.ItemRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    public void example() {

    }

    public boolean add(ItemDto registerDto) {
        return true;
    }
}
