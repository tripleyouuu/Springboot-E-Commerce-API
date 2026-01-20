package com.shopping.Ecommerce.webhook;

import com.shopping.Ecommerce.dto.PaymentWebhookRequest;
import com.shopping.Ecommerce.service.PaymentService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/webhooks/payment")
public class PaymentWebhookController {

    private final PaymentService paymentService;

    public PaymentWebhookController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    /**
     * Webhook endpoint used by the mock payment service.
     *
     * Expected payload:
     * {
     *   "orderId": 1,
     *   "status": "SUCCESS" | "FAILED"
     * }
     */
    @PostMapping
    public String handlePaymentWebhook(@RequestBody PaymentWebhookRequest request) {
        paymentService.handleMockWebhook(request.getOrderId(), request.getStatus());
        return "Webhook processed";
    }
}