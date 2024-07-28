package net.example.internPaymentService.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponse {
    private long paymentId;
    private long orderId;
    private String paymentStatus;
    private PaymentMode paymentMode;
    private long totalAmount;
    private Instant paymentDate;
}
