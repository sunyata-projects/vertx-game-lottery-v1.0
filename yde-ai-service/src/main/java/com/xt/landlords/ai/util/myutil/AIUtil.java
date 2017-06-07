package com.xt.landlords.ai.util.myutil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class AIUtil {
	final static Logger log = LoggerFactory.getLogger(AIUtil.class);

	public static int cardValue(int card) {
		if (card < 0 || card > 53) {
		}

		int grade = 0;
		// 2个王必须放在前边判断
		if (card == 52) {
			grade = 16;
		} else if (card == 53) {
			grade = 17;
		} else {
			grade = (card % 13) + 3;
		}

		return grade;
	}

	public static boolean isKing(int card) {
		boolean result = false;
		if (card == 52 || card == 53) {
			result = true;
		}

		return result;
	}

	public static boolean is2(int card) {
		boolean result = false;
		if (card == 12 || card == 25 || card == 38 || card == 51) {
			result = true;
		}

		return result;
	}

	// 对list排序
	public static void cardsOrder(List<Integer> list) {
		Collections.sort(list, new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				int a1 = o1;// 花色
				int a2 = o2;
				int b1 = cardValue(o1);// 数值
				int b2 = cardValue(o2);
				int flag = 0;
				flag = b2 - b1;
				if (flag == 0)
					return a2 - a1;
				else {
					return flag;
				}
			}
		});
	}

	// 得到最大相同数
	private static void getMax(List<Integer> cardIndex[], List<Integer> list) {
		int count[] = new int[14];// 1-13各算一种,王算第14种
		for (int i = 0; i < 14; i++)
			count[i] = 0;
		for (int i = 0, len = list.size(); i < len; i++) {
			if (isKing(list.get(i)))
				count[13]++;
			else
				count[cardValue(list.get(i)) - 3]++;
		}
		for (int i = 0; i < 14; i++) {
			switch (count[i]) {
			case 1:
				cardIndex[0].add(i + 3);
				break;
			case 2:
				cardIndex[1].add(i + 3);
				break;
			case 3:
				cardIndex[2].add(i + 3);
				break;
			case 4:
				cardIndex[3].add(i + 3);
				break;
			}
		}
	}

	// 判断牌型
	@SuppressWarnings("unchecked")
	public static CardType jugdeType(List<Integer> list) {
		cardsOrder(list);
		// 因为之前排序过所以比较好判断
		int len = list.size();
		// 单牌,对子，3不带，4个一样炸弹
		if (len < 5) { // 如果第一个和最后个相同，说明全部相同
			if (list.size() > 0 && cardValue(list.get(0)) == cardValue(list.get(len - 1))) {
				switch (len) {
				case 1:
					return CardType.c1;
				case 2:
					return CardType.c2;
				case 3:
					return CardType.c3;
				case 4:
					return CardType.c4;
				}
			}
			// 双王,化为对子返回
			if (len == 2 && isKing(list.get(0)) && isKing(list.get(1)))
				return CardType.c4;
			// 当第一个和最后个不同时,3带1
			if (len == 4 && ((cardValue(list.get(0)) == cardValue(list.get(len - 2)))
					|| cardValue(list.get(1)) == cardValue(list.get(len - 1))))
				return CardType.c31;
			else
				return CardType.c0;
		}
		// 当5张以上时，连字，3带2，飞机，2顺，4带2等等
		if (len > 4) {// 现在按相同数字最大出现次数
			List<Integer>[] cardIndex = new ArrayList[4];
			for (int i = 0; i < 4; i++)
				cardIndex[i] = new ArrayList<Integer>();
			// 求出各种数字出现频率
			getMax(cardIndex, list); // a[0,1,2,3]分别表示重复1,2,3,4次的牌
			// 3带2,必含重复3次的牌
			if (cardIndex[2].size() == 1 && cardIndex[1].size() == 1 && len == 5)
				return CardType.c32;
			// 4带2(单,双)
			if (cardIndex[3].size() == 1 && len == 6)
				return CardType.c411;
			if (cardIndex[3].size() == 1 && cardIndex[1].size() == 2 && len == 8)
				return CardType.c422;
			// 单连,保证不存在王
			if (!isKing(list.get(0)) && !is2(list.get(0)) && (cardIndex[0].size() == len)
					&& (cardValue(list.get(0)) - cardValue(list.get(len - 1)) == len - 1))
				return CardType.c123;
			// 连队
			if (!is2(list.get(0)) && cardIndex[1].size() == len / 2 && len % 2 == 0 && len / 2 >= 3
					&& (cardValue(list.get(0)) - cardValue(list.get(len - 1)) == (len / 2 - 1)))
				return CardType.c1122;
			// 飞机
			if (cardIndex[2].size() == len / 3 && (len % 3 == 0)
					&& (cardValue(list.get(0)) - cardValue(list.get(len - 1)) == (len / 3 - 1)))
				return CardType.c111222;
			// 飞机带n单,n/2对
			if (cardIndex[2].size() == len / 4
					&& ((Integer) (cardIndex[2].get(len / 4 - 1)) - (Integer) (cardIndex[2].get(0)) == len / 4 - 1)
					&& (cardIndex[2].size() == (cardIndex[0].size() + cardIndex[1].size() * 2)))
				return CardType.c11122234;

			// 飞机带n双
			if (cardIndex[2].size() == len / 5 && cardIndex[2].size() == len / 5
					&& ((Integer) (cardIndex[2].get(len / 5 - 1)) - (Integer) (cardIndex[2].get(0)) == len / 5 - 1))
				return CardType.c1112223344;

		}
		return CardType.c0;
	}

	// 检查牌的是否能出
	public static int checkCards(List<Integer> playCards, List<Integer> lastCards) {
		log.info("playCards={}, lastCards={}", playCards, lastCards);
		
		CardType playCardsType = jugdeType(playCards);
		log.info("playCardsType = {}", playCardsType);
		if(playCardsType == CardType.c0){
			return 0;
		}
		
		CardType lastCardsType = jugdeType(lastCards);
		log.info("lastCardsType = {}", lastCardsType);
		if(lastCardsType == CardType.c0){
			return 0;
		}
		
		// 如果张数不同直接过滤
		if (playCardsType != CardType.c4 && playCards.size() != lastCards.size())
			return 0;

		// 比较我的出牌类型
		if (playCardsType != CardType.c4 && playCardsType != lastCardsType)
			return 0;

		// 比较出的牌是否要大
		// 我是炸弹
		if (playCardsType == CardType.c4) {
			if (playCards.size() == 2)
				return 1;
			if (lastCardsType != CardType.c4) {
				return 1;
			}
		}

		// 单牌,对子,3带,4炸弹
		if (playCardsType == CardType.c1 || playCardsType == CardType.c2 || playCardsType == CardType.c3
				|| playCardsType == CardType.c4) {
			if (cardValue(getOrder(playCards).get(0)) > cardValue(getOrder(lastCards).get(0))) {
				return 1;
			} else {
				return 0;
			}
		}
		// 顺子,连队，飞机裸
		if (playCardsType == CardType.c123 || playCardsType == CardType.c1122 || playCardsType == CardType.c111222) {
			if (cardValue(getOrder(playCards).get(0)) > cardValue(getOrder(lastCards).get(0)))
				return 1;
			else
				return 0;
		}
		// 按重复多少排序
		// 3带1,3带2,飞机带单/双,4带1,2,只需比较第一个就行，独一无二的
		if (playCardsType == CardType.c31 || playCardsType == CardType.c32 || playCardsType == CardType.c411
				|| playCardsType == CardType.c422 || playCardsType == CardType.c11122234
				|| playCardsType == CardType.c1112223344) {
			List<Integer> a1 = getOrder(playCards); // 我出的牌
			List<Integer> a2 = getOrder(lastCards); // 当前最大牌
			if (cardValue(a1.get(0)) < cardValue(a2.get(0)))
				return 0;

		}
		return 1;
	}

	// 按照重复次数排序
	public static List<Integer> getOrder(List<Integer> list) {
		List<Integer> list2 = new ArrayList<Integer>(list);
		List<Integer> list3 = new ArrayList<Integer>();
		int len = list2.size();
		int a[] = new int[20];
		for (int i = 0; i < 20; i++) {
			a[i] = 0;
		}
		for (int i = 0; i < len; i++) {
			a[cardValue(list2.get(i))]++;
		}
		int max = 0;
		for (int i = 0; i < 20; i++) {
			max = 0;
			for (int j = 19; j >= 0; j--) {
				if (a[j] > a[max])
					max = j;
			}

			for (int k = 0; k < len; k++) {
				if (cardValue(list2.get(k)) == max) {
					list3.add(list2.get(k));
				}
			}
			list2.remove(list3);
			a[max] = 0;
		}
		return list3;
	}

	public static void main(String[] args) {
		// System.out.println(AIUtil.cardValue(53));
		// List<Integer> list = Arrays.asList(13, 14, 40, 16, 29, 42, 17, 43, 5,
		// 20, 8, 22, 35, 10, 12, 51, 52, 31, 41, 28);
		// System.out.println(list);
		// cardsOrder(list);
		// System.out.println(list);

		List<Integer> list = Arrays.asList(0, 13, 26, 1, 14, 27, 2, 15, 28, 3, 4, 5);
		// List<Integer> list = Arrays.asList(7,8,9,10,11);
		System.out.println(jugdeType(list));

	}

}
