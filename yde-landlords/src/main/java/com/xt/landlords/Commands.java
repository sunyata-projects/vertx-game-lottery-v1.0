package com.xt.landlords;

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


    //通知玩家余额发生变化
    public static final String NotifyBalanceChanged =             "10007";

    //常规赛50
    //断线续玩
    public static final String RegularBreakPlay =       "50001";
    //发牌
    public static final String RegularDeal =            "50003";
    //加注
    public static final String RegularRaise =           "50004";
//    //发暗牌及农民牌
//    public static final String RegularDark =            "50005";
    //出牌
    public static final String RegularPlay =            "50006";

    //是否翻牌
    public static final String RegularGuessSize =            "50007";

    //结算
    public static final String RegularClear =           "50008";

    //闯关赛51

    //断线续玩
    public static final String MissionBreakPlay =       "51001";
    //发牌
    public static final String MissionDeal =            "51002";
    //出牌
    public static final String MissionPlay =            "51003";
    //结算
    public static final String MissionClear =           "51004";


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

    //放弃游戏点数
    public static final String EliminateGameUpGamePoint =           "53006";
}