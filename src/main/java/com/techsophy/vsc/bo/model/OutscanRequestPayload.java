package com.techsophy.vsc.bo.model;

import java.util.List;

public class OutscanRequestPayload {
	private String operation;
	private String awbNumber;
	private String comments;
	private List<String> batchNumbers;
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public List<String> getBatchNumbers() {
		return batchNumbers;
	}
	public void setBatchNumbers(List<String> batchNumbers) {
		this.batchNumbers = batchNumbers;
	}
	public String getAwbNumber() {
		return awbNumber;
	}
	public void setAwbNumber(String awbNumber) {
		this.awbNumber = awbNumber;
	}

	
}
