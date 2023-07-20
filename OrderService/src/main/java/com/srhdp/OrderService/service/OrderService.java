package com.srhdp.OrderService.service;

import com.srhdp.OrderService.model.OrderRequest;
import com.srhdp.OrderService.model.OrderResponse;

public interface OrderService {
    long placeOrder(OrderRequest orderRequest);

    OrderResponse getOrderDetails(long orderId);
}
