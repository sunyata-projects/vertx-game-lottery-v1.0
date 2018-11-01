package com.xt.landlords.game.classic;

import com.xt.landlords.GameControllerState;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by leo on 17/4/26.
 */
public enum GameClassicEvent implements GameControllerState {
    Bet("OnBet"),//下注
    Deal("OnDeal"),//发牌
    Play("OnPlay"),//出牌
    Turn("OnTurn"),//转盘
    Summary("OnSummary"),//中间结算
    EnterGuessSize("OnEnterGuessSize"),//进入比倍
    GuessSize("OnOneGuessSize"),//一次比倍
    GameOver("OnGameOver");//结算
    /**
     * 描述
     */
    private String value;

    private GameClassicEvent(String value) {
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
        GameClassicEvent[] val = GameClassicEvent.values();
        for (GameClassicEvent e : val) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("value", e.getValue());
            map.put("name", e.name());
            list.add(map);
        }
        return list;
    }

    public static GameClassicEvent getEnum(String name) {
        GameClassicEvent resultEnum = null;
        GameClassicEvent[] enumAry = GameClassicEvent.values();
        for (int i = 0; i < enumAry.length; i++) {
            if (enumAry[i].name().equals(name)) {
                resultEnum = enumAry[i];
                break;
            }
        }
        return resultEnum;
    }

}