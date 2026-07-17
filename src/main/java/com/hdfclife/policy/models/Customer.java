package com.hdfclife.policy.models;

import java.util.UUID;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
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
    
    @NotBlank(message = "Nmae is required")
    private String name;
    
    @Min(value = 18, message = "Age must be at least 18")
    @Max(value = 65, message = "Age must not exceed 65")
    private int age;
    
    @NotBlank
    private String phno;

    private String pan;

    @NotBlank
    private String address;

    @NotBlank
    private String email;

    @NotBlank
    private String gender;
}
