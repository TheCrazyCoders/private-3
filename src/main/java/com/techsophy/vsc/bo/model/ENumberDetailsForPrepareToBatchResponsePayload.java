package com.techsophy.vsc.bo.model;

public class ENumberDetailsForPrepareToBatchResponsePayload {
	private String eNumber = "H78879843";
	private String applicantName = "Charlie";
	private String nationality = "Indian";
	private String passportNo = "R7897983S87";
	private String visaType = "Business Visa";
	private String visaCenter = "Hyderabad";
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
	public String getVisaCenter() {
		return visaCenter;
	}
	public void setVisaCenter(String visaCenter) {
		this.visaCenter = visaCenter;
	}
	

}
