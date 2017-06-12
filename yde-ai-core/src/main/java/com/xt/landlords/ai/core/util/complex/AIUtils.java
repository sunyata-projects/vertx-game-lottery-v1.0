package com.xt.landlords.ai.core.util.complex;

import java.util.*;
import java.util.Map.Entry;

public class AIUtils {

	public static boolean isBomb(Hand hand) {
		return hand.getType() == HandType.BOMB || hand.getType() == HandType.NUKE;
	}

	public static boolean hasNuke(byte[] cards) {
		return cards[PokerType.BJOKER.ordinal()] == 1 && cards[PokerType.RJOKER.ordinal()] == 1;
	}

	public static boolean hasNuke(CardsSummary cards) {
		return cards.get(PokerType.BJOKER.ordinal()) == 1 && cards.get(PokerType.RJOKER.ordinal()) == 1;
	}

	public static boolean hasBomb(byte[] cards) {
		if (hasNuke(cards)) {
			return true;
		} else {
			for (int i = 0; i < AIConstant.CARD_TYPE; i++) {
				if (cards[i] == 4) {
					return true;
				}
			}
		}
		return false;
	}

	public static int getBombNum(byte[] cards) {
		int num = 0;
		for (int i = 0; i < AIConstant.CARD_TYPE; i++) {
			if (cards[i] == 4) {
				num++;
			}
		}
		if (hasNuke(cards)) {
			num++;
		}
		return num;
	}

	public static int getBombNum(CardsSummary cards) {
		int num = 0;
		int maxKey = Math.min(cards.getHighest().ordinal(), PokerType.TWO.ordinal());
		for (int i = cards.getLowest().ordinal(); i <= maxKey; i++) {
			if (cards.get(i) == 4) {
				num++;
			}
		}
		if (hasNuke(cards)) {
			num++;
		}
		return num;
	}

	public static List<Hand> getAllBomb(CardsSummary cards) {
		List<Hand> bombs = new ArrayList<Hand>();
		int maxKey = Math.min(cards.getHighest().ordinal(), PokerType.TWO.ordinal());
		for (int i = cards.getLowest().ordinal(); i <= maxKey; i++) {
			if (cards.get(i) == 4) {
				bombs.add(new Hand(HandType.BOMB, i, 1, null));
			}
		}
		if (hasNuke(cards)) {
			bombs.add(Hand.NUKE_HAND);
		}
		return bombs;
	}

	public static PokerType getHighestBomb(CardsSummary cards) {
		if (hasNuke(cards)) {
			return PokerType.BJOKER;
		}
		int maxKey = Math.min(cards.getHighest().ordinal(), PokerType.TWO.ordinal());
		int minKey = cards.getLowest().ordinal();
		for (int i = maxKey; i >= minKey; i--) {
			if (cards.get(i) == 4) {
				return PokerType.values()[i];
			}
		}
		return null;
	}

	public static byte[] addCards(byte[] c0, byte[] c1) {
		byte[] ret = new byte[AIConstant.CARD_TYPE];
		for (int i = 0; i < AIConstant.CARD_TYPE; i++) {
			ret[i] = (byte) (c0[i] + c1[i]);
		}
		return ret;
	}

	public static byte[] subCards(byte[] c0, byte[] c1) {
		byte[] ret = new byte[AIConstant.CARD_TYPE];
		for (int i = 0; i < AIConstant.CARD_TYPE; i++) {
			ret[i] = (byte) (c0[i] - c1[i]);
		}
		return ret;
	}

	public static int countCardsNum(byte[] cards) {
		int sum = cards[0];
		for (int i = 1; i < cards.length; i++) {
			sum += cards[i];
		}
		return sum;
	}

	public static int getHighestCard(byte[] cards) {
		for (int i = cards.length - 1; i >= 0; i--) {
			if (cards[i] > 0) {
				return i;
			}
		}
		return cards.length - 1;
	}

	public static int getLowestCard(byte[] cards) {
		for (int i = 0; i < cards.length; i++) {
			if (cards[i] > 0) {
				return i;
			}
		}
		return 0;
	}

