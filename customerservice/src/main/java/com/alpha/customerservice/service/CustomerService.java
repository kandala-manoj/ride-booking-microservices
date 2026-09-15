package com.alpha.customerservice.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.alpha.customerservice.dto.CustomerRegisterDto;
import com.alpha.customerservice.dto.ResponseStructure;
import com.alpha.customerservice.entity.Customer;
import com.alpha.customerservice.exception.CustomerNotFoundException;
import com.alpha.customerservice.exception.EmailAlreadyExistsException;
import com.alpha.customerservice.exception.PhoneAlreadyExistsException;
import com.alpha.customerservice.repository.CustomerRepository;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository cr;

    // REGISTER CUSTOMER
    public ResponseStructure<Customer> registerNewCustomer(
            CustomerRegisterDto custdto) {

        // Check email
        if (cr.findByEmail(custdto.getEmail()) != null) {

            throw new EmailAlreadyExistsException();
        }

        // Check phone
        if (cr.findByPhone(custdto.getPhone()) != null) {

            throw new PhoneAlreadyExistsException();
        }

        Customer customer = new Customer();

        customer.setName(custdto.getName());
        customer.setEmail(custdto.getEmail());
        customer.setPhone(custdto.getPhone());

        // Initial wallet balance
        customer.setWallet(0);

        // Initial customer status
        customer.setStatus("ACTIVE");

        // Date and time
        customer.setCreatedAt(LocalDateTime.now());
        customer.setUpdatedAt(LocalDateTime.now());

        Customer savedCustomer =
                cr.save(customer);

        ResponseStructure<Customer> rs =
                new ResponseStructure<>();

        rs.setStatuscode(
                HttpStatus.CREATED.value()
        );

        rs.setMessage(
                "Customer Registered Successfully"
        );

        rs.setData(savedCustomer);

        return rs;
    }

    // GET CUSTOMER BY ID
    public ResponseStructure<Customer> getCustomerById(
            int id) {

        Customer customer =
                cr.findById(id)
                        .orElseThrow(
                                () -> new CustomerNotFoundException()
                        );

        ResponseStructure<Customer> rs =
                new ResponseStructure<>();

        rs.setStatuscode(
                HttpStatus.OK.value()
        );

        rs.setMessage(
                "Customer Found"
        );

        rs.setData(customer);

        return rs;
    }

    // GET ALL CUSTOMERS
    public ResponseStructure<List<Customer>> getAllCustomers() {

        List<Customer> customers =
                cr.findAll();

        ResponseStructure<List<Customer>> rs =
                new ResponseStructure<>();

        rs.setStatuscode(
                HttpStatus.OK.value()
        );

        rs.setMessage(
                "Customers Found"
        );

        rs.setData(customers);

        return rs;
    }

    // UPDATE CUSTOMER
    public ResponseStructure<Customer> updateCustomer(
            int id,
            CustomerRegisterDto dto) {

        Customer customer =
                cr.findById(id)
                        .orElseThrow(
                                () -> new CustomerNotFoundException()
                        );

        // Check whether email belongs to another customer
        Customer emailCustomer =
                cr.findByEmail(dto.getEmail());

        if (emailCustomer != null
                && emailCustomer.getCustomerId() != id) {

            throw new EmailAlreadyExistsException();
        }

        // Check whether phone belongs to another customer
        Customer phoneCustomer =
                cr.findByPhone(dto.getPhone());

        if (phoneCustomer != null
                && phoneCustomer.getCustomerId() != id) {

            throw new PhoneAlreadyExistsException();
        }

        customer.setName(dto.getName());
        customer.setEmail(dto.getEmail());
        customer.setPhone(dto.getPhone());

        customer.setUpdatedAt(
                LocalDateTime.now()
        );

        Customer updatedCustomer =
                cr.save(customer);

        ResponseStructure<Customer> rs =
                new ResponseStructure<>();

        rs.setStatuscode(
                HttpStatus.OK.value()
        );

        rs.setMessage(
                "Customer Updated Successfully"
        );

        rs.setData(updatedCustomer);

        return rs;
    }

    // DEACTIVATE CUSTOMER
    public ResponseStructure<String> deleteCustomerById(
            int id) {

        Customer customer =
                cr.findById(id)
                        .orElseThrow(
                                () -> new CustomerNotFoundException()
                        );

        // Soft delete
        customer.setStatus("INACTIVE");

        customer.setUpdatedAt(
                LocalDateTime.now()
        );

        cr.save(customer);

        ResponseStructure<String> rs =
                new ResponseStructure<>();

        rs.setStatuscode(
                HttpStatus.OK.value()
        );

        rs.setMessage(
                "Customer Deactivated Successfully"
        );

        rs.setData("INACTIVE");

        return rs;
    }
}