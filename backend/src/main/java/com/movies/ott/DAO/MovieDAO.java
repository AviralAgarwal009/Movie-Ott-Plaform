package com.movies.ott.DAO;

import java.util.List;

import com.movies.ott.Entity.Movies;

public interface MovieDAO {
	
	public List<Movies> getGenre(String genre);
	
	public List<Movies> getTopRated();
	
	public List<Movies> getTrending();
	
	public Movies getMovie(int id);
	
	public List<Movies> getAllMovies();

	public List<Movies> getMoviesByKeyword(String string);
}
