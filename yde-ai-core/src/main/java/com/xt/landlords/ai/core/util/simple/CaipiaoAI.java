package com.xt.landlords.ai.core.util.simple;

import com.xt.landlords.ai.core.util.complex.*;

import java.util.*;

/**
 * Created by leo on 17/11/9.
 */

public class CaipiaoAI {

    private static CaipiaoAI instance = new CaipiaoAI();

    public static CaipiaoAI getInstance() {
        return instance;
    }

    private CaipiaoAI() {
    }

    /**
     * @param status
     *            ��ǰ��Ϸ״̬
     * @param bombCount
     *            ������Ϸ�����ը����-1��ʾ�����䣬��С��0��ʾ����Ӯ���ұ���һ�������ը����
     * @return
     */
    public byte[] playGame(GameStatus status, int bombCount) {
        GameStatusAdv statusAdv = new GameStatusAdv(status);
        Hand hand = playGameResult(statusAdv, bombCount);
        byte[] outHand = AIUtils.handToCards(hand);
        status.updateWithStatusAdv(statusAdv);
        return outHand;
    }

    /**
     * 叫分接口
     *
     * @param status
     *            位置0 表示玩家，1,2分別表示機器人A，B
     * @return 0 不叫分 、1 叫一分、 2 叫两分 、 3叫三分
     */
    public int callGame(GameStatus status) {
        int calledPoint = status.getCalledPiont();
        int calledPlayer = status.getCalledPlayer();
        if (calledPoint > 0 && calledPoint != 0) {// 非玩家叫分,包括叫1分和叫2分,說明該機器人比玩家牌好，比當前機器人差，直接叫3分
            return 3;
        } else {
            byte[][] playerCards = new byte[AIConstant.PLAYER_NUM][];
            playerCards[0] = Arrays.copyOf(status.getPlayerCards()[0], AIConstant.CARD_TYPE);
            playerCards[1] = Arrays.copyOf(status.getPlayerCards()[1], AIConstant.CARD_TYPE);
            playerCards[2] = Arrays.copyOf(status.getPlayerCards()[2], AIConstant.CARD_TYPE);
            int aiSeat = status.getCurPlayer();
            int otherSeat = aiSeat == 1 ? 2 : 1;
            int lowestCon = PokerType.TWO.ordinal();

            byte[] oppCards = AIUtils.addCards(playerCards[0], playerCards[otherSeat]);
            double aiConNum = AIUtils.calControl(Arrays.copyOf(playerCards[aiSeat], playerCards[aiSeat].length),
                    oppCards, lowestCon);
            byte[] oppCards2 = AIUtils.addCards(playerCards[0], playerCards[aiSeat]);
            double aiConNum2 = AIUtils.calControl(Arrays.copyOf(playerCards[otherSeat], playerCards[aiSeat].length),
                    oppCards2, lowestCon);
            double playerConNum1 = AIUtils.calControl(Arrays.copyOf(playerCards[0], playerCards[0].length),
                    playerCards[aiSeat], lowestCon);
            double playerConNum2 = AIUtils.calControl(Arrays.copyOf(playerCards[0], playerCards[0].length),
                    playerCards[otherSeat], lowestCon);

            GameStatus status2 = status.makeLord(otherSeat);
            status = status.makeLord(aiSeat);
            GameStatusAdv statusAdv = new GameStatusAdv(status, 1);
            GameStatusAdv statusAdv2 = new GameStatusAdv(status2, 1);

            HandMapSummary[] summarys = new HandMapSummary[AIConstant.PLAYER_NUM];
            summarys[0] = findBestHandMap(statusAdv.getPlayerCards()[3 - aiSeat].getCards(), statusAdv, lowestCon,
                    3 - aiSeat, 0.0, false);// 玩家
            summarys[aiSeat] = findBestHandMap(statusAdv.getPlayerCards()[0].getCards(), statusAdv, lowestCon, 0,
                    aiConNum, false);// 當前機器人
            summarys[otherSeat] = findBestHandMap(statusAdv.getPlayerCards()[aiSeat].getCards(), statusAdv, lowestCon,
                    aiSeat, 0.0, false);// 另外一個機器人
            summarys[0].changeEffectiveHands(-playerConNum1);
            summarys[aiSeat].changeEffectiveHands(-aiConNum);
            summarys[otherSeat].changeEffectiveHands(-aiConNum2);
            if (compareSummary(summarys[aiSeat], summarys[0])) {// 比玩家的牌要好
                if (compareSummary(summarys[aiSeat], summarys[otherSeat])) {
                    HandMapSummary[] summarys2 = new HandMapSummary[AIConstant.PLAYER_NUM];
                    summarys2[0] = findBestHandMap(statusAdv2.getPlayerCards()[3 - otherSeat].getCards(), statusAdv2,
                            lowestCon, 3 - otherSeat, 0.0, false);// 玩家
                    summarys2[aiSeat] = findBestHandMap(statusAdv2.getPlayerCards()[0].getCards(), statusAdv2,
                            lowestCon, 0, 0.0, false);// 當前機器人
                    summarys2[otherSeat] = findBestHandMap(statusAdv2.getPlayerCards()[aiSeat].getCards(), statusAdv2,
                            lowestCon, otherSeat, aiConNum2, false);// 另外一個機器人
                    summarys2[0].changeEffectiveHands(-playerConNum2);
                    summarys2[aiSeat].changeEffectiveHands(-aiConNum);
                    summarys2[otherSeat].changeEffectiveHands(-aiConNum2);

                    if (compareSummary(summarys2[otherSeat], summarys2[0])) {
                        if (!compareSummary(summarys2[aiSeat], summarys[otherSeat])
                                && !compareSummary(summarys[aiSeat], summarys2[otherSeat])) {
                            if (calledPlayer == 0 && calledPoint == 2) {// 玩家叫2分
                                return 0;
                            } else {
                                return calledPoint + 1;
                            }

                        } else {
                            return 3;
                        }
                    } else {
                        return 3;
                    }
                } else {
                    return calledPoint + 1;
                }
            } else {
                return 0;
            }

        }
    }

    public int callGameResult(GameStatus status) {
        int calledPoint = status.getCalledPiont();
        int curPlayer = status.getCurPlayer();
        int calledPlayer = status.getCalledPlayer();
        byte[][] playerCards = status.getPlayerCards();
        int countOfTwo = playerCards[curPlayer][PokerType.TWO.ordinal()];
        int countOfAce = playerCards[curPlayer][PokerType.ACE.ordinal()];
        int countOfBjoker = playerCards[curPlayer][PokerType.BJOKER.ordinal()];
        int countOfRjoker = playerCards[curPlayer][PokerType.RJOKER.ordinal()];
        // 對炸彈的判斷,暫時沒加
        double t_value = 0;
        int reslut = 0;
        double valueOfJoker = 0;
        if (countOfBjoker == 1 && countOfRjoker == 1) {
            valueOfJoker = 4;
        } else if (countOfBjoker == 1) {
            valueOfJoker = 1.4;
        } else if (countOfRjoker == 1) {
            valueOfJoker = 2;
        } else {
            valueOfJoker = 0.8;
        }
        double valueOfTwo = 0;
        switch (countOfTwo) {
            case 0:
                valueOfTwo = 0.9;
                break;
            case 1:
                valueOfTwo = 1.2;
                break;
            case 2:
                valueOfTwo = 2.2;
                break;
            case 3:
                valueOfTwo = 4;
                break;
            case 4:
                valueOfTwo = 5;
                break;
        }
        double valueOfAce = 0;
        if (countOfTwo == 0) {
            valueOfAce = 0.2;
            t_value = valueOfTwo * valueOfJoker + countOfAce * valueOfAce;
        } else {
            double t_valueOfTwo = valueOfTwo * valueOfJoker;
            valueOfAce = t_valueOfTwo * 0.18;
            t_value = t_valueOfTwo + valueOfAce * countOfAce;
        }
        if (calledPoint == 0) {
            int temp = calledPlayer - curPlayer;
            if (temp == 1 || temp == -2) {
                t_value += 2 * valueOfAce;
            } else if (temp == -1 || temp == 2) {
                t_value += valueOfAce;
            }
        }
        if (t_value > 4.4) {
            reslut = 3;
        } else if (t_value > 3.5) {
            reslut = 2;
        } else if (t_value > 2.2) {
            reslut = 1;
        } else {
            reslut = 0;
        }
        if (calledPoint == 0) {
            Random rd = new Random(47);
            int num = rd.nextInt(100);
            switch (reslut) {
                case 0:
                    if (t_value > 2 && num > 59) {// 40%
                        reslut = 1;
                    }
                    break;
                case 1:
                    if (num > 89) {// 10%的概率
                        reslut = 0;
                    } else if (num > 74) {// 15%
                        reslut = 2;
                    }
                    break;
                case 2:
                    if (num > 94) {// 5%的概率
                        reslut = 0;
                    } else if (num > 79) {// 15%
                        reslut = 1;
                    } else if (num > 64) {// 15%
                        reslut = 3;
                    }
                    break;
                case 3:
                    if (num > 69) {// 30% 概率
                        reslut = 2;
                    }
                    break;
            }
        }
        if (reslut < calledPoint) {
            return 0;
        } else {
            return reslut;
        }
    }

    public int callGameResult(GameStatus status, boolean probability) {
        int calledPoint = status.getCalledPiont();
        int curPlayer = status.getCurPlayer();
        int calledPlayer = status.getCalledPlayer();
        byte[][] playerCards = status.getPlayerCards();
        int countOfTwo = playerCards[curPlayer][PokerType.TWO.ordinal()];
        int countOfAce = playerCards[curPlayer][PokerType.ACE.ordinal()];
        int countOfBjoker = playerCards[curPlayer][PokerType.BJOKER.ordinal()];
        int countOfRjoker = playerCards[curPlayer][PokerType.RJOKER.ordinal()];
        // 對炸彈的判斷,暫時沒加
        double t_value = 0;
        int reslut = 0;
        double valueOfJoker = 0;
        if (countOfBjoker == 1 && countOfRjoker == 1) {
            valueOfJoker = 4;
        } else if (countOfBjoker == 1) {
            valueOfJoker = 1.4;
        } else if (countOfRjoker == 1) {
            valueOfJoker = 2;
        } else {
            valueOfJoker = 0.8;
        }
        double valueOfTwo = 0;
        switch (countOfTwo) {
            case 0:
                valueOfTwo = 0.9;
                break;
            case 1:
                valueOfTwo = 1.2;
                break;
            case 2:
                valueOfTwo = 2.2;
                break;
            case 3:
                valueOfTwo = 4;
                break;
            case 4:
                valueOfTwo = 5;
                break;
        }
        double valueOfAce = 0;
        if (countOfTwo == 0) {
            valueOfAce = 0.2;
            t_value = valueOfTwo * valueOfJoker + countOfAce * valueOfAce;
        } else {
            double t_valueOfTwo = valueOfTwo * valueOfJoker;
            valueOfAce = t_valueOfTwo * 0.18;
            t_value = t_valueOfTwo + valueOfAce * countOfAce;
        }
        if (calledPoint == 0) {
            int temp = calledPlayer - curPlayer;
            if (temp == 1 || temp == -2) {
                t_value += 2 * valueOfAce;
            } else if (temp == -1 || temp == 2) {
                t_value += valueOfAce;
            }
        }
        if (t_value > 4.4) {
            reslut = 3;
        } else if (t_value > 3.5) {
            reslut = 2;
        } else if (t_value > 2.2) {
            reslut = 1;
        } else {
            reslut = 0;
        }
        if (calledPoint == 0 && !probability) {
            Random rd = new Random(47);
            int num = rd.nextInt(100);
            switch (reslut) {
                case 0:
                    if (t_value > 2 && num > 59) {// 40%
                        reslut = 1;
                    }
                    break;
                case 1:
                    if (num > 89) {// 10%的概率
                        reslut = 0;
                    } else if (num > 74) {// 15%
                        reslut = 2;
                    }
                    break;
                case 2:
                    if (num > 94) {// 5%的概率
                        reslut = 0;
                    } else if (num > 79) {// 15%
                        reslut = 1;
                    } else if (num > 64) {// 15%
                        reslut = 3;
                    }
                    break;
                case 3:
                    if (num > 69) {// 30% 概率
                        reslut = 2;
                    }
                    break;
            }
        }
        if (reslut <= calledPoint) {
            return 0;
        } else {
            return reslut;
        }
    }

