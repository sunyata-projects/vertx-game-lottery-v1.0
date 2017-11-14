package com.xt.landlords.game.crazy;

import com.xt.landlords.GameControllerState;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by leo on 17/4/26.
 */
public enum GameCrazyEvent implements GameControllerState {
    Bet("OnBet"),//下注
    Deal("OnDeal"),//发牌
    Drag("OnDrag"),//拖拽
    DragOver("OnDragOver"),//拖拽结束,结算
    EnterGuessSize("OnEnterGuessSize"),//进入比倍
    GuessSize("OnOneGuessSize"),//一次比倍
    GameOver("OnGameOver");//结算
    /**
     * 描述
     */
    private String value;

    private GameCrazyEvent(String value) {
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
        GameCrazyEvent[] val = GameCrazyEvent.values();
        for (GameCrazyEvent e : val) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("value", e.getValue());
            map.put("name", e.name());
            list.add(map);
        }
        return list;
    }

    public static GameCrazyEvent getEnum(String name) {
        GameCrazyEvent resultEnum = null;
        GameCrazyEvent[] enumAry = GameCrazyEvent.values();
        for (int i = 0; i < enumAry.length; i++) {
            if (enumAry[i].name().equals(name)) {
                resultEnum = enumAry[i];
                break;
            }
        }
        return resultEnum;
    }

}