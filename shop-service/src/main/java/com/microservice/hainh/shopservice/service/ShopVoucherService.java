package com.microservice.hainh.shopservice.service;

import com.microservice.hainh.shopservice.entity.ShopVoucher;
import com.microservice.hainh.shopservice.model.ShopVoucherDataModel;
import com.microservice.hainh.shopservice.model.VoucherDataModel;
import com.microservice.hainh.shopservice.repository.ShopVoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShopVoucherService {
    @Autowired
    private ShopVoucherRepository voucherRepository;

    public List<ShopVoucherDataModel> findShopVoucherByShopId(Long shopId) {
        List<ShopVoucher> datas = voucherRepository.findShopVoucherByShopId(shopId);
        return groupShopVouchersByShop(datas);
    }

    public List<ShopVoucherDataModel> findShopVouchers() {
        List<ShopVoucher> datas = voucherRepository.findAll();
        return groupShopVouchersByShop(datas);
    }



    public List<ShopVoucherDataModel> groupShopVouchersByShop(List<ShopVoucher> data) {
        return data.stream().collect(Collectors.groupingBy(shopVoucher -> shopVoucher.getShop().getId().toString(), Collectors.mapping(shopVoucher -> new VoucherDataModel(shopVoucher.getVoucher().getVoucherValue(), shopVoucher.getVoucher().getVoucherName()), Collectors.toList()))).entrySet().stream().map(entry -> {
            ShopVoucherDataModel model = new ShopVoucherDataModel();
            model.setShopId(entry.getKey());
            model.setVoucher(entry.getValue());
            return model;
        }).collect(Collectors.toList());
    }
}
