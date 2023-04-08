package com.dh.movie.service;


import com.dh.movie.event.MovieCreatedEventProducer;
import com.dh.movie.model.Movie;
import com.dh.movie.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {


    private final MovieRepository movieRepository;
    private final MovieCreatedEventProducer movieCreatedEventProducer;

    public MovieService(MovieRepository movieRepository, MovieCreatedEventProducer movieCreatedEventProducer) {
        this.movieRepository = movieRepository;
        this.movieCreatedEventProducer = movieCreatedEventProducer;
    }

    public List<Movie> findByGenre(String genre) {
        return movieRepository.findByGenre(genre);
    }

    public Movie save(Movie movie) {
        Movie movieCreated = movieRepository.save(movie);
        movieCreatedEventProducer.publishMovieCreatedEvent(movieCreated);
        return movieCreated;
    }
}
