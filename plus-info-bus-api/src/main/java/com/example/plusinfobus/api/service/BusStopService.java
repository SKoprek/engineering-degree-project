package com.example.plusinfobus.api.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.plusinfobus.api.model.BusStop;

@Repository
@Transactional
public class BusStopService {

	
	@PersistenceContext
	  private EntityManager entityManager;
	  


	  
	  public List<BusStop> search(String text) {
		    FullTextEntityManager fullTextEntityManager =
		            org.hibernate.search.jpa.Search.
		            getFullTextEntityManager(entityManager);
		        
		        // create the query using Hibernate Search query DSL
		        QueryBuilder queryBuilder = 
		            fullTextEntityManager.getSearchFactory()
		            .buildQueryBuilder().forEntity(BusStop.class).get();
		        
		        
		        Query luceneQuery = queryBuilder
//		        		.keyword().fuzzy().withEditDistanceUpTo(2).withPrefixLength(0).onFields("name")
//		                .matching(text).createQuery();
		        		.keyword()
		        		  .wildcard()
		        		  .onField("stopName")
		        		  .matching("*"+text+"*")
		        		  .createQuery();
		        // a very basic query by keywords
//		        Query query =
//		            queryBuilder
//		              .keyword()
//		              .onFields("name")
//		              .matching(text)
//		              .createQuery();

		        // wrap Lucene query in an Hibernate Query object
		        FullTextQuery jpaQuery =
		            fullTextEntityManager.createFullTextQuery(luceneQuery, BusStop.class);
		      
		        // execute search and return results (sorted by relevance as default)
		        @SuppressWarnings("unchecked")
		        List<BusStop> results =  jpaQuery.getResultList();
		        
		        return results;
	  }
	
}
