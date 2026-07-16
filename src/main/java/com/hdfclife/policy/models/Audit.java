package com.hdfclife.policy.models;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Audit {
    private UUID auditId;
    private UUID policyId;
    private UUID custId;
    private String action;
    private String module;
    private LocalDateTime timestamp;
}
