package com.xt.landlords;

/**
 * Created by leo on 17/4/28.
 */

public class Commands {
    //登录
    public static final String Login =      "10001";
    //登录出
    public static final String Logout =     "10002";
    //个性化信息获取
    public static final String Profile =    "10003";
    //踢人
    public static final String KickPlayer = "10004";
    //下注,人民币游戏的第一个命令
    public static final String Bet =             "10005";

    //兑换点数
    public static final String Exchange =             "10006";

    //常规赛50
    //断线续玩
    public static final String RegularBreakPlay =       "50001";
    //发牌
    public static final String RegularDeal =            "50003";
    //加注
    public static final String RegularRaise =           "50004";
    //发暗牌及农民牌
    public static final String RegularDark =            "50005";
    //出牌
    public static final String RegularPlay =            "50006";

    //积分赛51

    //拼图赛52
    //断线续玩
    public static final String PuzzleBreakPlay =       "52001";
    //发牌
    public static final String PuzzleDeal =            "52003";

    //结算
    public static final String PuzzleClear =           "52004";


    //消除53

    public static final String EliminateBreakPlay =       "53001";
    //点数下注
    public static final String EliminateGamePointBet =    "53003";
    //发牌
    public static final String EliminateDeal =            "53004";
    //结算
    public static final String EliminateClear =           "53005";
}
//
//public enum Commands {
//    Login("1111"),//登录
//    Logout("2222");//注销
//
//    /**
//     * 描述
//     */
//    private String label;
//
//    private Commands(String label) {
//        this.label = label;
//    }
//
//    public String getValue() {
//        return label;
//    }
//
//    public void setValue(String label) {
//        this.label = label;
//    }
//
//
//    public static List<Map<String, Object>> getList() {
//        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
//        Commands[] val = Commands.values();
//        for (Commands e : val) {
//            Map<String, Object> map = new HashMap<String, Object>();
//            map.put("label", e.getValue());
//            map.put("name", e.name());
//            list.add(map);
//        }
//        return list;
//    }
//
//    public static Commands getEnum(String name) {
//        Commands resultEnum = null;
//        Commands[] enumAry = Commands.values();
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
