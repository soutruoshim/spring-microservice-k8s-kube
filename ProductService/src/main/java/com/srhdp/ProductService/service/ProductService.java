package com.srhdp.ProductService.service;

import com.srhdp.ProductService.model.ProductRequest;
import com.srhdp.ProductService.model.ProductResponse;

public interface ProductService {
    long addProduct(ProductRequest productRequest);
    ProductResponse getProductById(long productId);
}
