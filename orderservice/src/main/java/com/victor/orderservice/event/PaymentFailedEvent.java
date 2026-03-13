package com.victor.orderservice.event;

public class PaymentFailedEvent {

    private Long orderId;
    private String customerEmail;
    private String reason;

    public PaymentFailedEvent() {
    }

    public PaymentFailedEvent(Long orderId, String customerEmail, String reason) {
        this.orderId = orderId;
        this.customerEmail = customerEmail;
        this.reason = reason;
    }

    public Long getOrderId() {
        return orderId;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public String getReason() {
        return reason;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}