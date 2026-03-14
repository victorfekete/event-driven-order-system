package com.victor.orderservice.consumer;

import com.victor.orderservice.event.PaymentFailedEvent;
import com.victor.orderservice.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class PaymentFailedConsumer {

    private static final Logger log = LoggerFactory.getLogger(PaymentFailedConsumer.class);

    private final OrderService orderService;

    public PaymentFailedConsumer(OrderService orderService) {
        this.orderService = orderService;
    }

    @KafkaListener(
            topics = "payment-failed",
            groupId = "order-service-group",
            containerFactory = "paymentFailedKafkaListenerContainerFactory"
    )
    public void consume(PaymentFailedEvent event) {
        log.info("Received payment-failed event for orderId={}", event.getOrderId());
        orderService.markOrderAsPaymentFailed(event.getOrderId());
    }
}