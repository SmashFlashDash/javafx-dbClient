package org.dbclient.server.services.repositories;

import org.dbclient.server.data.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
