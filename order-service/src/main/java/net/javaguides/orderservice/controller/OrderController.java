package net.javaguides.orderservice.controller;

import net.javaguides.orderservice.dto.Order;
import net.javaguides.orderservice.dto.OrderEvent;
import net.javaguides.orderservice.dto.stock.UpdateProductQuantityInStockRequest;
import net.javaguides.orderservice.publisher.OrderProducer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
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
        for (int i = 0; i < 1; i++) {
            order.setName(String.valueOf(i));
            OrderEvent event = new OrderEvent();
            event.setStatus("PENDING");
            event.setMessage("Order is in pending status");
            event.setOrder(createTestData());

            orderProducer.sendMessage(event);
//            orderProducer.sendMessagePubSub(event);
        }


        return "Order sent to the RabbitMQ ..";
    }


    private static List<UpdateProductQuantityInStockRequest> createTestData() {
        List<UpdateProductQuantityInStockRequest> listData = new ArrayList<>();

        listData.add(new UpdateProductQuantityInStockRequest("iPhone-10", 1));
        listData.add(new UpdateProductQuantityInStockRequest("iPhone-12", 1));

        return listData;
    }
}
