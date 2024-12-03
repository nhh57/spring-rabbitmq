package com.microservice.hainh.shopservice.repository;

import com.microservice.hainh.shopservice.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Long> {
}
