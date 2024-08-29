package net.javaguides.inventoryservice.consumer;

import com.rabbitmq.client.Channel;
import lombok.RequiredArgsConstructor;
import net.javaguides.inventoryservice.dto.event.OrderEvent;
import net.javaguides.inventoryservice.service.InventoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;


import java.io.IOException;

@Service
@RequiredArgsConstructor
public class StockConsumer {
    private Logger LOGGER = LoggerFactory.getLogger(StockConsumer.class);
    private final InventoryService inventorySer;

    @RabbitListener(queues = "${rabbitmq.queue.stock.name}")
    public void consume(OrderEvent event, Channel channel, Message message) throws IOException {
        try {
            double number = Math.random();
            System.out.println("===========number::" + number);
            if (number < 0.5) {
                throw new IllegalArgumentException("Consume Received notification failed");
            }
            inventorySer.updateProductQuantities(event.getOrder()); // Gọi service để cập nhật
            LOGGER.info(String.format("Order event received in stock service => %s", event.toString()));
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (IllegalArgumentException | IOException e) {
            System.out.println("Error occurred: " + e.getMessage() + ". Tiếp tục xử lý các process khác.");
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
        }
    }


    @RabbitListener(queues = "${rabbitmq.dlq.stock.name}")
    public void consumerToQueueFailed(OrderEvent event, Channel channel, Message message) throws IOException {
        try {
            System.out.println("HOT FIX in stock Received message: " + event.toString());
            inventorySer.updateProductQuantities(event.getOrder()); // Gọi service để cập nhật
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            System.err.println("Failed to process in stock message: " + e.getMessage());
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
            // Xử lý lỗi theo cách bạn muốn, có thể là requeue hoặc log lỗi
        }
    }
}
