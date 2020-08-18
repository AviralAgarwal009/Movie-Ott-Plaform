package com.movies.ott.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.movies.ott.Entity.Movies;
import com.movies.ott.Service.MovieService;
import com.movies.ott.Service.UserService;

@RestController
public class MovieController {

	@Autowired
	MovieService movieService;
	
	@Autowired
	UserService userService;
	
	@GetMapping("/movies/genre")
	private @ResponseBody List<Movies> getGenre(@RequestParam("genre") String genre){
		
		
		return movieService.getGenre(genre);
		
	}
	
	@GetMapping("/movies/rated")
	private List<Movies> getTopRated(){
		
		return movieService.getTopRated();
		
	}
	

	@GetMapping("/movies/trending")
	private List<Movies> getTrending(){
		
		return movieService.getTrending();
		
	}
	
	
	@GetMapping("/movies/history")
	private @ResponseBody List<Movies> getWatchHistory(@RequestParam("username")String username){
		
		System.out.println(username);
	
		List<Movies> l= movieService.getHistory(username);//return null if no user watch history
		
		return l;
	}
	
	@GetMapping("/movies/all")
	private List<Movies> getAllMovies(){
		
		return movieService.getAllMovies();
		
	}
	
	
	@GetMapping("/movies/recommend")
	private @ResponseBody List<Movies> getRecommendedMovies(@RequestParam("username")String username){
		
		return movieService.getRecommendedMovies(username);
		
		
	}
	
	
	
	@PutMapping("/movies/watched")
	private @ResponseBody String addToUsersWatchHistory(@RequestParam("username")String username,@RequestParam("movieId") int id) {
		
		
		userService.addToWatch(username,id);
		return "successful";
		
		
	}
	
}





