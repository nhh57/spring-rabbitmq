package com.example.producer;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class RabbitConfig_Direct {
    public static final String EXCHANGE = "logs.direct";

    @Bean
    DirectExchange directExchange() {
        return ExchangeBuilder.directExchange(EXCHANGE).durable(true).build();
    }

    @Bean Queue errorQ() { return new Queue("error.q", true); }
    @Bean Queue warnQ()  { return new Queue("warn.q", true); }

    @Bean Binding bindError(Queue errorQ, DirectExchange ex) {
        return BindingBuilder.bind(errorQ).to(ex).with("error");
    }

    @Bean Binding bindWarn(Queue warnQ, DirectExchange ex) {
        return BindingBuilder.bind(warnQ).to(ex).with("warn");
    }
}