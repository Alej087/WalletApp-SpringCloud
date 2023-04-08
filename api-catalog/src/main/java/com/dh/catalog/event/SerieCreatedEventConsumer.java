package com.dh.catalog.event;

import com.dh.catalog.client.MovieServiceClient;
import com.dh.catalog.client.SerieServiceClient;
import com.dh.catalog.config.RabbitMQConfig;
import com.dh.catalog.service.CatalogService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class SerieCreatedEventConsumer {

    private final CatalogService catalogService;

    public SerieCreatedEventConsumer(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_SERIE_CREATED)
    public void Listen(SerieServiceClient.SerieDTO serieMessage){
        System.out.println(serieMessage.getName());
        System.out.println(serieMessage.getGenre());
        catalogService.createSerie(serieMessage);
    }
}
