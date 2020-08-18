package com.movies.ott.DAO;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.movies.ott.Entity.IDF;


@Repository
public class IDFDAOImpl implements IDFDAO{
	
	@Autowired
	EntityManager entityManager;

	@Override
	public int getOccurrences(String word) {

		word=word.toLowerCase();
		Session session=entityManager.unwrap(Session.class);
		Query<IDF> q=session.createQuery("from IDF where word=:word");
		q.setParameter("word", word);
		List<IDF> l=q.getResultList();
		
		return l.get(0).getDocuments();
	}

}
