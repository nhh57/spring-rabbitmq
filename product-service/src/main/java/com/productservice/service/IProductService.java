package com.productservice.service;

import com.productservice.dto.request.ProductRequest;
import com.productservice.dto.response.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface IProductService {
    public void createProduct(ProductRequest productRequest);
    public List<ProductResponse> getAllProducts();
    public Page<ProductResponse> getAllProducts(String keyword,
                                                int categoryId,
                                                PageRequest pageRequest) throws Exception;
}
