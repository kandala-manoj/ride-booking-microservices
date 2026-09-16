package com.alpha.paymentservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alpha.paymentservice.entity.Payment;
import com.alpha.paymentservice.repository.PaymentRepository;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    public Payment makePayment(Payment payment) {

        payment.setPaymentStatus("SUCCESS");

        return paymentRepository.save(payment);
    }
}