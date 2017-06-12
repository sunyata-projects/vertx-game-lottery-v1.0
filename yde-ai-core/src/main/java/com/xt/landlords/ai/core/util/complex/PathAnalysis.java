package com.xt.landlords.ai.core.util.complex;

import java.util.*;
import java.util.Map.Entry;

public class PathAnalysis {

	public static CaipiaoAI ai = CaipiaoAI.getInstance();

	private GameStatusAdv statusAdv;

	public PathAnalysis(GameStatusAdv statusAdv) {
		ai = CaipiaoAI.getInstance();
		CardsSummary[] newPlayerCards = new CardsSummary[AIConstant.PLAYER_NUM];
		CardsSummary[] allcards = statusAdv.getPlayerCards();
		for (int i = 0; i < AIConstant.PLAYER_NUM; i++) {
			newPlayerCards[i] = new CardsSummary(allcards[i]);
		}
		Hand newOutHand = statusAdv.getOutHand();
		int newOutHandPlayer = statusAdv.getOutHandPlayer();
		int newCurPlayer = statusAdv.getCurPlayer();
		int newBombCount = statusAdv.getBombCount();
		this.statusAdv = new GameStatusAdv(newPlayerCards, newOutHand, newOutHandPlayer, newCurPlayer, newBombCount);
	}

	/**
	 * �򷨷���
	 */
	public static Map<Hand, Integer> playingstyle(GameStatusAdv status) {
		byte[] cards = status.getPlayerCards()[0].getCards();
		int lowestCon = calLowestControl(status);
		List<Map<HandType, List<Hand>>> kinds = new ArrayList<Map<HandType, List<Hand>>>();
		kinds.add(ai.splitCardKind1(cards, status, lowestCon, true));
		kinds.add(ai.splitCardKind2(cards, status, lowestCon, true));
		kinds.add(ai.splitCardKind3(cards, status, lowestCon, true));
		kinds.add(ai.splitCardKind4(cards, status, lowestCon, true));
		kinds.add(ai.splitCardKind5(cards, status, lowestCon, true));
		Map<Hand, Integer> HandEva = new HashMap<Hand, Integer>();
		for (Map<HandType, List<Hand>> map : kinds) {
			List<Hand> hands = new ArrayList<Hand>(32);
			if (map.get(HandType.TRIO_CHAIN_SOLO) != null) {
				hands.addAll(map.get(HandType.TRIO_CHAIN_SOLO));
			}
			if (map.get(HandType.TRIO_CHAIN_PAIR) != null) {
				hands.addAll(map.get(HandType.TRIO_CHAIN_PAIR));
			}
			if (map.get(HandType.PAIR_CHAIN) != null) {
				hands.addAll(map.get(HandType.PAIR_CHAIN));
			}
			if (map.get(HandType.SOLO_CHAIN) != null) {
				hands.addAll(map.get(HandType.SOLO_CHAIN));
			}
			if (map.get(HandType.PAIR) != null) {
				hands.addAll(map.get(HandType.PAIR));
			}
			if (map.get(HandType.SOLO) != null) {
				hands.addAll(map.get(HandType.SOLO));
			}
			if (map.get(HandType.TRIO_PAIR) != null) {
				hands.addAll(map.get(HandType.TRIO_PAIR));
			}
			if (map.get(HandType.TRIO_SOLO) != null) {
				hands.addAll(map.get(HandType.TRIO_SOLO));
			}
			if (hands.size() > 0) {
				for (Hand hand : hands) {
					if (HandEva.containsKey(hand)) {
						HandEva.put(hand, HandEva.get(hand) + 1);
					} else {
						HandEva.put(hand, 1);
					}
				}
			}
		}
		return HandEva;
	}

