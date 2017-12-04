package org.github.fleroy.repository;

import org.github.fleroy.entity.Item;
import org.springframework.data.repository.CrudRepository;

public interface AllItemRepository extends CrudRepository<Item, Long>, AllItemRepositoryCustom {
}
