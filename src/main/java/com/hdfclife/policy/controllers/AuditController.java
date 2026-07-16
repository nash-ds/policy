package com.hdfclife.policy.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hdfclife.policy.models.Audit;
import com.hdfclife.policy.service.AuditService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/audits")
@RequiredArgsConstructor
public class AuditController {

    private final AuditService auditService;

    @GetMapping
    public List<Audit> getAllAudits() {
        return auditService.getAllAudits();
    }
}
