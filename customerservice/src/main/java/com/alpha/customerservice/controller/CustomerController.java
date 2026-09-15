package com.alpha.customerservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alpha.customerservice.dto.CustomerRegisterDto;
import com.alpha.customerservice.dto.ResponseStructure;
import com.alpha.customerservice.entity.Customer;
import com.alpha.customerservice.service.CustomerService;

import jakarta.validation.Valid;

@RestController
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @PostMapping("/customer/registercustomer")
    public ResponseStructure<Customer> registerCustomer(
            @RequestBody @Valid CustomerRegisterDto dto) {

        return customerService.registerNewCustomer(dto);
    }

    @GetMapping("/customer/getcustomerbyid")
    public ResponseStructure<Customer> getCustomerById(
            @RequestParam int id) {

        return customerService.getCustomerById(id);
    }

    @GetMapping("/customer/getallcustomers")
    public ResponseStructure<List<Customer>> getAllCustomers() {

        return customerService.getAllCustomers();
    }

    @PutMapping("/customer/updatecustomer")
    public ResponseStructure<Customer> updateCustomer(
            @RequestParam int id,
            @RequestBody @Valid CustomerRegisterDto dto) {

        return customerService.updateCustomer(id, dto);
    }

    @DeleteMapping("/customer/deletecustomerbyid")
    public ResponseStructure<String> deleteCustomerById(
            @RequestParam int id) {

        return customerService.deleteCustomerById(id);
    }
}