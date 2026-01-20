package com.shopping.Ecommerce.service;

import com.shopping.Ecommerce.model.Order;
import com.shopping.Ecommerce.model.OrderStatus;
import com.shopping.Ecommerce.model.Payment;
import com.shopping.Ecommerce.model.PaymentStatus;
import com.shopping.Ecommerce.repository.OrderRepository;
import com.shopping.Ecommerce.repository.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    public PaymentService(PaymentRepository paymentRepository,
                          OrderRepository orderRepository) {
        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
    }

    /**
     * Create a mock payment for the given order.
     * Status starts as PENDING and will be updated via webhook.
     */
    public Payment createPayment(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (order.getStatus() != OrderStatus.CREATED) {
            throw new RuntimeException("Order is not in CREATED status");
        }

        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setAmount(order.getTotalAmount());
        payment.setPaymentId("pay_mock_" + UUID.randomUUID());
        payment.setStatus(PaymentStatus.PENDING);

        return paymentRepository.save(payment);
    }

    /**
     * Handle a webhook callback from the mock payment service.
     */
    public void handleMockWebhook(Long orderId, String status) {
        Payment payment = paymentRepository.findByOrder_Id(orderId)
                .orElseThrow(() -> new RuntimeException("Payment not found for order " + orderId));

        Order order = payment.getOrder();

        if ("SUCCESS".equalsIgnoreCase(status)) {
            payment.setStatus(PaymentStatus.SUCCESS);
            order.setStatus(OrderStatus.PAID);
        } else {
            payment.setStatus(PaymentStatus.FAILED);
            order.setStatus(OrderStatus.FAILED);
        }

        paymentRepository.save(payment);
        orderRepository.save(order);
    }
}