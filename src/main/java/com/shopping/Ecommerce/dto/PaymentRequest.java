package com.shopping.Ecommerce.dto;

public class PaymentRequest {

    private Long orderId;

    /**
     * Optional amount field to mirror the assignment request shape.
     * The backend always relies on the Order.totalAmount for safety.
     */
    private Double amount;

    public PaymentRequest() {}

    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}

