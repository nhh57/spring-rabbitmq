package com.example.springrabbitmq.consumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;



public class RabbitMQConsumer {

    private static final Logger LOGGER  = LoggerFactory.getLogger(RabbitMQConsumer.class);

    @RabbitListener(queues = {"${rabbitmq.queue.name}"})
    public void consume(String message){
        LOGGER.info(String.format("Received message -> %s",message));
    }
}
