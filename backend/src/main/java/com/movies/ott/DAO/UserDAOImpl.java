package com.movies.ott.DAO;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.movies.ott.Entity.User;

@Repository
public class UserDAOImpl implements UserDAO{

	
	@Autowired
	EntityManager entityManager;
	
	@Override
	public boolean createUser(User s) {
		
		//return false if username already exists else returns true by saving in the database
		
		//check if user with this username exists
		Session session=entityManager.unwrap(Session.class);
		Query<User> q=session.createQuery("from User where username=:username");
		q.setParameter("username",s.getUsername());
		List<User> l=q.getResultList();
		if(!l.isEmpty()) {
			return false;
		}
		
		session.save(s);	
		
		return true;
	}

	@Override
	public String getPassword(String username) {
		
		System.out.println("Hola");
		
		
		Session session=entityManager.unwrap(Session.class);
		
		Query<User> q=session.createQuery("from User where username=:username");
		q.setParameter("username",username );
		List<User> l=q.getResultList();
		
		if(l.isEmpty()) {
			return "NA";
		}else {
			return l.get(0).getPassword();
		}
		
	}

	@Override
	public String moviesWatchedByUser(String username) {
		
		Session session=entityManager.unwrap(Session.class);
		
		Query<User> q=session.createQuery("from User where username=:username");
		q.setParameter("username", username);
		
		List<User> l=q.getResultList();
		
		if(l.isEmpty()) {
			return null;
		}
		
		return l.get(0).getMovie();
	}

	@Override
	public void addToWatch(String username, int id) {
		
		Session session=entityManager.unwrap(Session.class);
		
		Query<User> q=session.createQuery("from User where username=:username");
		q.setParameter("username", username);
		
		List<User> l=q.getResultList();
		
		User u=l.get(0);
		u.setMovie(""+id+","+u.getMovie());
		
	}
	
	

}
