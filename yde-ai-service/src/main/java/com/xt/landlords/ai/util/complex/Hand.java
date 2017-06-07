package com.xt.landlords.ai.util.complex;

import java.util.Comparator;
import java.util.Set;

public class Hand {

	private HandType type;

	private PokerType key;

	private int len;

	private Set<PokerType> kickers;

	public static final Hand NUKE_HAND = new Hand(HandType.NUKE, PokerType.BJOKER, 1, null);

	// private static EnumMap<HandType, EnumMap<PokerType, Hand>> simpleHandMap
	// = new EnumMap<HandType, EnumMap<PokerType, Hand>>(
	// HandType.class);
	//
	// static {
	// simpleHandMap.put(HandType.SOLO, new EnumMap<PokerType,
	// Hand>(PokerType.class));
	// simpleHandMap.put(HandType.PAIR, new EnumMap<PokerType,
	// Hand>(PokerType.class));
	// simpleHandMap.put(HandType.TRIO, new EnumMap<PokerType,
	// Hand>(PokerType.class));
	// simpleHandMap.put(HandType.BOMB, new EnumMap<PokerType,
	// Hand>(PokerType.class));
	// }

	public Hand(HandType type, PokerType key, int len, Set<PokerType> kickers) {
		this.type = type;
		this.key = key;
		this.len = len;
		this.kickers = kickers;
	}

	public Hand(HandType type, int keyOrdinal, int len, Set<PokerType> kickers) {
		this.type = type;
		this.key = PokerType.values()[keyOrdinal];
		this.len = len;
		this.kickers = kickers;
	}

	public int size() {
		int s = type.getWidth() * len;
		if (kickers != null) {
			s += kickers.size() * type.getKickerWidth();
		}
		return s;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(type);
		builder.append("[:");
		if (type == HandType.NUKE) {
			builder.append("XD");
		} else {
			if (type.isChain()) {
				for (int i = 0; i < len; i++) {
					for (int j = 0; j < type.getWidth(); j++) {
						builder.append(PokerType.values()[key.ordinal() + i].getName());
					}
				}
			} else {
				for (int j = 0; j < type.getWidth(); j++) {
					builder.append(key.getName());
				}
			}
			if (type.getKickerWidth() > 0) {
				for (PokerType kicker : kickers) {
					for (int i = 0; i < type.getKickerWidth(); i++) {
						builder.append(kicker.getName());
					}
				}
			}
		}
		builder.append(']');
		return builder.toString();
	}
	public String toStringNoType() {
		StringBuilder builder = new StringBuilder();
		if (type == HandType.NUKE) {
			builder.append("XD");
		} else {
			if (type.isChain()) {
				for (int i = 0; i < len; i++) {
					for (int j = 0; j < type.getWidth(); j++) {
						builder.append(PokerType.values()[key.ordinal() + i].getName());
					}
				}
			} else {
				for (int j = 0; j < type.getWidth(); j++) {
					builder.append(key.getName());
				}
			}
			if (type.getKickerWidth() > 0) {
				for (PokerType kicker : kickers) {
					for (int i = 0; i < type.getKickerWidth(); i++) {
						builder.append(kicker.getName());
					}
				}
			}
		}
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		result = prime * result + ((kickers == null) ? 0 : kickers.hashCode());
		result = prime * result + len;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		Hand other = (Hand) obj;
		if (key != other.key)
			return false;
		if (kickers == null) {
			if (other.kickers != null)
				return false;
		} else if (!kickers.equals(other.kickers))
			return false;
		if (len != other.len)
			return false;
		if (type != other.type)
			return false;
		return true;
	}

	public static class HandComparator implements Comparator<Hand> {
		@Override
		public int compare(Hand o1, Hand o2) {
			if (o1.getKey() != o2.getKey()) {
				return o1.getKey().ordinal() - o2.getKey().ordinal();
			}
			return o2.size() - o1.size();
		}
	}

	public static boolean isHigherThan(Hand h0, Hand h1) {
		if (h0.getType() == HandType.NUKE) {
			return true;
		}
		if (h0.getType() == HandType.BOMB) {
			if (h1.getType() == HandType.NUKE) {
				return false;
			} else if (h1.getType() == HandType.BOMB) {
				return h0.getKey().ordinal() > h1.getKey().ordinal();
			} else {
				return true;
			}
		}
		if (h0.getType() == h1.getType()) {
			return h0.getKey().ordinal() > h1.getKey().ordinal() && h0.getLen() == h1.getLen();
		}
		return false;
	}

	public HandType getType() {
		return type;
	}

	public PokerType getKey() {
		return key;
	}

	public int getLen() {
		return len;
	}

	public Set<PokerType> getKickers() {
		return kickers;
	}

	public void setKickers(Set<PokerType> kickers) {
		this.kickers = kickers;
	}

}
