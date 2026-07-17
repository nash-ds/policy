package com.hdfclife.policy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.hdfclife.policy.models.Customer;
import com.hdfclife.policy.models.PolicyProposal;
import com.hdfclife.policy.models.ProposalRequest;
import com.hdfclife.policy.service.AuditService;
import com.hdfclife.policy.service.CustomerService;
import com.hdfclife.policy.service.PolicyProposalService;

@SpringBootTest
class PolicyApplicationTests {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private PolicyProposalService proposalService;

    @Autowired
    private AuditService auditService;

    @Test
    void completePolicyFlow() {

        // Create customer
        Customer customer = new Customer();
        customer.setName("Nash");
        customer.setAge(22);
        customer.setEmail("nash@gmail.com");
        customer.setPhno("9876543210");
        customer.setPan("ABCDE1234F");
        customer.setGender("Male");
        customer.setAddress("Mumbai");

        customer = customerService.createCustomer(customer);

        // Create proposal
        ProposalRequest request = new ProposalRequest();
        request.setCustomerId(customer.getCustomerId());
        request.setPolicyName("Pension");
        request.setPolicyTerm(20);
        request.setPolicyFreq("Quarterly");
        request.setPolicyPremium(60000);
        request.setSum(1000000);

        PolicyProposal.Nominee nominee = new PolicyProposal.Nominee();
        nominee.setName("John");
        nominee.setRelation("Brother");
        nominee.setAge(30);

        request.setNominee(nominee);

        PolicyProposal proposal = proposalService.createPolicy(request);

        // Submit proposal
        proposal = proposalService.submitPolicy(proposal.getPolicyId());

        // Assertions
        assertNotNull(proposal.getPolicyId());
        assertNotNull(proposal.getPolicyNumber());
        assertEquals("Active", proposal.getStatus());

        assertFalse(auditService.getAllAudits().isEmpty());
    }
}
