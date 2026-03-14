package com.victor.orderservice.consumer;

import com.victor.orderservice.event.PaymentCompletedEvent;
import com.victor.orderservice.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class PaymentCompletedConsumer {

    private static final Logger log = LoggerFactory.getLogger(PaymentCompletedConsumer.class);

    private final OrderService orderService;

    public PaymentCompletedConsumer(OrderService orderService) {
        this.orderService = orderService;
    }

    @KafkaListener(
            topics = "payment-completed",
            groupId = "order-service-group",
            containerFactory = "paymentCompletedKafkaListenerContainerFactory"
    )
    public void consume(PaymentCompletedEvent event) {
        log.info("Received payment-completed event for orderId={}", event.getOrderId());
        orderService.markOrderAsPaymentCompleted(event.getOrderId());
    }
}