package com.example.producer;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class RabbitConfig_Fanout {
    public static final String EXCHANGE = "logs.fanout";

    @Bean
    FanoutExchange fanoutExchange() {
        return ExchangeBuilder.fanoutExchange(EXCHANGE).durable(true).build();
    }

    @Bean Queue auditFQ() { return new Queue("auditF.q", true); }
    @Bean Queue monitorQ() { return new Queue("monitor.q", true); }
    @Bean Queue backupQ() { return new Queue("backup.q", true); }

    @Bean Binding bindAudit(Queue auditFQ, FanoutExchange ex) {
        return BindingBuilder.bind(auditFQ).to(ex);
    }
    @Bean Binding bindMonitor(Queue monitorQ, FanoutExchange ex) {
        return BindingBuilder.bind(monitorQ).to(ex);
    }
    @Bean Binding bindBackup(Queue backupQ, FanoutExchange ex) {
        return BindingBuilder.bind(backupQ).to(ex);
    }
}