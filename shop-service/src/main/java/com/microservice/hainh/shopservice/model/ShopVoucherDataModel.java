package com.microservice.hainh.shopservice.model;

import com.microservice.hainh.shopservice.entity.ShopVoucher;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Setter
@Getter
public class ShopVoucherDataModel {
    private String shopId;
    private List<VoucherDataModel> voucher;
}
