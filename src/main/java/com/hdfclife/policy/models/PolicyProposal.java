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
public class PolicyProposal {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Nominee {
        private String name;
        private String relation;
        private int age;
    }

    private UUID policyId;
    private UUID customerId;
    private String policyName;
    private int policyTerm;
    private String policyFreq;
    private int policyPremium;
    private int sum;
    private Nominee nominee;
    private String status;
    private int policyNumber;
    private LocalDateTime applyDate;
    private LocalDateTime maturityDate;
}