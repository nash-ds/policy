package com.hdfclife.policy.service;

import com.hdfclife.policy.models.Audit;

public interface AuditService {
    List<String> log(Audit audit);
}