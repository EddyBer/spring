package com.example.tierlist.repository;

import com.example.tierlist.entities.Ranks;
import org.springframework.data.repository.CrudRepository;

public interface RanksRepository extends CrudRepository<Ranks, Integer> {

    Iterable<Ranks> findByList(com.example.tierlist.entities.List list);

    Iterable<Ranks> findByListId(Integer id);
}
