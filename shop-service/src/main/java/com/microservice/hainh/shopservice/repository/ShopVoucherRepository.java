package com.microservice.hainh.shopservice.repository;

import com.microservice.hainh.shopservice.entity.ShopVoucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShopVoucherRepository extends JpaRepository<ShopVoucher,Long> {
    List<ShopVoucher> findShopVoucherByShopId(Long shopId);
}
