package com.hdfclife.policy.service;

import java.util.List;
import java.util.UUID;

import com.hdfclife.policy.models.Customer;

public interface CustomerService {
    
    List<Customer> getAllCustomers();
    Customer getCustomerById(UUID id);
    Customer createCustomer(Customer customer);
    Customer updateCustomer(UUID id, Customer customer);
}