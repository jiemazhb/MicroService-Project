package com.example.internOrderService.external.client;

import com.example.internOrderService.model.PaymentRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "PAYMENT-SERVICE/payments")
public interface PaymentServiceFeignClient {
    @PostMapping("/makePayment")
    public ResponseEntity<Long> makePayment(@RequestBody PaymentRequest paymentRequest);
}
