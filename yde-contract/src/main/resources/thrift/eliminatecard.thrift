namespace java com.xt.yde.thrift.card.puzzle

/**
  * 发牌信息
  */
struct EliminateCards {
  1: string cardId, //本套牌ID
  2: list<list<i32>> cards //拼图牌
}

service EliminateCardsService {
  /**
    * 获取发牌信息
    * awardLevel：奖励等级
    * bombNum：炸弹数量
    */
  EliminateCards getCards(1:i32 awardLevel,2:i32 bombNum);
}