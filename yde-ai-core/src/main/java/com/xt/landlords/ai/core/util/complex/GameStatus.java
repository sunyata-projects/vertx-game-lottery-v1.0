package com.xt.landlords.ai.core.util.complex;

import java.util.Arrays;

public class GameStatus {

	private byte[][] playerCards;

	private byte[] outHand;

	private int calledPiont;

	private byte[] underCards;

	private int calledPlayer;

	private int outHandPlayer;

	private int curPlayer;

	private int bombCount;

	/**
	 * @param playerCards
	 *            ��һά����Ϊ3����ʾ3�ҵ����ƣ�0Ϊ������1Ϊ�����¼ң�2Ϊ�����ϼҡ��ڶ�ά����
	 *            Ϊ15��3-K,A,2,С��,�����ֱ��Ӧ0 -14��ÿһλ����ֵ��ʾ�Ƶ�����
	 * @param outHand
	 *            ���һ�ִ�����ơ�
	 * @param outHandPlayer
	 *            ������һ���Ƶ����
	 * @param curPlayer
	 *            ��ǰӦ�ó��Ƶ����
	 * @param bombCount
	 *            �Ѿ������ը����
	 */
	public GameStatus(byte[][] playerCards, byte[] outHand, int outHandPlayer, int curPlayer, int bombCount) {
		this.playerCards = playerCards;
		this.outHand = outHand;
		this.outHandPlayer = outHandPlayer;
		this.curPlayer = curPlayer;
		this.bombCount = bombCount;
		this.underCards = new byte[AIConstant.UNDER_CAED_NUM];
	}

	public GameStatus(byte[][] playerCards, byte[] underCards, byte[] outHand, int outHandPlayer, int curPlayer,
					  int bombCount) {
		this.playerCards = playerCards;
		this.outHand = outHand;
		this.outHandPlayer = outHandPlayer;
		this.curPlayer = curPlayer;
		this.bombCount = bombCount;
		this.underCards = underCards;
	}

	/**
	 * 叫分的構造函數
	 * 
	 * @param playerCards
	 * @param calledPiont
	 *            上次叫分
	 * @param underCards
	 *            底牌
	 * @param calledPlayer
	 *            上一次叫分的人
	 * @param curPlayer
	 */

	public GameStatus(byte[][] playerCards, int calledPiont, byte[] underCards, int calledPlayer, int curPlayer) {
		this.playerCards = playerCards;
		this.curPlayer = curPlayer;
		this.underCards = underCards;
		this.calledPiont = calledPiont;
		this.calledPlayer = calledPlayer;
		this.outHand = new byte[15];
	}

	public GameStatus makeLord(int player) {
		if (this.underCards != null) {
			byte[][] newPlayerCards = new byte[3][];
			newPlayerCards[0] = Arrays.copyOf(playerCards[player], playerCards[player].length);
			newPlayerCards[1] = Arrays.copyOf(playerCards[(player + 1) % 3], playerCards[(player + 1) % 3].length);
			newPlayerCards[2] = Arrays.copyOf(playerCards[(player + 2) % 3], playerCards[(player + 2) % 3].length);
			newPlayerCards[0][this.underCards[0]]++;
			newPlayerCards[0][this.underCards[1]]++;
			newPlayerCards[0][this.underCards[2]]++;
			byte[] underCards = Arrays.copyOf(this.underCards, this.underCards.length);
			return new GameStatus(newPlayerCards, underCards, new byte[15], 0, 0, 0);
		} else {
			return null;
		}
	}

	public void updateWithStatusAdv(GameStatusAdv statusAdv) {
		CardsSummary[] cards = statusAdv.getPlayerCards();
		for (int i = 0; i < AIConstant.PLAYER_NUM; i++) {
			cards[i].copyCards(this.playerCards[i]);
		}
		this.outHand = AIUtils.handToCards(statusAdv.getOutHand());
		this.outHandPlayer = statusAdv.getOutHandPlayer();
		this.curPlayer = statusAdv.getCurPlayer();
		this.bombCount = statusAdv.getBombCount();
	}

	public boolean isFinish() {
		for (int i = 0; i < 15; ++i) {
			if (playerCards[outHandPlayer][i] != 0) {
				return false;
			}
		}
		return true;
	}

	public int maxBombs() {
		int num = 0;
		for (int i = 0; i < 13; i++) {
			if (playerCards[0][i] == 4 || playerCards[1][i] == 4 || playerCards[2][i] == 4) {
				num++;
			}
		}
		if ((playerCards[0][13] == 1 && playerCards[0][14] == 1) || (playerCards[1][13] == 1 && playerCards[1][14] == 1)
				|| (playerCards[2][13] == 1 && playerCards[2][14] == 1)) {
			num++;
		}
		return num;
	}

