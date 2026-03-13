package com.victor.notificationservice.consumer;

import com.victor.notificationservice.event.PaymentFailedEvent;
import com.victor.notificationservice.service.NotificationService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class PaymentFailedConsumer {

    private static final Logger log = LoggerFactory.getLogger(PaymentFailedConsumer.class);

    private final NotificationService notificationService;

    public PaymentFailedConsumer(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @KafkaListener(
            topics = "payment-failed",
            groupId = "notification-service-group",
            containerFactory = "paymentFailedKafkaListenerContainerFactory"
    )
    public void consume(PaymentFailedEvent event) {
        log.info("Received payment-failed event for orderId={}", event.getOrderId());
        notificationService.handlePaymentFailed(event);
    }
}