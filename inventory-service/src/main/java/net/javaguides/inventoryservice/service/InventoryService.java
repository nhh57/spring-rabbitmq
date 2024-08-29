package net.javaguides.inventoryservice.service;

import net.javaguides.inventoryservice.consumer.StockConsumer;
import net.javaguides.inventoryservice.dto.request.UpdateProductQuantityInStockRequest;
import net.javaguides.inventoryservice.dto.response.InventoryResponse;
import net.javaguides.inventoryservice.model.Inventory;
import net.javaguides.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryRepository inventoryRepo;
    private Logger LOGGER = LoggerFactory.getLogger(InventoryService.class);

    @Transactional(readOnly = true)
    public List<InventoryResponse> isInStockIn(List<String> skuCode) {
        return inventoryRepo.findBySkuCodeIn(skuCode)
                .stream()
                .map(inventory ->
                        InventoryResponse.builder()
                                .skuCode(inventory.getSkuCode())
                                .isInStock(inventory.getQuantity() > 0)
                                .build())
                .collect(Collectors.toList());
    }


    @Transactional
    public void updateProductQuantities(List<UpdateProductQuantityInStockRequest> listData) {
        LOGGER.info("========= InventoryService ::: updateProductQuantities START");
        LOGGER.info("========= InventoryService ::: updateProductQuantities listData::: " + listData);
        for (UpdateProductQuantityInStockRequest request : listData) {
            Inventory inventory = inventoryRepo.findBySkuCode(request.getSkuCode())
                    .orElseThrow(() -> new RuntimeException("Product not found with ID: " + request.getSkuCode()));
            int quantityOld = inventory.getQuantity();
            int quantityNew = inventory.getQuantity() - request.getQuantity();
            inventory.setQuantity(quantityNew);
            inventoryRepo.save(inventory);
        }
    }
}
