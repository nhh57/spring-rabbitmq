package com.productservice.dto.event;

import com.productservice.dto.request.UpdateProductQuantityInStockRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderEvent {
    private String status; // pending, progress, completed
    private String message;
    private List<UpdateProductQuantityInStockRequest> order;
}
