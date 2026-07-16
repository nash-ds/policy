package com.hdfclife.policy.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.hdfclife.policy.models.Audit;

@Service
public class AuditServiceImpl implements AuditService{
    private final List<Audit> audits = new ArrayList<>();
    
    @Override
    public void log(Audit audit) {
        audit.setAuditId(UUID.randomUUID());
        audit.setTimestamp(LocalDateTime.now());
        audits.add(audit);
    }

    @Override
    public List<Audit> getAllAudits() {
        return new ArrayList<>(audits);
    }
}