package com.dh.catalog.repository;

import com.dh.catalog.client.MovieServiceClient;
import com.dh.catalog.client.SerieServiceClient;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CatalogSerieRepository extends MongoRepository<SerieServiceClient.SerieDTO, String> {

    List<SerieServiceClient.SerieDTO> findAllByGenre(String genre);
}
