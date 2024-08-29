package com.productservice.model;

import jakarta.persistence.*;
import lombok.*;


import java.math.BigDecimal;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "product", schema = "testdata", catalog = "")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String name;
    @Column(name = "category_id")
    private int categoryId;
    private String description;
    private BigDecimal price;


}
