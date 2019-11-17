package com.techsophy.vsc.bo.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PrepareToBatchRequestPayload {
	private String operation;
	private String batchNo;
	private List<String> eNumbers;

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public List<String> geteNumbers() {
		return eNumbers;
	}

	public void seteNumbers(List<String> eNumbers) {
		this.eNumbers = eNumbers;
	}
}
