package com.microservice.hainh.shopservice.model;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Setter
@Getter
public class VoucherDataModel {
    private String voucherName;
    private String voucherValue;
}
