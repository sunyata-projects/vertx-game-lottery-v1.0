package com.xt.card.util;

public class ChangeUtil {

	public static String gameType(int iGameType) {
		String sGameType = "";
		switch (iGameType) {
		case 1:
			sGameType = "C";
			break;
		case 2:
			sGameType = "F";
			break;
		case 3:
			sGameType = "J";
			break;
		}

		return sGameType;
	}
	
	public static String gameResult(int iGameResult) {
		String sGameResult = "";
		if (iGameResult > 0) {
			sGameResult = "W";
		} else {
			sGameResult = "L";
		}

		return sGameResult;
	}

}
