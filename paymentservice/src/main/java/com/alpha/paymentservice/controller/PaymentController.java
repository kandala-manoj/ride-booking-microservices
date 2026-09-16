package com.alpha.paymentservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.alpha.paymentservice.entity.Payment;
import com.alpha.paymentservice.service.PaymentService;

@RestController
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/payment/pay")
    public Payment makePayment(@RequestBody Payment payment) {

        return paymentService.makePayment(payment);
    }
}