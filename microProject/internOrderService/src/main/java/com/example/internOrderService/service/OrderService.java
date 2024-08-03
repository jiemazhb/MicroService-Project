package com.example.internOrderService.service;

import com.example.internOrderService.model.OrderRequest;
import com.example.internOrderService.model.OrderResponse;

public interface OrderService {
    long placeOrder(OrderRequest orderRequest);
    long placeOrderUsingKafkaMQ(OrderRequest orderRequest);
    OrderResponse getOrderDetailByOrderId(long orderId);
}
