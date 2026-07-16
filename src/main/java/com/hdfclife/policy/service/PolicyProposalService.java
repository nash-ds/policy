package com.hdfclife.policy.service;
import java.util.UUID;

import com.hdfclife.policy.models.PolicyProposal;
import com.hdfclife.policy.models.ProposalRequest;

public interface PolicyProposalService {
    
    PolicyProposal createPolicy(ProposalRequest prq);
    PolicyProposal submitPolicy(UUID policyId);
    PolicyProposal getPolicy(UUID policyId);
    
}
