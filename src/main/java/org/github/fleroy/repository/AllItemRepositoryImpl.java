package org.github.fleroy.repository;

import org.apache.lucene.search.Query;
import org.github.fleroy.entity.Item;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.BooleanJunction;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.persistence.EntityManager;
import java.util.List;

public class AllItemRepositoryImpl implements AllItemRepositoryCustom {

	@Autowired
	@Qualifier("entityManagerFactory2")
	private EntityManager entityManager;

	@Override
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
