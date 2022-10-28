package io.Love2Code;

import io.Love2Code.Entity.CatalogItem;
import io.Love2Code.Entity.Movie;
import io.Love2Code.Entity.Rating;
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
public class MovieCatalogResource {

    @Autowired
    private RestTemplate restTemplate;
    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId){
            // get all rated movie IDs



               List<Rating> ratings = Arrays.asList(
                       new Rating("1234",4),
                       new Rating("5678",3)
               );

              return ratings.stream().map(rating ->{
                  Movie movie =   restTemplate.getForObject("http://localhost:8081/movies/transformers"+rating.getMovieId(), Movie.class);
                  return new CatalogItem(movie.getName(),"Description", rating.getRating());
              }).collect(Collectors.toList());
            // for each movie ID, Call movie info service and get details
            // put them all together
    }
}
