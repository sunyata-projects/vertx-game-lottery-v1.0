package com.xt.landlords.ai;

import com.xt.landlords.ai.trusteeship.TrusteeshipAI;
import com.xt.landlords.ai.util.complex.AIConstant;
import com.xt.landlords.ai.util.complex.CaipiaoAI;
import com.xt.landlords.ai.util.complex.GameStatus;
import com.xt.landlords.ai.util.myutil.AIUtil;
import com.xt.landlords.ai.util.myutil.CardType;
import com.xt.yde.thrift.ai.AIService;
import com.xt.yde.thrift.ai.CheckCards;
import com.xt.yde.thrift.ai.ShowCards;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.trylogic.spring.boot.thrift.annotation.ThriftController;

import java.util.*;
//import ru.trylogic.spring.boot.thrift.annotation.*;
//import ru.*

/**
 * Created by aleksandr on 01.09.15.
 */
@Component
@ThriftController("/ai")
public class AIServiceHandler implements AIService.Iface {

    Logger log = LoggerFactory.getLogger(AIServiceHandler.class);

    @Override
    public int checkCards(CheckCards checkCards) {
        // TODO Auto-generated method stub
        log.info("AI checkCards");
        long t1 = System.currentTimeMillis();
        int result = 0;
        if (checkCards.getLastPlace() > 0 && checkCards.getNowPlace() > 0) {//位置参数合法
            if (checkCards.getLastPlace() == checkCards.getNowPlace()) {//转一圈，同玩家
                if (checkCards.getPlayCards() != null && checkCards.getPlayCards().size() > 0) {//出牌不为空
                    CardType playCardsType = AIUtil.jugdeType(checkCards.getPlayCards());
                    if (playCardsType != CardType.c0) {
                        result = 1;
                    } else {//出牌不合法
                        log.info("PlayCards={}, playCardsType={}", checkCards.getPlayCards(), playCardsType);
                        result = 0;
                    }
                } else {//出牌为空, 不出牌
                    log.info("invalid cards. PlayCards={}", checkCards.getPlayCards());
                    result = 0;
                }
            } else {//不同玩家
                if (checkCards.getPlayCards() == null || checkCards.getPlayCards().size() == 0) {////出牌为空, 不出牌, 合法
                    result = 1;
                } else {//出牌不为空,比大小
                    if (checkCards.getLastCards() == null || checkCards.getLastCards().size() == 0) {//上一手牌为空，不合法
                        result = 0;
                        log.info("lastcards invalid, lastcards = {}", checkCards.getLastCards());
                    } else {
                        result = AIUtil.checkCards(checkCards.getPlayCards(), checkCards.getLastCards());
                    }
                }
            }
        } else {//位置参数不合法
            log.info("invalid params. lastplace={}, nowplace={}", checkCards.getLastPlace(), checkCards.getNowPlace());
            result = 0;
        }

        long t2 = System.currentTimeMillis();
        if (t2 - t1 > 20) {
            log.info("checkCards spend {}ms, result = {}", t2 - t1, result);
        }
        return result;
    }

    @Override
    public List<Integer> playCards(ShowCards showCards) {
        long t1 = System.currentTimeMillis();
        try {
            log.info("AI playCards");
            GameStatus gameStatus = ConvertShowCards2GameStatus(showCards);
            if (null == gameStatus) {
                return null;
            }

            byte[] outHand = null;

            if (showCards.getNowPlace() == 1) {
                outHand = TrusteeshipAI.getInstance().playGame(gameStatus,showCards.getTotalBombNumber());
                log.info("托管AI...");
            } else {
                int flag = showCards.getAiVersionFlag();
                log.info("农民AI...输赢标志，flag＝{}!注：0－难版本（输），1－简单版本（赢）", flag);
                if (flag == 0) {//难
                    outHand = CaipiaoAI.getInstance().playGame(gameStatus,showCards.getTotalBombNumber());
                } else if (flag == 1) {//容易
                    outHand = CaipiaoAI.getInstance().playGame(gameStatus,showCards.getTotalBombNumber());
                }
            }
//			log.info("郑outHand： {}", outHand);
            return convertByteArray2ListResult(showCards, outHand);
        } catch (Exception e) {
            log.error("playCards error!\n", e);
            return null;
        } finally {
            long t2 = System.currentTimeMillis();
            if (t2 - t1 > 20) {
                log.info("playCards spend {}ms", t2 - t1);
            }
        }
    }

