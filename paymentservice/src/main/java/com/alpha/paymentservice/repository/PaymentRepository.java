package com.alpha.paymentservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alpha.paymentservice.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {

}