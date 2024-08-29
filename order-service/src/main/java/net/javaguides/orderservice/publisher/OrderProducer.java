package net.javaguides.orderservice.publisher;

import net.javaguides.orderservice.dto.OrderEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.ByteBuffer;

@Service
public class OrderProducer {

    private Logger LOGGER = LoggerFactory.getLogger(OrderProducer.class);

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.binding.stock.routing.key}")
    private String stockRoutingKey;

    @Value("${rabbitmq.binding.email.routing.key}")
    private String emailRoutingKey;

    @Value("${rabbitmq.binding.product.routing.key}")
    private String productRoutingKey;

    private RabbitTemplate rabbitTemplate;

    public OrderProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(OrderEvent orderEvent) {
        LOGGER.info(String.format("Order event sent to RabbitMQ => %s", orderEvent.toString()));
        try {
            // send an order event to stock queue
            rabbitTemplate.convertAndSend(exchange, stockRoutingKey, orderEvent);

            // send an order event to email queue
            rabbitTemplate.convertAndSend(exchange, emailRoutingKey, orderEvent);

            // send an order event to product queue
            rabbitTemplate.convertAndSend(exchange, productRoutingKey, orderEvent);
        } catch (Exception e) {
            LOGGER.info(String.format("Failed to send Message" + e.getMessage()));
        }

    }

    public void sendMessagePubSub(OrderEvent orderEvent) {
        LOGGER.info(String.format("PUBSUB => %s", orderEvent.toString()));
        try {

            rabbitTemplate.convertAndSend("hainh", "", orderEvent);
        } catch (Exception e) {
            LOGGER.info(String.format("Failed to send Message" + e.getMessage()));
        }

    }
}
