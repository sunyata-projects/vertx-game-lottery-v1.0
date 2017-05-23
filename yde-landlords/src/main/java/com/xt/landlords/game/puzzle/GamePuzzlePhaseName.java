//package com.xt.landlords.game.puzzle;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * Created by leo on 17/5/15.
// */
//public enum GamePuzzlePhaseName {
//    Bet("bet"),
//    Deal("deal");
//
//    /**
//     * 描述
//     */
//    private String value;
//
//    private GamePuzzlePhaseName(String value) {
//        this.value = value;
//    }
//
//    public String getValue() {
//        return value;
//    }
//
//    public void setValue(String value) {
//        this.value = value;
//    }
//
//
//    public static List<Map<String, Object>> getList() {
//        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
//        GamePuzzlePhaseName[] val = GamePuzzlePhaseName.values();
//        for (GamePuzzlePhaseName e : val) {
//            Map<String, Object> map = new HashMap<String, Object>();
//            map.put("value", e.getValue());
//            map.put("name", e.name());
//            list.add(map);
//        }
//        return list;
//    }
//
//    public static GamePuzzlePhaseName getEnum(String name) {
//        GamePuzzlePhaseName resultEnum = null;
//        GamePuzzlePhaseName[] enumAry = GamePuzzlePhaseName.values();
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
