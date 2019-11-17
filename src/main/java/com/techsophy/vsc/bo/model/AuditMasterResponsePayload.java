package com.techsophy.vsc.bo.model;

import java.util.List;

public class AuditMasterResponsePayload {
    List<MasterResponsePayload> applicationType;
    List<MasterResponsePayload> auditType;
    List<MasterResponsePayload> holdReason;

    public List<MasterResponsePayload> getApplicationType() {
        return applicationType;
    }
    public void setApplicationType(List<MasterResponsePayload> applicationType) {
        this.applicationType = applicationType;
    }
    public List<MasterResponsePayload> getAuditType() {
        return auditType;
    }
    public void setAuditType(List<MasterResponsePayload> auditType) {
        this.auditType = auditType;
    }
    public List<MasterResponsePayload> getHoldReason() {
        return holdReason;
    }
    public void setHoldReason(List<MasterResponsePayload> holdReason) {
        this.holdReason = holdReason;
    }


}