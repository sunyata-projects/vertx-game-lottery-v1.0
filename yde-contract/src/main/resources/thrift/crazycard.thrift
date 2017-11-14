namespace java com.xt.yde.thrift.card.crazy

/**
  * 发牌信息
  */
struct CrazyCards {
  1: string cardId, //本套牌ID
  2: list<i32> center,
  3: list<i32> left,
  4: list<i32> right,
  5: i32 leftOne,
  6: i32 rightOne,
  7: list<i32> centerThree,
  8: i32 bombNums;
}

service CrazyCardsService {
  CrazyCards getCards(1:i32 prizeLevel);

}