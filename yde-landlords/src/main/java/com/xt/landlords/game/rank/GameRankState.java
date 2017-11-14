package com.xt.landlords.game.rank;

import com.xt.landlords.GameControllerState;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by leo on 17/4/26.
 */
public enum GameRankState implements GameControllerState {
    Init("Init"),
    Bet("Bet"),
    Deal("Deal"),
//    Play("Play"),
    Playing("Playing"),
//    PlayEnd("PlayEnd"),
//    Win("Win"),
//    Lose("Lose"),
    Turn("Turn"),
    GameOver("GameOver");

    /**
     * 描述
     */
    private String value;

    private GameRankState(String value) {
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
        GameRankState[] val = GameRankState.values();
        for (GameRankState e : val) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("value", e.getValue());
            map.put("name", e.name());
            list.add(map);
        }
        return list;
    }

    public static GameRankState getEnum(String name) {
        GameRankState resultEnum = null;
        GameRankState[] enumAry = GameRankState.values();
        for (int i = 0; i < enumAry.length; i++) {
            if (enumAry[i].name().equals(name)) {
                resultEnum = enumAry[i];
                break;
            }
        }
        return resultEnum;
    }

}