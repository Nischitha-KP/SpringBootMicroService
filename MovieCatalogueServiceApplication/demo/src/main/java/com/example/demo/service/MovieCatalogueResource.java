package com.example.demo.service;

import com.example.demo.model.CatalogItem;
import com.example.demo.model.Movie;
import com.example.demo.model.Rating;
import com.example.demo.model.UserRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogueResource {

    @Autowired
    private RestTemplate restTemplate;
    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId){
//        RestTemplate restTemplate = new RestTemplate();
        UserRating ratings =  restTemplate.getForObject("http://ratings-service/ratingsdata/users/"+
                    userId, UserRating.class);
            return ratings.getUserRating().stream()
                    .map(rating -> {
            Movie movie = restTemplate.getForObject("http://movie-info-service/movies/"+
                    rating.getMovieId(), Movie.class);
            return new CatalogItem(movie.getName(), "Test", rating.getRating());
        }).collect(Collectors.toList());



//        return ratings.stream().map(rating ->{
//            System.out.println("Fetching details for movie ID: " + rating.getMovieId());
//            Movie movie = builder.build()
//                    .get()
//                    .uri("http://localhost:8082/movies/"+ rating.getMovieId())
//                    .retrieve()
//                    .bodyToMono(Movie.class)
//                    .doOnError(error -> System.out.println("ERROR: " + error.getMessage()))
//                    .onErrorReturn(new Movie("Unknown", "Movie not found"))
//                    .block();
//
//            return new CatalogItem(movie.getName(), "Test", rating.getRating());
//        }).collect(Collectors.toList());

    }

}
