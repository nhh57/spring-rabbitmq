//package com.example.consumer;
//
//
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.stereotype.Component;
//
//@Component
//public class Consumers_Direct {
//
//    @RabbitListener(queues = "error.q")
//    public void onError(String msg) {
//        System.out.println("[error.q] " + msg);
//    }
//
//    @RabbitListener(queues = "warn.q")
//    public void onWarn(String msg) {
//        System.out.println("[warn.q] " + msg);
//    }
//}