package com.dh.catalog.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE_NAME = "backendExchange";
    public static final String TOPIC_MOVIE_CREATED = "com.dh.backend.moviecreated";
    public static final String QUEUE_MOVIE_CREATED = "NewMovie";

    public static final String TOPIC_SERIE_CREATED = "com.dh.backend.seriecreated";
    public static final String QUEUE_SERIE_CREATED = "NewSerie";

    @Bean
    public Queue queueMovieCreated(){
        return new Queue(QUEUE_MOVIE_CREATED);
    }

    @Bean
    public TopicExchange appExchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public Binding bindingMovieCreated(){
        return BindingBuilder.bind(queueMovieCreated()).to(appExchange()).with(TOPIC_MOVIE_CREATED);
    }

    @Bean
    public Queue queueSerieCreated(){
        return new Queue(QUEUE_SERIE_CREATED);
    }

    @Bean
    public Binding bindingSerieCreated(){
        return BindingBuilder.bind(queueSerieCreated()).to(appExchange()).with(TOPIC_SERIE_CREATED);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        final var rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
