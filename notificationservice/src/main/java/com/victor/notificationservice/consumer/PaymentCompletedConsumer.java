package com.victor.notificationservice.consumer;

import com.victor.notificationservice.event.PaymentCompletedEvent;
import com.victor.notificationservice.service.NotificationService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class PaymentCompletedConsumer {

    private final NotificationService notificationService;

    public PaymentCompletedConsumer(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @KafkaListener(topics = "payment-completed", groupId = "notification-service-group")
    public void consume(PaymentCompletedEvent event) {
        notificationService.handlePaymentCompleted(event);
    }
}