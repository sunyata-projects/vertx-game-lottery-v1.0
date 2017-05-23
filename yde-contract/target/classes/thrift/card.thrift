namespace java com.xt.yde.thrift.card

/**
  * 发牌信息
  */
struct Cards {
  1: string cardId, //本套牌ID
  2: list<i32> centerCards, //地主牌
  3: list<i32> rightCards, //右侧农民牌
  4: list<i32> leftCards, //左侧农民牌
  5: list<i32> underCards //底牌
}

service DBCardsService {
  /**
    * 获取发牌信息
    * gameType：游戏类型（常规：1、疯狂：2、积分：3）
    * winLevel：奖励等级
    * bombNum：炸弹数量
    * grade：难度等级
    */
  Cards getCards(1:i32 gameType, 2:i32 winLevel, 3:i32 bombNum, 4:i32 grade);
  
  /**
    * 根牌库ID获取发牌信息
    * cardId：牌库ID
    */
  Cards getCardsById(1:string cardId);
  Cards getFCardsById(1:string cardId);//疯狂赛
}