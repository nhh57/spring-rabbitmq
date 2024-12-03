package com.microservice.hainh.shopservice.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "shop")
public class Shop {
    @Id
    @Column(name = "shop_id", nullable = false)
    private Long id;

    @Column(name = "shop_name", nullable = false)
    private String shopName;

}