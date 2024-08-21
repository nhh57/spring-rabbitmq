package net.javaguides.stockservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.dlx.exchange.stock.name}")
    private String dlxExchangeStock;

    @Value("${rabbitmq.dlq.stock.name}")
    private String dlqNameStock;

    @Bean
    public DirectExchange dlxExchangeStock() {
        return new DirectExchange(dlxExchangeStock);
    }

    //  Dead Letter Queue
    @Bean
    public Queue deadLetterQueueStock() {
        return new Queue(dlqNameStock);
    }

    //    Binding Dead Letter Queue to Dead Letter Exchange
    @Bean
    public Binding dlqBindingStock() {
        return BindingBuilder.bind(deadLetterQueueStock())
                .to(dlxExchangeStock())
                .with(dlqNameStock);
    }

    @Bean
    public MessageConverter converter() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules(); // Tự động phát hiện và đăng ký các module Jackson cần thiết
        return new Jackson2JsonMessageConverter(objectMapper);
    }

    // configure RabbitTemplate
    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }
}
