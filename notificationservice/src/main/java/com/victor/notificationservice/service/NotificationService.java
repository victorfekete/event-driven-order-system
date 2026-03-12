package com.victor.notificationservice.service;

import com.victor.notificationservice.entity.Notification;
import com.victor.notificationservice.event.PaymentCompletedEvent;
import com.victor.notificationservice.repository.NotificationRepository;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public void handlePaymentCompleted(PaymentCompletedEvent event) {
        System.out.println("Received payment-completed event for order id: " + event.getOrderId());

        Notification notification = new Notification();
        notification.setOrderId(event.getOrderId());
        notification.setMessage("Payment completed successfully for order " + event.getOrderId());
        notification.setStatus("SENT");

        notificationRepository.save(notification);

        System.out.println("Notification saved for order id: " + event.getOrderId());
    }
}