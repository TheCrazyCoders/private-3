package com.techsophy.vsc.bo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PrepareToBatchResponsePayload {
	private String batchNo;
	private Integer noOfAddedApplications;

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public Integer getNoOfAddedApplications() {
		return noOfAddedApplications;
	}

	public void setNoOfAddedApplications(Integer noOfAddedApplications) {
		this.noOfAddedApplications = noOfAddedApplications;
	}
}
