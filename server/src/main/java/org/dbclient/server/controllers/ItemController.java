package org.dbclient.server.controllers;

import common.dto.ItemAddDto;
import common.dto.ItemDto;
import lombok.RequiredArgsConstructor;
import org.dbclient.server.data.Item;
import org.dbclient.server.services.ItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/item")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @PostMapping
    public ResponseEntity<Item> add(@RequestBody ItemAddDto itemDto) {
        return ResponseEntity.ok(itemService.add(itemDto));
    }

    @GetMapping
    public ResponseEntity<Item> get(@RequestParam Long id) {
        return ResponseEntity.ok(itemService.getById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Item>> getAll() {
        return ResponseEntity.ok(itemService.getAll());
    }

    @PutMapping
    public ResponseEntity<Item> update(@RequestBody ItemDto item) {
        return ResponseEntity.ok(itemService.update(item));
    }

    @DeleteMapping
    public ResponseEntity<String> delete(@RequestBody ItemDto itemDto) {
        itemService.delete(itemDto);
        return ResponseEntity.ok("");
    }

    //@GetMapping("/search")
    //public ResponseEntity<List<TestDto>> find(TestDto test, PageDto page) {
    //    return ResponseEntity.ok(List.of(new TestDto()));
    //}
}
