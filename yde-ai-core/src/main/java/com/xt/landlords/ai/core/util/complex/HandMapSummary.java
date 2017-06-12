package com.xt.landlords.ai.core.util.complex;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HandMapSummary {

	private Map<HandType, List<Hand>> handMap;

	private int realHands;

	private int extraBomb;

	private int multiControlsHands;

	private int handsType;

	private int solos;

	private int smallPairs;

	private PokerType lowestSolo;

	private PokerType sencondLowestSolo;

	private List<Hand> highestHandList;

	private List<Hand> unHighestHandList;

	private double soloConNum;

	private double effectiveHands;

	public HandMapSummary(Map<HandType, List<Hand>> handMap) {
		this.handMap = handMap;
		this.realHands = 0;
		this.extraBomb = 0;
		this.multiControlsHands = 0;
		this.effectiveHands = 0;
		this.handsType = 0;
		this.solos = 0;
		this.smallPairs = 0;
		this.lowestSolo = PokerType.RJOKER;
		this.sencondLowestSolo = PokerType.RJOKER;
		this.soloConNum = 0;
		this.highestHandList = new ArrayList<Hand>();
		this.unHighestHandList = new ArrayList<Hand>();
	}

	public void clear() {
		this.realHands = 0;
		this.extraBomb = 0;
		this.multiControlsHands = 0;
		this.effectiveHands = 0;
		this.handsType = 0;
		this.solos = 0;
		this.smallPairs = 0;
		this.lowestSolo = PokerType.RJOKER;
		this.sencondLowestSolo = PokerType.RJOKER;
		this.soloConNum = 0;
		this.highestHandList.clear();
		this.unHighestHandList.clear();
	}

	public void addHighestHand(Hand hand) {
		highestHandList.add(hand);
	}

	public void addUnHighestHand(Hand hand) {
		unHighestHandList.add(hand);
	}

	public void changeEffectiveHands(double effectiveHands) {
		this.effectiveHands += effectiveHands;
	}

	public void changeExtraBomb(int extraBomb) {
		this.extraBomb += extraBomb;
	}

	public void changeHandsType(int handsType) {
		this.handsType += handsType;
	}

	public void changeMultiControlsHands(int multiControlsHands) {
		this.multiControlsHands += multiControlsHands;
	}

	public void changeRealHands(int realHands) {
		this.realHands += realHands;
	}

	public void changeSolos(int solos) {
		this.solos += solos;
	}

	public void changeSmallPairs(int smallPairs) {
		this.smallPairs += smallPairs;
	}

	public int getSmallPairs() {
		return smallPairs;
	}

	// public void changeHighestHands(int highestHands) {
	// this.highestHands += highestHands;
	// }
	//
	// public void changeUnHighestHands(int unHighestHands) {
	// this.unHighestHands += unHighestHands;
	// }

	public Map<HandType, List<Hand>> getHandMap() {
		return handMap;
	}

	// public int getHighestHands() {
	// return highestHands;
	// }
	//
	// public int getUnHighestHands() {
	// return unHighestHands;
	// }

	public List<Hand> getHighestHandList() {
		return highestHandList;
	}

	public List<Hand> getUnHighestHandList() {
		return unHighestHandList;
	}

	public int getRealHands() {
		return realHands;
	}

	public int getExtraBomb() {
		return extraBomb;
	}

	public int getMultiControlsHands() {
		return multiControlsHands;
	}

	public double getEffectiveHands() {
		return effectiveHands;
	}

	public void setEffectiveHands(double effectiveHands) {
		this.effectiveHands = effectiveHands;
	}

	public int getHandsType() {
		return handsType;
	}

	public int getSolos() {
		return solos;
	}

	public PokerType getLowestSolo() {
		return lowestSolo;
	}

	public void setLowestSolo(PokerType lowestSolo) {
		this.lowestSolo = lowestSolo;
	}

	public PokerType getSencondLowestSolo() {
		return sencondLowestSolo;
	}

	public void setSencondLowestSolo(PokerType sencondLowestSolo) {
		this.sencondLowestSolo = sencondLowestSolo;
	}

	public double getSoloConNum() {
		return soloConNum;
	}

	public void setSoloConNum(double soloConNum) {
		this.soloConNum = soloConNum;
	}
}
