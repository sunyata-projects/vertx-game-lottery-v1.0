namespace java com.xt.yde.thrift.ai
/**
  * 验证出牌
  */
struct CheckCards {
  1: list<i32> lastCards, //上家出牌
  2: list<i32> playCards, //当前出牌
  3: i32 lastPlace //上一出牌人位置，1地主 2右边农民 3左边农民
  4: i32 nowPlace //当前出牌人位置，1地主 2右边农民 3左边农民
}

/**
  * 出牌信息
  */
struct ShowCards {
  1: list<i32> centerCards, //地主剩余牌
  2: list<i32> rightCards, //右侧农民剩余牌
  3: list<i32> leftCards, //左侧农民剩余牌
  4: list<i32> lastCards, //上家出牌
  5: i32 lastPlace, //上一出牌人位置，1地主 2右边农民 3左边农民
  6: i32 nowPlace, //当前出牌人位置，1地主 2右边农民 3左边农民
  7: i32 totalBombNumber, //总炸弹数，地主输-1，赢具体炸弹数
  8: i32 currentBombNumber, //当前打出的炸弹数
  9: i32 aiVersionFlag  //AI版本标志，0－难版本（输），1－简单版本（赢）
}

service AIService {
  /**
    * 返回1为合法，0为非法
    */
  i32 checkCards(1:CheckCards checkCards);

  /**
    * 返回List，当前出牌人所出牌列表
    */
  list<i32> playCards(1:ShowCards showCards);

  /**
    * 返回List，疯狂赛5张底牌
    */
  list<i32> getCrazyCommonCards(1:ShowCards showCards);
}