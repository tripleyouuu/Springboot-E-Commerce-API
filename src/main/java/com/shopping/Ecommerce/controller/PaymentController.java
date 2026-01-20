package com.shopping.Ecommerce.controller;

import com.shopping.Ecommerce.dto.PaymentRequest;
import com.shopping.Ecommerce.model.Payment;
import com.shopping.Ecommerce.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    /**
     * Create a payment for an order (MOCK).
     */
    @PostMapping("/create")
    public ResponseEntity<?> createPayment(@RequestBody PaymentRequest request) {
        Payment payment = paymentService.createPayment(request.getOrderId());

        return ResponseEntity.ok(
                java.util.Map.of(
                        "paymentId", payment.getPaymentId(),
                        "orderId", payment.getOrder().getId(),
                        "amount", payment.getAmount(),
                        "status", payment.getStatus().name()
                )
        );
    }
}
