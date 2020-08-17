package com.movies.ott.Service;

import java.util.List;

import com.movies.ott.Entity.Movies;

public interface MovieService {
	
	public List<Movies> getGenre(String genre);
	
	public List<Movies> getTopRated();//gives top 100 movies
	
	public List<Movies> getTrending();//gives 6 movies
	
	public List<Movies> getHistory(String username);//returns movie list or null
	
	public List<Movies> getAllMovies();

}
