package com.hdfclife.policy.models;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Policy {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Nominee {
        private String name;
        private String relation;
        private int age;
    }

    private UUID policy_id;
    private UUID cust_id;
    private String policy_name;
    private Integer policyTerm;
    private String policy_freq;
    private double policy_premium;
    private double sum;
    private Nominee nominee;
    private Status status;
    private String policynumber;
    private LocalDateTime applydate;
    private LocalDateTime issuedate;
}