	public static byte[] handToCards(Hand hand) {
		byte[] cards = new byte[AIConstant.CARD_TYPE];
		if (hand != null) {
			HandType type = hand.getType();
			if (type.isChain()) {
				for (int i = 0; i < hand.getLen(); i++) {
					cards[hand.getKey().ordinal() + i] = (byte) type.getWidth();
				}
			} else {
				if (type == HandType.NUKE) {
					cards[PokerType.BJOKER.ordinal()] = 1;
					cards[PokerType.RJOKER.ordinal()] = 1;
				} else {
					cards[hand.getKey().ordinal()] = (byte) type.getWidth();
				}
			}
			if (type.getKickerWidth() > 0) {
				for (PokerType poker : hand.getKickers()) {
					cards[poker.ordinal()] = (byte) type.getKickerWidth();
				}
			}
		}
		return cards;
	}

	public static boolean containsHand(byte[] cards, Hand hand) {
		byte[] handCards = handToCards(hand);
		for (int i = 0; i < AIConstant.CARD_TYPE; i++) {
			if (cards[i] < handCards[i]) {
				return false;
			}
		}
		return true;
	}

	public static Hand cardsToHand(byte[] cards) {
		Hand hand = null;
		int cardsNum = countCardsNum(cards);
		if (hasNuke(cards)) {
			if (cardsNum == 2) {
				hand = Hand.NUKE_HAND;
			}
		} else {
			Map<Byte, List<PokerType>> sortedPoker = new HashMap<Byte, List<PokerType>>();
			for (int i = 0; i < cards.length; i++) {
				if (cards[i] != 0) {
					List<PokerType> pokers = sortedPoker.get(cards[i]);
					if (pokers == null) {
						pokers = new ArrayList<PokerType>();
						sortedPoker.put(cards[i], pokers);
					}
					pokers.add(PokerType.values()[i]);
				}
			}
			if (sortedPoker.size() == 1) { // no kickers
				Entry<Byte, List<PokerType>> entry = sortedPoker.entrySet().iterator().next();
				byte width = entry.getKey();
				List<PokerType> pokers = entry.getValue();
				PokerType key = pokers.get(0);
				if (pokers.size() == 1) {
					switch (width) {
					case 1:
						hand = new Hand(HandType.SOLO, key, 1, null);
						break;
					case 2:
						hand = new Hand(HandType.PAIR, key, 1, null);
						break;
					case 3:
						hand = new Hand(HandType.TRIO, key, 1, null);
						break;
					case 4:
						hand = new Hand(HandType.BOMB, key, 1, null);
						break;
					default:
						break;
					}
				} else {
					PokerType end = pokers.get(pokers.size() - 1);
					int len = end.ordinal() - key.ordinal() + 1;
					if (end.ordinal() <= PokerType.ACE.ordinal() && len == pokers.size()) {
						switch (width) {
						case 1:
							if (len >= 5) {
								hand = new Hand(HandType.SOLO_CHAIN, key, len, null);
							}
							break;
						case 2:
							if (len >= 3) {
								hand = new Hand(HandType.PAIR_CHAIN, key, len, null);
							}
							break;
						case 3:
							if (len >= 2) {
								hand = new Hand(HandType.TRIO_CHAIN, key, len, null);
							}
							break;
						default:
							break;
						}
					}
				}
			} else if (sortedPoker.size() == 2) { // kicker
				boolean soloKicker = false;
				List<PokerType> kickerList = sortedPoker.get((byte) 1);
				if (kickerList == null) {
					kickerList = sortedPoker.get((byte) 2);
				} else {
					soloKicker = true;
				}
				if (kickerList != null) {
					Set<PokerType> kickers = EnumSet.copyOf(kickerList);
					List<PokerType> bombs = sortedPoker.get((byte) 4);
					if (bombs != null) {
						if (bombs.size() == 1 && kickers.size() == 2) {
							if (soloKicker) {
								hand = new Hand(HandType.FOUR_DUAL_SOLO, bombs.get(0), 1, kickers);
							} else {
								hand = new Hand(HandType.FOUR_DUAL_PAIR, bombs.get(0), 1, kickers);
							}
						}
					} else {
						List<PokerType> trios = sortedPoker.get((byte) 3);
						if (trios != null) {
							PokerType key = trios.get(0);
							if (trios.size() == 1) {
								if (kickers.size() == 1) {
									if (soloKicker) {
										hand = new Hand(HandType.TRIO_SOLO, key, 1, kickers);
									} else {
										hand = new Hand(HandType.TRIO_PAIR, key, 1, kickers);
									}
								}
							} else {
								PokerType end = trios.get(trios.size() - 1);
								int len = end.ordinal() - key.ordinal() + 1;
								if (end.ordinal() <= PokerType.ACE.ordinal() && len == trios.size()
										&& len == kickers.size()) {
									if (soloKicker) {
										hand = new Hand(HandType.TRIO_CHAIN_SOLO, key, len, kickers);
									} else {
										hand = new Hand(HandType.TRIO_CHAIN_PAIR, key, len, kickers);
									}
								}
							}
						}
					}
				}
			}
		}
		return hand;
	}

