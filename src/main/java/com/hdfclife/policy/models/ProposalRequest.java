package com.hdfclife.policy.models;

import java.util.UUID;

import com.hdfclife.policy.models.PolicyProposal.Nominee;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProposalRequest {

    @NotNull(message = "Customer ID is required")
    private UUID customerId;

    @NotBlank(message = "Policy name is required")
    private String policyName;

    @Min(value = 1, message = "Policy term is required")
    private int policyTerm;

    @NotBlank(message = "Payment frequency is required")
    private String policyFreq;

    @Min(value = 1, message = "Policy premium must be greater than 0")
    private int policyPremium;

    @Min(value = 1, message = "Sum assured must be greater than 0")
    private int sum;

    @NotNull(message = "Nominee details are required")
    @Valid
    private Nominee nominee;
    
}
