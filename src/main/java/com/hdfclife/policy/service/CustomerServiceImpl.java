package com.hdfclife.policy.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.hdfclife.policy.models.Customer;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final Map<UUID, Customer> customers = new HashMap<>();

    @Override
    public List<Customer> getAllCustomers() {
        if(customers.isEmpty()) {
            throw new IllegalArgumentException("No customers found.");
        }
        return new ArrayList<>(customers.values()   );
    }

    @Override
    public Customer getCustomerById(UUID id) {
        if(customers.containsKey(id)) {
            return customers.get(id);
        }
        throw new IllegalArgumentException("Customer not found.");
    }

    @Override
    public Customer createCustomer(Customer customer) {

        validateCustomer(customer);
        UUID id = UUID.randomUUID();
        customer.setCustomerId(id);
        customers.put(id, customer);
        return customer;
    }

    
    @Override
    public Customer updateCustomer(UUID id, Customer customer) {
        validateCustomer(customer);
        if(customers.containsKey(id)) {
            customers.put(id, customer);
            return customer;
        }
        throw new IllegalArgumentException("Customer not found.");
    }
    
    private void validateCustomer(Customer customer) {
        if (customer.getName() == null || customer.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Name is required.");
        }
        if (customer.getAge() < 18 || customer.getAge() > 65) {
            throw new IllegalArgumentException("Customer age must be between 18 and 65.");
        }
        String phone = customer.getPhno();

        if (phone == null || !phone.matches("\\d{10}")) {
            throw new IllegalArgumentException("Phone number must contain exactly 10 digits.");
        }
        boolean phoneExists = customers.values().stream()
                .anyMatch(c -> c.getPhno().equals(customer.getPhno()));

        if (phoneExists) {
            throw new IllegalArgumentException("Phone number already exists.");
        }
        String email = customer.getEmail();

        if (email == null ||
            !email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {

            throw new IllegalArgumentException("Invalid email.");
        }
        boolean emailExists = customers.values().stream()
                .anyMatch(c -> c.getEmail().equalsIgnoreCase(customer.getEmail()));

        if (emailExists) {
            throw new IllegalArgumentException("Email already exists.");
        }
        if (customer.getAddress() == null ||
            customer.getAddress().trim().isEmpty()) {

            throw new IllegalArgumentException("Address is required.");
        }
    }
}