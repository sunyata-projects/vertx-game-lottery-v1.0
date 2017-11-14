package com.xt.landlords.game.crazy;

import com.xt.landlords.GameControllerState;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by leo on 17/4/26.
 */
public enum GameCrazyState implements GameControllerState {
    Init("Init"),
    Bet("Bet"),
    Deal("Deal"),
    Drag("Drag"),
    DragOver("DragOver"),
    GuessSize("GuessSize"),
    GameOver("GameOver");


    /**
     * 描述
     */
    private String value;

    private GameCrazyState(String value) {
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
        GameCrazyState[] val = GameCrazyState.values();
        for (GameCrazyState e : val) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("value", e.getValue());
            map.put("name", e.name());
            list.add(map);
        }
        return list;
    }

    public static GameCrazyState getEnum(String name) {
        GameCrazyState resultEnum = null;
        GameCrazyState[] enumAry = GameCrazyState.values();
        for (int i = 0; i < enumAry.length; i++) {
            if (enumAry[i].name().equals(name)) {
                resultEnum = enumAry[i];
                break;
            }
        }
        return resultEnum;
    }

}