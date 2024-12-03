//package com.microservice.hainh.clientservice.config;
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.microservice.hainh.clientservice.model.VoucherDataModel;
//import org.springframework.amqp.core.*;
//import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
//import org.springframework.amqp.rabbit.connection.ConnectionFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.util.List;
//import java.util.concurrent.Executors;
//
//@RestController
//@RequestMapping("/api/queues")
//public class QueueController {
//
//    private static final Logger logger = LoggerFactory.getLogger(QueueController.class);
//
//    @Autowired
//    private AmqpAdmin amqpAdmin;
//
//    @Autowired
//    private FanoutExchange fanoutExchange;
//
//    @Autowired
//    private ConnectionFactory connectionFactory;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @PostMapping("/create-and-listen")
//    public String createAndListenQueue(@RequestParam String queueName) {
//        // Tạo queue
//        Queue queue = new Queue(queueName, false, true, true);
//        amqpAdmin.declareQueue(queue);
//
//        // Gắn queue vào exchange
//        Binding binding = BindingBuilder.bind(queue).to(fanoutExchange);
//        amqpAdmin.declareBinding(binding);
//
//        // Bắt đầu lắng nghe
//        startListening(queueName);
//
//        return "Queue " + queueName + " created, bound to exchange, and is now listening for messages.";
//    }
//
//    private void startListening(String queueName) {
//        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
//        container.setConnectionFactory(connectionFactory);
//        container.setQueueNames(queueName);
//        container.setMessageListener(message -> {
//            try {
//                // Chuyển đổi message body thành danh sách VoucherDataModel
//                String body = new String(message.getBody());
//                List<VoucherDataModel> vouchers = objectMapper.readValue(body, new TypeReference<List<VoucherDataModel>>() {});
//
//                // Gọi hàm xử lý từng voucher
//                vouchers.forEach(this::processVoucher);
//
//            } catch (Exception e) {
//                logger.error("Error processing message from queue {}: {}", queueName, e.getMessage());
//            }
//        });
//
//        // Chạy listener trên một thread riêng biệt
//        Executors.newSingleThreadExecutor().execute(container::start);
//    }
//
//    private void processVoucher(VoucherDataModel voucher) {
//        logger.info("Voucher Name: {}, Voucher Value: {}", voucher.getVoucherName(), voucher.getVoucherValue());
//    }
//}
