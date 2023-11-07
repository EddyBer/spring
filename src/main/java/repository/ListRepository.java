package repository;

import org.springframework.data.repository.CrudRepository;

public interface ListRepository extends CrudRepository<entities.List, Integer> {
}
