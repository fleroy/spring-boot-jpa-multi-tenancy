package org.github.fleroy.repository;

import org.springframework.data.repository.CrudRepository;
import org.github.fleroy.entity.Item;

public interface ItemRepository extends CrudRepository<Item, Long>, ItemRepositoryCustom {
}
