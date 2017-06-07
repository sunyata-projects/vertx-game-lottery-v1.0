package com.xt.landlords.ai.util.complex;

/**
 * @author Pan
 *
 */
public class TreeNode {

	public byte[][] playerCards;

	private int curPlayer;

	private int outPlayer;

	private Hand outHand;

	public Hand bestHand;

	private int bombCount;

	public TreeNode LastTreeNode = null;

	public TreeNode(int curPlayer, int outPlayer, Hand outHand, byte[][] playerCards, int bombCount) {
		this.curPlayer = curPlayer;
		this.outPlayer = outPlayer;
		this.outHand = outHand;
		this.playerCards = playerCards.clone();
		this.bombCount = bombCount;
	}

	public int getBombCount() {
		return bombCount;
	}

	public void setBombCount(int bombCount) {
		this.bombCount = bombCount;
	}

	public int getCurPlayer() {
		return curPlayer;
	}

	public void setCurPlayer(int curPlayer) {
		this.curPlayer = curPlayer;
	}

	public int getOutPlyer() {
		return outPlayer;
	}

	public void setOutPlyer(int outPlyer) {
		this.outPlayer = outPlyer;
	}

	public Hand getOutHand() {
		return outHand;
	}

	public void setOutHand(Hand outHand) {
		this.outHand = outHand;
	}

	public boolean comparePlayer() {
		if (outPlayer == curPlayer)
			return true;
		else {
			return false;
		}
	}

	/**
	 * 保存当前节点(将当前节点生成新节点)
	 * 
	 * @return
	 */
	public TreeNode saveNode() {
		byte[][] cards = new byte[playerCards.length][];
		for (int i = 0; i < AIConstant.PLAYER_NUM; i++) {
			cards[i] = playerCards[i].clone();
		}
		TreeNode node = new TreeNode(curPlayer, outPlayer, outHand, cards, bombCount);
		node.LastTreeNode = LastTreeNode;
		return node;
	}
}
