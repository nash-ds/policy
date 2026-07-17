package com.hdfclife.policy.controllers;

import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hdfclife.policy.models.PolicyProposal;
import com.hdfclife.policy.models.ProposalRequest;
import com.hdfclife.policy.service.PolicyProposalService;

import lombok.RequiredArgsConstructor;



@RequiredArgsConstructor
@RestController
@RequestMapping("/proposals")
public class ProposalController {
    private final PolicyProposalService policyProposalService;

    @GetMapping("/{id}")
    public PolicyProposal getPolicyWithId(@PathVariable UUID id) {
        return policyProposalService.getPolicy(id);
    }

    @PostMapping
    public PolicyProposal createPolicy(@RequestBody ProposalRequest policy) {
        return policyProposalService.createPolicy(policy) ;
    }
    
    @PostMapping("/{id}/submit")
    public PolicyProposal submitPolicy(@PathVariable UUID id) {        
        return policyProposalService.submitPolicy(id);
    }
    
    
    

}
