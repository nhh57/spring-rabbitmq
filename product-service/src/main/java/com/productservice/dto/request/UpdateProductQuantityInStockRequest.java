package com.productservice.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateProductQuantityInStockRequest {
    @JsonProperty("sku_code")
    private String skuCode;
    @JsonProperty("quantity")
    private int quantity;

    @Override
    public String toString() {
        return "updateProductQuantityInStockRequest{" +
                "skuCode=" + skuCode +
                ", quantity=" + quantity +
                '}';
    }
}
