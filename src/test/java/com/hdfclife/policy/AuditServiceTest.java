package com.hdfclife.policy;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

import com.hdfclife.policy.models.Audit;
import com.hdfclife.policy.service.AuditService;
import com.hdfclife.policy.service.AuditServiceImpl;

class AuditServiceTest {

    AuditService auditService = new AuditServiceImpl();

    @Test
    void shouldLogAuditSuccessfully() {

        Audit audit = new Audit();

        audit.setPolicyId(UUID.randomUUID());
        audit.setCustId(UUID.randomUUID());
        audit.setAction("Submit");
        audit.setModule("Policy");

        auditService.log(audit);

        List<Audit> audits = auditService.getAllAudits();

        assertEquals(1, audits.size());

        Audit saved = audits.get(0);

        assertNotNull(saved.getAuditId());
        assertNotNull(saved.getTimestamp());

        assertEquals("Submit", saved.getAction());
        assertEquals("Policy", saved.getModule());
        assertEquals(audit.getPolicyId(), saved.getPolicyId());
        assertEquals(audit.getCustId(), saved.getCustId());
    }
}