	public GameStatus makePoint(int point) {
		byte[][] newPlayerCards = new byte[AIConstant.PLAYER_NUM][];
		int newCurPlayer = curPlayer == 2 ? 0 : curPlayer + 1;
		int newCalledPlayer = calledPlayer;
		for (int i = 0; i < AIConstant.PLAYER_NUM; i++) {
			newPlayerCards[i] = Arrays.copyOf(playerCards[i], AIConstant.CARD_TYPE);
		}
		if (point >= calledPiont) {
			newCalledPlayer = this.curPlayer;
			return new GameStatus(newPlayerCards, point, underCards, newCalledPlayer, newCurPlayer);
		} else {
			return new GameStatus(newPlayerCards, calledPiont, underCards, newCalledPlayer, newCurPlayer);
		}

	}

	public GameStatus takeOut(Hand hand) {
		byte[][] newPlayerCards = new byte[AIConstant.PLAYER_NUM][];
		byte[] newOutHand;
		int newOutHandPlayer;
		int newCurPlayer = curPlayer == 2 ? 0 : curPlayer + 1;
		int newBombCount = bombCount;
		if (hand != null) {
			newOutHand = AIUtils.handToCards(hand);
			newOutHandPlayer = curPlayer;
			for (int i = 0; i < AIConstant.PLAYER_NUM; i++) {
				if (i != curPlayer) {
					newPlayerCards[i] = Arrays.copyOf(playerCards[i], AIConstant.CARD_TYPE);
				} else {
					newPlayerCards[i] = AIUtils.subCards(playerCards[i], newOutHand);
				}
			}
			if (AIUtils.isBomb(hand)) {
				newBombCount++;
			}
		} else {
			newOutHand = Arrays.copyOf(outHand, AIConstant.CARD_TYPE);
			newOutHandPlayer = outHandPlayer;
			for (int i = 0; i < AIConstant.PLAYER_NUM; i++) {
				newPlayerCards[i] = Arrays.copyOf(playerCards[i], AIConstant.CARD_TYPE);
			}
		}
		return new GameStatus(newPlayerCards, newOutHand, newOutHandPlayer, newCurPlayer, newBombCount);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + bombCount;
		result = prime * result + curPlayer;
		result = prime * result + Arrays.hashCode(outHand);
		result = prime * result + outHandPlayer;
		result = prime * result + Arrays.hashCode(playerCards);
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
		GameStatus other = (GameStatus) obj;
		if (bombCount != other.bombCount)
			return false;
		if (curPlayer != other.curPlayer)
			return false;
		if (!Arrays.equals(outHand, other.outHand))
			return false;
		if (outHandPlayer != other.outHandPlayer)
			return false;
		if (!Arrays.deepEquals(playerCards, other.playerCards))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("GameStatus [playerCards=");
		for (int i = 0; i < playerCards.length; i++) {
			builder.append(Arrays.toString(playerCards[i]));
		}
		builder.append(", outHand=");
		builder.append(Arrays.toString(outHand));
		builder.append(", outHandPlayer=");
		builder.append(outHandPlayer);
		builder.append(", curPlayer=");
		builder.append(curPlayer);
		builder.append(", bombCount=");
		builder.append(bombCount);
		builder.append("]");
		return builder.toString();
	}

	public String callStatustoString() {
		StringBuilder builder = new StringBuilder();
		builder.append("GameStatus [playerCards=");
		for (int i = 0; i < playerCards.length; i++) {
			for (int j = 0; j < playerCards[i].length; j++) {
				for (int j2 = 0; j2 < playerCards[i][j]; j2++) {
					builder.append(PokerType.values()[j].getName());
				}

			}
			builder.append(",");
		}
		builder.append(", underCards=");
		builder.append(PokerType.values()[underCards[0]].getName() + "," + PokerType.values()[underCards[1]].getName()
				+ "," + PokerType.values()[underCards[2]].getName());
		builder.append(", callPlayer=");
		builder.append(calledPlayer);
		builder.append(", curPlayer=");
		builder.append(curPlayer);
		builder.append(", calledPiont=");
		builder.append(calledPiont);
		builder.append("]");
		return builder.toString();
	}

	public byte[][] getPlayerCards() {
		return playerCards;
	}

	public void setPlayerCards(byte[][] playerCards) {
		this.playerCards = playerCards;
	}

	public byte[] getOutHand() {
		return outHand;
	}

	public void setOutHand(byte[] outHand) {
		this.outHand = outHand;
	}

	public int getOutHandPlayer() {
		return outHandPlayer;
	}

	public void setOutHandPlayer(int outHandPlayer) {
		this.outHandPlayer = outHandPlayer;
	}

	public int getCurPlayer() {
		return curPlayer;
	}

	public void setCurPlayer(int curPlayer) {
		this.curPlayer = curPlayer;
	}

	public int getBombCount() {
		return bombCount;
	}

	public void setBombCount(int bombCount) {
		this.bombCount = bombCount;
	}

	public int getCalledPiont() {
		return calledPiont;
	}

	public byte[] getUnderCards() {
		return underCards;
	}

	public int getCalledPlayer() {
		return calledPlayer;
	}

	public void setCalledPlayer(int calledPlayer) {
		this.calledPlayer = calledPlayer;
	}

	public void setCalledPoint(int calledPiont) {
		this.calledPiont = calledPiont;
	}
}
