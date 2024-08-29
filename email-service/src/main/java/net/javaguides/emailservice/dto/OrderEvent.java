package net.javaguides.emailservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.javaguides.emailservice.dto.request.UpdateProductQuantityInStockRequest;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderEvent {
    private String status; // pending, progress, completed
    private String message;
    private List<UpdateProductQuantityInStockRequest> order;
}
