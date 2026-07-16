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
    private UUID auditid;
    private UUID policy_id;
    private UUID cust_id;
    private String action;
    private String module;
    private String timestamp;
}
