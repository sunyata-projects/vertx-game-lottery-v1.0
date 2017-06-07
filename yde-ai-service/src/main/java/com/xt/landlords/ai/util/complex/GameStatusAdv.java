package com.xt.landlords.ai.util.complex;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class GameStatusAdv {

	private CardsSummary[] playerCards;

	private byte[] underCards;

	public byte[] getUnderCards() {
		return underCards;
	}

	private int calledPiont;

	private int calledPlayer;

	private Hand outHand;

	private int outHandPlayer;

	private int curPlayer;

	private int bombCount;

	private int lordDiscardCount;

	private int lordPassCount;

	private int farmerDiscardCount;

	private int covertValue;

	private StringBuilder path;

	private NewHand.StatusType statusType = NewHand.StatusType.unknow;

	public GameStatusAdv(CardsSummary[] playerCards, Hand outHand, int outHandPlayer, int curPlayer, int bombCount,
						 int lordDiscardCount) {
		this.playerCards = playerCards;
		this.outHand = outHand;
		this.outHandPlayer = outHandPlayer;
		this.curPlayer = curPlayer;
		this.bombCount = bombCount;
		this.lordDiscardCount = lordDiscardCount;
		this.path = new StringBuilder();
	}

	public GameStatusAdv(CardsSummary[] playerCards, Hand outHand, int outHandPlayer, int curPlayer, int bombCount,
						 int lordDiscardCount, int farmerDiscardCount, int lordPassCount, StringBuilder path) {
		this.playerCards = playerCards;
		this.outHand = outHand;
		this.outHandPlayer = outHandPlayer;
		this.curPlayer = curPlayer;
		this.bombCount = bombCount;
		this.lordDiscardCount = lordDiscardCount;
		this.farmerDiscardCount = farmerDiscardCount;
		this.lordPassCount = lordPassCount;
		this.path = path;
	}

	public GameStatusAdv(CardsSummary[] playerCards, Hand outHand, int outHandPlayer, int curPlayer, int covertValue,
						 StringBuilder path, int bombCount) {
		this.playerCards = playerCards;
		this.outHand = outHand;
		this.outHandPlayer = outHandPlayer;
		this.curPlayer = curPlayer;
		this.covertValue = covertValue;
		this.path = path;
		this.bombCount=bombCount;
	}

	public GameStatusAdv(CardsSummary[] playerCards, Hand outHand, int outHandPlayer, int curPlayer, int bombCount) {
		this.playerCards = playerCards;
		this.outHand = outHand;
		this.outHandPlayer = outHandPlayer;
		this.curPlayer = curPlayer;
		this.bombCount = bombCount;
		this.path = new StringBuilder();
	}

	public GameStatusAdv(GameStatus status) {
		this.playerCards = new CardsSummary[AIConstant.PLAYER_NUM];
		byte[][] allCards = status.getPlayerCards();
		for (int i = 0; i < AIConstant.PLAYER_NUM; i++) {
			playerCards[i] = new CardsSummary(allCards[i]);
		}
		this.underCards = Arrays.copyOf(status.getUnderCards(), 3);
		this.outHand = AIUtils.cardsToHand(status.getOutHand());
		this.outHandPlayer = status.getOutHandPlayer();
		this.curPlayer = status.getCurPlayer();
		this.bombCount = status.getBombCount();
		this.lordDiscardCount = 0;
		this.farmerDiscardCount = 0;
		this.lordPassCount = 0;
		this.path = new StringBuilder();
	}

	public GameStatusAdv(GameStatus status, int call) {
		this.playerCards = new CardsSummary[AIConstant.PLAYER_NUM];
		byte[][] allCards = status.getPlayerCards();
		for (int i = 0; i < AIConstant.PLAYER_NUM; i++) {
			playerCards[i] = new CardsSummary(allCards[i]);
		}
		this.underCards = Arrays.copyOf(status.getUnderCards(), 3);
		this.calledPlayer = status.getCalledPlayer();
		this.calledPiont = status.getCalledPiont();
		this.curPlayer = status.getCurPlayer();
		this.path = new StringBuilder();
	}

	public void takeOut(Hand hand) {
		if (hand != null) {
			outHandPlayer = curPlayer;
			outHand = hand;
			byte[] handCards = AIUtils.handToCards(hand);
			playerCards[curPlayer].remove(handCards);
			if (AIUtils.isBomb(hand)) {
				bombCount++;
			}
			if (curPlayer == 0) {
				path.append(hand.toStringNoType() + ",");
				lordDiscardCount++;
			} else {
				farmerDiscardCount++;
			}
		} else {
			if (curPlayer == 0) {
				lordPassCount++;
				path.append("pass,");
			}
		}
		curPlayer = (curPlayer == 2 ? 0 : curPlayer + 1);
	}

	public void setStatusType(NewHand.StatusType type, int bombCount) {
		this.statusType = type;
		//this.bombCount+=bombCount;
	}

	public boolean isFamerWin() {
		return this.statusType == NewHand.StatusType.win;
	}

	public boolean isFamerLose() {
		return this.statusType == NewHand.StatusType.lose;
	}

	public boolean isWin() {
		return this.statusType == NewHand.StatusType.win;
	}

	public boolean isLose() {
		return this.statusType == NewHand.StatusType.lose;
	}

	public GameStatusAdv takeOutWithNewStatus(Hand hand) {
		CardsSummary[] newPlayerCards = new CardsSummary[AIConstant.PLAYER_NUM];
		for (int i = 0; i < AIConstant.PLAYER_NUM; i++) {
			newPlayerCards[i] = new CardsSummary(playerCards[i]);
		}
		Hand newOutHand = outHand;
		int newOutHandPlayer = outHandPlayer;
		int newCurPlayer = (curPlayer == 2 ? 0 : curPlayer + 1);
		int newBombCount = bombCount;
		int newLordDiscardCount = lordDiscardCount;
		if (hand != null) {
			newOutHand = hand;
			newOutHandPlayer = curPlayer;
			byte[] handCards = AIUtils.handToCards(hand);
			newPlayerCards[curPlayer].remove(handCards);
			if (AIUtils.isBomb(hand)) {
				newBombCount++;
			}
			if (curPlayer == 0) {
				newLordDiscardCount++;
			}
		}
		return new GameStatusAdv(newPlayerCards, newOutHand, newOutHandPlayer, newCurPlayer, newBombCount,
				newLordDiscardCount);
	}

	public GameStatusAdv takeOutWithNewStatus2(Hand hand, List<Hand> moveHands) {
		CardsSummary[] newPlayerCards = new CardsSummary[AIConstant.PLAYER_NUM];
		for (int i = 0; i < AIConstant.PLAYER_NUM; i++) {
			newPlayerCards[i] = new CardsSummary(playerCards[i]);
		}
		Hand newOutHand = outHand;
		int newOutHandPlayer = outHandPlayer;
		int newCurPlayer = (curPlayer == 2 ? 0 : curPlayer + 1);
		int newCovertValue = covertValue;
		// int newBombCount = bombCount;
		// int newLordDiscardCount = lordDiscardCount;
		// int newFarmerDiscardCount =farmerDiscardCount;
		// int newLordPassCount= lordPassCount;
		StringBuilder newPath = new StringBuilder(path);
		int newBombCount = bombCount;
		Map<Hand, Integer> analysisHands = PathAnalysis.playingstyle(this);
		int covertCount = 0;
		if (hand != null) {
			newOutHand = hand;
			newOutHandPlayer = curPlayer;
			byte[] handCards = AIUtils.handToCards(hand);
			newPlayerCards[curPlayer].remove(handCards);
			
			if (AIUtils.isBomb(hand)) {
				newBombCount++;
			}
			if (outHandPlayer == curPlayer) {// ���
				if (!analysisHands.containsKey(hand)) {// �����в���ĸô򷨣����ʾ�ô�Ϊ���δ򷨣�
					if (outHand == null) {// ��ʾΪ��һ�γ���
						covertCount += 4;
					} else {
						covertCount += 2;
					}
				} else {
					if (outHand == null && hand.getKey().ordinal() > PokerType.JACK.ordinal()) {
						covertCount += 2;
					}
				}
			} else {// ����
				if (hand.getType() != HandType.TRIO_PAIR && hand.getType() != HandType.TRIO_SOLO
						&& hand.getType() != HandType.TRIO) {
					if (!analysisHands.containsKey(hand)) {// ��������
						covertCount += 2;
					}
				}
			}
			newPath.append(hand.toStringNoType() + ",");
		} else {
			newPath.append("pass,");
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
				// ������
				if (outHand.getType() != HandType.TRIO_PAIR && outHand.getType() != HandType.TRIO_SOLO
						&& outHand.getType() != HandType.TRIO) {
					Set<Entry<Hand, Integer>> entrtSet = analysisHands.entrySet();
					int canGoCount = 0;
					for (Entry<Hand, Integer> entry : entrtSet) {
						if (entry.getKey().getType() == outHand.getType()
								&& entry.getKey().getKey().ordinal() > outHand.getKey().ordinal()) {
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
		}
		newCovertValue += covertCount;
		return new GameStatusAdv(newPlayerCards, newOutHand, newOutHandPlayer, newCurPlayer, newCovertValue, newPath, newBombCount);
	}

	public boolean isFinish() {
		boolean finish = playerCards[outHandPlayer].getNum() == 0;
		return finish;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("GameStatusAdv [playCards=");
		builder.append(Arrays.toString(playerCards));
//		builder.append(",underCards=" + PokerType.values()[underCards[0]].getName() + ","
//				+ PokerType.values()[underCards[1]].getName() + "," + PokerType.values()[underCards[2]].getName());
		builder.append(", outHand=");
		builder.append(outHand);
		builder.append(", outHandPlayer=");
		builder.append(outHandPlayer);
		builder.append(", curPlayer=");
		builder.append(curPlayer);
		builder.append(", bombCount=");
		builder.append(bombCount);
		builder.append(", statusType=");
		builder.append(statusType);
		builder.append("]");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + bombCount;
		result = prime * result + curPlayer;
		result = prime * result + ((outHand == null) ? 0 : outHand.hashCode());
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
		GameStatusAdv other = (GameStatusAdv) obj;
		if (bombCount != other.bombCount)
			return false;
		if (curPlayer != other.curPlayer)
			return false;
		if (outHand == null) {
			if (other.outHand != null)
				return false;
		} else if (!outHand.equals(other.outHand))
			return false;
		if (outHandPlayer != other.outHandPlayer)
			return false;
		if (!Arrays.equals(playerCards, other.playerCards))
			return false;
		return true;
	}

	public CardsSummary[] getPlayerCards() {
		return playerCards;
	}

	public Hand getOutHand() {
		return outHand;
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

	public int getBombCount() {
		return bombCount;
	}

	public int getLordDiscardCount() {
		return lordDiscardCount;
	}

	public int getFarmerDiscardCount() {
		return farmerDiscardCount;
	}

	public int getLordPassCount() {
		return lordPassCount;
	}

	public String getPath() {
		return path.toString();
	}

	public int getCovertValue() {
		return covertValue;
	}
}
