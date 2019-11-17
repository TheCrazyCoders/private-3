package com.techsophy.vsc.bo.model;

import java.util.List;

public class InscanFromHubResponePayload {

	private List<ApplicantDetailsForInscanFromHubResponsePayload> applicantDetails;

	
	public List<ApplicantDetailsForInscanFromHubResponsePayload> getApplicantDetails() {
		return applicantDetails;
	}
	public void setApplicantDetails(List<ApplicantDetailsForInscanFromHubResponsePayload> applicantDetails) {
		this.applicantDetails = applicantDetails;
	}
}
