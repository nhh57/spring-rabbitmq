package net.javaguides.orderservice.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;


@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.queue.stock.name}")
    private String stockQueue;

    @Value("${rabbitmq.queue.email.name}")
    private String emailQueue;

    @Value("${rabbitmq.binding.stock.routing.key}")
    private String stockRoutingKey;

    @Value("${rabbitmq.binding.email.routing.key}")
    private String emailRoutingKey;

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.dlx.exchange.email.name}")
    private String dlxExchangeEmail;

    @Value("${rabbitmq.dlq.email.name}")
    private String dlqNameEmail;

    @Value("${rabbitmq.dlx.exchange.stock.name}")
    private String dlxExchangeStock;

    @Value("${rabbitmq.dlq.stock.name}")
    private String dlqNameStock;

    @Value("${spring.rabbitmq.host}")
    private String address;


    private String nameExchange = "hainh";

    // spring bean for queue - order queue
    @Bean
    public Queue stockQueue(){
        return QueueBuilder
                .durable(stockQueue)
                .withArgument("x-dead-letter-exchange", dlxExchangeStock)  // Dead Letter Exchange
                .withArgument("x-dead-letter-routing-key", dlqNameStock)   // Dead Letter Queue Routing Key
                .ttl(6000)
//                .exclusive()
                .build();
    }

    // spring bean for queue - order queue
    @Bean
    public Queue emailQueue(){
        return  QueueBuilder
                .durable(emailQueue)
                .withArgument("x-dead-letter-exchange", dlxExchangeEmail)  // Dead Letter Exchange
                .withArgument("x-dead-letter-routing-key", dlqNameEmail)   // Dead Letter Queue Routing Key
                .ttl(6000)
//                .exclusive() // Cấu hình hàng đợi là exclusive
                .build();
    }

    // Cấu hình Fanout Exchange
    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(nameExchange);
    }

    @Bean
    public TopicExchange exchange(){
        return new TopicExchange(exchange);
    }

    // spring bean for binding between exchange and queue using routing key
    @Bean
    public Binding binding(){
        return BindingBuilder
                .bind(stockQueue())
                .to(exchange())
                .with(stockRoutingKey);
    }

    // spring bean for binding between exchange and queue using routing key
    @Bean
    public Binding emailBinding(){
        return BindingBuilder
                .bind(emailQueue())
                .to(exchange())
                .with(emailRoutingKey);
    }

    // message converter
    @Bean
    public MessageConverter converter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public CachingConnectionFactory connectionFactory() {
        CachingConnectionFactory factory = new CachingConnectionFactory();
        factory.setAddresses(address);
        factory.setPublisherConfirmType(CachingConnectionFactory.ConfirmType.CORRELATED);
        factory.setPublisherReturns(true); // Enable publisher returns
        return factory;
    }

    // configure RabbitTemplate
    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        // Enable publisher confirms
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (ack) {
                System.out.println("Message sent successfully: " + correlationData);
            } else {
                System.err.println("Message failed to send: " + cause);
            }
        });

        // Enable publisher returns
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setReturnsCallback(returned -> {
            System.err.println("Message returned: " + returned.getMessage() + " - " + returned.getReplyText());
        });
        rabbitTemplate.setRetryTemplate(retryTemplate());
        return rabbitTemplate;
    }

    @Bean
    public RetryTemplate retryTemplate() {
        RetryTemplate retryTemplate = new RetryTemplate();

        // Configure retry policy
        SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
        retryPolicy.setMaxAttempts(3); // Retry 3 times
        retryTemplate.setRetryPolicy(retryPolicy);

        // Configure backoff policy
        ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
        backOffPolicy.setInitialInterval(500);
        backOffPolicy.setMultiplier(2.0);
        backOffPolicy.setMaxInterval(5000);
        retryTemplate.setBackOffPolicy(backOffPolicy);

        return retryTemplate;
    }
}