    public Hand playGameResult(GameStatusAdv status, int bombCount) {
        CardsSummary[] playerCards = status.getPlayerCards();

        int aiSeat = status.getCurPlayer();

        int lowestCon = calLowestControl(status);
        byte[] aiCards = playerCards[aiSeat].getCards();
        byte[] oppCards = aiSeat == AIConstant.LORD ? AIUtils.addCards(playerCards[AIConstant.UP_FARMER].getCards(),
                playerCards[AIConstant.DOWN_FARMER].getCards()) : playerCards[AIConstant.LORD].getCards();
        double aiConNum = AIUtils.calControl(aiCards, oppCards, lowestCon);
        HandMapSummary[] summarys = new HandMapSummary[AIConstant.PLAYER_NUM];
        boolean lordOnly1Card = playerCards[AIConstant.LORD].getNum() == 1;
        for (int i = 0; i < summarys.length; i++) {
            double conNum = (i == status.getCurPlayer() ? aiConNum : 0.0);
            summarys[i] = findBestHandMap(playerCards[i].getCards(), status, lowestCon, i, conNum, lordOnly1Card);
        }

        Hand hand = null;
        byte[][] cards = new byte[3][15];
        int pokerTypeNum = 0;
        for (int i = 0; i < 3; i++) {
            cards[i] = playerCards[i].getCards();
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 15; j++) {
                if (cards[i][j] > 0)
                    pokerTypeNum++;
            }
        }
        if (pokerTypeNum <= 12) {
            // �ȵ��ñ�Ӯ����
            NewHand nHand = new AISearch(status).SearchGoodMove();
            // NewHand nHand=new AISearch(status).SearchGoodMove2();
            if (nHand != null)// ��������н��ͷ��ؽ��
            {
                status.setStatusType(nHand.returnType, nHand.getBombCount());
                if (nHand.returnType == NewHand.StatusType.win) {
                    status.takeOut(nHand.hand);
                    return nHand.hand;
                }
            }

        }
        if (status.getCurPlayer() == AIConstant.LORD) {
            if (status.getCurPlayer() == status.getOutHandPlayer()) {
                hand = lordPlayFree(summarys, status, lowestCon);
            } else {
                hand = lordPlayHigher(summarys, status, lowestCon);
            }
        } else {
            // ������ԭ����������
            if (status.getCurPlayer() == AIConstant.DOWN_FARMER) {
                hand = downFarmerPlay(summarys, status, lowestCon, aiConNum);
            } else {
                hand = upFarmerPlay(summarys, status, lowestCon, aiConNum);
            }
            // check bomb when lord should win
            // if (bombCount >= 0 && status.getCurPlayer() !=
            // status.getOutHandPlayer()) {
            // int lordBombNum =
            // AIUtils.getBombNum(playerCards[AIConstant.LORD]);
            // if (hand == null) {
            // List<Hand> aiBombs =
            // AIUtils.getAllBomb(playerCards[status.getCurPlayer()]);
            // if (aiBombs.size() > 0 && status.getBombCount() + lordBombNum <
            // bombCount
            // && status.getOutHandPlayer() == AIConstant.LORD
            // && summarys[AIConstant.LORD].getRealHands() - lordBombNum <= 2) {
            // boolean useBomb = false;
            // if (status.getCurPlayer() == AIConstant.UP_FARMER) {
            // useBomb = true;
            // } else {
            // Hand higher = findHigherHand(status.getOutHand(),
            // playerCards[AIConstant.UP_FARMER]);
            // if (higher == null) {
            // useBomb = true;
            // }
            // }
            // if (useBomb) {
            // if (AIUtils.isBomb(status.getOutHand())) {
            // for (Hand bomb : aiBombs) {
            // if (bomb.getKey().ordinal() >
            // status.getOutHand().getKey().ordinal()) {
            // hand = bomb;
            // break;
            // }
            // }
            // } else {
            // hand = aiBombs.get(0);
            // }
            // }
            // }
            // } else {
            // if (AIUtils.isBomb(hand) && status.getBombCount() + lordBombNum
            // >= bombCount) {
            // hand = null;
            // }
            // }
            // }
        }
        // check
        if (status.getCurPlayer() == status.getOutHandPlayer()) {
            if (hand == null) {
                System.out.println("-----------error! hand=null " + status);
                hand = findLowestHand(summarys[aiSeat].getHandMap());
            }
        } else {
            if (hand != null && hand.getKey() == PokerType.BJOKER && hand.getType() == HandType.SOLO
                    && status.getOutHand().getKey().ordinal() < PokerType.TWO.ordinal()) {
                if (playerCards[AIConstant.LORD].get(PokerType.TWO.ordinal()) == 1
                        && playerCards[aiSeat].get(PokerType.TWO.ordinal()) >= 2) {
                    Map<HandType, List<Hand>> lordHandMap = summarys[AIConstant.LORD].getHandMap();
                    List<Hand> lordPairs = lordHandMap.get(HandType.PAIR);
                    boolean replaceTwo = false;
                    if (lordPairs == null || lordPairs.size() == 0) {
                        replaceTwo = true;
                    } else if (lordPairs.size() == 1) {
                        Hand lordPair = lordPairs.get(0);
                        Map<HandType, List<Hand>> aiHandMap = summarys[aiSeat].getHandMap();
                        Hand aiHighPair = findHigherHandInMapIgnoreBomb(aiHandMap, lordPair);
                        if (aiHighPair != null && aiHighPair.getKey() != PokerType.TWO) {
                            replaceTwo = true;
                        }
                    }
                    if (replaceTwo) {
                        hand = new Hand(HandType.SOLO, PokerType.TWO, 1, null);
                    }
                }
            }
            if (hand != null) {
                if (!isHandHigherThan(hand, status.getOutHand())) {
                    System.out.println("-----------error! " + hand + "<=" + status);
                    hand = null;
                }
            }
        }
        // update status
        status.takeOut(hand);
        return hand;
    }

    public Hand playGameResult(GameStatusAdv status, int bombCount, int typeNum) {
        CardsSummary[] playerCards = status.getPlayerCards();

        int aiSeat = status.getCurPlayer();

        int lowestCon = calLowestControl(status);
        byte[] aiCards = playerCards[aiSeat].getCards();
        byte[] oppCards = aiSeat == AIConstant.LORD ? AIUtils.addCards(playerCards[AIConstant.UP_FARMER].getCards(),
                playerCards[AIConstant.DOWN_FARMER].getCards()) : playerCards[AIConstant.LORD].getCards();
        double aiConNum = AIUtils.calControl(aiCards, oppCards, lowestCon);
        HandMapSummary[] summarys = new HandMapSummary[AIConstant.PLAYER_NUM];
        boolean lordOnly1Card = playerCards[AIConstant.LORD].getNum() == 1;
        for (int i = 0; i < summarys.length; i++) {
            double conNum = (i == status.getCurPlayer() ? aiConNum : 0.0);
            summarys[i] = findBestHandMap(playerCards[i].getCards(), status, lowestCon, i, conNum, lordOnly1Card);
        }

        Hand hand = null;
        if (status.getCurPlayer() == AIConstant.LORD) {
            if (status.getCurPlayer() == status.getOutHandPlayer()) {
                hand = lordPlayFree(summarys, status, lowestCon);
            } else {
                hand = lordPlayHigher(summarys, status, lowestCon);
            }
        } else {
            byte[][] cards = new byte[3][15];
            int pokerTypeNum = 0;
            for (int i = 0; i < 3; i++) {
                cards[i] = playerCards[i].getCards();
            }
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 15; j++) {
                    if (cards[i][j] > 0)
                        pokerTypeNum++;
                }
            }
            if (pokerTypeNum <= typeNum) {
                // �ȵ��ñ�Ӯ����
                NewHand nHand = new AISearch(status).SearchGoodMove();
                // NewHand nHand=new AISearch(status).SearchGoodMove2();
                if (nHand != null)// ��������н��ͷ��ؽ��
                {
                    status.setStatusType(nHand.returnType, nHand.getBombCount());
                    if (nHand.returnType == NewHand.StatusType.win) {
                        status.takeOut(nHand.hand);
                        return nHand.hand;
                    }
                }
            }
            // ������ԭ����������
            if (status.getCurPlayer() == AIConstant.DOWN_FARMER) {
                hand = downFarmerPlay(summarys, status, lowestCon, aiConNum);
            } else {
                hand = upFarmerPlay(summarys, status, lowestCon, aiConNum);
            }

        }
        // check
        if (status.getCurPlayer() == status.getOutHandPlayer()) {
            if (hand == null) {
                System.out.println("-----------error! hand=null " + status);
                hand = findLowestHand(summarys[aiSeat].getHandMap());
            }
        } else {
            if (hand != null && hand.getKey() == PokerType.BJOKER && hand.getType() == HandType.SOLO
                    && status.getOutHand().getKey().ordinal() < PokerType.TWO.ordinal()) {
                if (playerCards[AIConstant.LORD].get(PokerType.TWO.ordinal()) == 1
                        && playerCards[aiSeat].get(PokerType.TWO.ordinal()) >= 2) {
                    Map<HandType, List<Hand>> lordHandMap = summarys[AIConstant.LORD].getHandMap();
                    List<Hand> lordPairs = lordHandMap.get(HandType.PAIR);
                    boolean replaceTwo = false;
                    if (lordPairs == null || lordPairs.size() == 0) {
                        replaceTwo = true;
                    } else if (lordPairs.size() == 1) {
                        Hand lordPair = lordPairs.get(0);
                        Map<HandType, List<Hand>> aiHandMap = summarys[aiSeat].getHandMap();
                        Hand aiHighPair = findHigherHandInMapIgnoreBomb(aiHandMap, lordPair);
                        if (aiHighPair != null && aiHighPair.getKey() != PokerType.TWO) {
                            replaceTwo = true;
                        }
                    }
                    if (replaceTwo) {
                        hand = new Hand(HandType.SOLO, PokerType.TWO, 1, null);
                    }
                }
            }
            if (hand != null) {
                if (!isHandHigherThan(hand, status.getOutHand())) {
                    System.out.println("-----------error! " + hand + "<=" + status);
                    hand = null;
                }
            }
        }
        // update status
        status.takeOut(hand);
        return hand;
    }

    private Hand upFarmerPlay(HandMapSummary[] summarys, GameStatusAdv status, int lowestCon, double aiConNum) {
        if (status.getCurPlayer() == status.getOutHandPlayer()) {
            return upFarmerPlayFree(summarys, status, lowestCon, aiConNum);
        } else {
            return upFarmerPlayHigher(summarys, status, lowestCon, aiConNum);
        }
    }

    private Hand downFarmerPlay(HandMapSummary[] summarys, GameStatusAdv status, int lowestCon, double aiConNum) {
        if (status.getCurPlayer() == status.getOutHandPlayer()) {
            return downFarmerPlayFree(summarys, status, lowestCon, aiConNum);
        } else {
            return downFarmerPlayHigher(summarys, status, lowestCon, aiConNum);
        }
    }

    private Hand upFarmerPlayFree(HandMapSummary[] summarys, GameStatusAdv status, int lowestCon, double aiConNum) {
        CardsSummary[] allCards = status.getPlayerCards();
        CardsSummary aiCards = allCards[status.getCurPlayer()];
        CardsSummary lordCards = allCards[AIConstant.LORD];

        Hand hand = null;
        if (lordCards.getNum() == 1) {
            return farmerPlayFreeWithLord1Card(summarys, status, lowestCon);
        }

        hand = getWinHandForFarmer(summarys, status);
        if (hand != null && summarys[status.getCurPlayer()].getRealHands() > 1) {
            if (hand.size() == lordCards.getNum()) {
                Hand lordHigher = findHigherHand(hand, lordCards);
                if (lordHigher != null && !AIUtils.isBomb(lordHigher)) {
                    hand = null;
                }
            }
        }
        if (hand != null) {
            return hand;
        }

        boolean isGood = isGoodFarmer(summarys, status, lowestCon);
        if (isGood) {
            hand = upGoodFarmerPlayFree(summarys, status, lowestCon, aiConNum);
        } else {
            hand = upBadFarmerPlayFree(summarys, status, lowestCon, aiConNum);
        }

        if (hand != null && hand.getType() != HandType.SOLO) {
            if (hand.size() == lordCards.getNum()) {
                Hand lordHigher = findHigherHand(hand, lordCards);
                if (lordHigher != null && !AIUtils.isBomb(lordHigher)) {
                    hand = new Hand(HandType.SOLO, aiCards.getLowest(), 1, null);
                }
            }
        }
        return hand;
    }

    private Hand upFarmerPlayHigher(HandMapSummary[] summarys, GameStatusAdv status, int lowestCon, double aiConNum) {
        int other = status.getCurPlayer() == AIConstant.DOWN_FARMER ? AIConstant.UP_FARMER : AIConstant.DOWN_FARMER;
        CardsSummary[] allCards = status.getPlayerCards();
        CardsSummary aiCards = allCards[status.getCurPlayer()];
        CardsSummary lordCards = allCards[AIConstant.LORD];
        CardsSummary otherCards = allCards[other];

        HandMapSummary aiSummary = summarys[status.getCurPlayer()];
        Map<HandType, List<Hand>> handMap = aiSummary.getHandMap();
        HandMapSummary lordSummary = summarys[AIConstant.LORD];
        Map<HandType, List<Hand>> lordHandMap = lordSummary.getHandMap();

        Hand curHand = status.getOutHand();

        Hand hand = farmerEasyWin(summarys, status, curHand);
        if (hand != null) {
            return hand;
        }
        if (lordCards.getNum() == 1) {
            return farmerPlayLord1Cards(summarys, status, curHand, lowestCon);
        }

        if (lordSummary.getRealHands() == 1) {
            List<Hand> lordHighest = lordSummary.getHighestHandList();
            List<Hand> lordUnhighest = lordSummary.getUnHighestHandList();
            Hand lordHand;
            if (!lordHighest.isEmpty()) {
                lordHand = lordHighest.get(0);
            } else {
                lordHand = lordUnhighest.get(0);
            }
            if (isHandHigherThan(lordHand, curHand)) {
                if (AIUtils.isBomb(lordHand)) {
                    List<Hand> bombs = handMap.get(HandType.BOMB);
                    if (bombs != null && !bombs.isEmpty()) {
                        int bigBombNum = 0;
                        for (Hand bomb : bombs) {
                            if (bomb.getKey().ordinal() > lordHand.getKey().ordinal()) {
                                bigBombNum++;
                            }
                        }
                        if (bigBombNum > 0 && aiSummary.getRealHands() - bigBombNum == 1) {
                            return bombs.get(bombs.size() - 1);
                        }
                    }
                } else {
                    lordHand = new Hand(lordHand.getType(), lordHand.getKey().ordinal() - 1, lordHand.getLen(),
                            lordHand.getKickers());
                    hand = farmerMustPlayOverLord(summarys, status, lordHand, lowestCon);
                    if (hand != null) {
                        return hand;
                    }
                }
            }
        }

        boolean isGood = isGoodFarmer(summarys, status, lowestCon);
        if (status.getOutHandPlayer() == AIConstant.LORD) {
            if (lordSummary.getUnHighestHandList().size() - lordSummary.getExtraBomb() <= 1
                    && lordCards.getNum() <= 10) {
                return farmerMustPlayOverLord(summarys, status, curHand, lowestCon);
            }

            boolean mustHigher = false;
            if (isGood) {
                mustHigher = (curHand.getType() == HandType.SOLO_CHAIN && curHand.getLen() >= 8)
                        || (curHand.getType() != HandType.SOLO_CHAIN && curHand.getType().isChain())
                        || curHand.getType() == HandType.FOUR_DUAL_SOLO || curHand.getType() == HandType.FOUR_DUAL_PAIR
                        || (curHand.getType() == HandType.SOLO && curHand.getKey().ordinal() >= lowestCon);
            } else {
                mustHigher = (curHand.getType() != HandType.SOLO && curHand.getType() != HandType.PAIR)
                        || (curHand.getType() == HandType.SOLO && curHand.getKey().ordinal() >= lowestCon);
            }
            if (mustHigher) {
                hand = farmerMustPlay(summarys, status, curHand, lowestCon);
                if (hand != null && lordCards.getNum() > 10 && countHandControl(hand, lowestCon) >= 2
                        && hand.getKey().ordinal() - curHand.getKey().ordinal() > 2) {
                    hand = null;
                }
                return hand;
            }

            if (curHand.getType() == HandType.SOLO
                    && (curHand.getKey() == PokerType.TWO || curHand.getKey() == PokerType.BJOKER)) {
                hand = farmerPlayWhenLordOutBigSolo(status, false);
                if (hand == null) {
                    hand = farmerPlayNormal(summarys, status, curHand, lowestCon);
                }
                return hand;
            }
        } else { // curHand from down_farmer
            if (countHandControl(curHand, lowestCon) >= 1 || curHand.size() >= 4) {
                return null;
            }
        }

        if (curHand.getType() == HandType.SOLO) {
            boolean mustBlock = false;
            int lordHighest = lordCards.getHighest().ordinal();
            if (lordHighest > aiCards.getHighest().ordinal() && lordHighest > otherCards.getHighest().ordinal()
                    && lordSummary.getEffectiveHands() <= 2) {
                if (!AIUtils.hasBomb(aiCards.getCards()) && !AIUtils.hasBomb(otherCards.getCards())) {
                    mustBlock = true;
                }
            }
            int blocker = PokerType.QUEEN.ordinal();
            List<Hand> lordSolos = lordHandMap.get(HandType.SOLO);
            if (lordSolos != null) {
                for (int i = lordSolos.size() - 1; i >= 0; i--) {
                    int curSolo = lordSolos.get(i).getKey().ordinal();
                    if (curSolo <= PokerType.KING.ordinal()) {
                        blocker = curSolo;
                        break;
                    }
                }
                if (lordSolos.size() >= 3) {
                    int newBlocker = lordSolos.get(2).getKey().ordinal();
                    if (blocker > newBlocker) {
                        blocker = newBlocker;
                    }
                }
            }
            if ((mustBlock || !isGood) && curHand.getKey().ordinal() < blocker) {
                if (status.getOutHandPlayer() != AIConstant.LORD && curHand.getKey().ordinal() >= lowestCon
                        && !mustBlock) {
                    return null;
                }
                for (int i = PokerType.ACE.ordinal(); i >= blocker; i--) {
                    if (aiCards.get(i) == 1) {
                        return new Hand(HandType.SOLO, i, 1, null);
                    }
                }
                for (int i = PokerType.ACE.ordinal(); i >= blocker; i--) {
                    if (aiCards.get(i) == 2) {
                        return new Hand(HandType.SOLO, i, 1, null);
                    }
                }
                if ((status.getOutHandPlayer() == AIConstant.LORD
                        || curHand.getKey().ordinal() <= PokerType.TEN.ordinal()) && aiCards.get(PokerType.TWO) > 0) {
                    return new Hand(HandType.SOLO, PokerType.TWO, 1, null);
                }
                for (int i = blocker; i > curHand.getKey().ordinal(); i--) {
                    int num = aiCards.get(i);
                    if (num > 0 && num < 4) {
                        return new Hand(HandType.SOLO, i, 1, null);
                    }
                }
                return null;
            } else if (!isGood && status.getOutHandPlayer() == AIConstant.LORD && curHand.getKey().ordinal() >= blocker
                    && curHand.getKey().ordinal() <= PokerType.ACE.ordinal()) {
                for (int i = curHand.getKey().ordinal() + 1; i <= PokerType.TWO.ordinal(); i++) {
                    if (aiCards.get(i) > 0 && aiCards.get(i) < 4) {
                        return new Hand(HandType.SOLO, i, 1, null);
                    }
                }
            } else {
                if (curHand.getKey().ordinal() < blocker) {
                    List<Hand> aiSolos = handMap.get(HandType.SOLO);
                    if (aiSolos != null) {
                        for (Hand solo : aiSolos) {
                            int soloKey = solo.getKey().ordinal();
                            if (soloKey >= blocker && soloKey < lowestCon) {
                                return new Hand(HandType.SOLO, soloKey, 1, null);
                            }
                        }
                    }
                }
            }
            if (hand == null && status.getOutHandPlayer() == AIConstant.LORD) {
                farmerPlayNormal(summarys, status, curHand, lowestCon);
            }
        } else if (curHand.getType() == HandType.PAIR) {
            if (!isGood) {
                if (status.getOutHandPlayer() == AIConstant.LORD) {
                    return farmerMustPlay(summarys, status, curHand, lowestCon);
                } else {
                    if (curHand.getKey().ordinal() < PokerType.TEN.ordinal()) {
                        hand = farmerPlayNormal(summarys, status, curHand, lowestCon);
                        if (hand != null && hand.getKey().ordinal() >= PokerType.JACK.ordinal()) {
                            hand = null;
                        }
                    }
                    return hand;
                }
            } else {
                if (status.getOutHandPlayer() == AIConstant.LORD) {
                    return farmerPlayNormal(summarys, status, curHand, lowestCon);
                } else {
                    if (curHand.getKey().ordinal() < PokerType.TEN.ordinal()) {
                        hand = farmerPlayNormal(summarys, status, curHand, lowestCon);
                    }
                    return hand;
                }
            }
        }

        if (!isGood && status.getOutHandPlayer() != AIConstant.LORD) {
            return null;
        }

        if (status.getOutHandPlayer() == AIConstant.LORD) {
            if (lordCards.getNum() <= 6) {
                return farmerMustPlay(summarys, status, curHand, lowestCon);
            }
        }
        return farmerPlayNormal(summarys, status, curHand, lowestCon);
    }

    private Hand upGoodFarmerPlayFree(HandMapSummary[] summarys, GameStatusAdv status, int lowestCon, double aiConNum) {
        HandMapSummary aiSummary = summarys[status.getCurPlayer()];
        HandMapSummary lordSummary = summarys[AIConstant.LORD];
        Map<HandType, List<Hand>> handMap = aiSummary.getHandMap();
        Map<HandType, List<Hand>> lordHandMap = lordSummary.getHandMap();

        if (aiSummary.getUnHighestHandList().size() == 2 && aiSummary.getRealHands() == 2) {
            List<Hand> solos = handMap.get(HandType.SOLO);
            List<Hand> pairs = handMap.get(HandType.PAIR);
            if (solos != null && solos.size() == 1 && pairs != null && pairs.size() == 1) {
                if (solos.get(0).getKey().ordinal() < pairs.get(0).getKey().ordinal()) {
                    return solos.get(0);
                } else {
                    return pairs.get(0);
                }
            } else if (solos != null && solos.size() == 2) {
                return solos.get(0);
            } else if (pairs != null && pairs.size() == 2) {
                return pairs.get(0);
            } else {
                Set<Map.Entry<HandType, List<Hand>>> entrys = handMap.entrySet();
                for (Map.Entry<HandType, List<Hand>> entry : entrys) {
                    List<Hand> hands = entry.getValue();
                    if (hands != null && !hands.isEmpty()) {
                        if (entry.getKey() != HandType.SOLO && entry.getKey() != HandType.PAIR) {
                            return hands.get(0);
                        }
                    }
                }
            }
        }

        List<Hand> tmpHands = new ArrayList<Hand>();
        List<Hand> unHighestHandList = aiSummary.getUnHighestHandList();
        List<Hand> highestHandList = aiSummary.getHighestHandList();
        for (Hand unHighestHand : unHighestHandList) {
            if (findHigherHandInMapIgnoreBomb(lordHandMap, unHighestHand) == null) {
                tmpHands.add(unHighestHand);
            }
        }
        if (!tmpHands.isEmpty()) {
            Collections.sort(tmpHands, new Hand.HandComparator());
            for (Hand unHigh : tmpHands) {
                if (findHigherHandInMapIgnoreBomb(handMap, unHigh) != null) {
                    return unHigh;
                }
            }
            return tmpHands.get(0);
        }

        Hand hand = null;
        for (Hand highestHand : highestHandList) {
            if (findLowerHandInMap(lordHandMap, highestHand) == null) {
                if (highestHand.getType() == HandType.SOLO_CHAIN) {
                    return highestHand;
                }
                if (!AIUtils.isBomb(highestHand) && countHandControl(highestHand, lowestCon) == 0) {
                    if (hand == null || highestHand.getKey().ordinal() < hand.getKey().ordinal()) {
                        hand = highestHand;
                    }
                }
            }
        }
        if (hand != null) {
            return hand;
        }

        for (Hand unHighestHand : unHighestHandList) {
            if (countHandControl(unHighestHand, lowestCon) == 0) {
                Hand lordHigher = findHigherHandInMap(lordHandMap, unHighestHand);
                if (lordSummary.getHighestHandList().contains(lordHigher)) {
                    return unHighestHand;
                }
                // if(lordHigher.getType()==HandType.TRIO_PAIR||lordHigher.getType()==HandType.TRIO_SOLO){//判断在三带交换带牌之后没在最高牌List的情况
                // if(!hasHigherHandIgnoreBomb(lordHigher,status.getPlayerCards()[AIConstant.UP_FARMER])
                // &&!hasHigherHandIgnoreBomb(lordHigher,
                // status.getPlayerCards()[AIConstant.DOWN_FARMER])){
                // return unHighestHand;
                // }
                // }
            }
        }

        hand = farmerFindHandInMap(summarys, status, aiConNum);
        return hand;
    }

    private Hand upBadFarmerPlayFree(HandMapSummary[] summarys, GameStatusAdv status, int lowestCon, double aiConNum) {
        CardsSummary[] allCards = status.getPlayerCards();
        CardsSummary aiCards = allCards[status.getCurPlayer()];

        HandMapSummary aiSummary = summarys[status.getCurPlayer()];
        Map<HandType, List<Hand>> handMap = aiSummary.getHandMap();
        // HandMapSummary otherSummary = summarys[AIConstant.UP_FARMER];
        HandMapSummary otherSummary = summarys[AIConstant.DOWN_FARMER]; // 201612修改
        Map<HandType, List<Hand>> otherMap = otherSummary.getHandMap();

        Hand hand = null;
        for (int i = PokerType.EIGHT.ordinal(); i >= aiCards.getLowest().ordinal(); i--) {
            if (aiCards.get(i) == 2) {
                return new Hand(HandType.PAIR, i, 1, null);
            }
        }
        for (int i = lowestCon - 1; i >= aiCards.getLowest().ordinal(); i--) {
            if (aiCards.get(i) == 1) {
                return new Hand(HandType.SOLO, i, 1, null);
            }
        }

        int maxTypeLen = 0;
        HandType maxType = null;
        int handSize = 0;
        List<Hand> trioChains = otherMap.get(HandType.TRIO_CHAIN);
        if (trioChains != null) {
            handSize += trioChains.size();
        }
        List<Hand> trioChainSolos = otherMap.get(HandType.TRIO_CHAIN_SOLO);
        if (trioChainSolos != null) {
            handSize += trioChainSolos.size();
        }
        List<Hand> trioChainPairs = otherMap.get(HandType.TRIO_CHAIN_PAIR);
        if (trioChainPairs != null) {
            handSize += trioChainPairs.size();
        }
        if (handSize > maxTypeLen) {
            maxTypeLen = handSize;
            maxType = HandType.TRIO_CHAIN;
        }

        List<Hand> pairChains = otherMap.get(HandType.PAIR_CHAIN);
        handSize = pairChains == null ? 0 : pairChains.size();
        if (handSize > maxTypeLen) {
            maxTypeLen = handSize;
            maxType = HandType.PAIR_CHAIN;
        }

        List<Hand> soloChains = otherMap.get(HandType.SOLO_CHAIN);
        handSize = soloChains == null ? 0 : soloChains.size();
        if (handSize > maxTypeLen) {
            maxTypeLen = handSize;
            maxType = HandType.SOLO_CHAIN;
        }

        handSize = 0;
        List<Hand> trios = otherMap.get(HandType.TRIO);
        if (trios != null) {
            handSize += trios.size();
        }
        List<Hand> trioSolos = otherMap.get(HandType.TRIO_SOLO);
        if (trioSolos != null) {
            handSize += trioSolos.size();
        }
        List<Hand> trioPairs = otherMap.get(HandType.TRIO_PAIR);
        if (trioPairs != null) {
            handSize += trioPairs.size();
        }
        if (handSize > maxTypeLen) {
            maxTypeLen = handSize;
            maxType = HandType.TRIO;
        }

        List<Hand> pairs = otherMap.get(HandType.PAIR);
        handSize = pairs == null ? 0 : pairs.size();
        if (handSize > maxTypeLen) {
            maxTypeLen = handSize;
            maxType = HandType.PAIR;
        }

        List<Hand> solos = otherMap.get(HandType.SOLO);
        handSize = solos == null ? 0 : solos.size();
        if (handSize > maxTypeLen) {
            maxTypeLen = handSize;
            maxType = HandType.SOLO;
        }

        if (maxType == HandType.PAIR) {
            List<Hand> aiPairs = handMap.get(HandType.PAIR);
            if (aiPairs != null && !aiPairs.isEmpty()) {
                return aiPairs.get(0);
            }
        } else if (maxType == HandType.SOLO) {
            List<Hand> aiSolos = handMap.get(HandType.SOLO);
            if (aiSolos != null && !aiSolos.isEmpty()) {
                return aiSolos.get(0);
            }
        }
        hand = farmerFindHandInMap(summarys, status, aiConNum);
        return hand;
    }

    // private int countRemainCards(GameStatus status) {
    // byte[][] allCards = status.getPlayerCards();
    // int sum = 0;
    // for (int i = 0; i < AIConstant.PLAYER_NUM; i++) {
    // sum += AIUtils.countCardsNum(allCards[i]);
    // }
    // return sum;
    // }

    private Hand downFarmerPlayFree(HandMapSummary[] summarys, GameStatusAdv status, int lowestCon, double aiConNum) {
        CardsSummary[] allCards = status.getPlayerCards();
        CardsSummary aiCards = allCards[status.getCurPlayer()];
        CardsSummary lordCards = allCards[AIConstant.LORD];
        CardsSummary upCards = allCards[AIConstant.UP_FARMER];

        HandMapSummary aiSummary = summarys[status.getCurPlayer()];
        HandMapSummary upSummary = summarys[AIConstant.UP_FARMER];
        HandMapSummary lordSumary = summarys[AIConstant.LORD];

        if (aiSummary.getRealHands() == 1) {
            List<Hand> aiHighest = aiSummary.getHighestHandList();
            List<Hand> aiUnHighest = aiSummary.getUnHighestHandList();
            if (!aiHighest.isEmpty()) {
                return aiHighest.get(0);
            } else {
                return aiUnHighest.get(0);
            }
        }

        if (upCards.getNum() == 1) {
            int upSolo = upCards.getLowest().ordinal();
            List<Hand> bombs = AIUtils.getAllBomb(aiCards);
            if (!bombs.isEmpty()) {
                for (Hand bomb : bombs) {
                    if (findHigherHand(bomb, lordCards) == null) {
                        for (int i = aiCards.getLowest().ordinal(); i < upSolo; i++) {
                            if (aiCards.get(i) > 0 && i != bomb.getKey().ordinal()) {
                                return bomb;
                            }
                        }
                    }
                }
            }
            for (int i = aiCards.getLowest().ordinal(); i < upSolo; i++) {
                if (aiCards.get(i) > 0) {
                    return new Hand(HandType.SOLO, PokerType.values()[i], 1, null);
                }
            }
        }

        List<Hand> upHighest = upSummary.getHighestHandList();
        List<Hand> upUnHighest = upSummary.getUnHighestHandList();

        if (upSummary.getRealHands() == 1) {
            Hand upHand = upHighest.isEmpty() ? upUnHighest.get(0) : upHighest.get(0);
            List<Hand> bombs = AIUtils.getAllBomb(aiCards);
            if (!bombs.isEmpty()) {
                Hand aiHighestBomb = bombs.get(bombs.size() - 1);
                PokerType lordHighestBomb = AIUtils.getHighestBomb(lordCards);
                if (lordHighestBomb == null || lordHighestBomb.ordinal() < aiHighestBomb.getKey().ordinal()) {
                    for (Hand bomb : bombs) {
                        if (lordHighestBomb == null || lordHighestBomb.ordinal() < bomb.getKey().ordinal()
                                || lordCards.getNum() > 4) {
                            byte[] tmpCards = aiCards.getCards();
                            tmpCards[bomb.getKey().ordinal()] = 0;
                            if (bomb.getKey() == PokerType.BJOKER) {
                                tmpCards[PokerType.RJOKER.ordinal()] = 0;
                            }
                            Hand lower = findLowerHand(upHand, tmpCards);
                            if (lower != null) {
                                return lower;
                            }
                        }
                    }
                }
            } else {
                Hand lower = findLowerHand(upHand, aiCards.getCards());
                if (lower != null) {
                    return lower;
                }
            }
        }

        List<Hand> upBombs = upSummary.getHandMap().get(HandType.BOMB);
        if (upBombs != null && !upBombs.isEmpty()) {
            Hand upHighestBomb = upBombs.get(upBombs.size() - 1);
            PokerType lordHighestBomb = AIUtils.getHighestBomb(lordCards);
            boolean upWin;
            if (lordHighestBomb == null) {
                upWin = upSummary.getUnHighestHandList().size() - upBombs.size() <= 1;
            } else if (lordHighestBomb.ordinal() < upHighestBomb.getKey().ordinal()) {
                upWin = upSummary.getRealHands() <= 2 * upBombs.size() + 1;
            } else {
                upWin = upSummary.getRealHands() <= 2 * upBombs.size();
            }
            if (upWin) {
                byte[] cards = aiCards.getCards();
                for (Hand unHighest : upUnHighest) {
                    Hand lower = findLowerHand(unHighest, cards);
                    if (lower != null && lordSumary.getRealHands() != 1) {// ��Ϊ����ֻʣ��һ����ũ���¼�С��һ�����͵����ͣ��Ͳ��ܳ������ͣ����޸�ʱ��160831-pjy��
                        return lower;
                    }
                }
                for (Hand highest : upHighest) {
                    if (!AIUtils.isBomb(highest)) {
                        Hand lower = findLowerHand(highest, cards);
                        if (lower != null) {
                            return lower;
                        }
                    }
                }
            }
        }

        if (upCards.getNum() == 2) {
            List<Hand> bombs = AIUtils.getAllBomb(aiCards);
            if (!bombs.isEmpty()) {
                for (Hand bomb : bombs) {
                    if (findHigherHand(bomb, lordCards) == null) {
                        if (upCards.getLowest() == upCards.getHighest()) { // pair
                            for (int i = aiCards.getLowest().ordinal(); i < upCards.getHighest().ordinal(); i++) {
                                if (aiCards.get(i) >= 2 && i != bomb.getKey().ordinal()) {
                                    return bomb;
                                }
                            }
                        }
                        int firstMin = -1;
                        int secondMin = -1;
                        for (int i = aiCards.getLowest().ordinal(); i < upCards.getHighest().ordinal(); i++) {
                            if (aiCards.get(i) > 0 && i != bomb.getKey().ordinal()) {
                                if (firstMin == -1) {
                                    firstMin = i;
                                    if (aiCards.get(i) > 1) {
                                        secondMin = i;
                                        break;
                                    }
                                } else {
                                    secondMin = i;
                                    break;
                                }
                            }
                        }
                        if (firstMin > 0 && firstMin < upCards.getLowest().ordinal() && secondMin > 0) {
                            if (lordCards.getNum() > 1
                                    || lordCards.getLowest().ordinal() <= upCards.getHighest().ordinal()) {
                                return new Hand(HandType.SOLO, PokerType.values()[secondMin], 1, null);
                            }
                        }
                    }
                }
            }
            if (upCards.getLowest() == upCards.getHighest()) { // pair
                for (int i = aiCards.getLowest().ordinal(); i < upCards.getHighest().ordinal(); i++) {
                    if (aiCards.get(i) >= 2) {
                        return new Hand(HandType.PAIR, i, 1, null);
                    }
                }
            }
        }
        Hand hand = getWinHandForFarmer(summarys, status);
        if (hand != null) {
            return hand;
        }
        if (lordCards.getNum() == 1) {
            hand = farmerPlayFreeWithLord1Card(summarys, status, lowestCon);
            if (hand != null) {
                return hand;
            }
        }
        boolean isGood = isGoodFarmer(summarys, status, lowestCon);
        if (isGood) {
            hand = downGoodFarmerPlayFree(summarys, status, lowestCon, aiConNum);
        } else {
            hand = downBadFarmerPlayFree(summarys, status, lowestCon, aiConNum);
        }

        if (hand != null && hand.getType() != HandType.SOLO) {
            if (hand.size() == lordCards.getNum()) {
                Hand lordHigher = findHigherHand(hand, lordCards);
                if (lordHigher != null && !AIUtils.isBomb(lordHigher) && findHigherHand(lordHigher, upCards) == null) {
                    hand = new Hand(HandType.SOLO, aiCards.getLowest(), 1, null);
                }
            }
        }
        return hand;
    }

    private Hand downFarmerPlayHigher(HandMapSummary[] summarys, GameStatusAdv status, int lowestCon, double aiConNum) {
        CardsSummary[] allCards = status.getPlayerCards();
        CardsSummary aiCards = allCards[status.getCurPlayer()];
        CardsSummary lordCards = allCards[AIConstant.LORD];
        CardsSummary upCards = allCards[AIConstant.UP_FARMER];

        HandMapSummary aiSummary = summarys[status.getCurPlayer()];
        Map<HandType, List<Hand>> handMap = aiSummary.getHandMap();
        HandMapSummary lordSummary = summarys[AIConstant.LORD];
        Map<HandType, List<Hand>> lordHandMap = lordSummary.getHandMap();
        HandMapSummary upSummary = summarys[AIConstant.UP_FARMER];
        Map<HandType, List<Hand>> upHandMap = upSummary.getHandMap();

        Hand curHand = status.getOutHand();

        List<Hand> bombs = handMap.get(HandType.BOMB);

        if (upSummary.getRealHands() == 1) {
            Hand upHand = getOnlyHand(upSummary);
            if (isHandHigherThan(upHand, curHand)) {
                return null;
            }
        }

        Hand hand = farmerEasyWin(summarys, status, curHand);
        if (hand != null) {
            return hand;
        }
        if (upCards.getNum() == 1) {
            if (aiCards.getLowest().ordinal() < upCards.getLowest().ordinal()) {
                if (bombs != null && !bombs.isEmpty()) {
                    Hand aiHighestBomb = bombs.get(bombs.size() - 1);
                    if (isHandHigherThan(aiHighestBomb, curHand) && findHigherHand(aiHighestBomb, lordCards) == null) {
                        return aiHighestBomb;
                    }
                }
            }
            if (status.getOutHandPlayer() != AIConstant.LORD) {
                return null;
            } else if (curHand.getType() == HandType.SOLO
                    && curHand.getKey().ordinal() < upCards.getLowest().ordinal()) {
                return null;
            }
            hand = findHighestHand(curHand, aiCards);
            if (hand != null) {
                return hand;
            }
        }
        if (lordCards.getNum() == 1) {
            hand = farmerPlayLord1Cards(summarys, status, curHand, lowestCon);
            return hand;
        }
        if (status.getOutHandPlayer() == AIConstant.LORD
                && ((lordSummary.getUnHighestHandList().size() <= 1 && lordCards.getNum() <= 10)
                || (lordSummary.getUnHighestHandList().size() <= 2 && lordCards.getNum() <= 10
                && findHigherHand(curHand, upCards) == null)
                || ((upSummary.getEffectiveHands() <= lordSummary.getEffectiveHands())
                && (aiSummary.getEffectiveHands() >= lordSummary.getEffectiveHands())))) {
            hand = findHigherHandInMap(handMap, curHand);
            if (hand != null && !AIUtils.isBomb(hand)) {
                return hand;
            }
            Hand upHigher = findHigherHandInMap(upHandMap, curHand);
            if (upHigher != null && !AIUtils.isBomb(upHigher)) {
                return null;
            }
            hand = farmerMustPlayOverLord(summarys, status, curHand, lowestCon);
            return hand;
        }

        if (isGoodFarmer(summarys, status, lowestCon)) {
            if (status.getOutHandPlayer() == AIConstant.LORD
                    && ((curHand.getType() == HandType.SOLO_CHAIN && curHand.getLen() >= 8)
                    || (curHand.getType() != HandType.SOLO_CHAIN && curHand.getType().isChain()))) {
                if (findHigherHandInMap(handMap, curHand) == null
                        && findHigherHandInMap(lordHandMap, curHand) != null) {
                    return null;
                }
                hand = farmerMustPlay(summarys, status, curHand, lowestCon);
            } else if (status.getOutHandPlayer() == AIConstant.LORD && curHand.getType() == HandType.SOLO
                    && (curHand.getKey() == PokerType.TWO || curHand.getKey() == PokerType.BJOKER)) {
                hand = farmerPlayWhenLordOutBigSolo(status, true);
                if (hand == null) {
                    hand = farmerPlayNormal(summarys, status, curHand, lowestCon);
                }
            } else {
                if (status.getOutHandPlayer() != AIConstant.LORD
                        && (containControl(curHand, lowestCon) || upCards.getNum() <= 4 || curHand.size() >= 4
                        || (curHand.getKey().ordinal() >= PokerType.NINE.ordinal() && curHand.size() >= 2)
                        || curHand.getKey().ordinal() >= PokerType.JACK.ordinal())) {
                    return null;
                }
                hand = farmerPlayNormal(summarys, status, curHand, lowestCon);
            }
        } else { // bad farmer
            if (status.getOutHandPlayer() == AIConstant.LORD) {
                if (curHand.size() >= 3) {
                    hand = farmerMustPlay(summarys, status, curHand, lowestCon);
                } else if (curHand.getType() == HandType.SOLO
                        && (curHand.getKey() == PokerType.TWO || curHand.getKey() == PokerType.BJOKER)) {
                    hand = farmerPlayWhenLordOutBigSolo(status, false);
                } else {
                    if (isFirstHalf(status) || curHand.getType() == HandType.SOLO
                            || curHand.getType() == HandType.PAIR) {
                        hand = farmerPlayNormal(summarys, status, curHand, lowestCon);
                    } else {
                        hand = farmerMustPlay(summarys, status, curHand, lowestCon);
                    }
                }
            } else {
                hand = null;
            }
        }
        return hand;
    }

    private Hand farmerPlayWhenLordOutBigSolo(GameStatusAdv status, boolean isGood) {
        CardsSummary[] allCards = status.getPlayerCards();
        CardsSummary aiCards = allCards[status.getCurPlayer()];
        CardsSummary lordCards = allCards[AIConstant.LORD];
        if (aiCards.get(PokerType.BJOKER) == 1) {
            if (aiCards.get(PokerType.RJOKER) == 1 && isGood && status.getCurPlayer() == AIConstant.DOWN_FARMER) {
                return null;
            } else {
                return new Hand(HandType.SOLO, PokerType.BJOKER, 1, null);
            }
        } else if (aiCards.get(PokerType.RJOKER) == 1) {
            if (lordCards.get(PokerType.BJOKER) == 0) {
                return new Hand(HandType.SOLO, PokerType.RJOKER, 1, null);
            }
        }
        return null;
    }

    private Hand farmerPlayNormal(HandMapSummary[] summarys, GameStatusAdv status, Hand curHand, int lowestCon) {
        CardsSummary[] allCards = status.getPlayerCards();
        CardsSummary aiCards = allCards[status.getCurPlayer()];
        CardsSummary lordCards = allCards[AIConstant.LORD];
        int other = status.getCurPlayer() == AIConstant.UP_FARMER ? AIConstant.DOWN_FARMER : AIConstant.UP_FARMER;
        CardsSummary otherCards = allCards[other];

        HandMapSummary aiSummary = summarys[status.getCurPlayer()];
        Map<HandType, List<Hand>> handMap = aiSummary.getHandMap();

        HandMapSummary bestSummary = null;
        Hand bestHand = null;
        Hand higher = findHigherHandInMap(handMap, curHand);
        while (higher != null) {
            if (AIUtils.isBomb(higher)) {
                break;
            }
            byte[] handCards = AIUtils.handToCards(higher);
            byte[] tmpCards = AIUtils.subCards(aiCards.getCards(), handCards);
            double tmpConNum = AIUtils.calControl(tmpCards, lordCards.getCards(), lowestCon);
            HandMapSummary tmpSummary = findBestHandMap(tmpCards, status, lowestCon, status.getCurPlayer(), tmpConNum,
                    false);
            if (Double
                    .compare(tmpSummary.getSoloConNum() + tmpSummary.getExtraBomb() - tmpSummary.getEffectiveHands(),
                            -1.9) > 0
                    || Double.compare(tmpSummary.getSoloConNum() + tmpSummary.getExtraBomb(),
                    2.9) > 0
                    || Double
                    .compare(
                            tmpSummary.getSoloConNum() + tmpSummary.getExtraBomb()
                                    - tmpSummary
                                    .getEffectiveHands(),
                            aiSummary.getSoloConNum() + aiSummary.getExtraBomb()
                                    - aiSummary.getEffectiveHands()) >= 0
                    || (Double.compare(
                    tmpSummary.getSoloConNum() + tmpSummary.getExtraBomb() - tmpSummary.getEffectiveHands() + 1,
                    aiSummary.getSoloConNum() + aiSummary.getExtraBomb() - aiSummary.getEffectiveHands()) >= 0
                    && findHigherHand(higher, lordCards) == null)) {
                if (bestSummary == null || compareSummary(tmpSummary, bestSummary)) {
                    bestSummary = tmpSummary;
                    bestHand = higher;
                }
            }
            higher = findHigherHand(higher, aiCards);
        }

        if (bestHand == null) {
            for (higher = findHigherHand(curHand, aiCards); higher != null; higher = findHigherHand(higher, aiCards)) {
                if (AIUtils.isBomb(higher)) {
                    break;
                }
                byte[] handCards = AIUtils.handToCards(higher);
                byte[] tmpCards = AIUtils.subCards(aiCards.getCards(), handCards);
                if (AIUtils.getBombNum(tmpCards) < AIUtils.getBombNum(aiCards)) {
                    if (bestHand != null) {
                        continue;
                    }
                    Hand otherHigher = findHigherHand(curHand, otherCards);
                    if (otherHigher != null && AIUtils.isBomb(otherHigher)) {
                        continue;
                    } else if (!curHand.getType().isChain()) {
                        Hand lordHigher = findHigherHand(higher, lordCards);
                        if (lordHigher != null && !AIUtils.isBomb(lordHigher)) {
                            continue;
                        }
                    }
                }
                HandMapSummary tmpSummary = findBestHandMap(tmpCards, status, lowestCon, status.getCurPlayer(),
                        aiSummary.getSoloConNum() - countHandControl(higher, lowestCon), false);
                if (Double.compare(
                        tmpSummary.getSoloConNum() + tmpSummary.getExtraBomb() - tmpSummary.getEffectiveHands(),
                        -1.9) > 0
                        || Double.compare(tmpSummary.getSoloConNum() + tmpSummary.getExtraBomb(), 2.9) > 0
                        || Double
                        .compare(
                                tmpSummary.getSoloConNum() + tmpSummary.getExtraBomb()
                                        - tmpSummary
                                        .getEffectiveHands(),
                                aiSummary.getSoloConNum() + aiSummary.getExtraBomb()
                                        - aiSummary.getEffectiveHands()) >= 0
                        || (Double.compare(
                        tmpSummary.getSoloConNum() + tmpSummary.getExtraBomb() - tmpSummary.getEffectiveHands()
                                + 1,
                        aiSummary.getSoloConNum() + aiSummary.getExtraBomb()
                                - aiSummary.getEffectiveHands()) > 0
                        && findHigherHand(higher, lordCards) == null)) {
                    if (bestSummary == null || compareSummary(tmpSummary, bestSummary)) {
                        bestSummary = tmpSummary;
                        bestHand = higher;
                    }
                }
            }
        }
        return bestHand;
    }

    private Hand farmerMustPlayOverLord(HandMapSummary[] summarys, GameStatusAdv status, Hand curHand, int lowestCon) {
        CardsSummary[] allCards = status.getPlayerCards();
        CardsSummary aiCards = allCards[status.getCurPlayer()];
        CardsSummary lordCards = allCards[AIConstant.LORD];
        int other = status.getCurPlayer() == AIConstant.DOWN_FARMER ? AIConstant.UP_FARMER : AIConstant.DOWN_FARMER;
        CardsSummary otherCards = allCards[other];

        HandMapSummary aiSummary = summarys[status.getCurPlayer()];
        HandMapSummary otherSummary = summarys[other];
        HandMapSummary lordSummary = summarys[AIConstant.LORD];
        Map<HandType, List<Hand>> lordHandMap = lordSummary.getHandMap();

        HandMapSummary bestSummary = null;
        Hand bestHand = null;
        Hand higher = findHigherHand(curHand, aiCards);

        while (higher != null) {
            byte[] handCards = AIUtils.handToCards(higher);
            byte[] tmpCards = AIUtils.subCards(aiCards.getCards(), handCards);
            HandMapSummary tmpSummary = findBestHandMap(tmpCards, status, lowestCon, status.getCurPlayer(),
                    aiSummary.getSoloConNum() - countHandControl(higher, lowestCon), false);
            if (AIUtils.isBomb(higher)) {
                if (tmpSummary.getUnHighestHandList().size() == 1 && !AIUtils.hasBomb(lordCards.getCards())) {
                    return higher;
                }
                if (tmpSummary.getRealHands() == 1 && findHigherHand(higher, lordCards) == null) {
                    return higher;
                }
                if (lordCards.getHighest().ordinal() < lowestCon && lordSummary.getExtraBomb() == 0) {
                    return higher;
                }
            }
            if (bestSummary == null
                    || (findHigherHandInMapIgnoreBomb(lordHandMap, higher) == null
                    && findHigherHandInMapIgnoreBomb(lordHandMap, bestHand) != null)
                    || compareSummary(tmpSummary, bestSummary)) {
                bestSummary = tmpSummary;
                bestHand = higher;
            }
            higher = findHigherHand(higher, aiCards);
        }
        if (bestHand != null && AIUtils.isBomb(bestHand) && (!isSpring(allCards) || lordSummary.getRealHands() > 1)) {
            List<Hand> lordBombs = lordHandMap.get(HandType.BOMB);
            if (lordBombs != null && !lordBombs.isEmpty()) {
                if (lordBombs.size() <= 2) {
                    Hand lordHighestBomb = lordBombs.get(lordBombs.size() - 1);
                    if (findHigherHand(lordHighestBomb, otherCards) == null) {
                        if (lordSummary.getRealHands() <= 2 * lordBombs.size()) {
                            return null;
                        }
                    }
                }
            } else {
                if (lordSummary.getRealHands() == lordSummary.getHighestHandList().size()) {
                    if (aiSummary.getUnHighestHandList().size() - aiSummary.getExtraBomb() > 2
                            && otherSummary.getUnHighestHandList().size() - otherSummary.getExtraBomb() > 1) {
                        return null;
                    }
                }
            }
        }
        return bestHand;
    }

    private Hand farmerMustPlay(HandMapSummary[] summarys, GameStatusAdv status, Hand curHand, int lowestCon) {
        CardsSummary[] allCards = status.getPlayerCards();
        CardsSummary aiCards = allCards[status.getCurPlayer()];

        HandMapSummary aiSummary = summarys[status.getCurPlayer()];
        Map<HandType, List<Hand>> handMap = aiSummary.getHandMap();

        HandMapSummary lordSummary = summarys[AIConstant.LORD];

        Hand hand = findHigherHandInMap(handMap, curHand);
        if (hand != null && !AIUtils.isBomb(hand)) {
            return hand;
        }
        HandMapSummary bestSummary = null;
        Hand bestHand = null;
        Hand higher = findHigherHand(curHand, aiCards);
        List<Hand> bombs = AIUtils.getAllBomb(aiCards);
        while (higher != null && (!AIUtils.isBomb(higher) || bombs.size() >= 3
                || (isSpring(allCards) && lordSummary.getRealHands() <= 1))) {
            byte[] handCards = AIUtils.handToCards(higher);
            byte[] tmpCards = AIUtils.subCards(aiCards.getCards(), handCards);
            HandMapSummary tmpSummary = findBestHandMap(tmpCards, status, lowestCon, status.getCurPlayer(),
                    aiSummary.getSoloConNum() - countHandControl(higher, lowestCon), false);
            if (bestSummary == null || compareSummary(tmpSummary, bestSummary)) {
                bestSummary = tmpSummary;
                bestHand = higher;
            }
            higher = findHigherHand(higher, aiCards);
        }
        return bestHand;
    }

    private Hand farmerPlayLord1Cards(HandMapSummary[] summarys, GameStatusAdv status, Hand curHand, int lowestCon) {
        int other = status.getCurPlayer() == AIConstant.UP_FARMER ? AIConstant.DOWN_FARMER : AIConstant.UP_FARMER;
        CardsSummary[] allCards = status.getPlayerCards();
        CardsSummary aiCards = allCards[status.getCurPlayer()];
        CardsSummary lordCards = allCards[AIConstant.LORD];
        CardsSummary otherCards = allCards[other];

        HandMapSummary aiSummary = summarys[status.getCurPlayer()];
        HandMapSummary otherSummary = summarys[other];

        Map<HandType, List<Hand>> handMap = aiSummary.getHandMap();

        Hand hand = null;
        int lordSolo = lordCards.getLowest().ordinal();
        if (status.getOutHandPlayer() == AIConstant.LORD && curHand.getType() != HandType.SOLO) {
            return mustPlayCheckSolo(summarys, status, curHand, lowestCon);
        } else {
            if (curHand.getType() == HandType.SOLO && status.getCurPlayer() == AIConstant.UP_FARMER) {
                if (aiSummary.getSencondLowestSolo().ordinal() >= lordSolo) {
                    List<Hand> solos = handMap.get(HandType.SOLO);
                    for (Hand solo : solos) {
                        if (solo.getKey().ordinal() > curHand.getKey().ordinal()
                                && solo.getKey().ordinal() >= lordSolo) {
                            hand = solo;
                            return hand;
                        }
                    }
                    hand = findHigherHandInMap(handMap, curHand);
                    if (hand != null) {
                        return hand;
                    }
                }
                byte[] tmpCards = aiCards.getCards();
                for (int i = Math.max(curHand.getKey().ordinal() + 1, lordSolo); i <= lordCards.getHighest()
                        .ordinal(); i++) {
                    if (tmpCards[i] > 0) {
                        tmpCards[i]--;
                        HandMapSummary tmpSummary = findBestHandMapLordOnly1(tmpCards, status, lowestCon,
                                status.getCurPlayer());
                        if (tmpSummary.getSencondLowestSolo().ordinal() >= lordSolo) {
                            return new Hand(HandType.SOLO, i, 1, null);
                        }
                        tmpCards[i]++;
                    }
                }

                if (status.getOutHandPlayer() == AIConstant.DOWN_FARMER && curHand.getKey().ordinal() >= lordSolo) {
                    return null;
                } else {
                    for (int i = Math.max(curHand.getKey().ordinal() + 1, lordSolo); i < AIConstant.CARD_TYPE; i++) {
                        if (aiCards.get(i) == 1) {
                            return new Hand(HandType.SOLO, i, 1, null);
                        }

                    }
                    for (int i = Math.max(curHand.getKey().ordinal() + 1, lordSolo); i < AIConstant.CARD_TYPE; i++) {
                        if (aiCards.get(i) > 1) {
                            return new Hand(HandType.SOLO, i, 1, null);
                        }
                    }

                    List<Hand> bombs = handMap.get(HandType.BOMB);
                    if (bombs != null && !bombs.isEmpty()) {
                        if (bombs.size() > 1) {
                            return bombs.get(bombs.size() - 1);
                        } else {
                            List<Hand> aiHighest = aiSummary.getHighestHandList();
                            for (Hand high : aiHighest) {
                                if (findHigherHand(high, otherCards) != null) {
                                    return bombs.get(0);
                                }
                            }
                            for (int i = 0; i < AIConstant.CARD_TYPE; i++) {
                                if (aiCards.get(i) == 1 && i > lordSolo) {
                                    if (findHigherHand(new Hand(HandType.SOLO, i, 1, null), otherCards) != null) {
                                        return bombs.get(0);
                                    }
                                } else if (aiCards.get(i) == 2) {
                                    if (findHigherHand(new Hand(HandType.SOLO, i, 1, null), otherCards) != null
                                            || findHigherHand(new Hand(HandType.PAIR, i, 1, null),
                                            otherCards) != null) {
                                        return bombs.get(0);
                                    }
                                } else if (aiCards.get(i) == 3) {
                                    if (findHigherHand(new Hand(HandType.SOLO, i, 1, null), otherCards) != null
                                            || findHigherHand(new Hand(HandType.PAIR, i, 1, null), otherCards) != null
                                            || findHigherHand(new Hand(HandType.TRIO, i, 1, null),
                                            otherCards) != null) {
                                        return bombs.get(0);
                                    }
                                }
                            }
                            // Hand bomb = bombs.get(0);
                            // if (otherSummary.getSencondLowestSolo().ordinal()
                            // >= lordSolo) {
                            // byte[] aiCardsRemoveBomb = aiCards.getCards();
                            // aiCardsRemoveBomb[bomb.getKey().ordinal()] = 0;
                            // List<Hand> otherHighest = otherSummary
                            // .getHighestHandList();
                            // for (Hand highest : otherHighest) {
                            // if (highest.getType() == HandType.SOLO) {
                            // for (int i = lordSolo + 1; i < highest
                            // .getKey().ordinal(); i++) {
                            // if (aiCardsRemoveBomb[i] > 0) {
                            // return bomb;
                            // }
                            // }
                            // } else {
                            // if (findLowerHand(highest,
                            // aiCardsRemoveBomb) != null) {
                            // return bomb;
                            // }
                            // }
                            // }
                            // }
                        }
                    }
                }
            } else {
                if (aiSummary.getSencondLowestSolo().ordinal() >= lordSolo) {
                    hand = findHigherHandInMap(handMap, curHand);
                    if (hand != null) {
                        return hand;
                    } else {
                        Hand higher = findHigherHand(curHand, aiCards);
                        while (higher != null) {
                            byte[] handCards = AIUtils.handToCards(higher);
                            byte[] tmpCards = AIUtils.subCards(aiCards.getCards(), handCards);
                            HandMapSummary tmpSummary = findBestHandMapLordOnly1(tmpCards, status, lowestCon,
                                    status.getCurPlayer());
                            if (tmpSummary.getSencondLowestSolo().ordinal() >= lordCards.getLowest().ordinal()) {
                                return higher;
                            } else {
                                higher = findHigherHand(higher, aiCards);
                            }
                        }
                    }
                }
                if (status.getOutHandPlayer() == AIConstant.LORD && curHand.getType() == HandType.SOLO) {
                    if (otherSummary.getSencondLowestSolo().ordinal() > lordSolo
                            && findHigherHandInMap(otherSummary.getHandMap(), curHand) != null) {
                        return null;
                    }
                    Hand lordHand;
                    if (curHand.getKey().ordinal() >= lordSolo) {
                        lordHand = curHand;
                    } else {
                        if (lordSolo == 0){
                            lordHand = new Hand(HandType.SOLO, 0, 1, null);
                        }else{
                            lordHand = new Hand(HandType.SOLO, lordSolo - 1, 1, null);
                        }
                    }
                    hand = findHigherHandInMap(handMap, lordHand);
                    if (hand != null) {
                        if (!AIUtils.isBomb(hand)) {
                            return hand;
                        } else {
                            if (otherSummary.getSencondLowestSolo().ordinal() > lordSolo) {
                                return hand;
                            }
                        }
                    }
                }
            }
        }
        return hand;
    }

    private Hand farmerPlayFreeWithLord1Card(HandMapSummary[] summarys, GameStatusAdv status, int lowestCon) {
        CardsSummary[] allCards = status.getPlayerCards();
        CardsSummary aiCards = allCards[status.getCurPlayer()];
        CardsSummary lordCards = allCards[AIConstant.LORD];
        HandMapSummary aiSummary = summarys[status.getCurPlayer()];
        Map<HandType, List<Hand>> handMap = aiSummary.getHandMap();
        int lordSolo = lordCards.getLowest().ordinal();
        if (aiSummary.getSencondLowestSolo().ordinal() >= lordSolo) {
            for (Map.Entry<HandType, List<Hand>> entry : handMap.entrySet()) {
                List<Hand> hands = entry.getValue();
                if (hands != null && !hands.isEmpty() && entry.getKey() != HandType.SOLO) {
                    return hands.get(0);
                }
            }
            List<Hand> solos = handMap.get(HandType.SOLO);
            if (solos != null && !solos.isEmpty()) {
                return solos.get(solos.size() - 1);
            }
        }

        List<Hand> bombs = handMap.get(HandType.BOMB);
        if (bombs != null && !bombs.isEmpty()) {
            int bombNum = bombs.size();
            if (bombs.get(bombs.size() - 1).getKey() == PokerType.BJOKER) {
                bombNum--;
            }
            List<Hand> solos = handMap.get(HandType.SOLO);
            int lowSoloNum = 0;
            for (Hand solo : solos) {
                if (solo.getKey().ordinal() < lordSolo) {
                    lowSoloNum++;
                }
            }
            if (lowSoloNum <= 2 * bombNum + 1) {
                Set<PokerType> kickers = EnumSet.of(solos.get(0).getKey(), solos.get(1).getKey());
                return new Hand(HandType.FOUR_DUAL_SOLO, bombs.get(0).getKey(), 1, kickers);
            }
        }

        Hand hand = null;
        int otherFarmer = status.getCurPlayer() == AIConstant.DOWN_FARMER ? AIConstant.UP_FARMER
                : AIConstant.DOWN_FARMER;
        HandMapSummary otherFarmerSummary = findBestHandMapLordOnly1(allCards[otherFarmer].getCards(), status,
                lowestCon, otherFarmer);
        Map<HandType, List<Hand>> otherFarmerMap = otherFarmerSummary.getHandMap();
        boolean otherFarmerWin = otherFarmerSummary.getSencondLowestSolo().ordinal() >= lordSolo;
        if (!otherFarmerWin) {
            List<Hand> otherBombs = otherFarmerMap.get(HandType.BOMB);
            if (otherBombs != null && !otherBombs.isEmpty()) {
                List<Hand> otherBombsCopy = new ArrayList<Hand>(otherBombs);
                for (int i = otherBombsCopy.size() - 1; i >= 0; i--) {
                    Hand bomb = otherBombsCopy.get(i);
                    if (bomb.getKey() != PokerType.BJOKER) {
                        genFourDualSolo(otherFarmerSummary, bomb.getKey(), status, lowestCon, otherFarmer);
                        if (otherFarmerSummary.getSencondLowestSolo().ordinal() >= lordSolo) {
                            otherFarmerWin = true;
                            break;
                        }
                    }
                }
            }
        }
        if (otherFarmerWin) {
            List<Hand> hands = otherFarmerMap.get(HandType.PAIR);
            if (hands != null && !hands.isEmpty()) {
                hand = findLowerHandForLord1Card(aiCards, hands.get(hands.size() - 1));
                if (hand != null) {
                    return hand;
                }
            }
            hands = otherFarmerMap.get(HandType.SOLO_CHAIN);
            if (hands != null) {
                for (Hand chain : hands) {
                    hand = findLowerHandForLord1Card(aiCards, chain);
                    if (hand != null) {
                        return hand;
                    }
                }
            }
            hands = otherFarmerMap.get(HandType.TRIO_SOLO);
            if (hands != null && !hands.isEmpty()) {
                hand = findLowerHandForLord1Card(aiCards, hands.get(hands.size() - 1));
                if (hand != null) {
                    return hand;
                }
            }
            hands = otherFarmerMap.get(HandType.TRIO);
            if (hands != null && !hands.isEmpty()) {
                hand = findLowerHandForLord1Card(aiCards, hands.get(hands.size() - 1));
                if (hand != null) {
                    return hand;
                }
            }
        }

        List<Hand> pairs = handMap.get(HandType.PAIR);
        if (pairs != null && !pairs.isEmpty()) {
            return pairs.get(0);
        }

        hand = findLowestHandFromMap(handMap);
        if (status.getCurPlayer() == AIConstant.UP_FARMER && hand.getType() == HandType.SOLO
                && hand.getKey().ordinal() < lordSolo) {
            hand = null;
            List<Hand> solos = handMap.get(HandType.SOLO);
            if (solos != null) {
                for (Hand solo : solos) {
                    if (solo.getKey().ordinal() >= lordSolo) {
                        return solo;
                    }
                }
            }
        }

        if (hand == null && bombs != null && !bombs.isEmpty()) {
            Hand bomb = bombs.get(0);
            if (bomb.getKey() != PokerType.BJOKER) {
                List<Hand> solos = handMap.get(HandType.SOLO);
                if (solos.size() > 1) {
                    Set<PokerType> kickers = EnumSet.of(solos.get(0).getKey(), solos.get(1).getKey());
                    return new Hand(HandType.FOUR_DUAL_SOLO, bomb.getKey(), 1, kickers);
                }
            }
        }

        List<Hand> solos = handMap.get(HandType.SOLO);
        hand = solos.get(solos.size() - 1);
        return hand;
    }

    private Hand mustPlayCheckSolo(HandMapSummary[] summarys, GameStatusAdv status, Hand curHand, int lowestCon) {
        CardsSummary[] allCards = status.getPlayerCards();
        CardsSummary aiCards = allCards[status.getCurPlayer()];
        Hand bestHand = findHigherHand(curHand, aiCards);
        if (bestHand != null) {
            byte[] handCards = AIUtils.handToCards(bestHand);
            byte[] tmpCards = AIUtils.subCards(aiCards.getCards(), handCards);
            HandMapSummary bestSummary = findBestHandMapLordOnly1(tmpCards, status, lowestCon, status.getCurPlayer());
            Hand tmpHand = bestHand;
            do {
                tmpHand = findHigherHand(tmpHand, aiCards);
                if (tmpHand != null) {
                    handCards = AIUtils.handToCards(tmpHand);
                    tmpCards = AIUtils.subCards(aiCards.getCards(), handCards);
                    HandMapSummary tmpSummary = findBestHandMapLordOnly1(tmpCards, status, lowestCon,
                            status.getCurPlayer());
                    if (tmpSummary.getSencondLowestSolo().ordinal() > bestSummary.getSencondLowestSolo().ordinal()) {
                        bestHand = tmpHand;
                        bestSummary = tmpSummary;
                    }
                } else {
                    break;
                }
            } while (true);
        }
        return bestHand;
    }

    private Hand findHighestHand(Hand curHand, CardsSummary cards) {
        Hand highest = findHigherHand(curHand, cards);
        if (highest != null) {
            Hand secondHighest = null;
            do {
                secondHighest = highest;
                highest = findHigherHand(secondHighest, cards);
                if (highest == null) {
                    highest = secondHighest;
                    break;
                } else if (AIUtils.isBomb(highest) && secondHighest != null) {
                    highest = secondHighest;
                    break;
                }
            } while (true);
        }
        return highest;
    }

    private Hand farmerEasyWin(HandMapSummary[] summarys, GameStatusAdv status, Hand curHand) {
        HandMapSummary aiSummary = summarys[status.getCurPlayer()];
        HandMapSummary lordSummary = summarys[AIConstant.LORD];
        Map<HandType, List<Hand>> handMap = aiSummary.getHandMap();
        Map<HandType, List<Hand>> lordHandMap = lordSummary.getHandMap();

        CardsSummary[] allCards = status.getPlayerCards();
        CardsSummary lordCards = allCards[AIConstant.LORD];

        List<Hand> bombs = handMap.get(HandType.BOMB);
        Hand hand = null;
        if (aiSummary.getRealHands() == 1) {
            hand = findHigherHandInMap(handMap, curHand);
            if (hand != null) {
                return hand;
            }
        }
        if (bombs != null && !bombs.isEmpty()) {
            boolean useBomb = false;
            if (findHigherHand(bombs.get(bombs.size() - 1), lordCards) == null) {
                useBomb = (aiSummary.getRealHands() <= 2 * bombs.size());
            } else {
                List<Hand> lordBombs = lordHandMap.get(HandType.BOMB);
                if (lordBombs != null && lordSummary.getRealHands() > 2 * lordBombs.size()) {
                    useBomb = (aiSummary.getRealHands() <= 2 * bombs.size() - 1);
                }
            }
            if (useBomb) {
                for (Hand bomb : bombs) {
                    if (isHandHigherThan(bomb, curHand)) {
                        hand = bomb;
                    }
                }
            }
        }
        if (hand == null) {
            if (aiSummary.getUnHighestHandList().size() <= 1) {
                if (aiSummary.getRealHands() == 2) {
                    if (bombs != null) {
                        for (Hand bomb : bombs) {
                            if (isHandHigherThan(bomb, curHand) && findHigherHand(bomb, lordCards) == null) {
                                hand = bomb;
                            }
                        }
                    }
                }
                if (hand == null) {
                    if (status.getCurPlayer() == AIConstant.DOWN_FARMER) {
                        if (status.getOutHandPlayer() == AIConstant.UP_FARMER
                                && summarys[AIConstant.UP_FARMER].getRealHands() == 1) {
                            return null;
                        }
                    }
                    if (!AIUtils.isBomb(curHand)) {
                        List<Hand> allHigher = findAllHigherHandInMap(handMap, curHand);
                        if (!allHigher.isEmpty()) {
                            hand = allHigher.get(allHigher.size() - 1);
                        }
                    }
                    if (hand == null) {
                        if (bombs != null && !AIUtils.hasBomb(lordCards.getCards())) {
                            for (Hand bomb : bombs) {
                                if (isHandHigherThan(bomb, curHand)) {
                                    hand = bomb;
                                }
                            }
                        }
                    }
                }
            }
        }
        // if (hand != null && AIUtils.isBomb(hand)) {
        // if (!AIUtils.hasBomb(lordCards.getCards()) &&
        // lordSummary.getEffectiveHands() >= 3) {
        // return null;
        // }
        // }
        return hand;
    }

    private Hand downGoodFarmerPlayFree(HandMapSummary[] summarys, GameStatusAdv status, int lowestCon,
                                        double aiConNum) {
        Hand hand = null;
        HandMapSummary aiSummary = summarys[status.getCurPlayer()];
        Map<HandType, List<Hand>> handMap = aiSummary.getHandMap();
        if (aiSummary.getUnHighestHandList().size() == 2 && aiSummary.getRealHands() == 2) {
            List<Hand> solos = handMap.get(HandType.SOLO);
            List<Hand> pairs = handMap.get(HandType.PAIR);
            if (solos != null && solos.size() == 1 && pairs != null && pairs.size() == 1) {
                if (solos.get(0).getKey().ordinal() < pairs.get(0).getKey().ordinal()) {
                    return solos.get(0);
                } else {
                    return pairs.get(0);
                }
            } else if (solos != null && solos.size() == 2) {
                return solos.get(0);
            } else if (pairs != null && pairs.size() == 2) {
                return pairs.get(0);
            } else {
                Set<Map.Entry<HandType, List<Hand>>> entrys = handMap.entrySet();
                for (Map.Entry<HandType, List<Hand>> entry : entrys) {
                    List<Hand> hands = entry.getValue();
                    if (hands != null && !hands.isEmpty()) {
                        if (entry.getKey() != HandType.SOLO && entry.getKey() != HandType.PAIR) {
                            return hands.get(0);
                        }
                    }
                }
            }
        }
        hand = farmerFindHandInMap(summarys, status, aiConNum);
        return hand;
    }

    private Hand downBadFarmerPlayFree(HandMapSummary[] summarys, GameStatusAdv status, int lowestCon,
                                       double aiConNum) {
        HandMapSummary aiSummary = summarys[status.getCurPlayer()];
        Map<HandType, List<Hand>> handMap = aiSummary.getHandMap();
        HandMapSummary otherSummary = summarys[AIConstant.UP_FARMER];
        Map<HandType, List<Hand>> otherMap = otherSummary.getHandMap();
        HandMapSummary lordSummary = summarys[AIConstant.LORD];

        int maxTypeLen = 0;
        HandType maxType = null;
        int handSize = 0;
        List<Hand> trioChains = otherMap.get(HandType.TRIO_CHAIN);
        if (trioChains != null) {
            handSize += trioChains.size();
        }
        List<Hand> trioChainSolos = otherMap.get(HandType.TRIO_CHAIN_SOLO);
        if (trioChainSolos != null) {
            handSize += trioChainSolos.size();
        }
        List<Hand> trioChainPairs = otherMap.get(HandType.TRIO_CHAIN_PAIR);
        if (trioChainPairs != null) {
            handSize += trioChainPairs.size();
        }
        if (handSize > maxTypeLen) {
            maxTypeLen = handSize;
            maxType = HandType.TRIO_CHAIN;
        }

        List<Hand> pairChains = otherMap.get(HandType.PAIR_CHAIN);
        handSize = pairChains == null ? 0 : pairChains.size();
        if (handSize > maxTypeLen) {
            maxTypeLen = handSize;
            maxType = HandType.PAIR_CHAIN;
        }

        List<Hand> soloChains = otherMap.get(HandType.SOLO_CHAIN);
        handSize = soloChains == null ? 0 : soloChains.size();
        if (handSize > maxTypeLen) {
            maxTypeLen = handSize;
            maxType = HandType.SOLO_CHAIN;
        }

        handSize = 0;
        List<Hand> trios = otherMap.get(HandType.TRIO);
        if (trios != null) {
            handSize += trios.size();
        }
        List<Hand> trioSolos = otherMap.get(HandType.TRIO_SOLO);
        if (trioSolos != null) {
            handSize += trioSolos.size();
        }
        List<Hand> trioPairs = otherMap.get(HandType.TRIO_PAIR);
        if (trioPairs != null) {
            handSize += trioPairs.size();
        }
        if (handSize > maxTypeLen) {
            maxTypeLen = handSize;
            maxType = HandType.TRIO;
        }

        List<Hand> pairs = otherMap.get(HandType.PAIR);
        handSize = pairs == null ? 0 : pairs.size();
        if (handSize > maxTypeLen) {
            maxTypeLen = handSize;
            maxType = HandType.PAIR;
        }

        List<Hand> solos = otherMap.get(HandType.SOLO);
        handSize = solos == null ? 0 : solos.size();
        if (handSize > maxTypeLen) {
            maxTypeLen = handSize;
            maxType = HandType.SOLO;
        }

        Hand lordHand = null;
        if (lordSummary.getRealHands() == 1) {
            List<Hand> lordHighest = lordSummary.getHighestHandList();
            List<Hand> lordUnhighest = lordSummary.getUnHighestHandList();
            if (!lordHighest.isEmpty()) {
                lordHand = lordHighest.get(0);
            } else {
                lordHand = lordUnhighest.get(0);
            }
        }
        if (maxType == HandType.PAIR && (lordHand == null || lordHand.getType() != HandType.PAIR)) {
            List<Hand> aiPairs = handMap.get(HandType.PAIR);
            if (aiPairs != null && !aiPairs.isEmpty()) {
                return aiPairs.get(0);
            }
        } else if (maxType == HandType.SOLO && (lordHand == null || lordHand.getType() != HandType.SOLO)) {
            List<Hand> aiSolos = handMap.get(HandType.SOLO);
            if (aiSolos != null && !aiSolos.isEmpty()) {
                return aiSolos.get(0);
            }
        }
        return farmerFindHandInMap(summarys, status, aiConNum);
    }

    private void calcHandMap(GameStatusAdv status, HandMapSummary summary, int lowestControl, int pos) {
        double soloConNum = summary.getSoloConNum();
        summary.clear();
        Map<HandType, List<Hand>> handMap = summary.getHandMap();
        Set<Map.Entry<HandType, List<Hand>>> entrySet = handMap.entrySet();
        for (Map.Entry<HandType, List<Hand>> entry : entrySet) {
            HandType type = entry.getKey();
            List<Hand> hands = entry.getValue();
            if (hands != null && hands.size() > 0) {
                summary.changeRealHands(hands.size());
                if (type == HandType.SOLO_CHAIN) {
                    Set<Integer> chainLength = new HashSet<Integer>();
                    for (Hand chain : hands) {
                        chainLength.add(chain.getLen());
                    }
                    summary.changeHandsType(chainLength.size());
                } else {
                    if (type != HandType.BOMB) {
                        summary.changeHandsType(1);
                    }
                    if (type == HandType.SOLO) {
                        summary.changeSolos(hands.size());
                        summary.setLowestSolo(hands.get(0).getKey());
                        if (hands.size() > 1) {
                            summary.setSencondLowestSolo(hands.get(1).getKey());
                        }
                    }
                }
                for (Hand hand : hands) {
                    if (AIUtils.isBomb(hand)) {
                        if (hand.getKey().ordinal() < lowestControl) {
                            summary.changeExtraBomb(1);
                        }
                        summary.addHighestHand(hand);
                    } else {
                        if (isHighestHand(hand, status, pos)) {
                            summary.addHighestHand(hand);
                        } else {
                            summary.addUnHighestHand(hand);
                        }
                        int countOfControl = countHandControl(hand, lowestControl);
                        if (countOfControl >= 2) {
                            int count = countOfControl - 1 > soloConNum ? (int) (soloConNum + 0.5) : countOfControl - 1;// 有效的控牌,如果是无效的大牌就不算在内(四舍五入)
                            summary.changeMultiControlsHands(count);// 受影响的牌张数=Hand中大牌张数-1,小于等于
                            // 有效的大牌张数
                        }
                    }
                }
            }
        }
        if (handMap.get(HandType.BOMB) == null) {
            handMap.put(HandType.BOMB, new ArrayList<Hand>());
        }
        summary.setSoloConNum(soloConNum);
        summary.setEffectiveHands(summary.getUnHighestHandList().size() + summary.getMultiControlsHands() - soloConNum
                - summary.getExtraBomb());
    }

    private void genFourDualSolo(HandMapSummary summary, PokerType bombKey, GameStatusAdv status, int lowestCon,
                                 int pos) {
        Map<HandType, List<Hand>> handMap = summary.getHandMap();
        List<Hand> solos = handMap.get(HandType.SOLO);
        if (solos == null || solos.size() < 2) {
            return;
        }
        List<Hand> bombs = handMap.get(HandType.BOMB);
        for (int i = bombs.size() - 1; i >= 0; i--) {
            if (bombs.get(i).getKey() == bombKey) {
                bombs.remove(i);
                Set<PokerType> kickers = EnumSet.of(solos.get(0).getKey(), solos.get(1).getKey());
                solos.remove(0);
                solos.remove(0);
                Hand fourDualSolo = new Hand(HandType.FOUR_DUAL_SOLO, bombKey, 1, kickers);
                List<Hand> fourDualSoloList = handMap.get(HandType.FOUR_DUAL_SOLO);
                if (fourDualSoloList == null) {
                    fourDualSoloList = new ArrayList<Hand>();
                    handMap.put(HandType.FOUR_DUAL_SOLO, fourDualSoloList);
                }
                fourDualSoloList.add(fourDualSolo);
                calcHandMap(status, summary, lowestCon, pos);
                break;
            }
        }
    }

    private int calLowestControl(GameStatusAdv status) {
        CardsSummary[] playerCards = status.getPlayerCards();
        int remainCards = playerCards[0].getNum() + playerCards[1].getNum() + playerCards[2].getNum();
        int controlNum;
        if (remainCards <= 10) {
            controlNum = 2;
        } else if (remainCards <= 20) {
            controlNum = 4;
        } else {
            controlNum = 6;
        }
        int curControl = 0;
        for (int i = PokerType.RJOKER.ordinal(); i >= 0; i--) {
            curControl += (playerCards[0].get(i) + playerCards[1].get(i) + playerCards[2].get(i));
            if (curControl >= controlNum) {
                return i;
            }
        }
        return 0;
    }

    private boolean compareSummary(HandMapSummary s0, HandMapSummary s1) {
        if (s0.getEffectiveHands() < s1.getEffectiveHands()) {
            return true;
        } else if (s0.getEffectiveHands() > s1.getEffectiveHands()) {
            return false;
        } else {
            if (s0.getExtraBomb() > s1.getExtraBomb()) {
                return true;
            } else if (s0.getExtraBomb() < s1.getExtraBomb()) {
                return false;
            } else {
                if (s0.getHandsType() < s1.getHandsType()) {
                    return true;
                }
                if (s0.getHandsType() > s1.getHandsType()) {
                    return false;
                } else {
                    if (s0.getSolos() < s1.getSolos()) {
                        return true;
                    } else if (s0.getSolos() > s1.getSolos()) {
                        return false;
                    } else {
                        if (s0.getSoloConNum() - s0.getMultiControlsHands() > s1.getSoloConNum()
                                - s1.getMultiControlsHands()) {// 比較剩下的 能用控牌個數
                            return true;
                        } else if (s0.getSoloConNum() - s0.getMultiControlsHands() < s1.getSoloConNum()
                                - s1.getMultiControlsHands()) {
                            return false;
                        } else {
                            if (s0.getLowestSolo().ordinal() > s1.getLowestSolo().ordinal()) {
                                return true;
                            } else if (s0.getLowestSolo().ordinal() < s1.getLowestSolo().ordinal()) {
                                return false;
                            } else {
                                return s0.getRealHands() < s1.getRealHands();
                            }
                        }

                    }
                }
            }
        }
    }

    public int countHandControl(Hand hand, int lowestControl) {
        int ret = 0;
        switch (hand.getType()) {
            case NUKE:
                ret = 2;
                break;
            case SOLO:
                ret = hand.getKey().ordinal() >= lowestControl ? 1 : 0;
                break;
            case PAIR:
                ret = hand.getKey().ordinal() >= lowestControl ? 2 : 0;
                break;
            case TRIO:
                ret = hand.getKey().ordinal() >= lowestControl ? 3 : 0;
                break;
            case TRIO_SOLO:
                ret = hand.getKey().ordinal() >= lowestControl ? 3 : 0;
                if (hand.getKickers().iterator().next().ordinal() >= lowestControl) {
                    ret++;
                }
                break;
            case TRIO_PAIR:
                ret = hand.getKey().ordinal() >= lowestControl ? 3 : 0;
                if (hand.getKickers().iterator().next().ordinal() >= lowestControl) {
                    ret += 2;
                }
                break;
            case BOMB:
                ret = hand.getKey().ordinal() >= lowestControl ? 4 : 0;
                break;
            case FOUR_DUAL_SOLO:
                ret = hand.getKey().ordinal() >= lowestControl ? 4 : 0;
                for (PokerType kicker : hand.getKickers()) {
                    if (kicker.ordinal() >= lowestControl) {
                        ret++;
                    }
                }
                break;
            case FOUR_DUAL_PAIR:
                ret = hand.getKey().ordinal() >= lowestControl ? 4 : 0;
                for (PokerType kicker : hand.getKickers()) {
                    if (kicker.ordinal() >= lowestControl) {
                        ret += 2;
                    }
                }
                break;
            case SOLO_CHAIN:
                ret = Math.max(hand.getKey().ordinal() + hand.getLen() - lowestControl + 1, 0);
                break;
            case PAIR_CHAIN:
                ret = 2 * Math.max(hand.getKey().ordinal() + hand.getLen() - lowestControl + 1, 0);
                break;
            case TRIO_CHAIN:
                ret = 3 * Math.max(hand.getKey().ordinal() + hand.getLen() - lowestControl + 1, 0);
                break;
            case TRIO_CHAIN_SOLO:
                ret = 3 * Math.max(hand.getKey().ordinal() + hand.getLen() - lowestControl + 1, 0);
                for (PokerType kicker : hand.getKickers()) {
                    if (kicker.ordinal() >= lowestControl) {
                        ret++;
                    }
                }
                break;
            case TRIO_CHAIN_PAIR:
                ret = 3 * Math.max(hand.getKey().ordinal() + hand.getLen() - lowestControl + 1, 0);
                for (PokerType kicker : hand.getKickers()) {
                    if (kicker.ordinal() >= lowestControl) {
                        ret += 2;
                    }
                }
                break;
            default:
                break;
        }
        return ret;
    }

    // private int countRemainCards(GameStatus status) {
    // byte[][] allCards = status.getPlayerCards();
    // int sum = 0;
    // for (int i = 0; i < AIConstant.PLAYER_NUM; i++) {
    // sum += AIUtils.countCardsNum(allCards[i]);
    // }
    // return sum;
    // }

    private Hand findLowestHandFromMap(Map<HandType, List<Hand>> handMap) {
        Hand hand = null;
        for (Map.Entry<HandType, List<Hand>> entry : handMap.entrySet()) {
            if (entry.getKey() != HandType.BOMB) {
                List<Hand> hands = entry.getValue();
                if (hands != null) {
                    for (Hand h : hands) {
                        if (hand == null || h.getKey().ordinal() < hand.getKey().ordinal()
                                || (h.getKey().ordinal() == hand.getKey().ordinal() && h.size() > hand.size())) {
                            hand = h;
                        }
                    }
                }
            }
        }
        if (hand == null) {
            List<Hand> bombs = handMap.get(HandType.BOMB);
            if (bombs != null && bombs.size() > 0)
                hand = bombs.get(0);
        }
        return hand;
    }

    private Hand findLowerHandForLord1Card(CardsSummary cards, Hand hand) {
        if (hand.getType() == HandType.SOLO_CHAIN) {
            for (int i = cards.getLowest().ordinal(); i < hand.getKey().ordinal();) {
                if (cards.get(i) > 0) {
                    int j = i + 1;
                    while (cards.get(j) > 0) {
                        if (j - i + 1 == hand.getLen()) {
                            return new Hand(HandType.SOLO_CHAIN, i, hand.getLen(), null);
                        }
                        j++;
                    }
                    i = j + 1;
                } else {
                    i++;
                }
            }
        } else {
            HandType type = hand.getType();
            Hand lowerHand = null;
            if (type == HandType.SOLO || type == HandType.PAIR || type == HandType.TRIO || type == HandType.TRIO_SOLO) {
                for (int i = cards.getLowest().ordinal(); i < hand.getKey().ordinal(); i++) {
                    if (cards.get(i) >= type.getWidth()) {
                        lowerHand = new Hand(hand.getType(), i, 1, null);
                        if (type == HandType.TRIO_SOLO) {
                            for (int j = 0; j < AIConstant.CARD_TYPE; j++) {
                                if (cards.get(j) > 0 && j != i) {
                                    Set<PokerType> kicker = EnumSet.of(PokerType.values()[j]);
                                    lowerHand.setKickers(kicker);
                                    break;
                                }
                            }
                        }
                        return lowerHand;
                    }
                }
            }
        }
        return null;
    }

    private Hand farmerFindHandInMap(HandMapSummary[] summarys, GameStatusAdv status, double aiConNum) {
        // Hand hand = null;
        HandMapSummary aiSummary = summarys[status.getCurPlayer()];
        Map<HandType, List<Hand>> handMap = aiSummary.getHandMap();
        List<Hand> bombs = handMap.get(HandType.BOMB);
        List<Hand> unHighestHandList = aiSummary.getUnHighestHandList();
        List<Hand> highestHandList = aiSummary.getHighestHandList();
        if (aiSummary.getUnHighestHandList().size() == 2) {
            int num = 0;
            for (Hand unHighest : unHighestHandList) {
                if (unHighest.getType() == HandType.SOLO || unHighest.getType() == HandType.PAIR) {
                    num++;
                }
            }
            if (num == 2 && (bombs == null || bombs.isEmpty()) && aiSummary.getSoloConNum() < 0.5) {
                Hand bestHighest = findBestHighestHand(summarys, status, aiConNum);
                if (bestHighest != null) {
                    return bestHighest;
                }
            }
        }
        if (aiConNum < 2) {
            for (Hand highest : highestHandList) {
                if (highest.getType().isChain()) {
                    Hand lordLower = findLowerHand(highest, status.getPlayerCards()[AIConstant.LORD].getCards());
                    if (lordLower == null) {
                        return highest;
                    }
                }
            }
        }
        return findLowestHandFromMap(handMap);
    }

    private Hand findBestHighestHand(HandMapSummary[] summarys, GameStatusAdv status, double aiConNum) {
        HandMapSummary aiSummary = summarys[status.getCurPlayer()];
        HandMapSummary lordSummary = summarys[AIConstant.LORD];
        Map<HandType, List<Hand>> lordHandMap = lordSummary.getHandMap();
        List<Hand> unHighestHandList = aiSummary.getUnHighestHandList();
        List<Hand> highestHandList = aiSummary.getHighestHandList();

        Hand bestHighest = null;
        boolean higherThanLord = false;
        for (Hand highest : highestHandList) {
            if (!AIUtils.isBomb(highest)) {
                boolean higherThanSelfHand = false;
                boolean higherThanLordHand = false;
                for (Hand unHighest : unHighestHandList) {
                    if (Hand.isHigherThan(highest, unHighest)) {
                        higherThanSelfHand = true;
                        break;
                    }
                }
                if (!higherThanSelfHand) {
                    Hand lordLower = findLowerHandInMap(lordHandMap, highest);
                    higherThanLordHand = (lordLower != null);
                }
                if (!higherThanSelfHand) {
                    if (bestHighest == null || (higherThanLord && !higherThanLordHand)
                            || (higherThanLord == higherThanLordHand
                            && highest.getKey().ordinal() < bestHighest.getKey().ordinal())) {
                        bestHighest = highest;
                        higherThanLord = higherThanLordHand;
                    }
                }
            }
        }
        return bestHighest;
    }

    public HandMapSummary findBestHandMap(byte[] cards, GameStatusAdv status, int lowestControl, int pos,
                                          double soloConNum, boolean lordOnly1Card) {
        if (pos != 0 && lordOnly1Card) {
            return findBestHandMapLordOnly1(cards, status, lowestControl, pos);
        }
        byte[] cardsCopy = Arrays.copyOf(cards, cards.length);
        HandMapSummary summary = null;
        HandMapSummary summary1 = findBestHandGroup(status, lowestControl, soloConNum, pos, cardsCopy);
        List<Hand> solosList = summary1.getHandMap().get(HandType.SOLO);
        for (Hand soloHand : solosList) {
            if (soloHand.getKey().ordinal() >= lowestControl) {
                return summary1;
            }
        }
        List<Hand> solos = getControler(cardsCopy, lowestControl, status);
        if (solos.isEmpty()) {
            return summary1;
        }
        HandMapSummary summary2 = findBestHandGroup(status, lowestControl, soloConNum, pos, cardsCopy);
        summary2.getHandMap().get(HandType.SOLO).addAll(solos);
        calcHandMap(status, summary2, lowestControl, pos);//
        if (compareSummary(summary1, summary2)) {
            summary = summary1;
        } else {
            summary = summary2;
        }
        return summary;
    }

    private HandMapSummary findBestHandMapLordOnly1(byte[] cards, GameStatusAdv status, int lowestCon, int pos) {
        Map<HandType, List<Hand>> handMap5 = splitCardKind5(cards, status, lowestCon, true);
        HandMapSummary summary5 = new HandMapSummary(handMap5);
        calcHandMap(status, summary5, lowestCon, pos);
        Map<HandType, List<Hand>> handMap4 = splitCardKind4(cards, status, lowestCon, true);
        HandMapSummary summary4 = new HandMapSummary(handMap4);
        calcHandMap(status, summary4, lowestCon, pos);
        HandMapSummary summary = null;
        if (summary5.getSolos() < summary4.getSolos() || (summary5.getSolos() == summary4.getSolos()
                && summary5.getSencondLowestSolo().ordinal() >= summary4.getSencondLowestSolo().ordinal())) {
            summary = summary5;
        } else {
            summary = summary4;
        }
        return summary;
    }

    private Hand findLowerHand(Hand hand, byte[] cards) {
        byte[] copyCards = Arrays.copyOf(cards, cards.length);
        HandType type = hand.getType();
        Hand lower = null;
        if (type.isChain()) {
            int width = type.getWidth();
            int start = 0;
            int end = 0;
            int lowerKey = -1;
            for (int i = 0; i < hand.getKey().ordinal();) {
                if (cards[i] >= width) {
                    start = i;
                    do {
                        end = i;
                        if (end - start == hand.getLen() - 1) {
                            break;
                        }
                        i++;
                    } while (i <= PokerType.ACE.ordinal() && cards[i] >= width);

                    if (end - start == hand.getLen() - 1) {
                        for (int j = start; j <= end; ++j) {
                            copyCards[j] -= width;
                        }
                        lowerKey = start;
                        break;
                    }
                } else {
                    i++;
                }
            }
            if (lowerKey >= 0) {
                lower = new Hand(hand.getType(), PokerType.values()[lowerKey], hand.getLen(), null);
            }
        } else {
            for (int i = 0; i < hand.getKey().ordinal(); i++) {
                if (cards[i] >= type.getWidth()) {
                    lower = new Hand(type, PokerType.values()[i], 1, null);
                    break;
                }
            }
        }
        if (lower != null && type.getKickerWidth() > 0) {
            int kickerLen = (hand.getType() == HandType.FOUR_DUAL_SOLO || hand.getType() == HandType.FOUR_DUAL_PAIR) ? 2
                    : hand.getLen();
            Set<PokerType> kickers = EnumSet.noneOf(PokerType.class);
            int maxKicker = cards[PokerType.BJOKER.ordinal()] == 1 ? PokerType.BJOKER.ordinal()
                    : PokerType.RJOKER.ordinal();
            for (int i = 0; i <= maxKicker; i++) {
                if (cards[i] >= type.getKickerWidth()
                        && (i < lower.getKey().ordinal() || i >= lower.getKey().ordinal() + lower.getLen())) {
                    kickers.add(PokerType.values()[i]);
                    if (kickers.size() == kickerLen) {
                        lower.setKickers(kickers);
                        break;
                    }
                }
            }
            if (kickers.size() < kickerLen) {
                lower = null;
            }
        }
        return lower;
    }

    private Hand findHigherHand(Hand hand, CardsSummary cards) {
        if (hand.getType() == HandType.NUKE) {
            return null;
        }
        Hand higher = null;
        byte[] copyCards = cards.getCards();
        HandType type = hand.getType();
        if (type.isChain()) {
            int width = type.getWidth();
            int maxKey = PokerType.ACE.ordinal() - hand.getLen() + 1;
            int start = 0;
            int end = 0;
            int higherKey = 0;
            for (int i = hand.getKey().ordinal() + 1; i <= maxKey;) {
                if (cards.get(i) >= width) {
                    start = i;
                    do {
                        end = i;
                        if (end - start == hand.getLen() - 1) {
                            break;
                        }
                        i++;
                    } while (i <= PokerType.ACE.ordinal() && cards.get(i) >= width);

                    if (end - start == hand.getLen() - 1) {
                        for (int j = start; j <= end; ++j) {
                            copyCards[j] -= width;
                        }
                        higherKey = start;
                        break;
                    }
                } else {
                    i++;
                }
            }
            if (higherKey > 0) {
                higher = new Hand(hand.getType(), PokerType.values()[higherKey], hand.getLen(), null);
            }
        } else {
            if (hand.getKey() != PokerType.RJOKER) {
                for (int i = hand.getKey().ordinal() + 1; i < AIConstant.CARD_TYPE; i++) {
                    if (cards.get(i) >= type.getWidth()) {
                        higher = new Hand(type, PokerType.values()[i], 1, null);
                        break;
                    }
                }
            }
        }

        if (higher != null && type.getKickerWidth() > 0) {
            int kickerLen = (hand.getType() == HandType.FOUR_DUAL_SOLO || hand.getType() == HandType.FOUR_DUAL_PAIR) ? 2
                    : hand.getLen();
            Set<PokerType> kickers = EnumSet.noneOf(PokerType.class);
            int maxKicker = cards.get(PokerType.BJOKER) == 1 ? PokerType.BJOKER.ordinal() : PokerType.RJOKER.ordinal();
            for (int i = cards.getLowest().ordinal(); i <= maxKicker; i++) {
                if (cards.get(i) >= type.getKickerWidth()
                        && (i < higher.getKey().ordinal() || i >= higher.getKey().ordinal() + higher.getLen())) {
                    kickers.add(PokerType.values()[i]);
                    if (kickers.size() == kickerLen) {
                        higher.setKickers(kickers);
                        break;
                    }
                }
            }
            if (kickers.size() < kickerLen) {
                higher = null;
            }
        }

        if (higher == null) {
            if (hand.getType() != HandType.BOMB) {
                for (int i = cards.getLowest().ordinal(); i <= PokerType.TWO.ordinal(); i++) {
                    if (cards.get(i) == 4) {
                        higher = new Hand(HandType.BOMB, PokerType.values()[i], 1, null);
                        break;
                    }
                }
            } else {
                for (int i = hand.getKey().ordinal() + 1; i <= PokerType.TWO.ordinal(); i++) {
                    if (cards.get(i) == 4) {
                        higher = new Hand(HandType.BOMB, PokerType.values()[i], 1, null);
                        break;
                    }
                }
            }
            if (higher == null && cards.get(PokerType.BJOKER) == 1 && cards.get(PokerType.RJOKER) == 1) {
                higher = new Hand(HandType.NUKE, PokerType.BJOKER, 1, null);
            }
        }
        return higher;
    }

    private Hand findHigherHandInMap(Map<HandType, List<Hand>> handMap, Hand curHand) {
        if (curHand.getType() == HandType.NUKE) {
            return null;
        }
        List<Hand> hands = handMap.get(curHand.getType());
        if (hands != null) {
            if (curHand.getType() == HandType.TRIO_PAIR || curHand.getType() == HandType.TRIO_SOLO) {// 交换最小带牌
                // （）
                Set<PokerType> kicker = hands.get(0).getKickers();
                for (Hand hand : hands) {
                    if (isHandHigherThan(hand, curHand)) {
                        return new Hand(curHand.getType(), hand.getKey(), 1, kicker);
                    }
                }
            } else {
                for (Hand hand : hands) {
                    if (isHandHigherThan(hand, curHand)) {
                        return hand;
                    }
                }
            }
        } else {
            if (curHand.getType() == HandType.TRIO_PAIR) {// 如果为三带二，组合中没有能大的上的，尝试通过三不带和三带一中找
                hands = handMap.get(HandType.TRIO);
                List<Hand> trioSoloHands = handMap.get(HandType.TRIO_SOLO);
                if (trioSoloHands != null) {
                    hands.addAll(trioSoloHands);
                }
                if (hands != null) {
                    for (Hand hand : hands) {
                        if (hand.getKey().ordinal() > curHand.getKey().ordinal()) {
                            List<Hand> handPairs = handMap.get(HandType.PAIR);
                            if (handPairs != null && !handPairs.isEmpty()) {
                                if (handPairs.get(0).getKey().ordinal() < PokerType.QUEEN.ordinal()) {
                                    Set<PokerType> kickers = EnumSet.of(handPairs.get(0).getKey());
                                    return new Hand(HandType.TRIO_PAIR, hand.getKey(), 1, kickers);
                                }
                            }
                        }
                    }
                }
            } else if (curHand.getType() == HandType.TRIO_SOLO) {
                hands = handMap.get(HandType.TRIO);
                List<Hand> trioPairHand = handMap.get(HandType.TRIO_PAIR);
                if (trioPairHand != null) {
                    hands.addAll(trioPairHand);
                }
                if (hands != null) {
                    for (Hand hand : hands) {
                        if (hand.getKey().ordinal() > curHand.getKey().ordinal()) {
                            List<Hand> handSolo = handMap.get(HandType.SOLO);
                            if (handSolo != null && !handSolo.isEmpty()) {
                                if (handSolo.get(0).getKey().ordinal() < PokerType.TWO.ordinal()) {
                                    Set<PokerType> kickers = EnumSet.of(handSolo.get(0).getKey());
                                    return new Hand(HandType.TRIO_SOLO, hand.getKey(), 1, kickers);
                                }
                            }
                        }
                    }
                }
            }
        }

        if (curHand.getType() != HandType.BOMB) {
            List<Hand> bombs = handMap.get(HandType.BOMB);
            if (bombs != null && !bombs.isEmpty()) {
                return bombs.get(0);
            }
        }
        return null;
    }

    private Hand findHigherHandInMapIgnoreBomb(Map<HandType, List<Hand>> handMap, Hand curHand) {
        if (curHand.getType() == HandType.NUKE) {
            return null;
        }
        List<Hand> hands = handMap.get(curHand.getType());
        if (hands != null) {
            for (Hand hand : hands) {
                if (isHandHigherThan(hand, curHand)) {
                    return hand;
                }
            }
        }
        return null;
    }

    private Hand findLowerHandInMap(Map<HandType, List<Hand>> handMap, Hand curHand) {
        List<Hand> hands = handMap.get(curHand.getType());
        if (hands != null) {
            for (Hand hand : hands) {
                if (isHandHigherThan(curHand, hand)) {
                    return hand;
                }
            }
        }
        return null;
    }

    private List<Hand> findAllHigherHandInMap(Map<HandType, List<Hand>> handMap, Hand curHand) {
        List<Hand> highers = new ArrayList<Hand>();
        if (curHand.getType() == HandType.NUKE) {
            return highers;
        }
        List<Hand> hands = handMap.get(curHand.getType());
        if (hands != null) {
            for (Hand hand : hands) {
                if (isHandHigherThan(hand, curHand)) {
                    highers.add(hand);
                }
            }
        }
        return highers;
    }

    private void findKickersForTrio(Map<HandType, List<Hand>> handMap, GameStatusAdv status, int lowestCon) {
        List<Hand> trioChains = handMap.get(HandType.TRIO_CHAIN);
        List<Hand> trios = handMap.get(HandType.TRIO);
        List<Hand> solos = handMap.get(HandType.SOLO);
        List<Hand> pairs = handMap.get(HandType.PAIR);
        if (trioChains != null && trioChains.size() > 0) {
            for (Iterator<Hand> iterator = trioChains.iterator(); iterator.hasNext();) {
                Hand trioChain = (Hand) iterator.next();
                boolean useSolo = (solos != null && solos.size() >= trioChain.getLen());
                boolean usePair = (pairs != null && pairs.size() >= trioChain.getLen());
                if (useSolo && usePair) {
                    int soloKey = solos.get(trioChain.getLen() - 1).getKey().ordinal();
                    int pairKey = pairs.get(trioChain.getLen() - 1).getKey().ordinal();
                    if (soloKey > pairKey + 1 || (soloKey == pairKey + 1 && soloKey >= lowestCon)) {
                        useSolo = false;
                    } else {
                        usePair = false;
                    }
                }
                if (useSolo) {
                    Set<PokerType> kickers = EnumSet.noneOf(PokerType.class);
                    for (int i = 0; i < trioChain.getLen(); i++) {
                        kickers.add(solos.get(0).getKey());
                        solos.remove(0);
                    }
                    insertHandToMap(handMap,
                            new Hand(HandType.TRIO_CHAIN_SOLO, trioChain.getKey(), trioChain.getLen(), kickers));
                    iterator.remove();
                } else if (usePair) {
                    Set<PokerType> kickers = EnumSet.noneOf(PokerType.class);
                    for (int i = 0; i < trioChain.getLen(); i++) {
                        kickers.add(pairs.get(0).getKey());
                        pairs.remove(0);
                    }
                    insertHandToMap(handMap,
                            new Hand(HandType.TRIO_CHAIN_PAIR, trioChain.getKey(), trioChain.getLen(), kickers));
                    iterator.remove();
                }
            }
        }
        if (trios != null && trios.size() > 0) {

            if ((solos != null && solos.size() >= trios.size()) && (pairs != null && pairs.size() >= trios.size())) {
                int soloKey = solos.get(trios.size() - 1).getKey().ordinal();
                int pairKey = pairs.get(trios.size() - 1).getKey().ordinal();
                boolean usePair = !isOppSolo(status)
                        && (soloKey > pairKey + 1 || (soloKey == pairKey + 1 && soloKey >= lowestCon));
                List<Hand> kickerList = usePair ? pairs : solos;
                HandType type = usePair ? HandType.TRIO_PAIR : HandType.TRIO_SOLO;
                for (Hand trio : trios) {
                    Set<PokerType> kicker = EnumSet.of(kickerList.get(0).getKey());
                    kickerList.remove(0);
                    insertHandToMap(handMap, new Hand(type, trio.getKey(), 1, kicker));
                }
                trios.clear();
            } else {
                for (Iterator<Hand> iterator = trios.iterator(); iterator.hasNext();) {
                    Hand trio = (Hand) iterator.next();
                    List<Hand> kickerList = null;
                    HandType type = HandType.TRIO_SOLO;
                    if (solos != null && solos.size() > 0) {
                        kickerList = solos;
                    } else if (pairs != null && pairs.size() > 0) {
                        kickerList = pairs;
                        type = HandType.TRIO_PAIR;
                    }
                    if (kickerList != null) {
                        Set<PokerType> kicker = EnumSet.of(kickerList.get(0).getKey());
                        kickerList.remove(0);
                        insertHandToMap(handMap, new Hand(type, trio.getKey(), 1, kicker));
                        iterator.remove();
                    }
                }
            }

        }
    }

    private void findKickersForTrio(Map<HandType, List<Hand>> handMap, GameStatusAdv status, int lowestCon, int pos) {
        List<Hand> trioChains = handMap.get(HandType.TRIO_CHAIN);
        List<Hand> trios = handMap.get(HandType.TRIO);
        List<Hand> solos = handMap.get(HandType.SOLO);
        List<Hand> pairs = handMap.get(HandType.PAIR);
        if (trioChains != null && trioChains.size() > 0) {
            for (Iterator<Hand> iterator = trioChains.iterator(); iterator.hasNext();) {
                Hand trioChain = (Hand) iterator.next();
                boolean useSolo = (solos != null && solos.size() >= trioChain.getLen());
                boolean usePair = (pairs != null && pairs.size() >= trioChain.getLen());
                if (useSolo && usePair) {
                    int soloKey = solos.get(trioChain.getLen() - 1).getKey().ordinal();
                    int pairKey = pairs.get(trioChain.getLen() - 1).getKey().ordinal();
                    if (soloKey > pairKey + 1 || (soloKey == pairKey + 1 && soloKey >= lowestCon)) {
                        useSolo = false;
                    } else {
                        usePair = false;
                    }
                }
                if (useSolo) {
                    Set<PokerType> kickers = EnumSet.noneOf(PokerType.class);
                    for (int i = 0; i < trioChain.getLen(); i++) {
                        kickers.add(solos.get(0).getKey());
                        solos.remove(0);
                    }
                    insertHandToMap(handMap,
                            new Hand(HandType.TRIO_CHAIN_SOLO, trioChain.getKey(), trioChain.getLen(), kickers));
                    iterator.remove();
                } else if (usePair) {
                    Set<PokerType> kickers = EnumSet.noneOf(PokerType.class);
                    for (int i = 0; i < trioChain.getLen(); i++) {
                        kickers.add(pairs.get(0).getKey());
                        pairs.remove(0);
                    }
                    insertHandToMap(handMap,
                            new Hand(HandType.TRIO_CHAIN_PAIR, trioChain.getKey(), trioChain.getLen(), kickers));
                    iterator.remove();
                }
            }
        }
        if (trios != null && trios.size() > 0) {
            /**
             * 修改时间160931，panjy 将三带的带法 改成从小到大的顺序来带牌，可能带对也可能带单，
             */
            for (Iterator<Hand> iterator = trios.iterator(); iterator.hasNext();) {
                Hand trio = (Hand) iterator.next();
                PokerType pokerType = null;
                HandType type = HandType.TRIO_SOLO;
                if ((solos != null && solos.size() > 0) && (pairs != null && pairs.size() > 0)) {// 当单和对都不为空時
                    int soloIndex = solos.get(0).getKey().ordinal();
                    int pairIndex = pairs.get(0).getKey().ordinal();
                    if (soloIndex < pairIndex + 4 && soloIndex < lowestCon) {
                        pokerType = solos.get(0).getKey();
                        solos.remove(0);
                    } else {
                        if (!isHighestHand(pairs.get(0), status, pos)) {// 如果對子不是敵方要不起的牌
                            pokerType = pairs.get(0).getKey();
                            type = HandType.TRIO_PAIR;
                            pairs.remove(0);
                        } else {
                            if (solos.get(0).getKey().ordinal() < lowestCon) {
                                pokerType = solos.get(0).getKey();
                                solos.remove(0);
                            }
                        }

                    }
                } else if ((solos == null || solos.size() <= 0) && (pairs != null && pairs.size() > 0)) {// 当只有对子的时候
                    if (!isHighestHand(pairs.get(0), status, pos)) {// 如果對子不是敵方要不起的牌
                        pokerType = pairs.get(0).getKey();
                        type = HandType.TRIO_PAIR;
                        pairs.remove(0);
                    }
                } else if (solos != null && solos.size() > 0) {// 当只有solo的时候
                    if (solos.get(0).getKey().ordinal() < lowestCon) {
                        pokerType = solos.get(0).getKey();
                        solos.remove(0);
                    }
                }
                if (pokerType != null) {
                    Set<PokerType> kicker = EnumSet.of(pokerType);
                    insertHandToMap(handMap, new Hand(type, trio.getKey(), 1, kicker));
                    iterator.remove();
                }

            }

        }
    }

    private Hand findLowestHand(Map<HandType, List<Hand>> handMap) {
        Set<Map.Entry<HandType, List<Hand>>> entrySet = handMap.entrySet();
        Hand lowestHand = null;
        for (Map.Entry<HandType, List<Hand>> entry : entrySet) {
            if (entry.getKey() == HandType.BOMB || entry.getValue() == null) {
                continue;
            }
            for (Hand hand : entry.getValue()) {
                if (lowestHand == null || hand.getKey().ordinal() < lowestHand.getKey().ordinal()
                        || (hand.getKey().ordinal() == lowestHand.getKey().ordinal()
                        && hand.size() > lowestHand.size())) {
                    lowestHand = hand;
                }
            }
        }
        if (lowestHand == null) {
            List<Hand> bombs = handMap.get(HandType.BOMB);
            if (bombs != null && bombs.size() > 0)
                lowestHand = bombs.get(0);
        }
        return lowestHand;
    }

    private Hand getOnlyHand(HandMapSummary summary) {
        List<Hand> highestHand = summary.getHighestHandList();
        if (highestHand != null && !highestHand.isEmpty()) {
            return highestHand.get(0);
        } else {
            return summary.getUnHighestHandList().get(0);
        }
    }

    private void getBomb(byte[] cards, Map<HandType, List<Hand>> handMap) {
        List<Hand> hands = new ArrayList<Hand>();
        for (int i = 0; i <= PokerType.TWO.ordinal(); i++) {
            if (cards[i] == 4) {
                Hand hand = new Hand(HandType.BOMB, PokerType.values()[i], 1, null);
                hands.add(hand);
                cards[i] = 0;
            }
        }
        if (cards[PokerType.BJOKER.ordinal()] == 1 && cards[PokerType.RJOKER.ordinal()] == 1) {
            Hand hand = new Hand(HandType.NUKE, PokerType.BJOKER, 1, null);
            hands.add(hand);
            cards[PokerType.BJOKER.ordinal()] = 0;
            cards[PokerType.RJOKER.ordinal()] = 0;
        }
        handMap.put(HandType.BOMB, hands);
    }

    private void getNormalHand(byte[] cards, Map<HandType, List<Hand>> handMap) {
        List<Hand> solos = handMap.get(HandType.SOLO);
        if (solos == null) {
            solos = new ArrayList<Hand>();
            handMap.put(HandType.SOLO, solos);
        }
        List<Hand> pairs = handMap.get(HandType.PAIR);
        if (pairs == null) {
            pairs = new ArrayList<Hand>();
            handMap.put(HandType.PAIR, pairs);
        }
        List<Hand> trios = handMap.get(HandType.TRIO);
        if (trios == null) {
            trios = new ArrayList<Hand>();
            handMap.put(HandType.TRIO, trios);
        }
        List<Hand> bombs = handMap.get(HandType.BOMB);
        if (bombs == null) {
            bombs = new ArrayList<Hand>();
            handMap.put(HandType.BOMB, bombs);
        }
        if (cards[PokerType.BJOKER.ordinal()] == 1 && cards[PokerType.RJOKER.ordinal()] == 1) {
            bombs.add(new Hand(HandType.NUKE, PokerType.BJOKER, 1, null));
            cards[PokerType.BJOKER.ordinal()] = 0;
            cards[PokerType.RJOKER.ordinal()] = 0;
        }

        for (int i = 0; i <= PokerType.RJOKER.ordinal(); i++) {
            switch (cards[i]) {
                case 1:
                    solos.add(new Hand(HandType.SOLO, PokerType.values()[i], 1, null));
                    cards[i] = 0;
                    break;
                case 2:
                    pairs.add(new Hand(HandType.PAIR, PokerType.values()[i], 1, null));
                    cards[i] = 0;
                    break;
                case 3:
                    trios.add(new Hand(HandType.TRIO, PokerType.values()[i], 1, null));
                    cards[i] = 0;
                    break;
                case 4:
                    bombs.add(new Hand(HandType.BOMB, PokerType.values()[i], 1, null));
                    cards[i] = 0;
                    break;
                default:
                    break;
            }
        }
    }

    private void getPairChain(byte[] cards, Map<HandType, List<Hand>> handMap) {
        List<Hand> hands = new ArrayList<Hand>();
        int start;
        int end;
        for (int i = 0; i <= PokerType.QUEEN.ordinal(); i++) {
            if (cards[i] >= 2) {
                start = i;
                do {
                    end = i;
                    i++;
                } while (i <= PokerType.ACE.ordinal() && cards[i] >= 2);
                if (end - start + 1 >= AIConstant.MIN_PAIR_CHAIN_LENGTH) {
                    Hand hand = new Hand(HandType.PAIR_CHAIN, PokerType.values()[start], end - start + 1, null);
                    hands.add(hand);
                    for (int j = start; j <= end; j++) {
                        cards[j] -= 2;
                    }
                }
            }
        }
        handMap.put(HandType.PAIR_CHAIN, hands);
    }

    private void getSoloChain(byte[] cards, Map<HandType, List<Hand>> handMap) {
        List<Hand> hands = new ArrayList<Hand>();
        int start;
        int end;
        for (int i = 0; i <= PokerType.TEN.ordinal();) {
            if (cards[i] >= 1) {
                start = i;
                end = start;
                while (end + 1 <= PokerType.ACE.ordinal() && cards[end + 1] >= 1
                        && end - start + 1 < AIConstant.MIN_SOLO_CHAIN_LENGTH) {
                    end++;
                }
                if (end - start + 1 >= AIConstant.MIN_SOLO_CHAIN_LENGTH) {
                    Hand hand = new Hand(HandType.SOLO_CHAIN, PokerType.values()[start], end - start + 1, null);
                    hands.add(hand);
                    for (int j = start; j <= end; ++j) {
                        cards[j]--;
                    }
                } else {
                    i = end + 1;
                }
            } else {
                i++;
            }
        }
        expandSoloChain(cards, hands);
        optimizeSoloChain(cards, hands);
        handMap.put(HandType.SOLO_CHAIN, hands);
    }

    private void expandSoloChain(byte[] cards, List<Hand> soloChains) {
        for (int i = 0; i < soloChains.size(); i++) {
            Hand chain = soloChains.get(i);
            int end = chain.getKey().ordinal() + chain.getLen();
            while (end <= PokerType.ACE.ordinal() && cards[end] > 0) {
                cards[end]--;
                end++;
            }
            Hand newChain = new Hand(HandType.SOLO_CHAIN, chain.getKey(), end - chain.getKey().ordinal(), null);
            soloChains.set(i, newChain);
        }
        for (int i = 0; i < soloChains.size() - 1; i++) {
            for (int j = i + 1; j < soloChains.size();) {
                Hand first = soloChains.get(i);
                Hand second = soloChains.get(j);
                if (first.getKey().ordinal() + first.getLen() == second.getKey().ordinal()) {
                    Hand newChain = new Hand(HandType.SOLO_CHAIN, first.getKey(), first.getLen() + second.getLen(),
                            null);
                    soloChains.set(i, newChain);
                    soloChains.remove(j);
                } else {
                    j++;
                }
            }
        }
    }

    private void optimizeSoloChain(byte[] cards, List<Hand> soloChains) {
        for (int j = 0; j < soloChains.size(); j++) {
            Hand chain = soloChains.get(j);
            int maxPairLen = chain.getLen() - 5;
            if (maxPairLen >= 3) {
                int start = chain.getKey().ordinal();
                int end = chain.getKey().ordinal() + chain.getLen() - 1;
                int ll = 0;
                int hl = 0;
                while (ll < maxPairLen && cards[start + ll] > 0) {
                    ll++;
                }
                while (hl < maxPairLen && cards[end - hl] > 0) {
                    hl++;
                }
                int newStart = start;
                int newEnd = end;
                if (ll >= 3 && (ll >= hl || ll + hl <= maxPairLen)) {
                    newStart = start + ll;
                    for (int i = start; i <= newStart - 1; ++i) {
                        cards[i]++;
                    }
                }
                if (hl >= 3 && (hl > ll || ll + hl <= maxPairLen)) {
                    newEnd = end - hl;
                    for (int i = newEnd + 1; i <= end; ++i) {
                        cards[i]++;
                    }
                }
                Hand newChain = new Hand(HandType.SOLO_CHAIN, PokerType.values()[newStart], newEnd - newStart + 1,
                        null);
                soloChains.set(j, newChain);
            }
        }

        for (int j = 0; j < soloChains.size(); j++) {
            Hand chain = soloChains.get(j);
            int start = chain.getKey().ordinal();
            int end = chain.getKey().ordinal() + chain.getLen() - 1;
            int newStart = start;
            int newEnd = end;
            while (newEnd - newStart >= 5) {
                if (cards[newStart] > 0 && cards[newStart] >= cards[newEnd]) {
                    newStart++;
                } else if (cards[newEnd] > cards[newStart]) {
                    newEnd--;
                } else {
                    break;
                }
            }
            for (int i = start; i <= newStart - 1; ++i) {
                cards[i]++;
            }
            for (int i = newEnd + 1; i <= end; ++i) {
                cards[i]++;
            }
            Hand newChain = new Hand(HandType.SOLO_CHAIN, PokerType.values()[newStart], newEnd - newStart + 1, null);
            soloChains.set(j, newChain);
        }
    }

    private void getTrio(byte[] cards, Map<HandType, List<Hand>> handMap) {
        List<Hand> hands = new ArrayList<Hand>();
        for (int i = 0; i <= PokerType.TWO.ordinal(); i++) {
            if (cards[i] == 3) {
                Hand hand = new Hand(HandType.TRIO, PokerType.values()[i], 1, null);
                hands.add(hand);
                cards[i] = 0;
            }
        }
        handMap.put(HandType.TRIO, hands);
    }

    private void getTrioChain(byte[] cards, Map<HandType, List<Hand>> handMap) {
        List<Hand> hands = new ArrayList<Hand>();
        int start;
        int end;
        for (int i = 0; i <= PokerType.KING.ordinal(); i++) {
            if (cards[i] >= 3) {
                start = i;
                do {
                    end = i;
                    i++;
                } while (i <= PokerType.ACE.ordinal() && cards[i] >= 3);
                if (end - start + 1 >= AIConstant.MIN_TRIO_CHAIN_LENGTH) {
                    Hand hand = new Hand(HandType.TRIO_CHAIN, PokerType.values()[start], end - start + 1, null);
                    hands.add(hand);
                    for (int j = start; j <= end; j++) {
                        cards[j] -= 3;
                    }
                }
            }
        }
        handMap.put(HandType.TRIO_CHAIN, hands);
    }

    private Hand getWinHandForFarmer(HandMapSummary[] summarys, GameStatusAdv status) {
        int other = status.getCurPlayer() == AIConstant.UP_FARMER ? AIConstant.DOWN_FARMER : AIConstant.UP_FARMER;
        CardsSummary aiCards = status.getPlayerCards()[status.getCurPlayer()];
        CardsSummary lordCards = status.getPlayerCards()[AIConstant.LORD];
        CardsSummary otherCards = status.getPlayerCards()[other];
        HandMapSummary aiSummary = summarys[status.getCurPlayer()];
        HandMapSummary lordSummary = summarys[AIConstant.LORD];
        Map<HandType, List<Hand>> handMap = aiSummary.getHandMap();
        Map<HandType, List<Hand>> lordHandMap = lordSummary.getHandMap();

        List<Hand> highestHandList = aiSummary.getHighestHandList();
        List<Hand> unHighestHandList = aiSummary.getUnHighestHandList();
        if (aiSummary.getRealHands() == 1) {
            if (!highestHandList.isEmpty()) {
                return highestHandList.get(0);
            } else {
                return unHighestHandList.get(0);
            }
        }

        List<Hand> bombs = handMap.get(HandType.BOMB);
        List<Hand> lordBombs = lordHandMap.get(HandType.BOMB);
        boolean lordHasBomb = lordBombs != null && !lordBombs.isEmpty();
        int bombSize = bombs == null ? 0 : bombs.size();
        if (aiSummary.getUnHighestHandList().size() <= bombSize + 1) {
            for (Hand highestHand : highestHandList) {
                if (!AIUtils.isBomb(highestHand)) {
                    for (Hand unHighestHand : unHighestHandList) {
                        if (unHighestHand.getType() == highestHand.getType()
                                && unHighestHand.getLen() == highestHand.getLen()
                                && !AIUtils.containsHand(lordCards.getCards(), highestHand)) {
                            if (lordHasBomb) {
                                if (findHigherHandInMapIgnoreBomb(lordHandMap, unHighestHand) != null
                                        && aiSummary.getRealHands() > 2) {
                                    Hand lordHighestBomb = lordBombs.get(lordBombs.size() - 1);
                                    if (findHigherHand(lordHighestBomb, aiCards) == null
                                            && findHigherHand(lordHighestBomb, otherCards) == null) {
                                        if (lordSummary.getRealHands() <= 2 * lordBombs.size() + 1) {
                                            continue;
                                        }
                                    } else {
                                        if (lordSummary.getRealHands() <= 2 * lordBombs.size()) {
                                            continue;
                                        }
                                    }
                                }
                            } else {
                                if (unHighestHand.size() == lordCards.getNum()) {
                                    Hand lordHand = AIUtils.cardsToHand(lordCards);
                                    if (lordHand != null && isHandHigherThan(lordHand, unHighestHand)) {
                                        continue;
                                    }

                                }
                            }
                            return unHighestHand;
                        }
                    }
                }
            }
            Hand hand = null;
            for (Hand highestHand : highestHandList) {
                if (!AIUtils.isBomb(highestHand)) {
                    if (hand == null || hand.getKey().ordinal() > highestHand.getKey().ordinal()) {
                        hand = highestHand;
                    }
                }
            }
            if (hand != null) {
                return hand;
            }
            int lowerHandNum = 0;
            for (Hand unHighestHand : unHighestHandList) {
                if (unHighestHand.size() != lordCards.getNum()) {
                    return unHighestHand;
                } else {
                    Hand lordHand = AIUtils.cardsToHand(lordCards);
                    if (lordHand == null || !isHandHigherThan(lordHand, unHighestHand)) {
                        return unHighestHand;
                    } else {
                        lowerHandNum++;
                    }
                }
            }
            if (lordCards.getNum() == 1) {
                return null;
            }
            if (bombSize > 0 && aiSummary.getUnHighestHandList().size() <= bombSize && lowerHandNum == 1) {
                return bombs.get(0);
            }
        }
        return null;
    }

    private boolean isSpring(CardsSummary[] allCards) {
        return allCards[AIConstant.DOWN_FARMER].getNum() == 17 && allCards[AIConstant.UP_FARMER].getNum() == 17;
    }

    private boolean hasHigherHandIgnoreBomb(Hand hand, CardsSummary cards) {
        if (AIUtils.isBomb(hand)) {
            return false;
        }
        if (cards.getNum() < hand.size()) {
            return false;
        }
        Hand higher = findHigherHand(hand, cards);
        if (higher != null && !AIUtils.isBomb(higher)) {
            return true;
        } else {
            return false;
        }
    }

    private void insertHandToMap(Map<HandType, List<Hand>> handMap, Hand hand) {
        List<Hand> hands = handMap.get(hand.getType());
        if (hands == null) {
            hands = new ArrayList<Hand>();
            handMap.put(hand.getType(), hands);
        }
        hands.add(hand);
    }

    private boolean containControl(Hand hand, int lowestCon) {
        if (AIUtils.isBomb(hand)) {
            return true;
        }
        if (hand.getKey().ordinal() + hand.getLen() - 1 >= lowestCon) {
            return true;
        }
        Set<PokerType> kickers = hand.getKickers();
        if (kickers != null) {
            for (PokerType kicker : kickers) {
                if (kicker.ordinal() >= lowestCon) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isFirstHalf(GameStatusAdv status) {
        CardsSummary[] allCards = status.getPlayerCards();
        if (status.getCurPlayer() == AIConstant.LORD) {
            return allCards[AIConstant.UP_FARMER].getNum() > 8 && allCards[AIConstant.DOWN_FARMER].getNum() > 8;
        } else {
            return allCards[AIConstant.LORD].getNum() > 10;
        }
    }

    private boolean isGoodFarmer(HandMapSummary[] summarys, GameStatusAdv status, int lowestControl) {
        boolean isGood = false;
        HandMapSummary aiSummary = summarys[status.getCurPlayer()];

        CardsSummary[] allCards = status.getPlayerCards();
        CardsSummary lordCards = allCards[AIConstant.LORD];
        if (lordCards.getNum() == 1) {
            if (aiSummary.getSencondLowestSolo().ordinal() >= lordCards.getLowest().ordinal()) {
                isGood = true;
            } else {
                HandMapSummary newSummary = findBestHandMapLordOnly1(allCards[status.getCurPlayer()].getCards(), status,
                        lowestControl, status.getCurPlayer());
                summarys[status.getCurPlayer()] = newSummary;
                isGood = newSummary.getSencondLowestSolo().ordinal() >= lordCards.getLowest().ordinal();
            }
        } else {
            double eff = aiSummary.getEffectiveHands() - aiSummary.getExtraBomb();
            if (isFirstHalf(status)) {
                isGood = eff < 5.1;
            } else {
                if (status.getCurPlayer() == AIConstant.UP_FARMER) {
                    isGood = eff < 2.1;
                } else {
                    isGood = eff < 3.1;
                }
            }
        }

        int otherFarmer = status.getCurPlayer() == AIConstant.UP_FARMER ? AIConstant.DOWN_FARMER : AIConstant.UP_FARMER;
        HandMapSummary otherSummary = summarys[otherFarmer];
        HandMapSummary lordSummary = summarys[AIConstant.LORD];
        if (!isGood) {
            if (aiSummary.getEffectiveHands() <= 3 || aiSummary.getEffectiveHands() < otherSummary.getEffectiveHands()
                    || aiSummary.getEffectiveHands() < lordSummary.getEffectiveHands() + 1) {
                isGood = true;
            }
        } else {
            if (aiSummary.getEffectiveHands() >= 3 && aiSummary.getEffectiveHands() > otherSummary.getEffectiveHands()
                    && aiSummary.getEffectiveHands() >= lordSummary.getEffectiveHands() + 1) {
                isGood = false;
            }
        }
        return isGood;
    }

    private boolean isHandHigherThan(Hand h0, Hand h1) {
        if (h1.getType() == HandType.NUKE) {
            return false;
        } else if (h1.getType() == HandType.BOMB) {
            return h0.getType() == HandType.NUKE
                    || (h0.getType() == HandType.BOMB && h0.getKey().ordinal() > h1.getKey().ordinal());
        } else {
            if (h0.getType() == HandType.NUKE || h0.getType() == HandType.BOMB) {
                return true;
            } else {
                if (h0.getType() == h1.getType() && h0.getKey().ordinal() > h1.getKey().ordinal()) {
                    if (h0.getType().isChain()) {
                        return h0.getLen() == h1.getLen();
                    } else {
                        return true;
                    }
                } else {
                    return false;
                }
            }
        }
    }

    private boolean isHighestHand(Hand hand, GameStatusAdv status, int pos) {
        if (pos == AIConstant.LORD) {
            return !hasHigherHandIgnoreBomb(hand, status.getPlayerCards()[AIConstant.DOWN_FARMER])
                    && !hasHigherHandIgnoreBomb(hand, status.getPlayerCards()[AIConstant.UP_FARMER]);
        } else {
            return !hasHigherHandIgnoreBomb(hand, status.getPlayerCards()[AIConstant.LORD]);
        }
    }

    private boolean isOppSolo(GameStatusAdv status) {
        CardsSummary[] cards = status.getPlayerCards();
        if (status.getCurPlayer() == AIConstant.LORD) {
            return cards[AIConstant.DOWN_FARMER].getNum() == 1 || cards[AIConstant.UP_FARMER].getNum() == 1;
        } else {
            return cards[AIConstant.LORD].getNum() == 1;
        }
    }

    private Hand lordPlayFree(HandMapSummary[] summarys, GameStatusAdv status, int lowestCon) {
        // Hand hand = findLowestHand(summarys[AIConstant.LORD].getHandMap());
        Hand hand = null;
        CardsSummary[] allCards = status.getPlayerCards();

        CardsSummary upCards = allCards[AIConstant.UP_FARMER];
        CardsSummary downCards = allCards[AIConstant.DOWN_FARMER];

        HandMapSummary lordSummary = summarys[AIConstant.LORD];
        Map<HandType, List<Hand>> lordHandMap = lordSummary.getHandMap();

        List<Hand> highestHands = lordSummary.getHighestHandList();
        List<Hand> unhighestHands = lordSummary.getUnHighestHandList();
        List<Hand> bombs = lordHandMap.get(HandType.BOMB);
        List<Hand> solos = lordHandMap.get(HandType.SOLO);
        // // 春天
        // if (upCards.getNum() + downCards.getNum() == 34) {
        // if (unhighestHands.size() == 1) {
        // if (AIUtils.getBombNum(upCards) == 0 && AIUtils.getBombNum(downCards)
        // == 0) {
        // // return
        // }
        // }
        // }

        if (lordSummary.getRealHands() == 1) {
            hand = highestHands.isEmpty() ? unhighestHands.get(0) : highestHands.get(0);
        } else if (lordSummary.getUnHighestHandList().size() <= bombs.size() + 1) {
            Hand upHand = AIUtils.cardsToHand(upCards);
            Hand downHand = AIUtils.cardsToHand(downCards);
            for (Hand highestHand : highestHands) {
                if (!AIUtils.isBomb(highestHand)) {
                    for (Hand unHighestHand : unhighestHands) {
                        if (unHighestHand.getType() == highestHand.getType()
                                && unHighestHand.getLen() == highestHand.getLen()) {
                            if (unHighestHand != null && (upHand == null || !isHandHigherThan(upHand, unHighestHand))
                                    && (downHand == null || !isHandHigherThan(downHand, unHighestHand))) {
                                if (unHighestHand.getType() == HandType.SOLO
                                        || unHighestHand.getType() == HandType.PAIR) {// 需要排除同等大小的最大牌
                                    int key = highestHand.getKey().ordinal();
                                    int upKeyCount = upCards.get(key);
                                    int downKeyCount = downCards.get(key);
                                    if (downKeyCount < 1 && upKeyCount < 1
                                            && unHighestHand.getType() == HandType.SOLO) {
                                        hand = unHighestHand;
                                        break;
                                    }
                                    if (downKeyCount < 2 && upKeyCount < 2
                                            && unHighestHand.getType() == HandType.PAIR) {
                                        hand = unHighestHand;
                                        break;
                                    }
                                } else {
                                    hand = unHighestHand;
                                    break;
                                }
                            }
                        }
                    }
                }
            }
            if (hand == null) {
                if (bombs.size() > 0 && unhighestHands.size() > 1) {
                    for (Hand unHighestHand : unhighestHands) {
                        if ((upHand == null || !isHandHigherThan(upHand, unHighestHand))
                                && (downHand == null || !isHandHigherThan(downHand, unHighestHand))) {
                            hand = unHighestHand;
                            break;
                        }
                    }
                }
            }
            if (hand == null) {
                Hand tempHand = null;
                for (Hand highestHand : highestHands) {
                    if (!AIUtils.isBomb(highestHand)) {
                        if (tempHand == null) {
                            tempHand = highestHand;
                        } else {
                            if (highestHand.getKey().ordinal() < tempHand.getKey().ordinal()) {
                                tempHand = highestHand;
                            }
                        }
                        // hand = highestHand;
                        // break;
                    }
                }
                hand = tempHand;
            }
            if (hand == null) {
                for (Hand unHighestHand : unhighestHands) {
                    if ((upHand == null || !isHandHigherThan(upHand, unHighestHand))
                            && (downHand == null || !isHandHigherThan(downHand, unHighestHand))) {
                        hand = unHighestHand;
                        break;
                    }
                }
            }
            if (hand == null) {
                if (!bombs.isEmpty() && unhighestHands.size() <= bombs.size()) {
                    hand = bombs.get(0);
                }
            }
        } else {
            int farmerSolo = -1;
            if (upCards.getNum() == 1) {
                farmerSolo = upCards.getLowest().ordinal();
            }
            if (downCards.getNum() == 1) {
                if (farmerSolo == -1 || farmerSolo < downCards.getLowest().ordinal()) {
                    farmerSolo = downCards.getLowest().ordinal();
                }
            }
            if (farmerSolo >= 0) {
                for (Hand highestHand : highestHands) {
                    if (highestHand.getType() == HandType.PAIR_CHAIN
                            && highestHand.getKey().ordinal() < PokerType.NINE.ordinal()) {
                        hand = highestHand;
                        break;
                    }
                    if (highestHand.getType() == HandType.SOLO_CHAIN) {
                        Hand lower = findLowerHandInMap(lordHandMap, highestHand);
                        hand = lower == null ? highestHand : lower;
                        break;
                    }
                    if (highestHand.getType() == HandType.TRIO_CHAIN_SOLO
                            || highestHand.getType() == HandType.TRIO_CHAIN_PAIR) {
                        hand = highestHand;
                        break;
                    }
                }
                if (hand == null) {
                    for (Hand unhighestHand : unhighestHands) {
                        if (unhighestHand.getType() != HandType.SOLO && !containControl(unhighestHand, lowestCon)) {
                            hand = unhighestHand;
                            break;
                        }
                    }
                }
                if (hand == null) {
                    if (solos.size() >= 2) {
                        if (solos.get(1).getKey().ordinal() > farmerSolo) {
                            hand = solos.get(1);
                        }
                    }
                }
                if (hand == null) {
                    for (Hand unhighestHand : unhighestHands) {
                        if (unhighestHand.getType() != HandType.SOLO) {
                            hand = unhighestHand;
                            break;
                        }
                    }
                }
                if (hand == null) {
                    for (Hand highestHand : highestHands) {
                        if (!AIUtils.isBomb(highestHand)) {
                            hand = highestHand;
                            break;
                        }
                    }
                }
                if (hand == null) {
                    if (!bombs.isEmpty()) {
                        hand = bombs.get(0);
                    }
                }
                if (hand == null) {
                    if (!solos.isEmpty()) {
                        hand = solos.get(0);
                    }
                }
            } else {
				/*
				if (unhighestHands.size() == 2) {
					HandType type0 = unhighestHands.get(0).getType();
					HandType type1 = unhighestHands.get(1).getType();
					if ((type0 == HandType.SOLO || type0 == HandType.PAIR)
							&& (type1 == HandType.SOLO || type1 == HandType.PAIR) && bombs.isEmpty()
							&& lordSummary.getSoloConNum() < 0.5) {
						if (!highestHands.isEmpty()) {
							hand = highestHands.get(0);
						}
					}
				}*/
                if (hand == null) {
                    for (Hand highestHand : highestHands) {
                        if (highestHand.getType() == HandType.SOLO_CHAIN
                                && highestHand.getKey().ordinal() <= PokerType.SIX.ordinal()) {
                            hand = highestHand;
                            break;
                        }
                        if (highestHand.getType() == HandType.PAIR_CHAIN
                                && highestHand.getKey().ordinal() <= PokerType.EIGHT.ordinal()) {
                            hand = highestHand;
                            break;
                        }
                        if ((highestHand.getType() == HandType.TRIO_CHAIN_SOLO
                                || highestHand.getType() == HandType.TRIO_CHAIN_PAIR)
                                && highestHand.getKey().ordinal() <= PokerType.SEVEN.ordinal()) {
                            hand = highestHand;
                            break;
                        }
                    }
                }
                if (hand == null) {
                    if (lordSummary.getRealHands() - lordSummary.getSoloConNum() + lordSummary.getExtraBomb() < 2) {
                        Set<Map.Entry<HandType, List<Hand>>> entrySet = lordHandMap.entrySet();
                        int maxCount = 0;
                        Hand minHand = null;
                        for (Map.Entry<HandType, List<Hand>> entry : entrySet) {
                            HandType type = entry.getKey();
                            if (type == HandType.PAIR || type == HandType.PAIR_CHAIN || type == HandType.SOLO
                                    || type == HandType.SOLO_CHAIN || type == HandType.TRIO
                                    || type == HandType.TRIO_CHAIN || type == HandType.TRIO_CHAIN_PAIR
                                    || type == HandType.TRIO_CHAIN_SOLO || type == HandType.TRIO_PAIR
                                    || type == HandType.TRIO_SOLO) {
                                List<Hand> hands = entry.getValue();
                                if (hands != null && hands.size() > 0) {
                                    if (hands.size() > maxCount) {
                                        Hand hand2 = entry.getValue().get(0);
                                        if (!containControl(hand2, lowestCon)) {
                                            minHand = hand2;
                                            maxCount = hands.size();
                                        }
                                    } else if (hands.size() == maxCount) {
                                        if (minHand.size() < hands.get(0).size()) {
                                            Hand hand2 = entry.getValue().get(0);
                                            if (!containControl(hand2, lowestCon)) {
                                                minHand = hand2;
                                            }
                                        }
                                    }

                                }
                            }

                        }
                        hand = minHand;
                    }
                    // hand = findLowestHand(lordSummary.getHandMap());
                }
            }
        }
        if (hand == null) {
            hand = findLowestHand(lordSummary.getHandMap());
        }
        return hand;
    }

    private Hand lordPlayHigher(HandMapSummary[] summarys, GameStatusAdv status, int lowestCon) {
        // Hand hand =
        // findHigherHandInMap(summarys[AIConstant.LORD].getHandMap(),
        // status.getOutHand());
        Hand hand = null;
        Hand curHand = status.getOutHand();
        CardsSummary[] allCards = status.getPlayerCards();

        CardsSummary lordCards = allCards[AIConstant.LORD];
        CardsSummary upCards = allCards[AIConstant.UP_FARMER];
        CardsSummary downCards = allCards[AIConstant.DOWN_FARMER];

        HandMapSummary lordSummary = summarys[AIConstant.LORD];
        Map<HandType, List<Hand>> lordHandMap = lordSummary.getHandMap();

        List<Hand> solos = lordHandMap.get(HandType.SOLO);
        hand = lordEasyWin(summarys, status, curHand);
        if (hand != null) {
            return hand;
        }
        if ((upCards.getNum() == 1 || downCards.getNum() == 1) && curHand.getType() == HandType.SOLO) {
            int farmerSolo = -1;
            if (upCards.getNum() == 1) {
                farmerSolo = upCards.getLowest().ordinal();
            }
            if (downCards.getNum() == 1) {
                if (farmerSolo == -1 || farmerSolo < downCards.getLowest().ordinal()) {
                    farmerSolo = downCards.getLowest().ordinal();
                }
            }
            if (solos.size() >= 2) {
                if (solos.get(1).getKey().ordinal() > curHand.getKey().ordinal()) {
                    return solos.get(1);
                }
            }
            if (solos.size() == 1) {
                if (solos.get(0).getKey().ordinal() > curHand.getKey().ordinal()
                        && solos.get(0).getKey().ordinal() >= farmerSolo) {
                    return solos.get(0);
                }
            }
            for (int i = Math.max(lordCards.getLowest().ordinal(), curHand.getKey().ordinal() + 1); i <= lordCards
                    .getHighest().ordinal(); i++) {
                if (lordCards.get(i) > 0) {
                    return new Hand(HandType.SOLO, i, 1, null);
                }
            }
            for (int i = lordCards.getHighest().ordinal(); i > curHand.getKey().ordinal(); i--) {
                if (lordCards.get(i) > 0) {
                    return new Hand(HandType.SOLO, i, 1, null);
                }
            }
        }
        Hand higher = findHigherHandInMap(lordHandMap, curHand);
        while (higher != null) {
            if (higher.getType().isChain()) {
                return higher;
            }
            if (!containControl(higher, lowestCon)) {
                return higher;
            }
            if (!AIUtils.isBomb(higher)) {
                byte[] handCards = AIUtils.handToCards(higher);
                byte[] tmpCards = AIUtils.subCards(lordCards.getCards(), handCards);
                double tmpConNum = AIUtils.calControl(tmpCards,
                        AIUtils.addCards(downCards.getCards(), upCards.getCards()), lowestCon);
                HandMapSummary tmpSummary = findBestHandMap(tmpCards, status, lowestCon, status.getCurPlayer(),
                        tmpConNum, false);
                if (Double.compare(tmpSummary.getEffectiveHands(), 2.0) < 0) {
                    return higher;
                }
                if (Double.compare(tmpSummary.getSoloConNum() + tmpSummary.getExtraBomb(), 2.9) > 0) {
                    return higher;
                }
                if (Double.compare(tmpSummary.getEffectiveHands(), lordSummary.getEffectiveHands()) <= 0) {
                    return higher;
                }
                if (Double.compare(tmpSummary.getEffectiveHands(), lordSummary.getEffectiveHands() + 1) <= 0
                        && findHigherHand(higher, downCards) == null && findHigherHand(higher, upCards) == null) {
                    return higher;
                }
                if (upCards.getNum() == 1
                        || (downCards.getNum() == 1 && status.getOutHandPlayer() == AIConstant.DOWN_FARMER)) {
                    return higher;
                }
            }
            higher = findHigherHandInMap(lordHandMap, higher);
        }

        higher = findHigherHand(curHand, lordCards);
        Hand bestHand = null;
        HandMapSummary bestSummary = null;
        while (higher != null) {
            byte[] handCards = AIUtils.handToCards(higher);
            byte[] tmpCards = AIUtils.subCards(lordCards.getCards(), handCards);
            double tmpConNum = AIUtils.calControl(tmpCards, AIUtils.addCards(downCards.getCards(), upCards.getCards()),
                    lowestCon);
            HandMapSummary tmpSummary = findBestHandMap(tmpCards, status, lowestCon, status.getCurPlayer(), tmpConNum,
                    false);
            if (Double.compare(tmpSummary.getEffectiveHands(), 2.0) < 0
                    || Double.compare(tmpSummary.getSoloConNum() + tmpSummary.getExtraBomb(), 2.9) > 0
                    || Double.compare(tmpSummary.getEffectiveHands(), lordSummary.getEffectiveHands()) <= 0
                    || (Double.compare(tmpSummary.getEffectiveHands(), lordSummary.getEffectiveHands() + 1) <= 0
                    && findHigherHand(higher, downCards) == null && findHigherHand(higher, upCards) == null)) {
                boolean isBetter = false;
                if (bestHand == null) {
                    isBetter = true;
                } else {
                    if (Double.compare(tmpSummary.getEffectiveHands(), bestSummary.getEffectiveHands()) < 0) {
                        isBetter = true;
                    } else if (Double.compare(tmpSummary.getEffectiveHands(), bestSummary.getEffectiveHands()) == 0) {
                        if (tmpSummary.getUnHighestHandList().size() < bestSummary.getUnHighestHandList().size()) {
                            isBetter = true;
                        } else if (tmpSummary.getUnHighestHandList().size() == bestSummary.getUnHighestHandList()
                                .size()) {
                            if (tmpSummary.getHandsType() < bestSummary.getHandsType()) {
                                isBetter = true;
                            } else if (tmpSummary.getHandsType() == bestSummary.getHandsType()) {
                                isBetter = tmpSummary.getSolos() < bestSummary.getSolos();
                            }
                        }
                    }
                }
                if (isBetter) {
                    bestHand = higher;
                    bestSummary = tmpSummary;
                }
            }
            higher = findHigherHand(higher, lordCards);
        }
        if (bestHand != null) {
            if (AIUtils.isBomb(bestHand)) {// 模拟剩余牌,如果能赢就出炸弹,不能赢就不出炸弹
                if (isWinAfterBomb(bestHand, status)) {
                    return bestHand;
                }
            } else {
                return bestHand;
            }
        }
        return hand;
    }

    private Hand lordEasyWin(HandMapSummary[] summarys, GameStatusAdv status, Hand curHand) {
        HandMapSummary aiSummary = summarys[AIConstant.LORD];
        HandMapSummary upSummary = summarys[AIConstant.UP_FARMER];
        HandMapSummary downSummary = summarys[AIConstant.DOWN_FARMER];

        Map<HandType, List<Hand>> handMap = aiSummary.getHandMap();

        CardsSummary[] allCards = status.getPlayerCards();
        CardsSummary lordCards = allCards[AIConstant.LORD];

        List<Hand> bombs = handMap.get(HandType.BOMB);
        Hand hand = null;
        if (aiSummary.getRealHands() == 1) {
            hand = findHigherHandInMap(handMap, curHand);
            if (hand != null) {
                return hand;
            }
        }
        if (bombs != null && !bombs.isEmpty()) {
            boolean useBomb = false;
            if (findHigherHand(bombs.get(bombs.size() - 1), lordCards) == null) {
                useBomb = (aiSummary.getRealHands() <= 2 * bombs.size());
            } else {
                List<Hand> upfarmerBombs = upSummary.getHandMap().get(HandType.BOMB);
                List<Hand> downfarmerBombs = downSummary.getHandMap().get(HandType.BOMB);
                int upfarmerRealHands = upSummary.getRealHands();
                int downfarmerRealHands = downSummary.getRealHands();
                if ((upfarmerBombs != null && upfarmerRealHands > 2 * upfarmerBombs.size())
                        || (downfarmerBombs != null && downfarmerRealHands > 2 * downfarmerBombs.size())) {
                    useBomb = (aiSummary.getRealHands() <= 2 * bombs.size() - 1);
                }
            }
            if (useBomb) {
                for (Hand bomb : bombs) {
                    if (isHandHigherThan(bomb, curHand)) {
                        hand = bomb;
                    }
                }
            }
        }
        if (hand == null) {
            if (aiSummary.getUnHighestHandList().size() <= 1) {
                if (aiSummary.getRealHands() == 2) {
                    if (bombs != null) {
                        for (Hand bomb : bombs) {
                            if (isHandHigherThan(bomb, curHand) && findHigherHand(bomb, lordCards) == null) {
                                hand = bomb;
                            }
                        }
                    }
                }
                if (hand == null) {
                    if (status.getCurPlayer() == AIConstant.DOWN_FARMER) {
                        if (status.getOutHandPlayer() == AIConstant.UP_FARMER
                                && summarys[AIConstant.UP_FARMER].getRealHands() == 1) {
                            return null;
                        }
                    }
                    if (!AIUtils.isBomb(curHand)) {
                        List<Hand> allHigher = findAllHigherHandInMap(handMap, curHand);
                        if (!allHigher.isEmpty()) {
                            hand = allHigher.get(allHigher.size() - 1);
                        }
                    }
                    if (hand == null) {
                        if (bombs != null && !AIUtils.hasBomb(lordCards.getCards())) {
                            for (Hand bomb : bombs) {
                                if (isHandHigherThan(bomb, curHand)) {
                                    hand = bomb;
                                }
                            }
                        }
                    }
                }
            }
        }
        return hand;
    }

    private Hand lordEasyWin(HandMapSummary lordSummary, Hand curHand, List<Hand> otherBombs) {
        Map<HandType, List<Hand>> lordHandMap = lordSummary.getHandMap();
        List<Hand> lordBombs = lordHandMap.get(HandType.BOMB);
        List<Hand> unhighestHands = lordSummary.getUnHighestHandList();
        Hand hand = null;
        if (unhighestHands.size() <= lordBombs.size()) {
            Hand higher = findHigherHandInMap(lordHandMap, curHand);
            while (higher != null) {
                if (!unhighestHands.contains(higher)) { // 用大牌收回牌权
                    if (AIUtils.isBomb(higher)) {
                        if (higher.getType() == HandType.NUKE) {
                            return higher;
                        } else {
                            for (int i = 0; i < otherBombs.size(); i++) {
                                Hand hand2 = otherBombs.get(i);
                                if (hand2.getKey().ordinal() > higher.getKey().ordinal()) {
                                    i = 0;
                                    higher = findHigherHandInMap(lordHandMap, higher);
                                    if (higher == null) {
                                        break;
                                    }
                                }
                            }
                            if (higher != null) {
                                return higher;
                            }
                        }
                    } else {
                        return higher;
                    }
                } else {
                    higher = findHigherHandInMap(lordHandMap, higher);
                }

            }
        }
        return hand;
    }

    public Map<HandType, List<Hand>> splitCardKind1(byte[] cards, GameStatusAdv status, int lowestCon,
                                                    boolean searchKicker) {
        byte[] cardsCopy = Arrays.copyOf(cards, cards.length);
        Map<HandType, List<Hand>> handMap = new EnumMap<HandType, List<Hand>>(HandType.class);
        getBomb(cardsCopy, handMap);
        getTrioChain(cardsCopy, handMap);
        getTrio(cardsCopy, handMap);
        getPairChain(cardsCopy, handMap);
        getSoloChain(cardsCopy, handMap);
        getNormalHand(cardsCopy, handMap);
        if (searchKicker) {
            findKickersForTrio(handMap, status, lowestCon);
        }
        return handMap;
    }

    public Map<HandType, List<Hand>> splitCardKind2(byte[] cards, GameStatusAdv status, int lowestCon,
                                                    boolean searchKicker) {
        byte[] cardsCopy = Arrays.copyOf(cards, cards.length);
        Map<HandType, List<Hand>> handMap = new EnumMap<HandType, List<Hand>>(HandType.class);
        getSoloChain(cardsCopy, handMap);
        getBomb(cardsCopy, handMap);
        getTrioChain(cardsCopy, handMap);
        getPairChain(cardsCopy, handMap);
        getNormalHand(cardsCopy, handMap);
        if (searchKicker) {
            findKickersForTrio(handMap, status, lowestCon);
        }
        return handMap;
    }

    public Map<HandType, List<Hand>> splitCardKind3(byte[] cards, GameStatusAdv status, int lowestCon,
                                                    boolean searchKicker) {
        byte[] cardsCopy = Arrays.copyOf(cards, cards.length);
        Map<HandType, List<Hand>> handMap = new EnumMap<HandType, List<Hand>>(HandType.class);
        getBomb(cardsCopy, handMap);
        getTrioChain(cardsCopy, handMap);
        getTrio(cardsCopy, handMap);
        getSoloChain(cardsCopy, handMap);
        getPairChain(cardsCopy, handMap);
        getNormalHand(cardsCopy, handMap);
        if (searchKicker) {
            findKickersForTrio(handMap, status, lowestCon);
        }
        return handMap;
    }

    public Map<HandType, List<Hand>> splitCardKind4(byte[] cards, GameStatusAdv status, int lowestCon,
                                                    boolean searchKicker) {
        byte[] cardsCopy = Arrays.copyOf(cards, cards.length);
        Map<HandType, List<Hand>> handMap = new EnumMap<HandType, List<Hand>>(HandType.class);
        getBomb(cardsCopy, handMap);
        getTrio(cardsCopy, handMap);
        getSoloChain(cardsCopy, handMap);
        getNormalHand(cardsCopy, handMap);
        if (searchKicker) {
            findKickersForTrio(handMap, status, lowestCon);
        }
        return handMap;
    }

    public Map<HandType, List<Hand>> splitCardKind5(byte[] cards, GameStatusAdv status, int lowestCon,
                                                    boolean searchKicker) {
        byte[] cardsCopy = Arrays.copyOf(cards, cards.length);
        Map<HandType, List<Hand>> handMap = new EnumMap<HandType, List<Hand>>(HandType.class);
        getSoloChain(cardsCopy, handMap);
        getBomb(cardsCopy, handMap);
        getNormalHand(cardsCopy, handMap);
        if (searchKicker) {
            findKickersForTrio(handMap, status, lowestCon);
        }
        return handMap;
    }

    private HandMapSummary findBestHandGroup(GameStatusAdv status, int lowestControl, double soloConNum, int pos,
                                             byte[] cards) {
        Map<HandType, List<Hand>> handMap1 = splitCardKind1(cards, status, lowestControl, true);
        HandMapSummary summary1 = new HandMapSummary(handMap1);
        summary1.setSoloConNum(soloConNum);//
        calcHandMap(status, summary1, lowestControl, pos);
        Map<HandType, List<Hand>> handMap2 = splitCardKind2(cards, status, lowestControl, true);
        HandMapSummary summary2 = new HandMapSummary(handMap2);
        summary2.setSoloConNum(soloConNum);//
        calcHandMap(status, summary2, lowestControl, pos);
        Map<HandType, List<Hand>> handMap3 = splitCardKind3(cards, status, lowestControl, true);
        HandMapSummary summary3 = new HandMapSummary(handMap3);
        summary3.setSoloConNum(soloConNum);//
        calcHandMap(status, summary3, lowestControl, pos);
        HandMapSummary summary = null;
        if (compareSummary(summary1, summary2)) {
            summary = summary1;
        } else {
            summary = summary2;
        }
        if (compareSummary(summary3, summary)) {
            summary = summary3;
        }
        return summary;
    }

    /**
     *
     * @param cards
     * @param handMap
     */
    public List<Hand> getControler(byte[] cards, int lowestCon, GameStatusAdv status) {
        CardsSummary[] playerCards = status.getPlayerCards();
        int maxControler = 0;
        for (int i = PokerType.RJOKER.ordinal(); i >= 0; i--) {
            int curControl = (playerCards[0].get(i) + playerCards[1].get(i) + playerCards[2].get(i));
            if (curControl >= 0) {
                maxControler = i;
                break;
            }
        }
        List<Hand> solos = new ArrayList<Hand>();
        if (maxControler == PokerType.BJOKER.ordinal() || maxControler == PokerType.RJOKER.ordinal()) {
            if (cards[PokerType.TWO.ordinal()] == 0) {
                if (maxControler == PokerType.BJOKER.ordinal()) {
                    if (cards[maxControler] == 1) {
                        solos.add(new Hand(HandType.SOLO, PokerType.BJOKER.ordinal(), 1, null));
                        cards[maxControler] -= 1;
                    }
                } else if (maxControler == PokerType.RJOKER.ordinal()) {
                    if (cards[maxControler] == 1) {
                        solos.add(new Hand(HandType.SOLO, PokerType.RJOKER.ordinal(), 1, null));
                        cards[maxControler] -= 1;
                    }
                    if (cards[maxControler - 1] == 1) {
                        solos.add(0, new Hand(HandType.SOLO, PokerType.BJOKER.ordinal(), 1, null));
                        cards[maxControler - 1] -= 1;
                    }
                }
                return solos;
            } else {
                maxControler = PokerType.TWO.ordinal();
            }

        }
        if (cards[maxControler] == 3) {
            Hand hand1 = new Hand(HandType.SOLO, PokerType.values()[maxControler], 1, null);
            solos.add(0, hand1);
            cards[maxControler] -= 1;
        } else if (cards[maxControler] == 2) {
            Hand hand1 = new Hand(HandType.SOLO, PokerType.values()[maxControler], 1, null);
            Hand hand2 = new Hand(HandType.SOLO, PokerType.values()[maxControler], 1, null);
            solos.add(0, hand2);
            solos.add(0, hand1);
            cards[maxControler] -= 2;
        } else if (cards[maxControler] == 1) {
            Hand hand1 = new Hand(HandType.SOLO, PokerType.values()[maxControler], 1, null);
            solos.add(0, hand1);
            cards[maxControler] -= 1;
        }

        return solos;
    }

    private boolean isWinAfterBomb(Hand higher, GameStatusAdv status) {
        GameStatusAdv newStatusAdv = status.takeOutWithNewStatus(higher);
        if (newStatusAdv.isFinish()) {
            return true;
        } else {
            instance.playGameResult(newStatusAdv, 0);
            if (newStatusAdv.isFinish()) {
                return false;
            } else {
                if (newStatusAdv.isLose()) {
                    return true;
                }
                if (newStatusAdv.isWin()) {
                    return false;
                } else {
                    instance.playGameResult(newStatusAdv, 0);
                    if (newStatusAdv.isFinish()) {
                        return false;
                    } else {
                        if (newStatusAdv.isLose()) {
                            return true;
                        }
                        if (newStatusAdv.isWin()) {
                            return false;
                        } else {
                            if (BoobWin(newStatusAdv)) {
                                return true;
                            } else {
                                return false;
                            }
                        }
                    }
                }
            }
        }
    }

    private boolean BoobWin(GameStatusAdv newStatus) {
        instance.playGameResult(newStatus, 0);
        if (newStatus.isFinish()) {
            return true;
        } else {
            if (newStatus.isWin()) {
                return true;
            }
            if (newStatus.isLose()) {
                return false;
            } else {
                // 地主下家打牌
                instance.playGameResult(newStatus, 0);
                if (newStatus.isFinish()) {
                    return false;
                } else {
                    if (newStatus.isLose()) {
                        return true;
                    }
                    if (newStatus.isWin()) {
                        return false;
                    } else {
                        // 地主上家打牌
                        instance.playGameResult(newStatus, 0);
                        if (newStatus.isFinish()) {
                            return false;
                        } else {
                            if (newStatus.isLose()) {
                                return true;
                            }
                            if (newStatus.isWin()) {
                                return false;
                            } else {
                                return BoobWin(newStatus);
                            }

                        }
                    }

                }

            }
        }
    }
}
