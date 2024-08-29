package com.productservice.consumer;

import com.productservice.dto.event.OrderEvent;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ProductConsumer {
    private Logger LOGGER = LoggerFactory.getLogger(ProductConsumer.class);

    @RabbitListener(queues = "${rabbitmq.queue.product.name}")
    public void consume(OrderEvent event, Channel channel, Message message) throws IOException {
        try {
            double number = Math.random();
            System.out.println("===========number::" + number);
            if (number < 0.5) {
                throw new IllegalArgumentException("Consume Received notification failed");
            }

            LOGGER.info(String.format("Product event received in product service => %s", event.toString()));
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (IllegalArgumentException e) {
            System.out.println("Error occurred: " + e.getMessage() + ". Tiếp tục xử lý các process khác.");
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
        }
    }


    @RabbitListener(queues = "${rabbitmq.dlq.product.name}")
    public void consumerToQueueFailed(OrderEvent event, Channel channel, Message message) throws IOException {
        try {
            System.out.println("HOT FIX in product Received message: " + event.toString());
            // Xử lý thông điệp theo logic của bạn
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            System.err.println("Failed  to process in product message: " + e.getMessage());
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
            // Xử lý lỗi theo cách bạn muốn, có thể là requeue hoặc log lỗi
        }
    }
}
