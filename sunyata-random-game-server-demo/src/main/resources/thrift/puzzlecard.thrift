namespace java com.xt.yde.thrift.card.puzzle

/**
  * 发牌信息
  */
struct PuzzleCards {
  1: string cardId, //本套牌ID
  2: list<list<list<i32>>> cards //拼图牌
}

service PuzzleCardsService {
  /**
    * 获取发牌信息
    * gameType：游戏类型（常规：1、疯狂：2、积分：3）
    * winLevel：奖励等级
    * bombNum：炸弹数量
    * grade：难度等级
    */
  PuzzleCards getCards(1:i32 grade);
}