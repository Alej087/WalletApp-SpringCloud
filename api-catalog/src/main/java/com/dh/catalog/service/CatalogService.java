package com.dh.catalog.service;

import com.dh.catalog.client.MovieServiceClient;
import com.dh.catalog.client.SerieServiceClient;
import com.dh.catalog.repository.CatalogMovieRepository;
import com.dh.catalog.repository.CatalogSerieRepository;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
@Slf4j
public class CatalogService {

    private final MovieServiceClient movieServiceClient;

    private final SerieServiceClient serieServiceClient;
    private final CatalogMovieRepository movieRepository;
    private final CatalogSerieRepository serieRepository;

    public CatalogService(MovieServiceClient movieServiceClient, SerieServiceClient serieServiceClient, CatalogMovieRepository movieRepository, CatalogSerieRepository serieRepository) {
        this.movieServiceClient = movieServiceClient;
        this.serieServiceClient = serieServiceClient;
        this.movieRepository = movieRepository;
        this.serieRepository = serieRepository;
    }

    public void createMovie(MovieServiceClient.MovieDto movieDto){
        movieRepository.save(movieDto);
    }

    public void createSerie(SerieServiceClient.SerieDTO serieDTO){
        serieRepository.save(serieDTO);
    }

    public List<MovieServiceClient.MovieDto> getAllMoviesOfflineByGenre(String genre) {
        return movieRepository.findAllByGenre(genre);
    }

    public List<SerieServiceClient.SerieDTO> getAllSeriesOfflineByGenre(String genre) {
        return serieRepository.findAllByGenre(genre);
    }

    /* Se selecciona este microservicio, debido a que todas las consultas que realizamos llegan por medio de este, el cual a su vez envía consultas
    a los microservicios de movies y series, por lo cual, si en algun momento estos 2 microservicios no responden, lo que se hace es que catalog
    consulte con su metodo fallback a la base de datos offline, arrojando al menos los registros que ya tiene en su base de datos actualmente por el
    momento */

    @Retry(name="catalogOnline")
    @CircuitBreaker(name = "catalogOnline", fallbackMethod = "getMovieOnlineByGenreFallback")
    public List<MovieServiceClient.MovieDto> getMovieOnlineByGenre(String genre){
        return movieServiceClient.getMovieByGenre(genre);
    };

    public List<MovieServiceClient.MovieDto> getMovieOnlineByGenreFallback(String genre, Throwable ex) throws Exception{
        log.info(ex.getMessage());
        return movieRepository.findAllByGenre(genre);
    };

    @Retry(name="catalogOnline")
    @CircuitBreaker(name = "catalogOnline", fallbackMethod = "getSerieOnlineByGenreFallback")
    public List<SerieServiceClient.SerieDTO> getSerieOnlineByGenre(String genre) {
        return serieServiceClient.getSerieByGenre(genre);
    };

    public List<SerieServiceClient.SerieDTO> getSerieOnlineByGenreFallback(String genre, Throwable ex) throws Exception{
        log.info(ex.getMessage());
        return serieRepository.findAllByGenre(genre);
    };

}
