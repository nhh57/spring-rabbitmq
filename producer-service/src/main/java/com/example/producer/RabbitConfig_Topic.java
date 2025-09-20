package com.example.producer;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig_Topic {
    public static final String EXCHANGE = "finance.events"; // topic bus

    // Queues (mỗi hệ thống 1 queue)
    @Bean Queue accountingQ()
    { return QueueBuilder
            .durable("accounting.q")
            .build(); }
    @Bean Queue fraudQ()
    { return QueueBuilder
            .durable("fraud.q")
            .build(); }
    @Bean Queue crmQ()
    { return QueueBuilder
            .durable("crm.q")
            .build(); }

    @Bean Queue auditQ()
    { return QueueBuilder
            .durable("audit.q")
            .build(); }

    // Topic exchange
    @Bean TopicExchange financeEvents() {
        return ExchangeBuilder
                .topicExchange(EXCHANGE)
                .durable(true)
                .build();
    }

    // Bindings theo PATTERN (linh động)
    // Key chuẩn: domain.action.channel.region.version
    // Ví dụ: payment.success.card.vn.v1
    @Bean Binding accountingBind(Queue accountingQ,
         TopicExchange financeEvents) {
        // Kế toán chỉ nhận giao dịch thành công ở bất kỳ kênh/khu vực/phiên bản
        return BindingBuilder
                .bind(accountingQ)
                .to(financeEvents)
                .with("payment.success.#");
    }
    @Bean Binding fraudBind(Queue fraudQ,
            TopicExchange financeEvents) {
        // Risk nhận mọi giao dịch failed của mọi domain/kênh/khu vực
        return BindingBuilder
                .bind(fraudQ)
                .to(financeEvents)
                .with("*.failed.#");
    }
    @Bean Binding crmBind(Queue crmQ,
          TopicExchange financeEvents) {
        // CRM quan tâm các thanh toán thành công qua kênh mobile (mọi region/version)
        return BindingBuilder
                .bind(crmQ)
                .to(financeEvents)
                .with("payment.success.mobile.#");
    }
    @Bean Binding auditBind(Queue auditQ, 
        TopicExchange financeEvents) {
        // Audit nhận TẤT CẢ
        return BindingBuilder
                .bind(auditQ)
                .to(financeEvents)
                .with("#");
    }
}