	public static Hand cardsToHand(CardsSummary cards) {
		Hand hand = null;
		if (cards.get(PokerType.BJOKER) == 1 && cards.get(PokerType.RJOKER) == 1) {
			if (cards.getNum() == 2) {
				hand = new Hand(HandType.NUKE, PokerType.BJOKER, 1, null);
			}
		} else {
			Map<Byte, List<PokerType>> sortedPoker = new HashMap<Byte, List<PokerType>>();
			for (int i = cards.getLowest().ordinal(); i <= cards.getHighest().ordinal(); i++) {
				if (cards.get(i) != 0) {
					List<PokerType> pokers = sortedPoker.get(cards.get(i));
					if (pokers == null) {
						pokers = new ArrayList<PokerType>(12);
						sortedPoker.put(cards.get(i), pokers);
					}
					pokers.add(PokerType.values()[i]);
				}
			}
			if (sortedPoker.size() == 1) { // no kickers
				Entry<Byte, List<PokerType>> entry = sortedPoker.entrySet().iterator().next();
				byte width = entry.getKey();
				List<PokerType> pokers = entry.getValue();
				PokerType key = pokers.get(0);
				if (pokers.size() == 1) {
					switch (width) {
					case 1:
						hand = new Hand(HandType.SOLO, key, 1, null);
						break;
					case 2:
						hand = new Hand(HandType.PAIR, key, 1, null);
						break;
					case 3:
						hand = new Hand(HandType.TRIO, key, 1, null);
						break;
					case 4:
						hand = new Hand(HandType.BOMB, key, 1, null);
						break;
					default:
						break;
					}
				} else {
					PokerType end = pokers.get(pokers.size() - 1);
					int len = end.ordinal() - key.ordinal() + 1;
					if (end.ordinal() <= PokerType.ACE.ordinal() && len == pokers.size()) {
						switch (width) {
						case 1:
							if (len >= 5) {
								hand = new Hand(HandType.SOLO_CHAIN, key, len, null);
							}
							break;
						case 2:
							if (len >= 3) {
								hand = new Hand(HandType.PAIR_CHAIN, key, len, null);
							}
							break;
						case 3:
							if (len >= 2) {
								hand = new Hand(HandType.TRIO_CHAIN, key, len, null);
							}
							break;
						default:
							break;
						}
					}
				}
			} else if (sortedPoker.size() == 2) { // kicker
				boolean soloKicker = false;
				List<PokerType> kickerList = sortedPoker.get((byte) 1);
				if (kickerList == null) {
					kickerList = sortedPoker.get((byte) 2);
				} else {
					soloKicker = true;
				}
				if (kickerList != null) {
					Set<PokerType> kickers = EnumSet.copyOf(kickerList);
					List<PokerType> bombs = sortedPoker.get((byte) 4);
					if (bombs != null) {
						if (bombs.size() == 1 && kickers.size() == 2) {
							if (soloKicker) {
								hand = new Hand(HandType.FOUR_DUAL_SOLO, bombs.get(0), 1, kickers);
							} else {
								hand = new Hand(HandType.FOUR_DUAL_PAIR, bombs.get(0), 1, kickers);
							}
						}
					} else {
						List<PokerType> trios = sortedPoker.get((byte) 3);
						if (trios != null) {
							PokerType key = trios.get(0);
							if (trios.size() == 1) {
								if (kickers.size() == 1) {
									if (soloKicker) {
										hand = new Hand(HandType.TRIO_SOLO, key, 1, kickers);
									} else {
										hand = new Hand(HandType.TRIO_PAIR, key, 1, kickers);
									}
								}
							} else {
								PokerType end = trios.get(trios.size() - 1);
								int len = end.ordinal() - key.ordinal() + 1;
								if (end.ordinal() <= PokerType.ACE.ordinal() && len == trios.size()
										&& len == kickers.size()) {
									if (soloKicker) {
										hand = new Hand(HandType.TRIO_CHAIN_SOLO, key, len, kickers);
									} else {
										hand = new Hand(HandType.TRIO_CHAIN_PAIR, key, len, kickers);
									}
								}
							}
						}
					}
				}
			}
		}
		return hand;
	}

