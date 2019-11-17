package com.techsophy.vsc.bo.model;

public class InscanFromHubEnumberRequestPayload {
	private String eNumber;
	private String status="";
	
	public String geteNumber() {
		return eNumber;
	}
	public void seteNumber(String eNumber) {
		this.eNumber = eNumber;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
