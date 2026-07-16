package com.hdfclife.policy.models;

import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

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
    private String timestamp;
}
