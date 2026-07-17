package com.hdfclife.policy.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.hdfclife.policy.exception.ValidationException;
import com.hdfclife.policy.models.Customer;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final Map<UUID, Customer> customers = new HashMap<>();

    @Override
    public List<Customer> getAllCustomers() {
        if(customers.isEmpty()) {
            throw new ValidationException("No customers found.");
        }
        return new ArrayList<>(customers.values()   );
    }

    @Override
    public Customer getCustomerById(UUID id) {
        if(customers.containsKey(id)) {
            return customers.get(id);
        }
        throw new ValidationException("Customer not found.");
    }

    @Override
    public Customer createCustomer(Customer customer) {

        validateCustomer(customer,null);
        UUID id = UUID.randomUUID();
        customer.setCustomerId(id);
        customers.put(id, customer);
        return customer;
    }

    
    @Override
    public Customer updateCustomer(UUID id, Customer customer) {
        validateCustomer(customer, id);
        if(customers.containsKey(id)) {
            customer.setCustomerId(id);
            customers.put(id, customer);
            return customer;
        }
        throw new ValidationException("Customer not found.");
    }
    
    private void validateCustomer(Customer customer, UUID id) {
        for (Customer existing : customers.values()) {

            if (id != null && existing.getCustomerId().equals(id)) {
                continue;
            }

            if (existing.getPhno().equals(customer.getPhno())) {
                throw new ValidationException("Phone number already exists.");
            }

            if (existing.getEmail().equalsIgnoreCase(customer.getEmail())) {
                throw new ValidationException("Email already exists.");
            }

            if (customer.getPan() != null &&
                existing.getPan() != null &&
                existing.getPan().equalsIgnoreCase(customer.getPan())) {

                throw new ValidationException("PAN already exists.");
            }
        }

        if (customer.getName() == null || customer.getName().trim().isEmpty()) {
            throw new ValidationException("Name is required.");
        }
        if (customer.getAge() < 18 || customer.getAge() > 65) {
            throw new ValidationException("Customer age must be between 18 and 65.");
        }
        String phone = customer.getPhno();

        if (phone == null || !phone.matches("\\d{10}")) {
            throw new ValidationException("Phone number must contain exactly 10 digits.");
        }

        String email = customer.getEmail();

        if (email == null ||
            !email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {

            throw new ValidationException("Invalid email.");
        }
        if (customer.getAddress() == null ||
            customer.getAddress().trim().isEmpty()) {

            throw new ValidationException("Address is required.");
        }
    }
}