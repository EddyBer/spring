package com.example.tierlist.repository;

import com.example.tierlist.entities.List;
import org.springframework.data.repository.CrudRepository;

public interface ListRepository extends CrudRepository<List, Integer> {
    Iterable<List> findByUser(com.example.tierlist.entities.User user);

}
