package com.victor.paymentservice.producer;

import com.victor.paymentservice.event.PaymentCompletedEvent;
import com.victor.paymentservice.event.PaymentFailedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class PaymentProducer {

    private static final Logger log = LoggerFactory.getLogger(PaymentProducer.class);

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public PaymentProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendPaymentCompletedEvent(PaymentCompletedEvent event) {
        kafkaTemplate.send("payment-completed", event);
        log.info("Sent event to topic=payment-completed for orderId={}", event.getOrderId());
    }

    public void sendPaymentFailedEvent(PaymentFailedEvent event) {
        kafkaTemplate.send("payment-failed", event);
        log.info("Sent event to topic=payment-failed for orderId={}", event.getOrderId());
    }
}