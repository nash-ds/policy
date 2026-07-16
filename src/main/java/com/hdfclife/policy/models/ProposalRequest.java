package com.hdfclife.policy.models;

import java.util.UUID;

import com.hdfclife.policy.models.PolicyProposal.Nominee;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProposalRequest {

    private UUID customerId;
    private String policyName;
    private int policyTerm;
    private String policyFreq;
    private int policyPremium;
    private int sum;
    private Nominee nominee;
    
}
