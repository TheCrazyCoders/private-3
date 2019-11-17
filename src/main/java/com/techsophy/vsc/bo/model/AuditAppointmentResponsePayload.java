package com.techsophy.vsc.bo.model;

public class AuditAppointmentResponsePayload {
	private String visaType;
	private String noOfVisits;
	private String portOfEntry;
	private String modeOfEntry;
	private String saudiMission;
	private String travellingAs;
	private String sponserId;
	private String invitationNo;
	private Integer noOfApplicant;
	
	private AuditApplicantResponsePayload applicantData;
	
	public String getSponserId() {
		return sponserId;
	}

	public void setSponserId(String sponserId) {
		this.sponserId = sponserId;
	}

	public String getInvitationNo() {
		return invitationNo;
	}

	public void setInvitationNo(String invitationNo) {
		this.invitationNo = invitationNo;
	}

	public String getVisaType() {
		return visaType;
	}

	public void setVisaType(String visaType) {
		this.visaType = visaType;
	}

	public String getNoOfVisits() {
		return noOfVisits;
	}

	public void setNoOfVisits(String noOfVisits) {
		this.noOfVisits = noOfVisits;
	}

	public String getPortOfEntry() {
		return portOfEntry;
	}

	public void setPortOfEntry(String portOfEntry) {
		this.portOfEntry = portOfEntry;
	}

	public String getModeOfEntry() {
		return modeOfEntry;
	}

	public void setModeOfEntry(String modeOfEntry) {
		this.modeOfEntry = modeOfEntry;
	}

	public String getSaudiMission() {
		return saudiMission;
	}

	public void setSaudiMission(String saudiMission) {
		this.saudiMission = saudiMission;
	}

	public String getTravellingAs() {
		return travellingAs;
	}

	public void setTravellingAs(String travellingAs) {
		this.travellingAs = travellingAs;
	}

	public AuditApplicantResponsePayload getApplicantData() {
		return applicantData;
	}

	public void setApplicantData(AuditApplicantResponsePayload applicantData) {
		this.applicantData = applicantData;
	}

	public Integer getNoOfApplicant() {
		return noOfApplicant;
	}

	public void setNoOfApplicant(Integer noOfApplicant) {
		this.noOfApplicant = noOfApplicant;
	}
}
