package com.xt.landlords.ai.core.util.complex;

import java.util.HashMap;
import java.util.Map;

public enum PokerType {

	THREE('3'), 
	FOUR('4'), 
	FIVE('5'), 
	SIX('6'), 
	SEVEN('7'),
	EIGHT('8'), 
	NINE('9'), 
	TEN('T'), 
	JACK('J'), 
	QUEEN('Q'), 
	KING('K'), 
	ACE('A'), 
	TWO('2'), 
	BJOKER('X'), 
	RJOKER('D');

	private final char name;

	private static final Map<Character, PokerType> charToType = new HashMap<Character, PokerType>();
	static {
		for (PokerType type : values()) {
			charToType.put(type.getName(), type);
		}
	}

	public static PokerType fromName(char name) {
		return charToType.get(Character.toUpperCase(name));
	}

	private PokerType(char name) {
		this.name = name;
	}

	public char getName() {
		return name;
	}

}
