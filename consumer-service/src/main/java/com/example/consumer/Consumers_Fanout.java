package com.example.consumer;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class Consumers_Fanout {

    @RabbitListener(queues = "auditF.q")
    public void onAudit(String msg) {
        System.out.println("[audit.q] " + msg);
    }

    @RabbitListener(queues = "monitor.q")
    public void onMonitor(String msg) {
        System.out.println("[monitor.q] " + msg);
    }

    @RabbitListener(queues = "backup.q")
    public void onBackup(String msg) {
        System.out.println("[backup.q] " + msg);
    }
}