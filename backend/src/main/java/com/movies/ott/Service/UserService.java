package com.movies.ott.Service;

import com.movies.ott.Entity.User;

public interface UserService {
	
	public boolean createUser(User s);
	
	public String getPassword(String username);
	
	public String moviesWatchedByUser(String username);

	public void addToWatch(String username, int id);

}
