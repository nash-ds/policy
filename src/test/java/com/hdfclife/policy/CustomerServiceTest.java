package com.hdfclife.policy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

import com.hdfclife.policy.exception.ValidationException;
import com.hdfclife.policy.models.Customer;
import com.hdfclife.policy.service.CustomerService;
import com.hdfclife.policy.service.CustomerServiceImpl;

class CustomerServiceTest {

    CustomerService customerService = new CustomerServiceImpl();

    @Test
    void shouldThrowExceptionWhenAgeIsLessThan18() {

        Customer c = new Customer();
        c.setName("Nash");
        c.setAge(16);

        assertThrows(
            ValidationException.class,
            () -> customerService.createCustomer(c)
        );
    }
        
    private Customer getValidCustomer() {
        Customer customer = new Customer();

        customer.setName("Nash");
        customer.setAge(22);
        customer.setPhno("9876543210");
        customer.setEmail("nash@gmail.com");
        customer.setPan("ABCDE1234F");
        customer.setGender("Male");
        customer.setAddress("Mumbai");

        return customer;
    }

    @Test
    void shouldCreateCustomerSuccessfully() {

        Customer customer = getValidCustomer();

        Customer saved = customerService.createCustomer(customer);

        assertNotNull(saved);
        assertNotNull(saved.getCustomerId());
        assertEquals("Nash", saved.getName());
    }

    @Test
    void shouldNotCreateCustomerSuccessfully() {

        //customer with same phno and email exists so this should fail
        Customer customer = getValidCustomer();

        Customer saved = customerService.createCustomer(customer);

        assertThrows(
            ValidationException.class,
            () -> customerService.createCustomer(customer)
        );
    }

        @Test
    void shouldRejectAgeGreaterThan65() {

        Customer customer = getValidCustomer();
        customer.setAge(70);

        assertThrows(
                ValidationException.class,
                () -> customerService.createCustomer(customer));
    }

    @Test
    void shouldRejectInvalidPhone() {

        Customer customer = getValidCustomer();
        customer.setPhno("12345");

        assertThrows(
                ValidationException.class,
                () -> customerService.createCustomer(customer));
    }

    @Test
    void shouldRejectDuplicatePhone() {

        Customer c1 = getValidCustomer();
        customerService.createCustomer(c1);

        Customer c2 = getValidCustomer();
        c2.setEmail("abc@gmail.com");
        c2.setPan("PQRSX1234Z");

        assertThrows(
                ValidationException.class,
                () -> customerService.createCustomer(c2));
    }

    @Test
    void shouldRejectInvalidEmail() {

        Customer customer = getValidCustomer();
        customer.setEmail("invalid-email");

        assertThrows(
                ValidationException.class,
                () -> customerService.createCustomer(customer));
    }
    
    @Test
    void shouldUpdateCustomerSuccessfully() {

        Customer saved = customerService.createCustomer(getValidCustomer());

        Customer updated = getValidCustomer();
        updated.setAddress("Pune");

        Customer result = customerService.updateCustomer(saved.getCustomerId(), updated);

        assertEquals(saved.getCustomerId(), result.getCustomerId());
        assertEquals("Pune", result.getAddress());
    }

}