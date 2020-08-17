package com.movies.ott.Service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.movies.ott.DAO.UserDAO;
import com.movies.ott.Entity.User;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	UserDAO userDAO;

	@Override
	@Transactional
	public boolean createUser(User s) {
		
		//return true if created succefully else return false
		
		boolean result=userDAO.createUser(s);
		
		return result;
		
	}

	@Override
	@Transactional
	public String getPassword(String username) {
	
		return userDAO.getPassword(username);
		
	}

	@Override
	@Transactional
	public String moviesWatchedByUser(String username) {

		String names=userDAO.moviesWatchedByUser(username);
		return names;
	}

	@Override
	@Transactional
	public void addToWatch(String username, int id) {
		userDAO.addToWatch(username,id);
		
	}
	
	

}
