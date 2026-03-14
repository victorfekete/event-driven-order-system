package com.victor.paymentservice.service;

import com.victor.paymentservice.entity.Payment;
import com.victor.paymentservice.event.OrderCreatedEvent;
import com.victor.paymentservice.event.PaymentCompletedEvent;
import com.victor.paymentservice.event.PaymentFailedEvent;
import com.victor.paymentservice.producer.PaymentProducer;
import com.victor.paymentservice.repository.PaymentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PaymentService {

    private static final Logger log = LoggerFactory.getLogger(PaymentService.class);

    private final PaymentRepository paymentRepository;
    private final PaymentProducer paymentProducer;

    public PaymentService(PaymentRepository paymentRepository, PaymentProducer paymentProducer) {
        this.paymentRepository = paymentRepository;
        this.paymentProducer = paymentProducer;
    }

    public void processPayment(OrderCreatedEvent event) {
        log.info("Processing payment for orderId={}", event.getOrderId());

        if (paymentRepository.findByOrderId(event.getOrderId()).isPresent()) {
            log.warn("Payment already exists for orderId={}, skipping duplicate event", event.getOrderId());
            return;
        }


        if (event.getPrice().compareTo(BigDecimal.valueOf(5000)) <= 0) {
            Payment payment = new Payment();
            payment.setOrderId(event.getOrderId());
            payment.setStatus("COMPLETED");
            payment = paymentRepository.save(payment);

            log.info("Payment completed and saved for orderId={}", event.getOrderId());

            PaymentCompletedEvent completedEvent = new PaymentCompletedEvent(
                    payment.getId(),
                    event.getOrderId(),
                    event.getPrice(),
                    "COMPLETED"
            );

            paymentProducer.sendPaymentCompletedEvent(completedEvent);
            log.info("payment-completed event published for orderId={}", event.getOrderId());

        } else {
            Payment payment = new Payment();
            payment.setOrderId(event.getOrderId());
            payment.setStatus("FAILED");
            payment = paymentRepository.save(payment);

            log.info("Payment failed and saved for orderId={}", event.getOrderId());

            PaymentFailedEvent failedEvent = new PaymentFailedEvent(
                    payment.getId(),
                    event.getOrderId(),
                    event.getPrice(),
                    "FAILED"
            );

            paymentProducer.sendPaymentFailedEvent(failedEvent);
            log.info("payment-failed event published for orderId={}", event.getOrderId());
        }
    }

    public java.util.List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

}