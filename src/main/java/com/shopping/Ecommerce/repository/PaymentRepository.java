package com.shopping.Ecommerce.repository;

import com.shopping.Ecommerce.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    /**
     * Find a payment associated with the given order id.
     */
    Optional<Payment> findByOrder_Id(Long orderId);
}