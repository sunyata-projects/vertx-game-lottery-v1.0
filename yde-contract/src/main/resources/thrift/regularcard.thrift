namespace java com.xt.yde.thrift.card.puzzle

/**
  * 发牌信息
  */
struct RegularCards {
  1: string cardId, //本套牌ID
  2: list<i32> center,
  3: list<i32> left,
  4: list<i32> right,
  5: list<i32> under
}

service RegularCardsService {
  /**
    * 获取发牌信息
    * awardLevel：奖励等级
    */
  RegularCards getCards17();

  RegularCards getCards37(1:i32 prizeRandom,2:string centerId);

}