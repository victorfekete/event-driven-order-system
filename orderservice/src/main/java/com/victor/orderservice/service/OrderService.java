package com.victor.orderservice.service;

import com.victor.orderservice.dto.CreateOrderRequest;
import com.victor.orderservice.entity.Order;
import com.victor.orderservice.entity.OrderStatus;
import com.victor.orderservice.event.OrderCreatedEvent;
import com.victor.orderservice.producer.OrderProducer;
import com.victor.orderservice.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderService.class);

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
        order.setStatus(OrderStatus.CREATED);

        Order savedOrder = orderRepository.save(order);
        log.info("Order saved in database with id={}", savedOrder.getId());

        OrderCreatedEvent event = new OrderCreatedEvent(
                savedOrder.getId(),
                savedOrder.getCustomerEmail(),
                savedOrder.getProductName(),
                savedOrder.getQuantity(),
                savedOrder.getPrice()
        );

        orderProducer.sendOrderCreatedEvent(event);
        log.info("order-created event published for orderId={}", savedOrder.getId());

        return savedOrder;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
    }

    public void markOrderAsPaymentCompleted(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));

        order.setStatus(OrderStatus.PAYMENT_COMPLETED);
        orderRepository.save(order);

        log.info("Order status updated to PAYMENT_COMPLETED for orderId={}", orderId);
    }

    public void markOrderAsPaymentFailed(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));

        order.setStatus(OrderStatus.PAYMENT_FAILED);
        orderRepository.save(order);

        log.info("Order status updated to PAYMENT_FAILED for orderId={}", orderId);
    }
}