package com.xt.landlords;//package com.xt.landlords;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * Created by leo on 17/5/15.
// */
//public enum GameTypes {
//    //常规赛
//    Regular(10001),
//    //拼图
//    Puzzle(10003),
//    //消除
//    Eliminate(10004),
//    //闯关
//    Mission(10005),
//
//    //闯关
//    Crazy(10006);
//
//    /**
//     * 描述
//     */
//    private int value;
//
//    private GameTypes(int value) {
//        this.value = value;
//    }
//
//    public int getValue() {
//        return value;
//    }
//
//    public void setValue(int value) {
//        this.value = value;
//    }
//
//
//    public static List<Map<String, Object>> getList() {
//        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
//        GameTypes[] val = GameTypes.values();
//        for (GameTypes e : val) {
//            Map<String, Object> map = new HashMap<String, Object>();
//            map.put("value", e.getValue());
//            map.put("name", e.name());
//            list.add(map);
//        }
//        return list;
//    }
//
//    public static GameTypes getEnum(String name) {
//        GameTypes resultEnum = null;
//        GameTypes[] enumAry = GameTypes.values();
//        for (int i = 0; i < enumAry.length; i++) {
//            if (enumAry[i].name().equals(name)) {
//                resultEnum = enumAry[i];
//                break;
//            }
//        }
//        return resultEnum;
//    }
//
//}
