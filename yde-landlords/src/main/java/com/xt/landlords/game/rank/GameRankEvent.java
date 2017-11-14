package com.xt.landlords.game.rank;

import com.xt.landlords.GameControllerState;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by leo on 17/4/26.
 */
public enum GameRankEvent implements GameControllerState {
    Bet("OnBet"),//下注
    Deal("OnDeal"),//发牌
    Play("OnPlay"),//出牌
    Turn("OnTurn"),//转盘
    RoundClear("OnRoundClear"),//每局结算
    GameOver("OnGameOver");//结算
    /**
     * 描述
     */
    private String value;

    private GameRankEvent(String value) {
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
        GameRankEvent[] val = GameRankEvent.values();
        for (GameRankEvent e : val) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("value", e.getValue());
            map.put("name", e.name());
            list.add(map);
        }
        return list;
    }

    public static GameRankEvent getEnum(String name) {
        GameRankEvent resultEnum = null;
        GameRankEvent[] enumAry = GameRankEvent.values();
        for (int i = 0; i < enumAry.length; i++) {
            if (enumAry[i].name().equals(name)) {
                resultEnum = enumAry[i];
                break;
            }
        }
        return resultEnum;
    }

}