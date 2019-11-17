package com.techsophy.vsc.bo.model;

public class InscanDetailsReponsePayload {
	private String eNumber;
	private String applicantName;
	private String passportNo;
	private String nationality;
	private String visaType;
	private String biometricDoneBy;

	private Byte isAuditMandate;

	private String batchNo;

	private String sourceVscCenter;
	private String destVscCenter;

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

	public String getBiometricDoneBy() {
		return biometricDoneBy;
	}

	public void setBiometricDoneBy(String biometricDoneBy) {
		this.biometricDoneBy = biometricDoneBy;
	}

	public String geteNumber() {
		return eNumber;
	}

	public void seteNumber(String eNumber) {
		this.eNumber = eNumber;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public Byte getIsAuditMandate() {
		return isAuditMandate;
	}

	public void setIsAuditMandate(Byte isAuditMandate) {
		this.isAuditMandate = isAuditMandate;
	}

	public String getSourceVscCenter() {
		return sourceVscCenter;
	}

	public void setSourceVscCenter(String sourceVscCenter) {
		this.sourceVscCenter = sourceVscCenter;
	}

	public String getDestVscCenter() {
		return destVscCenter;
	}

	public void setDestVscCenter(String destVscCenter) {
		this.destVscCenter = destVscCenter;
	}
}
