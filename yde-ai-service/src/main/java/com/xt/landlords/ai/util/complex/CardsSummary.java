package com.xt.landlords.ai.util.complex;

import java.util.Arrays;

public class CardsSummary {

	private byte[] cards;

	private int num;

	private PokerType lowest;

	private PokerType highest;

	public CardsSummary(byte[] cards) {
		this.cards = cards;
		this.lowest = null;
		this.num = 0;
		for (int i = 0; i < cards.length; i++) {
			if (cards[i] > 0) {
				num += cards[i];
				highest = PokerType.values()[i];
				if (lowest == null) {
					lowest = PokerType.values()[i];
				}
			}
		}
	}

	public CardsSummary(CardsSummary cardsSummary) {
		this.cards = Arrays.copyOf(cardsSummary.cards, AIConstant.CARD_TYPE);
		this.num = cardsSummary.num;
		this.lowest = cardsSummary.lowest;
		this.highest = cardsSummary.highest;
	}

	public byte get(int index) {
		return cards[index];
	}

	public byte get(PokerType poker) {
		return cards[poker.ordinal()];
	}

	public void remove(byte[] removeCards) {
		this.lowest = null;
		this.num = 0;
		for (int i = 0; i < cards.length; i++) {
			cards[i] -= removeCards[i];
			if (cards[i] > 0) {
				num += cards[i];
				highest = PokerType.values()[i];
				if (lowest == null) {
					lowest = PokerType.values()[i];
				}
			}
		}
	}

	public void copyCards(byte[] cardsDest) {
		System.arraycopy(this.cards, 0, cardsDest, 0, AIConstant.CARD_TYPE);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < cards.length; i++) {
			for (int j = 0; j < cards[i]; j++) {
				builder.append(PokerType.values()[i].getName());
			}
		}
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(cards);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CardsSummary other = (CardsSummary) obj;
		if (!Arrays.equals(cards, other.cards))
			return false;
		return true;
	}

	public byte[] getCards() {
		return Arrays.copyOf(cards, cards.length);
	}

	public int getNum() {
		return num;
	}

	public PokerType getLowest() {
		return lowest;
	}

	public PokerType getHighest() {
		return highest;
	}
}
