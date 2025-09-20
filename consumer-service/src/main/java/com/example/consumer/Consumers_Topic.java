//package com.example.consumer;
//
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.stereotype.Component;
//
//@Component
//public class Consumers_Topic {
//    @RabbitListener(queues = "accounting.q")
//    public void onAccounting(String msg) {
//        System.out.println("[accounting.q] " + msg);
//    }
//
//    @RabbitListener(queues = "fraud.q")
//    public void onFraud(String msg) {
//        System.out.println("[fraud.q] " + msg);
//    }
//
//    @RabbitListener(queues = "crm.q")
//    public void onCrm(String msg) {
//        System.out.println("[crm.q] " + msg);
//    }
//
//    @RabbitListener(queues = "audit.q")
//    public void onAudit(String msg) {
//        System.out.println("[audit.q] " + msg);
//    }
//}