package com.dh.catalog.event;

import com.dh.catalog.client.MovieServiceClient;
import com.dh.catalog.config.RabbitMQConfig;
import com.dh.catalog.service.CatalogService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class MovieCreatedEventConsumer {

    private final CatalogService catalogService;

    public MovieCreatedEventConsumer(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_MOVIE_CREATED)
    public void Listen(MovieServiceClient.MovieDto movieMessage){
        try{
            System.out.println(movieMessage.getName());
            System.out.println(movieMessage.getGenre());
            catalogService.createMovie(movieMessage);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }
}
