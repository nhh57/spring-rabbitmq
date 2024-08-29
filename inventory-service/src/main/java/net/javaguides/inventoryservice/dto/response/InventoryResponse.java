package net.javaguides.inventoryservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonProperty;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryResponse {
    @JsonProperty("sku_code")
    private String skuCode;
    @JsonProperty("is_in_stock")
    private boolean isInStock;
}
