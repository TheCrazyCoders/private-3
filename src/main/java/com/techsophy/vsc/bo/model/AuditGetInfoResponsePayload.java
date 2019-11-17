package com.techsophy.vsc.bo.model;

public class AuditGetInfoResponsePayload {
    private String eNumber;
    private String applicantName;
    private String passportNo;
    private String nationality;
    private String visaType;

    public AuditGetInfoResponsePayload(String eNumber, String applicantName, String passportNo, String nationality, String visaType) {
        this.eNumber = eNumber;
        this.applicantName = applicantName;
        this.passportNo = passportNo;
        this.nationality = nationality;
        this.visaType = visaType;
    }

    public String geteNumber() {
        return eNumber;
    }

    public void seteNumber(String eNumber) {
        this.eNumber = eNumber;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public String getPassportNo() {
        return passportNo;
    }

    public void setPassportNo(String passportNo) {
        this.passportNo = passportNo;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getVisaType() {
        return visaType;
    }

    public void setVisaType(String visaType) {
        this.visaType = visaType;
    }
}
