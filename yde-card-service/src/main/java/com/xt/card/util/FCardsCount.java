package com.xt.card.util;

public class FCardsCount {
	private int win0;
	private int win1;
	private int win2;
	private int win3;
	private int win4;
	private int win5;
	private int win6;
	private int win7;
	private int win8;
	
	public FCardsCount(){
		
	}
	
	public int get(String type){
		if(type.equals("win0")){
			return win0;
		}
		else if(type.equals("win1")){
			return win1;
		}
		else if(type.equals("win2")){
			return win2;
		}
		else if(type.equals("win3")){
			return win3;
		}
		else if(type.equals("win4")){
			return win4;
		}
		else if(type.equals("win5")){
			return win5;
		}
		else if(type.equals("win6")){
			return win6;
		}
		else if(type.equals("win7")){
			return win7;
		}
		else if(type.equals("win8")){
			return win8;
		}
		return -1;
	}

	public int getWin0() {
		return win0;
	}

	public void setWin0(int win0) {
		this.win0 = win0;
	}

	public int getWin1() {
		return win1;
	}

	public void setWin1(int win1) {
		this.win1 = win1;
	}

	public int getWin2() {
		return win2;
	}

	public void setWin2(int win2) {
		this.win2 = win2;
	}

	public int getWin3() {
		return win3;
	}

	public void setWin3(int win3) {
		this.win3 = win3;
	}

	public int getWin4() {
		return win4;
	}

	public void setWin4(int win4) {
		this.win4 = win4;
	}

	public int getWin5() {
		return win5;
	}

	public void setWin5(int win5) {
		this.win5 = win5;
	}

	public int getWin6() {
		return win6;
	}

	public void setWin6(int win6) {
		this.win6 = win6;
	}

	public int getWin7() {
		return win7;
	}

	public void setWin7(int win7) {
		this.win7 = win7;
	}

	public int getWin8() {
		return win8;
	}

	public void setWin8(int win8) {
		this.win8 = win8;
	}

	@Override
	public String toString() {
		return "FCardsCount [win0=" + win0 + ", win1=" + win1 + ", win2="
				+ win2 + ", win3=" + win3 + ", win4=" + win4 + ", win5=" + win5
				+ ", win6=" + win6 + ", win7=" + win7 + ", win8=" + win8 + "]";
	}

	
}
