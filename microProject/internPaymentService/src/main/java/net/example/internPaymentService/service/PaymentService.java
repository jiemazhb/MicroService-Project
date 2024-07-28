package net.example.internPaymentService.service;

import net.example.internPaymentService.model.PaymentRequest;
import net.example.internPaymentService.model.PaymentResponse;

public interface PaymentService {
    long makePayment(PaymentRequest paymentRequest);

    PaymentResponse getPaymentByOrderId(long orderId);
}
