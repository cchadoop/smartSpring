package com.smart.rabbitmq.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class ProducerConsumerConfig {
    @Bean
    public Queue myQueue(){
        return new Queue("myqueue");
    }
}
