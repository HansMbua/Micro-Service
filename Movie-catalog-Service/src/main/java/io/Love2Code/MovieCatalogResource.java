package io.Love2Code;

import io.Love2Code.Entity.CatalogItem;
import io.Love2Code.Entity.Movie;
import io.Love2Code.Entity.Rating;
import io.Love2Code.Entity.UserRatings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private WebClient.Builder webClientBuilder;

    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {
        // get all rated movie IDs
        UserRatings ratings = restTemplate.getForObject("http://rating-Data-service/ratingsdata/user/"+userId, UserRatings.class);

      return   ratings.getUserRating().stream().map(rating -> {
          // for each movie ID, Call movie info service and get details
            Movie movie = restTemplate.getForObject("http://movie-info-service/movies/transformers" + rating.getMovieId(), Movie.class);
          // put them all together
            return new CatalogItem(movie.getName(), "Description", rating.getRating());
        }).collect(Collectors.toList());

    }
}


  /*
      Movie movie = webClientBuilder.build()
                          .get()
                          .uri("http://localhost:8081/movies/transformers" + rating.getMovieId())
                          .retrieve()
                          .bodyToMono(Movie.class)
                          .block();

  */