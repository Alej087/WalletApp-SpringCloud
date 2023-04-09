package com.dh.catalog.client;

import com.dh.catalog.config.LoadBalanceConfiguration;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name="movie-service")
@LoadBalancerClient(value="movie-service",configuration = LoadBalanceConfiguration.class)
public interface MovieServiceClient {

	@GetMapping("/api/v1/movies/{genre}")
	List<MovieDto> getMovieByGenre(@PathVariable (value = "genre") String genre);


	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	@Document(collection = "Movies")
	class MovieDto{
		@Id
		private Long id;

		private String name;

		private String genre;

		private String urlStream;
	}

}
