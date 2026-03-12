package com.victor.paymentservice.consumer;

import com.victor.paymentservice.event.OrderCreatedEvent;
import com.victor.paymentservice.service.PaymentService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class OrderConsumer {

    private final PaymentService paymentService;

    public OrderConsumer(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @KafkaListener(topics = "order-created", groupId = "payment-service-group-2")
    public void consume(OrderCreatedEvent event) {
        paymentService.processPayment(event);
    }
}