package com.shopping.Ecommerce.dto;

public class PaymentWebhookRequest {

    /**
     * For the mock payment service we only need to know which order
     * this callback refers to and the final status.
     */
    private Long orderId;
    private String status; // SUCCESS / FAILED

    public PaymentWebhookRequest() {}

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}

