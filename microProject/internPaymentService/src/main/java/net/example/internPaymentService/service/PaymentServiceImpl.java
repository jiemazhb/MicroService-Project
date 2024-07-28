package net.example.internPaymentService.service;

import lombok.extern.log4j.Log4j2;
import net.example.internPaymentService.entity.PaymentEntity;
import net.example.internPaymentService.model.PaymentMode;
import net.example.internPaymentService.model.PaymentRequest;
import net.example.internPaymentService.model.PaymentResponse;
import net.example.internPaymentService.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Log4j2
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;
    @Override
    public long makePayment(PaymentRequest paymentRequest) {
        log.info("make payment now...");
        PaymentEntity paymentEntity = PaymentEntity.builder()
                .orderId(paymentRequest.getOrderId())
                .referenceNum(paymentRequest.getReferenceNum())
                .totalAmount(paymentRequest.getTotalAmount())
                .paymentDate(Instant.now())
                .paymentMode(paymentRequest.getPaymentMode().name())
                .paymentStatus("SUCCESS")
                .build();
        this.paymentRepository.save(paymentEntity);
        log.info("payment is done.");
        return paymentEntity.getPaymentId();
    }

    @Override
    public PaymentResponse getPaymentByOrderId(long orderId) {

        PaymentEntity paymentEntity = this.paymentRepository.findByOrderId(orderId);

        if (paymentEntity == null) {
            throw new RuntimeException("Payment not found for order ID: " + orderId);
        }

        PaymentResponse paymentResponse = PaymentResponse.builder()
                .paymentId(paymentEntity.getPaymentId())
                .orderId(paymentEntity.getOrderId())
                .totalAmount(paymentEntity.getTotalAmount())
                .paymentMode(PaymentMode.valueOf(paymentEntity.getPaymentMode()))
                .paymentDate(paymentEntity.getPaymentDate())
                .paymentStatus(paymentEntity.getPaymentStatus())
                .build();
        return paymentResponse;
    }
}
