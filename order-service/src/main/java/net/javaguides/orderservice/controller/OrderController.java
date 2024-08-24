package net.javaguides.orderservice.controller;

import net.javaguides.orderservice.dto.Order;
import net.javaguides.orderservice.dto.OrderEvent;
import net.javaguides.orderservice.publisher.OrderProducer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class OrderController {

    private OrderProducer orderProducer;

    public OrderController(OrderProducer orderProducer) {
        this.orderProducer = orderProducer;
    }

    @PostMapping("/orders")
    public String placeOrder(@RequestBody Order order) {

        order.setOrderId(UUID.randomUUID().toString());
        for (int i = 0; i < 10; i++) {
            order.setName(String.valueOf(i));
            OrderEvent event = new OrderEvent();
            event.setStatus("PENDING");
            event.setMessage("Order is in pending status");
            event.setOrder(order);

            orderProducer.sendMessage(event);
            orderProducer.sendMessagePubSub(event);
        }


        return "Order sent to the RabbitMQ ..";
    }
}
