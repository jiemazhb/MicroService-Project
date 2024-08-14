package com.example.internOrderService.controller;

import com.example.internOrderService.model.OrderRequest;
import com.example.internOrderService.model.OrderResponse;
import com.example.internOrderService.service.OrderService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@Log4j2
public class OrderController {
    @Autowired
    private OrderService orderService;
    @PostMapping("/placeOrder")
    public ResponseEntity<Long> placeOrder(@RequestBody OrderRequest orderRequest){
        long orderId = this.orderService.placeOrder(orderRequest);
        return new ResponseEntity<>(orderId, HttpStatus.OK);
    }
    @PostMapping("/placeOrderByKafkaMessageQueue")
    public ResponseEntity<Long> placeOrderByKafkaMessageQueue(@RequestBody OrderRequest orderRequest){
        long orderId = this.orderService.placeOrderUsingKafkaMQ(orderRequest);
        return new ResponseEntity<>(orderId, HttpStatus.OK);
    }
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrderDetailByOrderId(@PathVariable long orderId){
        log.info("==============>getOrderDetailByOrderId   收到 =================");
        OrderResponse orderResponse = this.orderService.getOrderDetailByOrderId(orderId);
        return new ResponseEntity<>(orderResponse,HttpStatus.OK);
    }
}
