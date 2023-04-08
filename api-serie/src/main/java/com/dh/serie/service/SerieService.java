package com.dh.serie.service;

import com.dh.serie.event.SerieCreatedEventProducer;
import com.dh.serie.model.Serie;
import com.dh.serie.repository.SerieRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SerieService {

    private final SerieRepository repository;

    private final SerieCreatedEventProducer serieCreatedEventProducer;


    public SerieService(SerieRepository repository, SerieCreatedEventProducer serieCreatedEventProducer) {
        this.repository = repository;
        this.serieCreatedEventProducer = serieCreatedEventProducer;
    }

    public List<Serie> getAll() {
        return repository.findAll();
    }

    public List<Serie> getSeriesBygGenre(String genre) {
        return repository.findAllByGenre(genre);
    }

    public String create(Serie serie) {
        Serie serieCreated = repository.save(serie);
        serieCreatedEventProducer.publishSerieCreatedEvent(serieCreated);
        return serieCreated.getId();
    }
}
