package com.dh.catalog.event;

import com.dh.catalog.client.MovieServiceClient;
import com.dh.catalog.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class MovieCreatedEventConsumer {

    @RabbitListener(queues = RabbitMQConfig.QUEUE_MOVIE_CREATED)
    public void Listen(MovieServiceClient.MovieDto movieMessage){
        System.out.println(movieMessage.getName());
        System.out.println(movieMessage.getGenre());

    }
}
