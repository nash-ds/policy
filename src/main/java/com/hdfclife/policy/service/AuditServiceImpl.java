package com.hdfclife.policy.service;

public class AuditServiceImpl implements AuditService{
    private final List<Audit> audits = new ArrayList<>();
    
    @Override
    public List<String> log(Audit audit) {
        audit.setAuditId(UUID.randomUUID());
        audit.setTimestamp(LocalDateTime.now());
        String formatted_audit = String.format("Audit ID: %s, Policy ID: %s, Customer ID: %s, Action: %s, Module: %s, Timestamp: %s",
                audit.getAuditId(), audit.getPolicyId(), audit.getCustId(), audit.getAction(), audit.getModule(),
                audit.getTimestamp());
        audits.add(audit);
        return formatted_audit;
    }
}