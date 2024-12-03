package com.microservice.hainh.clientservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange("voucher.exchange");
    }

    @Bean
    public Queue anonymousQueue() {
        return QueueBuilder
                .nonDurable() // Queue không bền vững, bị xóa khi kết nối bị hủy
                .autoDelete() // Tự động xóa queue khi không còn sử dụng
//                .withArgument("x-dead-letter-exchange", dlxExchangeEmail)  // Dead Letter Exchange
//                .withArgument("x-dead-letter-routing-key", dlqNameEmail)   // Dead Letter Queue Routing Key
                .exclusive()  // Chỉ kết nối hiện tại mới có thể sử dụng queue này
                .build();
    }

    @Bean
    public Binding emailQueueBinding() {
        return BindingBuilder.bind(anonymousQueue()).to(fanoutExchange());
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
