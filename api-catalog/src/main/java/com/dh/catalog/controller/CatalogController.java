package com.dh.catalog.controller;

import com.dh.catalog.client.MovieServiceClient;

import com.dh.catalog.client.SerieServiceClient;
import com.dh.catalog.service.CatalogService;
import lombok.*;
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

	private final CatalogService catalogService;

	public CatalogController(MovieServiceClient movieServiceClient, SerieServiceClient serieServiceClient, CatalogService catalogService) {
		this.movieServiceClient = movieServiceClient;
		this.serieServiceClient = serieServiceClient;
		this.catalogService = catalogService;
	}

	@GetMapping("/movies/{genre}")
	ResponseEntity<List<MovieServiceClient.MovieDto>> getGenre(@PathVariable String genre) {
		return ResponseEntity.ok(movieServiceClient.getMovieByGenre(genre));
	}

	@GetMapping("/series/{genre}")
	List<SerieServiceClient.SerieDTO> getSerieByGenre(@PathVariable String genre) {
		return serieServiceClient.getSerieByGenre(genre);
	}

	@GetMapping("/{genre}")/*ONLINE*/
	MovieAndSerieDTO getMovieAndSerieByGenre(@PathVariable String genre){
		List<MovieServiceClient.MovieDto> movies = movieServiceClient.getMovieByGenre(genre);
		List<SerieServiceClient.SerieDTO> series = serieServiceClient.getSerieByGenre(genre);
		MovieAndSerieDTO movieAndSerieDTO = new MovieAndSerieDTO(movies,series);
		return movieAndSerieDTO;
	}

	@GetMapping("/offline/{genre}")/*OFFLINE*/
	List<MovieServiceClient.MovieDto> getMovieAndSerieByGenreOffline(@PathVariable String genre){
	return catalogService.getAllMoviesByGenre(genre);
	}

	@Setter
	@Getter
	@AllArgsConstructor
	@NoArgsConstructor
	class	MovieAndSerieDTO{
		private List<MovieServiceClient.MovieDto> movies;
		private List<SerieServiceClient.SerieDTO> series;
	}

}
