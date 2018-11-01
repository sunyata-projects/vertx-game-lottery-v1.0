namespace java com.xt.yde.thrift.card.rank

/**
  * 发牌信息
  */
struct RankCards {
  1: string cardId, //本套牌ID
  2: list<i32> center,
  3: list<i32> left,
  4: list<i32> right,
  5: list<i32> under,
  6: i32 bombNumbers,//炸弹数量
}

service RankCardsService {
  /**
    * 获取发牌信息
    * lose：是否必输
    * bombNumbers:炸弹数量
    */
  RankCards getCards(1:i32 prizeLevel);

}