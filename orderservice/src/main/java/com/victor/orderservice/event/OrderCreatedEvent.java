package com.victor.orderservice.event;

import java.math.BigDecimal;

public class OrderCreatedEvent {

    private Long orderId;
    private String customerEmail;
    private String productName;
    private Integer quantity;
    private BigDecimal price;

    public OrderCreatedEvent() {
    }

    public OrderCreatedEvent(Long orderId, String customerEmail, String productName, Integer quantity, BigDecimal price) {
        this.orderId = orderId;
        this.customerEmail = customerEmail;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
    }

    public Long getOrderId() {
        return orderId;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public String getProductName() {
        return productName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}