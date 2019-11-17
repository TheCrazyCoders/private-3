package com.techsophy.vsc.bo.model;

import java.util.List;

public class InscanFromHubRequestPayload {
	private List<InscanFromHubEnumberRequestPayload> eNumberDetails;
	private String inscanFrom;
	private String processAt;

	public List<InscanFromHubEnumberRequestPayload> geteNumberDetails() {
		return eNumberDetails;
	}

	public void seteNumberDetails(List<InscanFromHubEnumberRequestPayload> eNumberDetails) {
		this.eNumberDetails = eNumberDetails;
	}

	public String getInscanFrom() {
		return inscanFrom;
	}

	public void setInscanFrom(String inscanFrom) {
		this.inscanFrom = inscanFrom;
	}

	public String getProcessAt() {
		return processAt;
	}

	public void setProcessAt(String processAt) {
		this.processAt = processAt;
	}
	
	
}
