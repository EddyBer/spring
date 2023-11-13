package com.example.tierlist.repository;

import com.example.tierlist.entities.Item;
import com.example.tierlist.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface ItemRepository extends CrudRepository<Item, Integer> {
    Iterable<Item> findByUser(User user);

    Iterable<Item> findById(String id);
}
