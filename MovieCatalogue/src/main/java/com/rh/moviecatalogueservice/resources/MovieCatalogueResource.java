package com.rh.moviecatalogueservice.resources;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.rh.moviecatalogueservice.models.CatalogItem;
import com.rh.moviecatalogueservice.models.Movie;
import com.rh.moviecatalogueservice.models.Rating;
import com.rh.moviecatalogueservice.models.UserRating;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogueResource {

	

	@Autowired
	RestTemplate restTemplate;
	
	@RequestMapping("/{userId}")
	public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {	
		System.out.println("http://ratings-data-service/ratingsdata/user/" + userId);
		UserRating userRating = restTemplate.getForObject("http://ratings-data-service/ratingsdata/user/" + userId, UserRating.class);
		List<CatalogItem> catalogue = userRating.getRatings().stream().map(
				rating -> {
					Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class);					
					return new CatalogItem(movie.getName(), movie.getDescription(), rating.getRating());
				}
				).collect(Collectors.toList());
			
		return catalogue;
	}
}
