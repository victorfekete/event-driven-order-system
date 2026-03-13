package com.victor.notificationservice.service;

import com.victor.notificationservice.entity.Notification;
import com.victor.notificationservice.event.PaymentCompletedEvent;
import com.victor.notificationservice.event.PaymentFailedEvent;
import com.victor.notificationservice.repository.NotificationRepository;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
public class NotificationService {

    private static final Logger log = LoggerFactory.getLogger(NotificationService.class);

    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public void handlePaymentCompleted(PaymentCompletedEvent event) {
        Notification notification = new Notification();
        notification.setOrderId(event.getOrderId());
        notification.setMessage("Payment completed successfully for order " + event.getOrderId());
        notification.setStatus("SENT");

        notificationRepository.save(notification);
        log.info("Success notification saved in database for orderId={}", event.getOrderId());
    }

    public void handlePaymentFailed(PaymentFailedEvent event) {
        Notification notification = new Notification();
        notification.setOrderId(event.getOrderId());
        notification.setMessage("Payment failed for order " + event.getOrderId());
        notification.setStatus("FAILED");

        notificationRepository.save(notification);
        log.info("Failure notification saved in database for orderId={}", event.getOrderId());
    }

    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }
}