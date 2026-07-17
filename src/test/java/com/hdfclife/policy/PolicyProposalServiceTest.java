package com.hdfclife.policy;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

import com.hdfclife.policy.exception.ValidationException;
import com.hdfclife.policy.models.Customer;
import com.hdfclife.policy.models.PolicyProposal;
import com.hdfclife.policy.models.ProposalRequest;
import com.hdfclife.policy.service.AuditService;
import com.hdfclife.policy.service.AuditServiceImpl;
import com.hdfclife.policy.service.CustomerService;
import com.hdfclife.policy.service.CustomerServiceImpl;
import com.hdfclife.policy.service.PolicyProposalService;
import com.hdfclife.policy.service.PolicyProposalServiceImpl;
import com.hdfclife.policy.service.ReferenceMasterService;

public class PolicyProposalServiceTest {

CustomerService customerService = new CustomerServiceImpl();
ReferenceMasterService referenceMasterService = new ReferenceMasterService();
AuditService auditService = new AuditServiceImpl();

PolicyProposalService proposalService =
        new PolicyProposalServiceImpl(
                customerService,
                referenceMasterService,
                auditService);

    @Test
    void shouldCreatePolicySuccessfully() {

        Customer customer = customerService.createCustomer(getValidCustomer());

        ProposalRequest request = getValidProposal(customer.getCustomerId());

        PolicyProposal proposal = proposalService.createPolicy(request);

        assertNotNull(proposal);
        assertNotNull(proposal.getPolicyId());
        assertEquals("Draft", proposal.getStatus());
    }

    @Test
    void shouldRejectInvalidCustomer() {

        ProposalRequest request = getValidProposal(UUID.randomUUID());

        assertThrows(
                ValidationException.class,
                () -> proposalService.createPolicy(request));
    }

    @Test
    void shouldRejectInvalidPolicyTerm() {

        Customer customer = customerService.createCustomer(getValidCustomer());

        ProposalRequest request = getValidProposal(customer.getCustomerId());

        request.setPolicyTerm(11);

        assertThrows(
                ValidationException.class,
                () -> proposalService.createPolicy(request));
    }

    @Test
    void shouldRejectInvalidPaymentFrequency() {

        Customer customer = customerService.createCustomer(getValidCustomer());

        ProposalRequest request = getValidProposal(customer.getCustomerId());

        request.setPolicyFreq("Weekly");

        assertThrows(
                ValidationException.class,
                () -> proposalService.createPolicy(request));
    }

    @Test
    void shouldRejectPremiumBelowMinimum() {

        Customer customer = customerService.createCustomer(getValidCustomer());

        ProposalRequest request = getValidProposal(customer.getCustomerId());

        request.setPolicyPremium(4000);

        assertThrows(
                ValidationException.class,
                () -> proposalService.createPolicy(request));
    }

    @Test
    void shouldRejectWhenPanMissing() {

        Customer customer = getValidCustomer();
        customer.setPan("");

        customer = customerService.createCustomer(customer);

        ProposalRequest request = getValidProposal(customer.getCustomerId());

        request.setPolicyPremium(70000);

        assertThrows(
                ValidationException.class,
                () -> proposalService.createPolicy(request));
    }

    @Test
    void shouldRejectSameNominee() {

        Customer customer = customerService.createCustomer(getValidCustomer());

        ProposalRequest request = getValidProposal(customer.getCustomerId());

        request.getNominee().setName(customer.getName());

        assertThrows(
                ValidationException.class,
                () -> proposalService.createPolicy(request));
    }

    @Test
    void shouldSubmitPolicySuccessfully() {

        Customer customer = customerService.createCustomer(getValidCustomer());

        ProposalRequest request = getValidProposal(customer.getCustomerId());

        PolicyProposal proposal = proposalService.createPolicy(request);

        PolicyProposal submitted =
                proposalService.submitPolicy(proposal.getPolicyId());

        assertEquals("Active", submitted.getStatus());

        assertNotNull(submitted.getPolicyNumber());
    }

    private Customer getValidCustomer() {

        Customer customer = new Customer();

        customer.setName("Nash");
        customer.setAge(25);
        customer.setPhno("9876543210");
        customer.setEmail("nash@gmail.com");
        customer.setPan("ABCDE1234F");
        customer.setAddress("Mumbai");
        customer.setGender("Male");

        return customer;
    }

    private ProposalRequest getValidProposal(UUID customerId) {

        ProposalRequest request = new ProposalRequest();

        request.setCustomerId(customerId);
        request.setPolicyName("Pension");
        request.setPolicyTerm(20);
        request.setPolicyFreq("Quarterly");
        request.setPolicyPremium(60000);
        request.setSum(1000000);

        PolicyProposal.Nominee nominee =
                new PolicyProposal.Nominee();

        nominee.setName("John");
        nominee.setRelation("Brother");
        nominee.setAge(30);

        request.setNominee(nominee);

        return request;
    }

}
