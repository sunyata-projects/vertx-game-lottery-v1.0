package com.xt.yde.custom;

/**
 * Created by leo on 17/8/29.
 */
public class PrizeLevelConfigInfo {
    private Integer gameType;
    private Integer prizeRandom;


    public Integer getGameType() {
        return gameType;
    }

    public PrizeLevelConfigInfo setGameType(Integer gameType) {
        this.gameType = gameType;
        return this;
    }

    public Integer getPrizeRandom() {
        return prizeRandom;
    }

    public PrizeLevelConfigInfo setPrizeRandom(Integer prizeRandom) {
        this.prizeRandom = prizeRandom;
        return this;
    }


}
