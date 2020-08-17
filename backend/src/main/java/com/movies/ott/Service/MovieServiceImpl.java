package com.movies.ott.Service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.movies.ott.DAO.MovieDAO;
import com.movies.ott.Entity.Movies;


@Service
public class MovieServiceImpl implements MovieService {
	
	@Autowired
	MovieDAO movieDAO;
	
	@Autowired
	UserService userService;

	@Override
	@Transactional
	public List<Movies> getGenre(String genre) {
		
		return movieDAO.getGenre(genre);
	}

	@Override
	@Transactional
	public List<Movies> getTopRated() {
		
		return movieDAO.getTopRated();
		
	}

	@Override
	@Transactional
	public List<Movies> getTrending() {
		
		return movieDAO.getTrending();
		
	}

	@Override
	@Transactional
	public List<Movies> getHistory(String username) {

		String movieString=userService.moviesWatchedByUser(username);
		if(movieString==null) {
			return null;
		}
		
		//get Movies
		String[] ids=movieString.split(",");
		
		
		List<Movies> mov=new ArrayList<>();
		
		for(int i=0;i<ids.length;i++) {
			
			int id=Integer.parseInt(ids[i]);
			
			Movies m=movieDAO.getMovie(id);
			mov.add(m);
			
		}
		
		return mov;
		
	}

	@Override
	@Transactional
	public List<Movies> getAllMovies() {
		
		return movieDAO.getAllMovies();
		
	}
	
	

}
