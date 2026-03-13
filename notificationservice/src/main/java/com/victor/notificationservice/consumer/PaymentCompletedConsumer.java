package com.victor.notificationservice.consumer;

import com.victor.notificationservice.event.PaymentCompletedEvent;
import com.victor.notificationservice.service.NotificationService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class PaymentCompletedConsumer {

    private static final Logger log = LoggerFactory.getLogger(PaymentCompletedConsumer.class);

    private final NotificationService notificationService;

    public PaymentCompletedConsumer(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @KafkaListener(
            topics = "payment-completed",
            groupId = "notification-service-group",
            containerFactory = "paymentCompletedKafkaListenerContainerFactory"
    )
    public void consume(PaymentCompletedEvent event) {
        log.info("Received payment-completed event for orderId={}", event.getOrderId());
        notificationService.handlePaymentCompleted(event);
    }
}