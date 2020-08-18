package com.movies.ott.Service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.movies.ott.DAO.IDFDAO;
import com.movies.ott.DAO.MovieDAO;
import com.movies.ott.Entity.Movies;


@Service
public class MovieServiceImpl implements MovieService {
	
	@Autowired
	MovieDAO movieDAO;
	
	@Autowired
	UserService userService;
	
	@Autowired
	IDFDAO idfDAO;

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

	@Override
	@Transactional
	public List<Movies> getRecommendedMovies(String username) {
		
		
		//load stopWords in a list for checking later
		List<String> stopWord=new ArrayList<>();
		BufferedReader br=null;
		try {
			br = new BufferedReader(new FileReader("stopWords.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String str="";
		try {
			while((str=br.readLine())!=null) {
				stopWord.add(str);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		//first fetch movies that this perticular user has in watched
		String movieString=userService.moviesWatchedByUser(username);
		if(movieString==null) {
			return null;
		}
		
		
		//now get details of all the movies watched by the user
		String[] ids=movieString.split(",");
		List<Movies> mov=new ArrayList<>();
		for(int i=0;i<ids.length;i++) {
			
			int id=Integer.parseInt(ids[i]);
			
			Movies m=movieDAO.getMovie(id);
			mov.add(m);
			
		}
		

		//now form a final query using plot of the all movies 
		String query="";
		for(Movies m:mov) {
			query+=m.getPlot();
			
		}
		//now iterate through each word of the query and find all the movies which contains that particular word
		String[] queryWords=query.split("\\W+");
		List<Movies> requiredMovies=new ArrayList<>();
		List<Movies> l=null;
		int c=0;
		for(int i=0;i<queryWords.length;i++) {
			
			
			String lower=queryWords[i].toLowerCase();
			if(stopWord.contains(lower)) {
				continue;
			}
			
			l=movieDAO.getMoviesByKeyword(queryWords[i]);
			
			for(Movies m2:l) {
				
				//we can filter only those movies which have potential
				int count=0;
				String[] w=m2.getPlot().split("\\W+");
				
				List<String> mem=new ArrayList<>();
				
				for(int j=0;j<w.length;j++) {
					//System.out.println(w[j]);
					
					if(mem.contains(w[j])) {
						continue;
					}
					mem.add(w[j]);
					
					for(int k=0;k<queryWords.length;k++) {
						if(w[j].equalsIgnoreCase(queryWords[k])) {
							count++;
						}
					}
					
				}
				if(!(count>25)) {
					continue;
				}
				
				if(!requiredMovies.contains(m2)) {
					c++;
					requiredMovies.add(m2);
				}
			}
			
		}
		System.out.println("Total movies "+c);
		
		
		//first calculate total occurrences of each word in query
		HashMap<String, Integer> tf=new HashMap<>();
		
		for(int i=0;i<queryWords.length;i++) {
			
			if(tf.containsKey(queryWords[i])){
				int value=tf.get(queryWords[i]);
				value++;
				tf.put(queryWords[i], value);
			}else {
				tf.put(queryWords[i], 1);
			}
			
		}
		
		//calculate tf-idf score of each word in query and store it
		//as tf contain all the unique words so iterate through that only
		
		HashMap<String, Integer> memoizeOccurrences=new HashMap<>();
		
		System.out.println("Start");
		HashMap<String, Float> queryScore=new HashMap<>();
		
		for (Map.Entry<String,Integer> entry : tf.entrySet())  {
			
			if(memoizeOccurrences.containsKey(entry.getKey())) {
				float score=(float) (entry.getValue()*(Math.log(4157)/memoizeOccurrences.get(entry.getKey())));//there are 4157 movies
				queryScore.put(entry.getKey(),score);
			}
			
			else{
				int occurrences=idfDAO.getOccurrences(entry.getKey());//idf get occurences of each word
				
				//calculate score
				float score=(float) (entry.getValue()*(Math.log(4157)/occurrences));//there are 4157 movies
				queryScore.put(entry.getKey(),score);
				memoizeOccurrences.put(entry.getKey(), occurrences);
			}
		}		
		System.out.println("end");

		
		//now required movies contains all the movies which have 
		//one or more query words in it
		//now iterate movies in required movies one by one 
		//and using vector space model decide the score
		
		
		HashMap<Movies,Float> resultSet= new HashMap<>();
		c=0;
		for(Movies m2:requiredMovies) {
			
		
			//first calculate total occurrences of each word in movie plot
			HashMap<String, Integer> tfM=new HashMap<>();
			
			String movieWord[]=m2.getPlot().split("\\W+");
			
			for(int i=0;i<movieWord.length;i++) {
				
				if(tfM.containsKey(queryWords[i])){
					int value=tfM.get(queryWords[i]);
					value++;
					tfM.put(queryWords[i], value);
				}else {
					tfM.put(queryWords[i], 1);
				}
				
			}
			
			//calculate tf-idf score of each word in movie plot and store it
			//as tfM contain all the unique words so iterate through that only
			HashMap<String, Float> movieScore=new HashMap<>();
			
			for (Map.Entry<String,Integer> entry : tfM.entrySet())  {
				
				if(memoizeOccurrences.containsKey(entry.getKey())) {
					float score=(float) (entry.getValue()*(Math.log(4157)/memoizeOccurrences.get(entry.getKey())));//there are 4157 movies
					movieScore.put(entry.getKey(),score);
				}
				
				int occurrences=idfDAO.getOccurrences(entry.getKey());//idf get occurences of each word
				//calculate score
				float score=(float) (entry.getValue()*(Math.log(4157)/occurrences));//there are 4157 movies
				movieScore.put(entry.getKey(),score);
			}	
			
			//now movieScore and queryScore contains all the words
			//with their tf-idf score
			//now use vector space model to calculate score between the movie
			
			
			//iterate queryScore to calculate denominator
			double value1=0;
			for (Map.Entry<String,Float> entry : queryScore.entrySet())  {
				value1+=entry.getValue()*entry.getValue();
				
			}	
			value1=Math.sqrt(value1);
			
			//iterate movieScore to calculate denominator
			double value2=0;
			for (Map.Entry<String,Float> entry : movieScore.entrySet())  {
				value2+=entry.getValue()*entry.getValue();
				
			}	
			value2=Math.sqrt(value2);
			
			//now iterate movieScore for nominator
			float nom=0;
			for (Map.Entry<String,Float> entry : movieScore.entrySet())  {
				
				nom+=queryScore.get(entry.getKey())*entry.getValue();
				
			}	
			float result=(float) (nom/(value1*value2));
			resultSet.put(m2, result);	
			c++;
			System.out.println(result+" "+c);
			
		}
		
		
		HashMap<Movies, Float> r=sortByValue(resultSet);
		List<Movies> list=new ArrayList<>();
		
		for (Map.Entry<Movies,Float> entry : r.entrySet())  {
			list.add(entry.getKey());
			
		}		
		
		return list;
	}
	
	public static HashMap<Movies, Float> sortByValue(HashMap<Movies, Float> hm) 
    { 
        // Create a list from elements of HashMap 
        List<Map.Entry<Movies, Float> > list = 
               new LinkedList<Map.Entry<Movies, Float> >(hm.entrySet()); 
  
        // Sort the list 
        Collections.sort(list, new Comparator<Map.Entry<Movies, Float> >() { 
            public int compare(Map.Entry<Movies, Float> o1,  
                               Map.Entry<Movies, Float> o2) 
            { 
                return (o1.getValue()).compareTo(o2.getValue()); 
            } 
        }); 
          
        // put data from sorted list to hashmap  
        HashMap<Movies, Float> temp = new LinkedHashMap<Movies, Float>(); 
        for (Map.Entry<Movies, Float> aa : list) { 
            temp.put(aa.getKey(), aa.getValue()); 
        } 
        return temp; 
    } 
	
	

}
