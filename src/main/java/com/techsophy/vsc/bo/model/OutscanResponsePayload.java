package com.techsophy.vsc.bo.model;

import java.util.List;

public class OutscanResponsePayload {
	private String batchNo;
	private String vscCode;
	private String vscCity;
	private String status;
	private List<String> eNumbers;
	private String destVscCity;

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<String> geteNumbers() {
		return eNumbers;
	}

	public void seteNumbers(List<String> eNumbers) {
		this.eNumbers = eNumbers;
	}

	public String getVscCode() {
		return vscCode;
	}

	public void setVscCode(String vscCode) {
		this.vscCode = vscCode;
	}

	public String getVscCity() {
		return vscCity;
	}

	public void setVscCity(String vscCity) {
		this.vscCity = vscCity;
	}

	public String getDestVscCity() {
		return destVscCity;
	}

	public void setDestVscCity(String destVscCity) {
		this.destVscCity = destVscCity;
	}
}
