package net.javaguides.inventoryservice.controller;

import net.javaguides.inventoryservice.dto.response.InventoryResponse;
import net.javaguides.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javaguides.inventoryservice.dto.response.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/inventory")
@Slf4j
public class InventoryController {

    private final InventoryService inventorySer;

    @RequestMapping(value = "/is", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<BaseResponse> isInStocks(@RequestParam List<String> skuCode) {
        log.info("START isInStock");
        log.info("skuCode: " + skuCode);
        BaseResponse response = new BaseResponse();
        List<InventoryResponse> listData = inventorySer.isInStockIn(skuCode);
        log.info("listData: " + listData);
        response.setData(listData);
        log.info("END isInStock");
        return new ResponseEntity<BaseResponse>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<InventoryResponse> sisInStock(@RequestParam List<String> skuCode) {
        log.info("skuCode: " + skuCode);
        return inventorySer.isInStockIn(skuCode);
    }
}
