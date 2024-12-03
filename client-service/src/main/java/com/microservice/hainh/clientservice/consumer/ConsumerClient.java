package com.microservice.hainh.clientservice.consumer;

import com.microservice.hainh.clientservice.model.VoucherDataModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsumerClient {
    private static final Logger logger = LoggerFactory.getLogger(ConsumerClient.class);

    @RabbitListener(queues = "#{anonymousQueue.name}")
    public void consumeMessage(List<VoucherDataModel> eventList) {
        try {
            logger.info("Received voucher list: {}", eventList);
            for (VoucherDataModel voucher : eventList) {
                processVoucher(voucher);
            }
        } catch (Exception e) {
            logger.error("Error processing voucher list", e);
        }
    }

    private void processVoucher(VoucherDataModel voucher) {
        logger.info("Voucher Name: {}, Voucher Value: {}", voucher.getVoucherName(), voucher.getVoucherValue());
    }
}