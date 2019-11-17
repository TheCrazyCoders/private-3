package com.techsophy.vsc.bo.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class InscanRequestPayload {
	private List<InscanforEnumberRequestPayload> eNumbers;
	private String operation;

	public List<InscanforEnumberRequestPayload> geteNumbers() {
		return eNumbers;
	}

	public void seteNumbers(List<InscanforEnumberRequestPayload> eNumbers) {
		this.eNumbers = eNumbers;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

}
