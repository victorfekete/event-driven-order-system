package com.victor.paymentservice.producer;

import com.victor.paymentservice.event.PaymentCompletedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class PaymentProducer {

    private static final String TOPIC = "payment-completed";

    private final KafkaTemplate<String, PaymentCompletedEvent> kafkaTemplate;

    public PaymentProducer(KafkaTemplate<String, PaymentCompletedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendPaymentCompletedEvent(PaymentCompletedEvent event) {
        System.out.println("Publishing payment-completed event for order id: " + event.getOrderId());
        kafkaTemplate.send(TOPIC, event);
    }
}