package com.xt.landlords.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by leo on 17/6/8.
 */
public class Utility {
    public static int decideBomNums(List<Integer> cardsList) {

        if (cardsList.contains(52) && cardsList.contains(53)) {
            return 1;
        }
        if (cardsList.size() != 4) {
            return 0;
        }
        int tempNum = 0;
        List<Integer> list = new ArrayList<Integer>();
        for (int e : cardsList) {
            if (e != 52 && e != 53) {
                list.add(e % 13);
            }
        }
        //判断是否有重复的超过4个的牌
        HashMap<Integer, Integer> tempMap = new HashMap<Integer, Integer>();
        for (int i = 0; i < list.size(); i++) {
            if (tempMap.containsKey(list.get(i))) {
                tempMap.put(list.get(i), tempMap.get(list.get(i)) + 1);
            } else {
                tempMap.put(list.get(i), 1);
            }
        }
        if (tempMap.values().size() == 1) {//赵振华加的
            for (Integer e : tempMap.values()) {
                if (e == 4) {
                    tempNum++;
                }
            }
        }
//		logger.info("此次出牌炸弹数为:"+tempNum);
        return tempNum;
    }
}
