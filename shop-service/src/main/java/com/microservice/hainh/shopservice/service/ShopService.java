package com.microservice.hainh.shopservice.service;


import com.microservice.hainh.shopservice.entity.Shop;
import com.microservice.hainh.shopservice.repository.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ShopService {
    @Autowired
    private ShopRepository shopRepository;
    public Optional<Shop> getShopById(Long shopId) {
        return shopRepository.findById(shopId);
    }
}