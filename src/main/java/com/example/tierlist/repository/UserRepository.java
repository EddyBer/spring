package com.example.tierlist.repository;

import com.example.tierlist.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {

    Iterable<User> findByEmail(String eamil);
    Iterable<User> findByLogin(String login);
}
