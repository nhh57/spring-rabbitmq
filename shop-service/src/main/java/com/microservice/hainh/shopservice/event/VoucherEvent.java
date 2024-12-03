package com.microservice.hainh.shopservice.event;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Setter
@Getter
public class VoucherEvent {
    private String voucherName;
    private String voucherValue;

}
