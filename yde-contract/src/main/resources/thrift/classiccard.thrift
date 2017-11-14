namespace java com.xt.yde.thrift.card.classic

/**
  * 发牌信息
  */
struct ClassicCards {
  1: string cardId, //本套牌ID
  2: list<i32> center,
  3: list<i32> left,
  4: list<i32> right,
  5: list<i32> under,
  6: i32 bombNumbers,//炸弹数量
}

service ClassicCardsService {
  /**
    * 获取发牌信息
    */
  ClassicCards getCards(1:i32 prizeLevel);

}