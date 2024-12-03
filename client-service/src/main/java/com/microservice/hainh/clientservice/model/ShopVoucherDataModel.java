package com.microservice.hainh.clientservice.model;

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
