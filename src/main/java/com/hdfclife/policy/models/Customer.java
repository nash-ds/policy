package com.hdfclife.policy.models;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    
    private UUID customerId;
    private String name;
    private int age;
    private String phno;
    private String pan;
    private String address;
    private String email;
    private String gender;
}
