package com.movies.ott.DAO;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.movies.ott.Entity.Movies;


@Repository
public class MovieDAOImpl implements MovieDAO {
	
	@Autowired
	EntityManager entityManager;

	@Override
	public List<Movies> getGenre(String genre) {
		
		Session session=entityManager.unwrap(Session.class);
		 //LOWER(trees.`title`)
		genre=genre.toLowerCase();
		Query<Movies> q=session.createQuery("from Movies where LOWER(genre) LIKE '%"+genre+"%'");
		//q.setParameter("genre", genre);
		List<Movies> l=q.getResultList();
		
		return l;
	}

	@Override
	public List<Movies> getTopRated() {
		
		Session session=entityManager.unwrap(Session.class);
		Query<Movies> q=session.createQuery("from Movies order by rating desc");
		q.setMaxResults(100);
		
		List<Movies> l=q.getResultList();
		
		return l;
	}

	@Override
	public List<Movies> getTrending() {
		Session session=entityManager.unwrap(Session.class);
		Query<Movies> q=session.createQuery("from Movies order by views desc");
		q.setMaxResults(100);
		
		List<Movies> l=q.getResultList();
		
		return l;
	}

	@Override
	public Movies getMovie(int id) {
		
		Session session=entityManager.unwrap(Session.class);

		Movies m=session.get(Movies.class, id);		
		
		return m;
	}

	@Override
	public List<Movies> getAllMovies() {

		Session session=entityManager.unwrap(Session.class);
		
		Query<Movies> q=session.createQuery("from Movies");
		
		return q.getResultList();
	}

}



