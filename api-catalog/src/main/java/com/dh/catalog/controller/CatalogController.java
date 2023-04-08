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

	private final CatalogService catalogService;

	public CatalogController(CatalogService catalogService) {
		this.catalogService = catalogService;
	}

	@GetMapping("/movies/{genre}")/*ONLINE*/
	ResponseEntity<List<MovieServiceClient.MovieDto>> getGenre(@PathVariable String genre) {
		return ResponseEntity.ok(catalogService.getMovieOnlineByGenre(genre));
	}

	@GetMapping("/series/{genre}")/*ONLINE*/
	List<SerieServiceClient.SerieDTO> getSerieByGenre(@PathVariable String genre) {
		return catalogService.getSerieOnlineByGenre(genre);
	}

	@GetMapping("/online/{genre}")/*ONLINE*/
	MovieAndSerieDTO getMovieAndSerieOnlineByGenre(@PathVariable String genre){
		List<MovieServiceClient.MovieDto> movies = catalogService.getMovieOnlineByGenre(genre);
		List<SerieServiceClient.SerieDTO> series = catalogService.getSerieOnlineByGenre(genre);
		MovieAndSerieDTO movieAndSerieDTO = new MovieAndSerieDTO(movies,series);
		return movieAndSerieDTO;
	}

	@GetMapping("/offline/{genre}")/*OFFLINE*/
	MovieAndSerieDTO getMovieAndSerieByGenreOffline(@PathVariable String genre){
	List<MovieServiceClient.MovieDto> movies = catalogService.getAllMoviesOfflineByGenre(genre);
	List<SerieServiceClient.SerieDTO> series = catalogService.getAllSeriesOfflineByGenre(genre);
	MovieAndSerieDTO movieAndSerieDTO = new MovieAndSerieDTO(movies,series);
	return movieAndSerieDTO;
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
