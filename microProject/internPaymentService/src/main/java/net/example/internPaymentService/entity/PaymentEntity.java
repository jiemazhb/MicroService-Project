package net.example.internPaymentService.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
//@Table(name = "payment_table")

public class PaymentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long paymentId;
    private long orderId;
    private String paymentMode;
    private String referenceNum;
    private long totalAmount;
    private Instant paymentDate;
    private String paymentStatus;
}
