package com.xt.landlords.card.util;

import java.util.HashMap;
import java.util.Map;

public class WinLevelUtil {

	private static Map<Integer, Integer> winLevelMap = new HashMap<Integer, Integer>();

	public static void initWinLevelMap() {
//		Properties properties = PropertiesUtil.getProperties(WinLevelUtil.class, "winlevel.properties");
//		Iterator<Entry<Object, Object>> it = properties.entrySet().iterator();
//		while (it.hasNext()) {
//			Entry<Object, Object> entry = it.next();
//			winLevelMap.put(Integer.parseInt(entry.getKey().toString()), Integer.parseInt(entry.getValue().toString()));
//		}
	}
	
	public static int getWinLevel(int prizeLevelId) {
		Integer i = winLevelMap.get(prizeLevelId);
		return i == null ? 0 : i;
	}
	
	public static void main(String[] args) {
		System.out.println(getWinLevel(1));
	}

}
