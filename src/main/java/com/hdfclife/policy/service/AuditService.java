package com.hdfclife.policy.service;

import java.util.List;

import com.hdfclife.policy.models.Audit;

public interface AuditService {
    void log(Audit audit);
    List<Audit> getAllAudits();
}