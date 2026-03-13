package com.victor.orderservice.controller;

import com.victor.orderservice.dto.CreateOrderRequest;
import com.victor.orderservice.entity.Order;
import com.victor.orderservice.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private static final Logger log = LoggerFactory.getLogger(OrderController.class);

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public Order createOrder(@RequestBody CreateOrderRequest request) {
        log.info("Received create order request: customerEmail={}, productName={}, quantity={}, price={}",
                request.getCustomerEmail(),
                request.getProductName(),
                request.getQuantity(),
                request.getPrice());

        Order savedOrder = orderService.createOrder(request);

        log.info("Returning response for orderId={}", savedOrder.getId());
        return savedOrder;
    }

    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{id}")
    public Order getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }
}