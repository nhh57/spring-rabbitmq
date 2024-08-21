package net.javaguides.emailservice.config;

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

    @Value("${rabbitmq.dlx.exchange.email.name}")
    private String dlxExchangeEmail;

    @Value("${rabbitmq.dlq.email.name}")
    private String dlqNameEmail;

    // Dead Letter Exchange
    @Bean
    public DirectExchange dlxExchangeEmail() {
        return new DirectExchange(dlxExchangeEmail);
    }


    // Dead Letter Queue
    @Bean
    public Queue deadLetterQueueEmail() {
        return new Queue(dlqNameEmail);
    }


    // Binding Dead Letter Queue to Dead Letter Exchange
    @Bean
    public Binding dlqBindingEmail() {
        return BindingBuilder.bind(deadLetterQueueEmail())
                .to(dlxExchangeEmail())
                .with(dlqNameEmail);
    }


    // message converter
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