    @Override
    public List<Integer> getCrazyCommonCards(ShowCards showCards) {
        long t1 = System.currentTimeMillis();
        try {
            GameStatus gameStatus = ConvertShowCards2GameStatus(showCards);
            if (null == gameStatus) {
                return null;
            }

            /////////////////////////////////////////////
//			byte[][] playerCards = new byte[3][];
//			playerCards[0] = new byte[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 3, 4, 1, 1};
//			playerCards[1] = new byte[]{1, 3, 2, 1, 1, 2, 2, 1, 1, 2, 1, 0, 0, 0, 0};
//			playerCards[2] = new byte[]{2, 0, 1, 2, 2, 1, 1, 2, 2, 1, 2, 1, 0, 0, 0};
////			gameStatus.setPlayerCards(playerCards);
//			GameStatus gameStatus = new GameStatus(playerCards, null, 0, 0, 0);
            /////////////////////////////////////////////


            //找出所有玩家手中的假炸弹 1－假炸弹，0－真炸弹或无关牌
            byte[] falseBomb = new byte[15];
            for (int i = 0; i < 15; i++) {//初始化
                falseBomb[i] = 0;
            }
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 15; j++) {
                    if ((j == 13 && gameStatus.getPlayerCards()[i][j] == 1 && gameStatus.getPlayerCards()[i][14] == 0)
                            || (j == 14 && gameStatus.getPlayerCards()[i][j] == 1 && gameStatus.getPlayerCards()
                            [i][13] == 0)) {
                        falseBomb[j] = 1;
                    }
                    if (gameStatus.getPlayerCards()[i][j] == 3) {
                        falseBomb[j] = 1;
                    }
                }
            }

            //找出每个玩家手中的炸弹牌、假炸弹、无用牌
            Map[] players = new Map[3];
            for (int k = 0; k < 3; k++) {
                List<Integer> trueBombIndexList = new ArrayList<Integer>();
                List<Integer> falseBombIndexList = new ArrayList<Integer>();
                List<Integer> nonBombIndexList = new ArrayList<Integer>();
                players[k] = new HashMap<String, List<Integer>>();
                for (int i = 0; i < 15; i++) {
                    if (13 == i && gameStatus.getPlayerCards()[k][i] == 1 && gameStatus.getPlayerCards()[k][i + 1] ==
                            1) {
                        trueBombIndexList.add(13);
                        i++;
                        continue;
                    }
                    if (gameStatus.getPlayerCards()[k][i] == 4) {
                        trueBombIndexList.add(i);
                    } else {
                        if (falseBomb[i] == 1 && gameStatus.getPlayerCards()[k][i] == 1) {
                            falseBombIndexList.add(i);
                        } else if (gameStatus.getPlayerCards()[k][i] > 0) {
                            nonBombIndexList.add(i);
                        }
                    }
                }
                players[k].put("trueBomb", trueBombIndexList);
                players[k].put("flaseBomb", falseBombIndexList);
                players[k].put("nonBomb", nonBombIndexList);
            }

