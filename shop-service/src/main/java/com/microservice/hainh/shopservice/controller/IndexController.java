package com.microservice.hainh.shopservice.controller;

import com.microservice.hainh.shopservice.model.ShopVoucherDataModel;
import com.microservice.hainh.shopservice.service.ShopService;
import com.microservice.hainh.shopservice.service.ShopVoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController

@RequestMapping("/api/v1")

public class IndexController {

    private static final Logger log = LoggerFactory.getLogger(IndexController.class);
    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    private ShopService shopService;

    @Autowired
    private ShopVoucherService shopVoucherService;
//
//    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
//    public String sendMessage() {
//        String exchange = "voucher.exchange";
//        String routingKey = "voucher.new";
//        VoucherMessage message = new VoucherMessage("voucher001", "shop123", "10% OFF");
//        rabbitTemplate.convertAndSend(exchange, routingKey, message);
//        log.info("message::{}", message);
//        return message.toString();
//    }


    @GetMapping(value = "/vouchers-by-shop/", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ShopVoucherDataModel> getVouchers() {
        List<ShopVoucherDataModel> data = new ArrayList<>();
        try {
            data = shopVoucherService.findShopVouchers();
            log.info("getVouchersByShopId:{}", data);
            data.forEach(x -> {
                String exchange = "voucher.exchange";
                String routingKey = "voucher.new." + x.getShopId();
                log.info("routingKey:{} ::: {}", routingKey, x.getVoucher());
                rabbitTemplate.convertAndSend(exchange, routingKey, x.getVoucher());
                routingKey = null;
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }


    @GetMapping(value = "/vouchers-by-shop/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ShopVoucherDataModel> getVouchersByShop(@PathVariable String id) {
        List<ShopVoucherDataModel> data = new ArrayList<>();
        try {
            data = shopVoucherService.findShopVoucherByShopId(Long.valueOf(id));

            log.info("getVouchersByShopId:{}", data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

}