	/**
	 * change aiCards and oppCards!
	 * 
	 * @param aiCards
	 * @param oppCards
	 * @param lowestControl
	 * @return
	 */
	public static double calControl(byte[] cards, byte[] oppCards, int lowestControl) {
		byte[] aiCards = Arrays.copyOf(cards, cards.length);
		int aiConNum = 0;
		int lordConNum = 0;
		for (int i = lowestControl; i < AIConstant.CARD_TYPE; i++) {
			aiConNum += aiCards[i];
			lordConNum += oppCards[i];
		}
		int aiWin = 0;
		int lordWin = 0;
		if (aiConNum == 0) {
			lordWin = lordConNum;
		}
		while (aiConNum > 0 && lordConNum > 0) {
			byte[] curCon = aiCards;
			byte[] lastCon = null;
			for (int i = lowestControl; i < AIConstant.CARD_TYPE; i++) {
				if (curCon[i] > 0) {
					lastCon = curCon;
					curCon[i]--;
					if (curCon == aiCards) {
						aiConNum--;
						curCon = oppCards;
					} else {
						lordConNum--;
						curCon = aiCards;
					}
				}
			}
			if (lastCon == aiCards) {
				aiWin++;
			} else if (lastCon == oppCards) {
				lordWin++;
			}
		}
		double conNum = aiConNum + aiWin - lordWin * 0.1;
		return conNum > 0 ? conNum : 0;
	}

	// private static void genAllTrioKicker(byte[] cards, PokerType trioKey,
	// List<Hand> hands) {
	//
	// Map<HandType, List<Hand>> handMap1 = ai.splitCardKind1(cards, null, 0,
	// false);
	// Set<PokerType> soloKickerSet1 = findSoloKickers(trioKey.ordinal(), 1,
	// 1, handMap1);
	// Set<PokerType> pairKickerSet1 = findPairKickers(trioKey.ordinal(), 1,
	// 1, handMap1);
	//
	// Map<HandType, List<Hand>> handMap2 = ai.splitCardKind2(cards, null, 0,
	// false);
	// Set<PokerType> soloKickerSet2 = findSoloKickers(trioKey.ordinal(), 1,
	// 1, handMap2);
	// Set<PokerType> pairKickerSet2 = findPairKickers(trioKey.ordinal(), 1,
	// 1, handMap2);
	//
	// Map<HandType, List<Hand>> handMap3 = ai.splitCardKind3(cards, null, 0,
	// false);
	// Set<PokerType> soloKickerSet3 = findSoloKickers(trioKey.ordinal(), 1,
	// 1, handMap3);
	// Set<PokerType> pairKickerSet3 = findPairKickers(trioKey.ordinal(), 1,
	// 1, handMap3);
	//
	// soloKickerSet1.addAll(soloKickerSet2);
	// soloKickerSet1.addAll(soloKickerSet3);
	//
	// pairKickerSet1.addAll(pairKickerSet2);
	// pairKickerSet1.addAll(pairKickerSet3);
	//
	// for (PokerType kicker : soloKickerSet1) {
	// hands.add(new Hand(HandType.TRIO_SOLO, trioKey, 1, EnumSet
	// .of(kicker)));
	// }
	//
	// for (PokerType kicker : pairKickerSet1) {
	// hands.add(new Hand(HandType.TRIO_PAIR, trioKey, 1, EnumSet
	// .of(kicker)));
	// }
	//
	// // int maxKicker = getMaxKicker(cards);
	// // for (int i = 0; i <= maxKicker; i++) {
	// // if (i != trioKey.ordinal()) {
	// // if (cards[i] >= 1) {
	// // hands.add(new Hand(HandType.TRIO_SOLO, trioKey, 1, EnumSet
	// // .of(PokerType.values()[i])));
	// // }
	// // if (cards[i] >= 2) {
	// // hands.add(new Hand(HandType.TRIO_PAIR, trioKey, 1, EnumSet
	// // .of(PokerType.values()[i])));
	// // }
	// // }
	// // }
	// }