	private static int calLowestControl(GameStatusAdv status) {
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

	public int evaluation(String pathString) {
		// String Cardstr =
		// "3344567888TJQKKAA222,34566778TJJJKK2XD,3455679999TTQQQAA";
		// String[] cards = Cardstr.split(",");
		// String outCards = "88";
		// int outHandPlayer = 0;
		// int curPlayer = 0;
		// GameStatus status = GameUtils.genStatus(cards, outCards,
		// outHandPlayer, curPlayer);
		// GameStatusAdv statusAdv = new GameStatusAdv(status);
		// String pathString =
		// "88,AA,T,pass,pass,pass,pass,KK,33,pass,Q,pass,J,2,pass,pass,2,45678,2,4,";
		String[] outhandStr = pathString.split(",");
		int covertCount = 0;
		int len = outhandStr.length;
		int index = 0;
		for (String string : outhandStr) {
			index++;
			Hand outhand;
			if (string.equals("pass")) {
				outhand = null;
			} else {
				byte[] outHand = new byte[AIConstant.CARD_TYPE];
				for (int i = 0; i < string.length(); i++) {
					PokerType type = PokerType.fromName(string.charAt(i));
					if (type != null) {
						outHand[type.ordinal()]++;
					}
				}
				outhand = AIUtils.cardsToHand(outHand);
			}
			Map<Hand, Integer> analysisHands = playingstyle(statusAdv);
			// List<Hand> moveHands;

			if (statusAdv.getCurPlayer() == statusAdv.getOutHandPlayer()) {
				if (!analysisHands.containsKey(outhand)) {// ������в������ĸô򷨣����ʾ�ô�Ϊ���δ򷨣�
					if (index == 1) {// ��һ�����Ƿ������򷨵Ļ������һ��
						covertCount += 4;
					} else {
						covertCount += 2;
					}
				}
			} else {// ����
				Hand otherOutHand = statusAdv.getOutHand();
				if (outhand == null) {// pass,�Ƿ���û��pass��
					List<Hand> moveHands = AISearch.GetMoves(otherOutHand, statusAdv.getPlayerCards()[0].getCards());
					int moveCount = moveHands.size();
					if (moveCount > 0 && moveHands.get(0) != null) {// ����
						if (moveHands.get(0).getType() == HandType.BOMB) {
							switch (moveCount) {
							case 1:
								covertCount++;
								break;
							default:
								covertCount += 2;
								break;
							}
						}
						// ����������
						if (otherOutHand.getType() != HandType.TRIO_PAIR && otherOutHand.getType() != HandType.TRIO_SOLO
								&& otherOutHand.getType() != HandType.TRIO) {
							Set<Entry<Hand, Integer>> entrtSet = analysisHands.entrySet();
							int canGoCount = 0;
							for (Entry<Hand, Integer> entry : entrtSet) {
								if (entry.getKey().getType() == otherOutHand.getType()
										&& entry.getKey().getKey().ordinal() > otherOutHand.getKey().ordinal()) {
									canGoCount++;
								}
							}
							if (canGoCount > 2) {
								covertCount += 2;
							} else {
								covertCount++;
							}
						}

					}
				} else {// ûpass�����Ƿ��������Ĵ�
						// ��������������
					if (outhand.getType() != HandType.TRIO_PAIR && outhand.getType() != HandType.TRIO_SOLO
							&& outhand.getType() != HandType.TRIO) {
						if (!analysisHands.containsKey(outhand)) {// �������������
							covertCount += 2;
						}
					}
				}
			}
			statusAdv.takeOut(outhand);
			if (!statusAdv.isFinish()) {
				ai.playGameResult(statusAdv, 0);
				ai.playGameResult(statusAdv, 0);
			}
		}
		// System.out.println(covertCount);

		return covertCount;
	}

	public int evaluationForOneStep(Hand outhand) {
		int covertCount = 0;
		Map<Hand, Integer> analysisHands = playingstyle(statusAdv);
		// List<Hand> moveHands;
		if (statusAdv.getCurPlayer() == statusAdv.getOutHandPlayer()) {
			if (!analysisHands.containsKey(outhand)) {// ������в������ĸô򷨣����ʾ�ô�Ϊ���δ򷨣�
				covertCount += 2;
			}
		} else {// ����
			Hand otherOutHand = statusAdv.getOutHand();
			if (outhand == null) {// pass,�Ƿ���û��pass��
				List<Hand> moveHands = AISearch.GetMoves(otherOutHand, statusAdv.getPlayerCards()[0].getCards());
				int moveCount = moveHands.size();
				if (moveCount > 0 && moveHands.get(0) != null) {// ����
					if (moveHands.get(0).getType() == HandType.BOMB) {
						switch (moveCount) {
						case 1:
							covertCount++;
							break;
						default:
							covertCount += 2;
							break;
						}
					}
					// ����������
					if (otherOutHand.getType() != HandType.TRIO_PAIR && otherOutHand.getType() != HandType.TRIO_SOLO
							&& otherOutHand.getType() != HandType.TRIO) {
						Set<Entry<Hand, Integer>> entrtSet = analysisHands.entrySet();
						int canGoCount = 0;
						for (Entry<Hand, Integer> entry : entrtSet) {
							if (entry.getKey().getType() == otherOutHand.getType()
									&& entry.getKey().getKey().ordinal() > otherOutHand.getKey().ordinal()) {
								canGoCount++;
							}
						}
						if (canGoCount > 2) {
							covertCount += 2;
						} else {
							covertCount++;
						}
					}

				}
			} else {// ûpass�����Ƿ��������Ĵ�
					// ��������������
				if (outhand.getType() != HandType.TRIO_PAIR && outhand.getType() != HandType.TRIO_SOLO
						&& outhand.getType() != HandType.TRIO) {
					if (!analysisHands.containsKey(outhand)) {// �������������
						covertCount += 2;
					}
				}
			}
		}
		return covertCount;
	}

	public static void main(String[] args) {
		// TODO �Զ����ɵķ������

	}

}
