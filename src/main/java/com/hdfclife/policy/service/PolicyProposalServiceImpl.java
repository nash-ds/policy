package com.hdfclife.policy.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.hdfclife.policy.models.Audit;
import com.hdfclife.policy.models.Customer;
import com.hdfclife.policy.models.PolicyProposal;
import com.hdfclife.policy.models.ProposalRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PolicyProposalServiceImpl implements PolicyProposalService {
        private final CustomerService customerService;
        private final ReferenceMasterService referenceMasterService;
        private final AuditService auditservice;
        private int policyCounter;

        private final Map<UUID, PolicyProposal> policies = new HashMap<>();


    @Override
    public PolicyProposal createPolicy(ProposalRequest policy) {
        validatePolicy(policy);

        UUID id = UUID.randomUUID();
        PolicyProposal pp = new PolicyProposal();
        pp.setPolicyId(id);
        pp.setCustomerId(policy.getCustomerId());
        pp.setPolicyName(policy.getPolicyName());
        pp.setPolicyTerm(policy.getPolicyTerm());
        pp.setPolicyFreq(policy.getPolicyFreq());
        pp.setPolicyPremium(policy.getPolicyPremium());
        pp.setSum(policy.getSum());
        pp.setNominee(policy.getNominee());
        pp.setStatus("Draft");
        pp.setApplyDate(LocalDateTime.now());
        pp.setMaturityDate(pp.getApplyDate().plusYears(pp.getPolicyTerm()));
        policies.put(id, pp);
        return pp;
    }

    @Override
    public PolicyProposal submitPolicy(UUID policyId) {
        if(policies.containsKey(policyId)) {
            PolicyProposal pp = policies.get(policyId);
            validatePolicy(pp);
            pp.setStatus("Submitted");
            int policyNumber = policyCounter++;
            pp.setPolicyNumber(policyNumber);
            policies.put(policyId, pp);
            Audit audit = new Audit();
            audit.setPolicyId(policyId);
            audit.setCustId(pp.getCustomerId());
            audit.setAction("Submit");
            audit.setModule("Policy");
            auditservice.log(audit);
            return pp;
        }
        throw new IllegalArgumentException("Policy not found.");
    }

    @Override
    public PolicyProposal getPolicy(UUID policyId) {
        if(policies.containsKey(policyId)) {
            return policies.get(policyId);
        }
        throw new IllegalArgumentException("Policy not found.");
    }

    private void validatePolicy(ProposalRequest prq) {
        Customer customer = validateCustomer(prq.getCustomerId());
        validatePolicyName(prq.getPolicyName());
        validatePolicyTerm(prq.getPolicyTerm());
        validatePaymentFrequency(prq.getPolicyFreq());
        validatePremium(prq.getPolicyPremium(), prq.getSum(), customer);
        validateNominee(prq.getNominee(), customer);
    }

    private void validatePolicy(PolicyProposal pp) {
        Customer customer = validateCustomer(pp.getCustomerId());
        validatePolicyName(pp.getPolicyName());
        validatePolicyTerm(pp.getPolicyTerm());
        validatePaymentFrequency(pp.getPolicyFreq());
        validatePremium(pp.getPolicyPremium(), pp.getSum(), customer);
        validateNominee(pp.getNominee(), customer);
    }

    private Customer validateCustomer(UUID customerId) {
        if (customerId == null) {
            throw new IllegalArgumentException("Customer ID is required.");
        }

        Customer customer = customerService.getCustomerById(customerId);

        if (customer == null) {
            throw new IllegalArgumentException("Customer does not exist.");
        }
        return customer;
    }

    private void validatePolicyName(String policyName) {
        if (policyName == null || policyName.trim().isEmpty()) {
            throw new IllegalArgumentException("Policy name is required.");
        }
    }

    private void validateNominee(PolicyProposal.Nominee nominee, Customer customer) {
        if (nominee == null ||
            nominee.getName() == null ||
            nominee.getName().trim().isEmpty() ||
            nominee.getRelation() == null ||
            nominee.getRelation().trim().isEmpty() ||
            nominee.getAge() <= 0) {

            throw new IllegalArgumentException("Nominee details are invalid.");
        }
        if (customer.getName().equalsIgnoreCase(nominee.getName())) {
            throw new IllegalArgumentException("Nominee cannot be the same as the customer.");
        }
    }

    private void validatePolicyTerm(int policyTerm) {
        List<Integer> validTerms = referenceMasterService
                .getReferenceMasterList("policyterm")
                .stream()
                .map(obj -> Integer.parseInt(obj.toString()))
                .toList();

        if (!validTerms.contains(policyTerm)) {
            throw new IllegalArgumentException("Invalid policy term.");
        }
    }

    private void validatePremium(int policyPremium, int sum, Customer customer) {
        if (policyPremium < 5000) {
            throw new IllegalArgumentException("Minimum annual premium should be Rs. 5,000.");
        }

        if (sum < 100000 || sum > 50000000) {
            throw new IllegalArgumentException("Sum assured must be between Rs. 1,00,000 and Rs. 5,00,00,000.");
        }

        if (policyPremium > 50000) {
            if (customer.getPan() == null || customer.getPan().trim().isEmpty()) {
                throw new IllegalArgumentException(
                        "PAN Number is not mentioned for customer name=" + customer.getName() + " with customer id=" + customer.getCustomerId() + ".PAN is mandatory when annual premium exceeds Rs. 50,000.");
            }
        }        
    }

    private void validatePaymentFrequency(String policyFreq) {
        List<String> validFrequencies = referenceMasterService
                .getReferenceMasterList("paymentfrequency")
                .stream()
                .map(Object::toString)
                .toList();

        if (policyFreq == null ||
            !validFrequencies.contains(policyFreq.toUpperCase())) {

            throw new IllegalArgumentException("Invalid payment frequency.");
        }    
    }
}