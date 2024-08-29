package com.productservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.productservice.dto.response.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ProductRedisServiceImpl implements IProductRedisService {
    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper redisObjectMapper;
    private final Logger log = LoggerFactory.getLogger(ProductRedisServiceImpl.class);

    private String getKeyFrom(String keyword, int categoryId, PageRequest pageRequest) {
        log.info("====> ProductRedisServiceImpl - getKeyFrom START");
        int pageNumber = pageRequest.getPageNumber();
        int pageSize = pageRequest.getPageSize();
        Sort sort = pageRequest.getSort();
//        String sortDirection = Objects.requireNonNull(sort.getOrderFor("id"))
//                .getDirection() == Sort.Direction.ASC ? "asc" : "desc";
        String sortDirection = sort.getOrderFor("id")
                .getDirection() == Sort.Direction.ASC ? "asc" : "desc";
        String keyFormat = String.format("%s:%d:%d:%d:%s", keyword, categoryId, pageNumber, pageSize, sortDirection);
        log.info(String.format("====> ProductRedisServiceImpl - getKeyFrom ==== keyFormat::: %s", keyFormat));
        log.info("====> ProductRedisServiceImpl - getKeyFrom END");
        return keyFormat;
    }
    /*
    {
        "all_products:1:10:asc":"list of products object"
    }
    */

    @Override
    public void clear() {
        redisTemplate.getConnectionFactory().getConnection().flushAll();
    }

    @Override
    public List<ProductResponse> getAllProducts(String keyword, int categoryId, PageRequest pageRequest) throws JsonProcessingException {
        log.info("====> ProductRedisServiceImpl - getAllProducts START ");
        log.info(String.format("====> ProductRedisServiceImpl - getAllProducts ==== keyword::: %s, category_id::: %d,  pageRequest::: %s", keyword, categoryId, pageRequest));
        String key = this.getKeyFrom(keyword, categoryId, pageRequest);
        String json = String.valueOf(redisTemplate.opsForValue().get(key));
        List<ProductResponse> productResponses =
                json != null ?
                        redisObjectMapper.readValue(json, new TypeReference<List<ProductResponse>>() {
                        })
                        : null;
        log.info("====> ProductRedisServiceImpl - getAllProducts END ");
        return productResponses;
    }

    @Override
    public void saveAllProducts(List<ProductResponse> productResponses, String keyword, int categoryId, PageRequest pageRequest) throws JsonProcessingException {
        log.info("====> ProductRedisServiceImpl - saveAllProducts START ");
        log.info(String.format("====> ProductRedisServiceImpl - saveAllProducts ==== productResponses::: %s, keyword::: %s, categoryId::: %d, pageRequest::: %s", productResponses, keyword, categoryId, pageRequest));
        String key = this.getKeyFrom(keyword, categoryId, pageRequest);
        String json = redisObjectMapper.writeValueAsString(productResponses);
        log.info(String.format("====> ProductRedisServiceImpl - saveAllProducts ==== key::: %s, json::: %s", key, json));
        redisTemplate.opsForValue().set(key, json);
        log.info("====> ProductRedisServiceImpl - saveAllProducts END ");
    }


    @Override
    public void deleteProductsCache(String keyword, int categoryId, PageRequest pageRequest) {
        log.info("====> ProductRedisServiceImpl - deleteProductsCache START");
        log.info(String.format("====> ProductRedisServiceImpl - deleteProductsCache ==== keyword::: %s, category_id::: %d, pageRequest::: %s", keyword, categoryId, pageRequest));
        String key = this.getKeyFrom(keyword, categoryId, pageRequest);
        Boolean isDeleted = redisTemplate.delete(key);
        if (Boolean.TRUE.equals(isDeleted)) {
            log.info(String.format("====> ProductRedisServiceImpl - deleteProductsCache ==== Cache with key '%s' deleted successfully", key));
        } else {
            log.warn(String.format("====> ProductRedisServiceImpl - deleteProductsCache ==== Cache with key '%s' not found or could not be deleted", key));
        }
        log.info("====> ProductRedisServiceImpl - deleteProductsCache END");
    }
}
