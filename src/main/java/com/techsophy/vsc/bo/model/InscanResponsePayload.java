package com.techsophy.vsc.bo.model;

import java.util.ArrayList;
import java.util.List;

public class InscanResponsePayload {
	
	private List<InscanDetailsReponsePayload> inscanData = new ArrayList<>();

	public List<InscanDetailsReponsePayload> getInscanData() {
		return inscanData;
	}

	public void setInscanData(List<InscanDetailsReponsePayload> inscanData) {
		this.inscanData = inscanData;
	}

}
