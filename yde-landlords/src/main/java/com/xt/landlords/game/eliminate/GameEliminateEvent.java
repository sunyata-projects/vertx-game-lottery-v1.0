package com.xt.landlords.game.eliminate;

import com.xt.landlords.GameControllerState;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by leo on 17/4/26.
 */
public enum GameEliminateEvent implements GameControllerState {
    Exchange("OnExchange"), Bet("OnBet"), Deal("OnDeal"), GameOver("OnGameOver");


    /**
     * 描述
     */
    private String value;

    private GameEliminateEvent(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


    public static List<Map<String, Object>> getList() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        GameEliminateEvent[] val = GameEliminateEvent.values();
        for (GameEliminateEvent e : val) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("value", e.getValue());
            map.put("name", e.name());
            list.add(map);
        }
        return list;
    }

    public static GameEliminateEvent getEnum(String name) {
        GameEliminateEvent resultEnum = null;
        GameEliminateEvent[] enumAry = GameEliminateEvent.values();
        for (int i = 0; i < enumAry.length; i++) {
            if (enumAry[i].name().equals(name)) {
                resultEnum = enumAry[i];
                break;
            }
        }
        return resultEnum;
    }

}