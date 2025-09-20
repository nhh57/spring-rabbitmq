package com.example.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication
public class ProducerServiceApplication implements CommandLineRunner {
    private final RabbitTemplate template;
public ProducerServiceApplication(RabbitTemplate rabbitTemplate){
    this.template = rabbitTemplate;
}
    public static void main(String[] args) {
        SpringApplication.run(ProducerServiceApplication.class, args);
    }

    @Override public void run(String... args) {
//        // 1) Thanh toán thành công qua MOBILE tại VN
//        send(RabbitConfig_Topic.EXCHANGE,"payment.success.mobile.vn.v1",
//                "{txId:1, status:'SUCCESS', channel:'mobile', region:'vn'}");
//
//        // 2) Thanh toán thất bại qua CARD tại SG
//        send(RabbitConfig_Topic.EXCHANGE,"payment.failed.card.sg.v1",
//                "{txId:2, status:'FAILED', channel:'card', region:'sg'}");
//
//        // 3) Chuyển khoản khởi tạo (không phải payment) – vẫn vào audit
//        send(RabbitConfig_Topic.EXCHANGE,"transfer.created.web.vn.v1",
//                "{txId:3, type:'TRANSFER', action:'CREATED'}");
//
//        send(RabbitConfig_Direct.EXCHANGE,"error","Error occurred!");
//
//        send(RabbitConfig_Direct.EXCHANGE,"warn", "This is a warning!");
        // gửi message — routingKey có thể để trống vì fanout bỏ qua
        send(RabbitConfig_Fanout.EXCHANGE,"", "System broadcast: Maintenance at 2 AM!");
    }


    private void send(String exchange,String routingKey, String payload) {
        System.out.println("Publish: " + routingKey + " -> " + payload);
        template.convertAndSend(exchange, routingKey, payload);
    }




}
