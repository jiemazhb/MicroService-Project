package net.example.internPaymentService.controller;

import net.example.internPaymentService.model.PaymentRequest;
import net.example.internPaymentService.model.PaymentResponse;
import net.example.internPaymentService.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;
    @PostMapping("/makePayment")
    public ResponseEntity<Long> makePayment(@RequestBody PaymentRequest paymentRequest){
        long paymentId = this.paymentService.makePayment(paymentRequest);
        return new ResponseEntity<>(paymentId, HttpStatus.OK);
    }
    @GetMapping("/{orderId}")
    public ResponseEntity<PaymentResponse> getPaymentByOrderId(@PathVariable long orderId){
         PaymentResponse paymentResponse = this.paymentService.getPaymentByOrderId(orderId);
         return new ResponseEntity<>(paymentResponse, HttpStatus.OK);
    }
}
