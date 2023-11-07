package repository;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<entities.User, Integer> {
}
