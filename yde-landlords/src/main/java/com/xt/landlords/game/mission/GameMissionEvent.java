package com.xt.landlords.game.mission;

import com.xt.landlords.GameControllerState;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by leo on 17/4/26.
 */
public enum GameMissionEvent implements GameControllerState {
    Bet("OnBet"), Deal("OnDeal"), Play("OnPlay"), GameOver("OnGameOver");


    /**
     * 描述
     */
    private String value;

    private GameMissionEvent(String value) {
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
        GameMissionEvent[] val = GameMissionEvent.values();
        for (GameMissionEvent e : val) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("value", e.getValue());
            map.put("name", e.name());
            list.add(map);
        }
        return list;
    }

    public static GameMissionEvent getEnum(String name) {
        GameMissionEvent resultEnum = null;
        GameMissionEvent[] enumAry = GameMissionEvent.values();
        for (int i = 0; i < enumAry.length; i++) {
            if (enumAry[i].name().equals(name)) {
                resultEnum = enumAry[i];
                break;
            }
        }
        return resultEnum;
    }

}