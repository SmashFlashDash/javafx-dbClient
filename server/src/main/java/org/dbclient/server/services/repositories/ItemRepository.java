package org.dbclient.server.services.repositories;

import org.dbclient.server.data.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findAllByName(String name);

    //не сработает @UpdateTimestamp
    //@Modifying
    //@Query("update Item u set u.price = 2.2 where u.id = 1")
    //Item updateItem(Long id, String name, ItemCategory category, Float price, Integer amount);
}
