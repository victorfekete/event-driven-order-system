package com.victor.paymentservice.consumer;

import com.victor.paymentservice.event.OrderCreatedEvent;
import com.victor.paymentservice.service.PaymentService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
public class OrderConsumer {

    private static final Logger log = LoggerFactory.getLogger(OrderConsumer.class);

    private final PaymentService paymentService;

    public OrderConsumer(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @KafkaListener(topics = "order-created", groupId = "payment-service-group-2")
    public void consume(OrderCreatedEvent event) {
        log.info("Received order-created event for orderId={}", event.getOrderId());
        paymentService.processPayment(event);
    }
}