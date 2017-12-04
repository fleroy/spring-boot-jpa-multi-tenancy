package org.github.fleroy.repository;

import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.BooleanJunction;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.github.fleroy.entity.Item;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

public class ItemRepositoryImpl implements ItemRepositoryCustom {

	@Autowired
	@Qualifier("entityManagerFactory")
	private EntityManager entityManager;

	@Override
	@Transactional
	public List<Item> search() {
		FullTextEntityManager fullTextEntityManager =  Search.getFullTextEntityManager(entityManager);
		QueryBuilder qb = fullTextEntityManager.getSearchFactory()
				.buildQueryBuilder()
				.forEntity(Item.class)
				.get();

		BooleanJunction<BooleanJunction> junction = qb.bool();
		junction.must(qb.all().createQuery());

		Query searchQuery = junction.createQuery();

		FullTextQuery persistenceQuery = fullTextEntityManager.createFullTextQuery(searchQuery, Item.class);
		return persistenceQuery.getResultList();
	}
}
