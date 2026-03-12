package com.victor.paymentservice.service;

import com.victor.paymentservice.entity.Payment;
import com.victor.paymentservice.event.OrderCreatedEvent;
import com.victor.paymentservice.event.PaymentCompletedEvent;
import com.victor.paymentservice.producer.PaymentProducer;
import com.victor.paymentservice.repository.PaymentRepository;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentProducer paymentProducer;

    public PaymentService(PaymentRepository paymentRepository, PaymentProducer paymentProducer) {
        this.paymentRepository = paymentRepository;
        this.paymentProducer = paymentProducer;
    }

    public void processPayment(OrderCreatedEvent event) {
        System.out.println("Processing payment for order id: " + event.getOrderId());

        Payment payment = new Payment();
        payment.setOrderId(event.getOrderId());
        payment.setAmount(event.getPrice());
        payment.setStatus("SUCCESS");

        Payment savedPayment = paymentRepository.save(payment);

        System.out.println("Payment saved for order id: " + event.getOrderId());

        PaymentCompletedEvent completedEvent = new PaymentCompletedEvent(
                savedPayment.getId(),
                savedPayment.getOrderId(),
                savedPayment.getAmount(),
                savedPayment.getStatus()
        );

        paymentProducer.sendPaymentCompletedEvent(completedEvent);
    }
}