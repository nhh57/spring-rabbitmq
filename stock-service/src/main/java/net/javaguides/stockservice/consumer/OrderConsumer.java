package net.javaguides.stockservice.consumer;

import com.rabbitmq.client.Channel;
import net.javaguides.stockservice.dto.OrderEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class OrderConsumer {

    private Logger LOGGER = LoggerFactory.getLogger(OrderConsumer.class);

    @RabbitListener(queues = "${rabbitmq.queue.stock.name}")
    public void consume(OrderEvent event, Channel channel, Message message) {
        LOGGER.info(String.format("Order event received => %s", event.toString()));

        try {
            // Process the event (e.g., save order event data in the database)
            // If processing is successful, acknowledge the message
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            // If processing fails, reject the message and requeue it
            try {
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
            } catch (Exception ex) {
                LOGGER.error("Error during message rejection: ", ex);
            }
            LOGGER.error("Error processing message: ", e);
        }
    }


    @RabbitListener(queues = "${rabbitmq.dlq.stock.name}")
    public void consumerToQueueFailed(OrderEvent event, Channel channel, Message message) throws IOException {
        try {
            System.out.println("HOT FIX Received message: " + event.toString());
            // Xử lý thông điệp theo logic của bạn
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            System.err.println("Failed to process message: " + e.getMessage());
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
            // Xử lý lỗi theo cách bạn muốn, có thể là requeue hoặc log lỗi
        }
    }
}
