package com.dh.catalog.service;

import com.dh.catalog.client.MovieServiceClient;
import com.dh.catalog.client.SerieServiceClient;
import com.dh.catalog.repository.CatalogMovieRepository;
import com.dh.catalog.repository.CatalogSerieRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CatalogService {

    private final CatalogMovieRepository movieRepository;
    private final CatalogSerieRepository serieRepository;

    public CatalogService(CatalogMovieRepository movieRepository, CatalogSerieRepository serieRepository) {
        this.movieRepository = movieRepository;
        this.serieRepository = serieRepository;
    }

    public void createMovie(MovieServiceClient.MovieDto movieDto){
        movieRepository.save(movieDto);
    }

    public void createSerie(SerieServiceClient.SerieDTO serieDTO){
        serieRepository.save(serieDTO);
    }

    public List<MovieServiceClient.MovieDto> getAllMoviesByGenre(String genre) {
        return movieRepository.findAllByGenre(genre);
    }

    public List<SerieServiceClient.SerieDTO> getAllSeriesByGenre(String genre) {
        return serieRepository.findAllByGenre(genre);
    }

}
