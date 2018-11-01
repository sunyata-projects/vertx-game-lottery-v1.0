package com.xt.landlords.game.classic;

import com.xt.landlords.GameControllerState;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by leo on 17/4/26.
 */
public enum GameClassicState implements GameControllerState {
    Init("Init"),
    Bet("Bet"),
    Deal("Deal"),
    Playing("Playing"),
    Turn("Turn"),
    GuessSize("GuessSize"),
    GameOver("GameOver");


    /**
     * 描述
     */
    private String value;

    private GameClassicState(String value) {
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
        GameClassicState[] val = GameClassicState.values();
        for (GameClassicState e : val) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("value", e.getValue());
            map.put("name", e.name());
            list.add(map);
        }
        return list;
    }

    public static GameClassicState getEnum(String name) {
        GameClassicState resultEnum = null;
        GameClassicState[] enumAry = GameClassicState.values();
        for (int i = 0; i < enumAry.length; i++) {
            if (enumAry[i].name().equals(name)) {
                resultEnum = enumAry[i];
                break;
            }
        }
        return resultEnum;
    }

}