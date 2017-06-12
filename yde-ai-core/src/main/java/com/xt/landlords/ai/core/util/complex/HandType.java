package com.xt.landlords.ai.core.util.complex;

public enum HandType {

    SOLO(1, 0, false),
    PAIR(2, 0, false),
    TRIO(3, 0, false),
    TRIO_SOLO(3, 1, false),
    TRIO_PAIR(3, 2, false),
    BOMB(4, 0, false),
    NUKE(4, 0, false), //set width = 4 for check bomb
    FOUR_DUAL_SOLO(4, 1, false),
    FOUR_DUAL_PAIR(4, 2, false),
    SOLO_CHAIN(1, 0 , true), 
    PAIR_CHAIN(2, 0, true),
    TRIO_CHAIN(3, 0, true),
    TRIO_CHAIN_SOLO(3, 1, true),
    TRIO_CHAIN_PAIR(3, 2, true);

	private final int width;

	private final int kickerWidth;

	private final boolean chain;

	private HandType(int width, int kickerWidth, boolean chain) {
		this.width = width;
		this.kickerWidth = kickerWidth;
		this.chain = chain;
	}

	public int getWidth() {
		return width;
	}

	public int getKickerWidth() {
		return kickerWidth;
	}

	public boolean isChain() {
		return chain;
	}
	
	

}
