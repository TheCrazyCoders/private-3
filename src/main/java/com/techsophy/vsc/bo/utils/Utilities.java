package com.techsophy.vsc.bo.utils;

import com.techsophy.vsc.enums.BoProcessStage;

public class Utilities {

	public static BoProcessStage getBoProcessStage(String code) {
		code = code.toUpperCase();
		switch (code) {
		case "S":
			return BoProcessStage.SPK;
		case "H":
			return BoProcessStage.HUB;
		case "M":
			return BoProcessStage.MSN;
		default:
			return null;
		}
	}
}
