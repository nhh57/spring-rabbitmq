package net.javaguides.emailservice.consumer;

import com.rabbitmq.client.Channel;
import net.javaguides.emailservice.dto.OrderEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class OrderConsumer {

    private Logger LOGGER = LoggerFactory.getLogger(OrderConsumer.class);

    @RabbitListener(queues = "${rabbitmq.queue.email.name}")
    public void consume(OrderEvent event, Channel channel, Message message) throws IOException {
        try {
            double number = Math.random();
            System.out.println("===========number::" + number);
            if (number < 0.5) {
                throw new IllegalArgumentException("Send notification failed");
            }
            LOGGER.info(String.format("Order event received in email service => %s", event.toString()));
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (IllegalArgumentException e) {
            System.out.println("Error occurred: " + e.getMessage() + ". Tiếp tục xử lý các process khác.");
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
        }
    }


    public void consumeFailed(OrderEvent event, Channel channel, Message message) throws IOException {
        try {
            double number = Math.random();
            System.out.println("===========number::" + number);
            if (number < 0.5) {
                throw new IllegalArgumentException("Send notification failed");
            }
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (IllegalArgumentException e) {
            System.out.println("Order không hợp lệ, nhưng chương trình vẫn tiếp tục chạy.");
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
        }
    }
}
