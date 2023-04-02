package com.dh.catalog.controller;

import com.dh.catalog.client.MovieServiceClient;

import com.dh.catalog.client.SerieServiceClient;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/catalog")
public class CatalogController {

	private final MovieServiceClient movieServiceClient;
	private final SerieServiceClient serieServiceClient;

	public CatalogController(MovieServiceClient movieServiceClient, SerieServiceClient serieServiceClient) {
		this.movieServiceClient = movieServiceClient;
		this.serieServiceClient = serieServiceClient;
	}

	/*@GetMapping("/{genre}")
	ResponseEntity<List<MovieServiceClient.MovieDto>> getGenre(@PathVariable String genre) {
		return ResponseEntity.ok(movieServiceClient.getMovieByGenre(genre));
	}*/

	@GetMapping("/{genre}")
	MovieSerieDTO getSerieByGenre(@PathVariable String genre) {
		List<MovieServiceClient.MovieDto> movie =  movieServiceClient.getMovieByGenre(genre);
		List<SerieServiceClient.SerieDTO> serie = serieServiceClient.getSeriesByGenre(genre);
		MovieSerieDTO movieSerieDTO = new MovieSerieDTO(movie,serie);
		return movieSerieDTO;
	}

	@Setter
	@Getter
	@Builder
	static class MovieSerieDTO{
		private List<MovieServiceClient.MovieDto> movies;
		private List<SerieServiceClient.SerieDTO> series;
	}

}
