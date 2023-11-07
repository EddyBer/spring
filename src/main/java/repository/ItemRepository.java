package repository;

import org.springframework.data.repository.CrudRepository;

public interface ItemRepository extends CrudRepository<entities.Item, Integer> {
}
