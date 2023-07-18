package com.srhdp.OrderService.service;

import com.srhdp.OrderService.model.OrderRequest;

public interface OrderService {
    long placeOrder(OrderRequest orderRequest);
}
