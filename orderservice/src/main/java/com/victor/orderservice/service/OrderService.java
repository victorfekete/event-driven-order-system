package com.victor.orderservice.service;

import com.victor.orderservice.dto.CreateOrderRequest;
import com.victor.orderservice.entity.Order;
import com.victor.orderservice.event.OrderCreatedEvent;
import com.victor.orderservice.producer.OrderProducer;
import com.victor.orderservice.repository.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderProducer orderProducer;

    public OrderService(OrderRepository orderRepository, OrderProducer orderProducer) {
        this.orderRepository = orderRepository;
        this.orderProducer = orderProducer;
    }

    public Order createOrder(CreateOrderRequest request) {
        Order order = new Order();
        order.setCustomerEmail(request.getCustomerEmail());
        order.setProductName(request.getProductName());
        order.setQuantity(request.getQuantity());
        order.setPrice(request.getPrice());
        order.setStatus("CREATED");

        Order savedOrder = orderRepository.save(order);

        OrderCreatedEvent event = new OrderCreatedEvent(
                savedOrder.getId(),
                savedOrder.getCustomerEmail(),
                savedOrder.getProductName(),
                savedOrder.getQuantity(),
                savedOrder.getPrice(),
                savedOrder.getStatus()
        );

        orderProducer.sendOrderCreatedEvent(event);

        return savedOrder;
    }
}