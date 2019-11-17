package com.techsophy.vsc.bo.model;

import java.util.List;

import com.techsophy.vsc.model.GenericList;

public class ApplicantDetailsForInscanFromHubResponsePayload {

	private String eNumber;
	private String applicantName;
	private String passportNo;
	private String nationality;
	private String visaType;
	private List<GenericList> statusList;
	private String serviceCenter;
	
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
	
	public String getServiceCenter() {
		return serviceCenter;
	}
	public void setServiceCenter(String serviceCenter) {
		this.serviceCenter = serviceCenter;
	}
	public List<GenericList> getStatusList() {
		return statusList;
	}
	public void setStatusList(List<GenericList> statusList) {
		this.statusList = statusList;
	}
	
	
	
}