	// private static void genAllBombKicker(byte[] cards, PokerType bombKey,
	// List<Hand> hands) {
	// int maxKicker = getMaxKicker(cards);
	// for (int i = 0; i <= maxKicker; i++) {
	// if (i != bombKey.ordinal() && cards[i] >= 1) {
	// for (int j = i + 1; j <= maxKicker; j++) {
	// if (j != bombKey.ordinal()) {
	// if (cards[j] >= 1) {
	// hands.add(new Hand(HandType.FOUR_DUAL_SOLO,
	// bombKey, 1, EnumSet.of(
	// PokerType.values()[i],
	// PokerType.values()[j])));
	// }
	// if (cards[i] >= 2 && cards[j] >= 2) {
	// hands.add(new Hand(HandType.FOUR_DUAL_PAIR,
	// bombKey, 1, EnumSet.of(
	// PokerType.values()[i],
	// PokerType.values()[j])));
	// }
	// }
	// }
	// }
	// }
	// }

	// private static void genTrioChainKicker(byte[] cards, Hand trioChain,
	// List<Hand> hands) {
	// int maxKicker = getMaxKicker(cards);
	// List<PokerType> soloKicker = new ArrayList<PokerType>(15);
	// List<PokerType> pairKicker = new ArrayList<PokerType>(15);
	// for (int i = 0; i <= maxKicker; i++) {
	// if (i < trioChain.getKey().ordinal()
	// || i >= trioChain.getKey().ordinal() + trioChain.getLen()) {
	// if (cards[i] >= 1) {
	// soloKicker.add(PokerType.values()[i]);
	// }
	// if (cards[i] >= 2) {
	// pairKicker.add(PokerType.values()[i]);
	// }
	// }
	// }
	// if (soloKicker.size() >= trioChain.getLen()) {
	// Combination comb = new Combination(trioChain.getLen(),
	// soloKicker.size());
	// while (comb.next()) {
	// Set<PokerType> kickers = EnumSet.noneOf(PokerType.class);
	// for (int i = 0; i < trioChain.getLen(); i++) {
	// kickers.add(soloKicker.get(comb.get(i)));
	// }
	// hands.add(new Hand(HandType.TRIO_CHAIN_SOLO,
	// trioChain.getKey(), trioChain.getLen(), kickers));
	// }
	// }
	// if (pairKicker.size() >= trioChain.getLen()) {
	// Combination comb = new Combination(trioChain.getLen(),
	// pairKicker.size());
	// while (comb.next()) {
	// Set<PokerType> kickers = EnumSet.noneOf(PokerType.class);
	// for (int i = 0; i < trioChain.getLen(); i++) {
	// kickers.add(pairKicker.get(comb.get(i)));
	// }
	// hands.add(new Hand(HandType.TRIO_CHAIN_PAIR,
	// trioChain.getKey(), trioChain.getLen(), kickers));
	// }
	// }
	// }

	public static String cardsToString(byte[] cards) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < cards.length; i++) {
			if (cards[i] > 0) {
				for (int j = 0; j < cards[i]; j++) {
					sb.append(PokerType.values()[i].getName());
				}
			}
		}
		if (sb.length() == 0) {
			sb.append("pass");
		}
		return sb.toString();
	}

	public static String handToString(Hand hand) {
		byte[] cards = handToCards(hand);
		return cardsToString(cards);
	}

	public static String handMapToString(Map<HandType, List<Hand>> handMap) {
		StringBuffer sb = new StringBuffer();
		Collection<List<Hand>> handsList = handMap.values();
		for (List<Hand> list : handsList) {
			for (Hand hand : list) {
				sb.append(handToString(hand));
				sb.append('\n');
			}
		}
		return sb.toString();
	}

}
