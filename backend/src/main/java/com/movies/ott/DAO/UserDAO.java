package com.movies.ott.DAO;

import com.movies.ott.Entity.User;

public interface UserDAO {
	
	public boolean createUser(User s);

	public String getPassword(String username);

	public String moviesWatchedByUser(String username);

	public void addToWatch(String username, int id);
}
