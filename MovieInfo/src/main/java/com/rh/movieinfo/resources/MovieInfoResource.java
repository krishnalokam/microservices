package com.rh.movieinfo.resources;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.rh.movieinfo.models.Movie;
import com.rh.movieinfo.models.MovieSummary;

@RestController
@RequestMapping("/movies")
public class MovieInfoResource {

	@Value("${api.key}")
	private String apiKey;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@RequestMapping("/{movieId}")
	public Movie getMovieInfo(@PathVariable("movieId") String movieId) {
		MovieSummary movieSummary;
		try {
			movieSummary = restTemplate.getForObject("https://api.themoviedb.org/3/movie/" + movieId + "?api_key=" +  apiKey, MovieSummary.class);
		}catch(Exception e) {
			e.printStackTrace();
			movieSummary = new MovieSummary();
			movieSummary.setTitle("Bla Bla Bla ");
			movieSummary.setOverview("Bla Bla Bla");
		}
		
//		return Collections.singletonList(new Movie(1,"Inception"));
		return new Movie(movieId,movieSummary.getTitle(),movieSummary.getOverview());
	}
}