            //找出5张牌的数组，0、1、2对应地主牌，3、4分别对应两个农民的牌
            int[] commonCards = new int[5];
            int count = 0;
            for (int i = 0; i < 3; i++) {
                int k = (i == 0 ? 3 : 1);//地主抽三张牌
                List<Integer> tmpList = null;
                for (int j = 0; j < k; j++) {
                    //计算概率，返回1，抽炸弹牌，返回0，抽假炸弹和无用牌
                    int gailv = calculateGailv();
                    if (gailv == 1) {
                        tmpList = ((ArrayList<Integer>) players[i].get("trueBomb"));
                        if (null != tmpList && tmpList.size() > 0) {
                            commonCards[count++] = tmpList.get(0);
                            ((ArrayList<Integer>) players[i].get("trueBomb")).remove(0);
                        } else {
                            tmpList = ((ArrayList<Integer>) players[i].get("flaseBomb"));
                            if (null != tmpList && tmpList.size() > 0) {
                                commonCards[count++] = tmpList.get(0);
                                ((ArrayList<Integer>) players[i].get("flaseBomb")).remove(0);
                            } else {
                                commonCards[count++] = ((ArrayList<Integer>) players[i].get("nonBomb")).get(0);
                                ((ArrayList<Integer>) players[i].get("nonBomb")).remove(0);
                            }
                        }
                    } else {
                        //简单随机，省时，偶数时，直接取无用牌，奇数是先考虑假炸弹
                        if (System.currentTimeMillis() % 2 == 0) {
                            tmpList = ((ArrayList<Integer>) players[i].get("nonBomb"));
                            if (null != tmpList && tmpList.size() > 0) {
                                commonCards[count++] = tmpList.get(0);
                                ((ArrayList<Integer>) players[i].get("nonBomb")).remove(0);
                            } else {
                                commonCards[count++] = ((ArrayList<Integer>) players[i].get("flaseBomb")).get(0);
                                ((ArrayList<Integer>) players[i].get("flaseBomb")).remove(0);
                            }
                        } else {
                            tmpList = ((ArrayList<Integer>) players[i].get("flaseBomb"));
                            if (null != tmpList && tmpList.size() > 0) {
                                commonCards[count++] = tmpList.get(0);
                                ((ArrayList<Integer>) players[i].get("flaseBomb")).remove(0);
                            } else {
                                commonCards[count++] = ((ArrayList<Integer>) players[i].get("nonBomb")).get(0);
                                ((ArrayList<Integer>) players[i].get("nonBomb")).remove(0);
                            }
                        }
                    }
                }
            }
            //将5个牌转为List返回
            return converArray2List(showCards, commonCards);
        } catch (Exception e) {
            log.error("getCrazyCommonCards error:\n", e);
            return null;
        } finally {
            long t2 = System.currentTimeMillis();
            if (t2 - t1 > 20) {
                log.info("getCrazyCommonCards spend {}ms", t2 - t1);
            }
        }
    }

    /**
     * 计算概率
     *
     * @return 1－炸弹  0－假炸弹或无用牌
     */
    private int calculateGailv() {
        Random rand = new Random();
        float t = rand.nextFloat();
        if (t <= 0.5) {//此处0.5既炸弹的概率
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * 将数组转成列表
     *
     * @param showCards
     * @param commonCards
     * @return
     */
    private List<Integer> converArray2List(ShowCards showCards, int[] commonCards) {
//		StringBuilder sb = new StringBuilder();
//		for(int i = 0; i < commonCards.length; i++){
//			sb.append(commonCards[i]).append(",");
//		}
//		log.info("commonCards={}", sb.toString());
        List<Integer> commonCardsList = new ArrayList<Integer>();
        List<Integer> curPlayerCards = new ArrayList<Integer>();
        try {
            for (int i = 0; i < 5; i++) {
                if (i < 3) {
                    curPlayerCards = showCards.getCenterCards();
                }
                if (i == 3) {
                    curPlayerCards = showCards.getRightCards();
                }
                if (i == 4) {
                    curPlayerCards = showCards.getLeftCards();
                }
                for (int j = 0; j < curPlayerCards.size(); j++) {
                    int value = curPlayerCards.get(j);

                    if (52 == value) {
                        if (commonCards[i] == 13) {
                            commonCardsList.add(value);
                            break;
                        }
                    } else if (53 == value) {
                        if (commonCards[i] == 14) {
                            commonCardsList.add(value);
                            break;
                        }
                    } else {
                        int index = value % 13;
                        if (commonCards[i] == index) {
                            commonCardsList.add(value);
                            break;
                        }
                    }
//					if(52 == value && commonCards[i] == 13){
//						commonCardsList.add(value);
//						break;
//					}else if(53 == value && commonCards[i] == 14){
//						commonCardsList.add(value);
//						break;
//					}else{
//						int index = value % 13;
//						if(commonCards[i] == index){
//							commonCardsList.add(value);
//							break;
//						}
//					}
                }
            }
        } catch (Exception e) {
            curPlayerCards = null;
            log.error("converArray2List error!\n", e);
        }
        log.info("CrazyCommonCards={}", commonCardsList);
        return commonCardsList;
    }

    /**
     * 将郑蓝舟返回的出牌结果数组，转换为游戏协议中的数值，并转为列表类型
     *
     * @param showCards
     * @param outHand
     * @return
     */
    private List<Integer> convertByteArray2ListResult(ShowCards showCards, byte[] outHand) {
        List<Integer> outHandList = new ArrayList<Integer>();
        List<Integer> curPlayerCards = new ArrayList<Integer>();
        try {
            if (1 == showCards.nowPlace) {
                curPlayerCards = showCards.getCenterCards();
            } else if (2 == showCards.nowPlace) {
                curPlayerCards = showCards.getRightCards();
            } else if (3 == showCards.nowPlace) {
                curPlayerCards = showCards.getLeftCards();
            }

            Collections.sort(curPlayerCards, new Comparator<Integer>() {
                public int compare(Integer arg0, Integer arg1) {
                    return arg0.compareTo(arg1);
                }
            });
//			log.info("curPlayerCards={}", curPlayerCards);


            for (int i = 0; i < curPlayerCards.size(); i++) {
                int value = curPlayerCards.get(i);
                if (52 == value && outHand[13] > 0) {
                    outHandList.add(value);
                    outHand[13]--;
                } else if (53 == value && outHand[14] > 0) {
                    outHandList.add(value);
                    outHand[14]--;
                } else {
                    int index = value % 13;
                    if (outHand[index] > 0) {
                        outHandList.add(value);
                        outHand[index]--;
                    }
                }
            }
            log.info("转后outHandList： {}", outHandList);
            return outHandList;
        } catch (Exception e) {
            log.error("convertByteArray2ListResult error!\n", e);
            return null;
        }
    }

    /**
     * 将游戏协议改成郑蓝舟的算法所用对象
     *
     * @param showCards
     * @return
     */
    private GameStatus ConvertShowCards2GameStatus(ShowCards showCards) {
        byte[][] playerCards = new byte[AIConstant.PLAYER_NUM][AIConstant.CARD_TYPE];//玩家牌型
        byte[] outHand = new byte[AIConstant.CARD_TYPE];//最后一手打出的牌
        int outHandPlayer = -1;//打出最后一手牌的玩家
        int curPlayer = -1; //当前应该出牌的玩家
        int bombCount = showCards.getCurrentBombNumber();//已经打出的炸弹数
        log.debug("当前打出炸弹数：{},总炸弹数{}", bombCount, showCards.getTotalBombNumber());

        List<Integer> tempCardList = new ArrayList<Integer>();
        try {
            //0为地主牌
            log.debug("centerCards {}", showCards.getCenterCards());
            if (showCards.getCenterCards() != null) {
                for (int i = 0; i < (tempCardList = showCards.getCenterCards()).size(); i++) {
                    int value = ConvertCardValue(tempCardList.get(i));
                    playerCards[0][value]++;
                }
            }
            log.debug("郑playerCards[地主]： {}", playerCards[0]);

            //1为地主下家
            log.debug("rightCards {}", showCards.getRightCards());
            if (showCards.getRightCards() != null) {
                for (int i = 0; i < (tempCardList = showCards.getRightCards()).size(); i++) {
                    int value = ConvertCardValue(tempCardList.get(i));
                    playerCards[1][value]++;
                }
            }
            log.debug("郑playerCards[地主下家]： {}", playerCards[1]);

            //2为地主上家
            log.debug("leftCards {}", showCards.getLeftCards());
            if (showCards.getLeftCards() != null) {
                for (int i = 0; i < (tempCardList = showCards.getLeftCards()).size(); i++) {
                    int value = ConvertCardValue(tempCardList.get(i));
                    playerCards[2][value]++;
                }
            }
            log.debug("郑playerCards[地主上家]： {}", playerCards[2]);

            // 最后一手打出的牌
            log.debug("lastCards {}", showCards.getLastCards());
            if (showCards.getLastCards() != null) {
                for (int i = 0; i < (tempCardList = showCards.getLastCards()).size(); i++) {
                    int value = ConvertCardValue(tempCardList.get(i));
                    outHand[value]++;
                }
            }
            log.info("最后一手打出的牌]： {}", outHand);

            log.debug("lastPlace {}", showCards.getLastPlace());
            outHandPlayer = showCards.getLastPlace() - 1;
            log.info("打出最后一手牌的玩家]： {}", outHandPlayer);

            log.debug("nowPlace {}", showCards.getNowPlace());
            curPlayer = showCards.getNowPlace() - 1;
            log.info("当前应该出牌的玩家]： {}", curPlayer);

            GameStatus gameStatus = new GameStatus(playerCards, outHand, outHandPlayer, curPlayer, bombCount);
            return gameStatus;
        } catch (Exception e) {
            log.error("ConvertShowCards2GameStatus error!\n", e);
            return null;
        }
    }

    /**
     * 将游戏服务的牌值，转为郑蓝舟算法的值
     *
     * @param card
     * @return
     */
    private int ConvertCardValue(int card) {
        int value = -1;
        if (52 == card) {
            value = 13;
        } else if (53 == card) {
            value = 14;
        } else {
            value = card % 13;
        }
        return value;
    }
}