package com.alpha.customerservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alpha.customerservice.entity.Customer;

public interface CustomerRepository
        extends JpaRepository<Customer, Integer> {

    Customer findByEmail(String email);

    Customer findByPhone(long phone);
}