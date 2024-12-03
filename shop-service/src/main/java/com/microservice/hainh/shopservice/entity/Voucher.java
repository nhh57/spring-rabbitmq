package com.microservice.hainh.shopservice.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "voucher")
public class Voucher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "voucher_id", nullable = false)
    private Long id;

    @Column(name = "voucher_name", nullable = false)
    private String voucherName;

    @Column(name = "voucher_value", nullable = false)
    private String voucherValue;

}