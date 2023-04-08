package com.dh.serie.event;

import com.dh.serie.config.RabbitMQConfig;
import com.dh.serie.model.Serie;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class SerieCreatedEventProducer {

    private final RabbitTemplate rabbitTemplate;

    public SerieCreatedEventProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishSerieCreatedEvent(Serie serieMessage){
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME,RabbitMQConfig.TOPIC_SERIE_CREATED,serieMessage);
    }
}
