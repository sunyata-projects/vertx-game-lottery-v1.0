namespace java com.xt.yde.thrift.card.mission

/**
  * 发牌信息
  */
struct MissionCards {
  1: string cardId, //本套牌ID
  2: list<i32> center,
  3: list<i32> left,
  4: list<i32> right,
  5: list<i32> under
}

service MissionCardsService {
  /**
    * 获取发牌信息
    * lose：是否必输入
    */
  MissionCards getCards(1:bool lose,2:i32 missionIndex);

}