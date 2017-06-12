package com.xt.landlords.ai.core.util.complex;


public class NewHand {
	public Hand hand;
	public StatusType returnType;
	private int bombCount;
	public NewHand(Boolean isWin, Hand hand)
	{
		returnType=isWin? StatusType.win: StatusType.lose;
		this.hand=hand;
	}
	public void setBombCount(int count)
	{
		this.bombCount=count;
	}
	public int getBombCount() {
		return this.bombCount;
	}
	public enum StatusType {
	win,
	lose,
	unknow
};
}
