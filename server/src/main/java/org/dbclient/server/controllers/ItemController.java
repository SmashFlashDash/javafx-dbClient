package org.dbclient.server.controllers;

import common.ItemDto;
import lombok.RequiredArgsConstructor;
import org.dbclient.server.services.ItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/item")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @PostMapping
    public ResponseEntity<String> add(@RequestBody ItemDto registerDto) {
        if (itemService.add(registerDto)) {
            return ResponseEntity.ok("");
        }
        return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);
    }

    @GetMapping
    public ResponseEntity<ItemDto> get() {
        return ResponseEntity.ok(new ItemDto());
    }


    @PutMapping
    public ResponseEntity<ItemDto> update(@RequestBody ItemDto test) {
        return ResponseEntity.ok(new ItemDto());
    }

    @DeleteMapping
    public void delete() {
        itemService.example();
    }

    //@GetMapping("/search")
    //public ResponseEntity<List<TestDto>> find(TestDto test, PageDto page) {
    //    return ResponseEntity.ok(List.of(new TestDto()));
    //}
}
