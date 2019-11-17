package com.techsophy.vsc.bo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ENumberDetailsForBatchResponsePayload {

	private String eNumber;
	private String applicantName;
	private String nationality;
	private String passportNo;
	private String visaType;
	private String status;
	private String vscCode;
	private String vscCenter;
	private String destVscCenter;

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

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getPassportNo() {
		return passportNo;
	}

	public void setPassportNo(String passportNo) {
		this.passportNo = passportNo;
	}

	public String getVisaType() {
		return visaType;
	}

	public void setVisaType(String visaType) {
		this.visaType = visaType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getVscCode() {
		return vscCode;
	}

	public void setVscCode(String vscCode) {
		this.vscCode = vscCode;
	}

	public String getVscCenter() {
		return vscCenter;
	}

	public void setVscCenter(String vscCenter) {
		this.vscCenter = vscCenter;
	}

	public String getDestVscCenter() {
		return destVscCenter;
	}

	public void setDestVscCenter(String destVscCenter) {
		this.destVscCenter = destVscCenter;
	}
}
